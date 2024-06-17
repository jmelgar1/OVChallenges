package org.ovclub.ovchallenges.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.commands.ovevote;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.util.EventUtility;

import net.md_5.bungee.api.ChatColor;

public class SendVoteFinished extends BukkitRunnable implements Listener{

	//Create new EventUtility object (to get list of events)
	public EventUtility dev1 = new EventUtility();
	
	ovevote ov1 = new ovevote();

	private final Plugin plugin;

	public SendVoteFinished(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {	
		
		ov1.clearAllInventories();

		//will only run if 2 or more players are online
		if(Bukkit.getServer().getOnlinePlayers().size() >= 2) {

			//get winning event
			Challenge winningChallenge = EventUtility.determineEvent(plugin.getData().getEvents());
			plugin.getData().setWinningEvent(winningChallenge);
			//set the event here some how
//
//			//create section with winning event name
//			ConfigurationSection currentEventSection = eventData.createSection("current-event");
//			ConfigurationSection winningEventSection = currentEventSection.createSection(winningChallenge);
//
//			pluginClass.getEventData().set("eventid", 0);

			if(winningChallenge != null) {

				StartEventCountdown3Min threeMin = new StartEventCountdown3Min(plugin);
				threeMin.runTaskTimer(plugin, 0, 20);

				for(Player p : EventUtility.getNonParticipatingPlayers(plugin.getData().getParticipants())) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FROG_TONGUE, 500F, 0.8F);
					p.sendMessage(ChatColor.RED + "You did not vote for an event "
							+ "and will not be participating!");
				}

				//send to players who joined
				for(Player p : plugin.getData().getParticipants()) {
					if(p != null) {
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FROG_TONGUE, 500F, 0.8F);
						p.sendMessage(ChatColor.GREEN + "The voting time has expired and "
								+ "the event will start in 2 minutes!");

						threeMin.addPlayer(p);
					}
				}

				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					p.sendMessage(ChatColor.LIGHT_PURPLE + winningChallenge.getName()
								+ " has won the vote!");
				}
//
//				//save files
//				pluginClass.saveSmallEventsFile();
//				pluginClass.saveEventDataFile();
//				pluginClass.reloadEventDataFile();

				Send30SecondReminder secondReminder = new Send30SecondReminder(plugin);
				secondReminder.runTaskLater(plugin, 3000);

			} else {
				//if no one voted. try again in 20 minutes
				Bukkit.broadcastMessage(ChatColor.RED + "Not enough players voted for an event! Trying again in 10 minutes!");
				SendDailyEventVote sendDailyEventVote = new SendDailyEventVote(plugin);
				sendDailyEventVote.runTaskLater(plugin, 24000);
			}
		}
	}
}
