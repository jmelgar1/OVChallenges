package org.ovclub.ovchallenges.runnables;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.util.EventUtility;

public class SendVoteFinished extends BukkitRunnable {

	private final Plugin plugin;

	public SendVoteFinished(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		plugin.getData().clearAllInventories();

		if(plugin.getData().getParticipants().isEmpty()) {
			Bukkit.broadcast(ConfigManager.NOT_ENOUGH_PLAYERS);
			SendDailyEventVote sendDailyEventVote = new SendDailyEventVote(plugin);
			sendDailyEventVote.runTaskLater(plugin, 12000);
			this.cancel();
			return;
		}

		Challenge winningChallenge = EventUtility.determineEvent(plugin.getData().getChallenges());
		plugin.getData().setWinningChallenge(winningChallenge);

		StartEventCountdown3Min threeMin = new StartEventCountdown3Min(plugin);
		threeMin.runTaskTimer(plugin, 0, 20);

		for(Player p : Bukkit.getOnlinePlayers()) {
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FROG_TONGUE, 500F, 0.8F);
			p.sendMessage(ConfigManager.CHALLENGE_WON_VOTE
					.replaceText(builder -> builder.matchLiteral("{challenge}")
					.replacement(Component.text(winningChallenge.getName()).color(winningChallenge.getColor()))));

			if(!plugin.getData().getParticipants().contains(p.getUniqueId())) {
				p.sendMessage(ConfigManager.DID_NOT_VOTE);
			} else {
				p.sendMessage(ConfigManager.TIME_EXPIRED);
			}
		}

		winningChallenge.setBossBarVisibility(true);

		//TODO: ENABLE THIS
//		Send30SecondReminder secondReminder = new Send30SecondReminder(plugin);
//		secondReminder.runTaskLater(plugin, 3000);

		plugin.getData().disableVotingPeriod();

//
//			//create section with winning event name
//			ConfigurationSection currentEventSection = eventData.createSection("current-event");
//			ConfigurationSection winningEventSection = currentEventSection.createSection(winningChallenge);
	}
}
