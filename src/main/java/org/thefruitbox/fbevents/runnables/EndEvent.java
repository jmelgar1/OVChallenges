package org.thefruitbox.fbevents.runnables;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.thefruitbox.fbevents.Main;
import org.thefruitbox.fbevents.managers.DetermineEventData;
import org.thefruitbox.fbevents.smalleventmanager.DailyEvents;

import net.md_5.bungee.api.ChatColor;

public class EndEvent extends BukkitRunnable{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	//start event instance
	private StartEvent startEventClass = StartEvent.getInstance();
	
	DailyEvents dailyEvents = new DailyEvents();
	
	ConfigurationSection eventConfig = mainClass.getSmallEvents();
	ConfigurationSection event = eventConfig.getConfigurationSection(dailyEvents.winningEvent);
	ConfigurationSection placement = event.getConfigurationSection("placements");
	int first = placement.getInt("first");
	int second = placement.getInt("second");
	int third = placement.getInt("third");

	@Override
	public void run() {
		
		HashMap<String, Integer> topScores = new HashMap<String, Integer>();
		
		for(String player : mainClass.getEventData().getStringList("participants")) {
			topScores.put(player, dailyEvents.winningEventSection.getInt(player));
		}
		
		//get greatest to least 
		Map<String, Integer> topScores1 = UpdateScoreboard.sortByValue(topScores);
		
		Bukkit.broadcastMessage(ChatColor.GRAY + "----- " + ChatColor.LIGHT_PURPLE + dailyEvents.winningEvent + " Results!" + ChatColor.GRAY + " -----");
		
		int counter = 1;
		int sponges = 0;
		for(Map.Entry<String, Integer> entry : topScores1.entrySet()) {
			
			Player p = Bukkit.getPlayer(entry.getKey());
			
			if(p != null) {
				String playerUUIDString = Bukkit.getPlayer(entry.getKey()).getUniqueId().toString();
				
					if(counter < 4) {
		
						Bukkit.broadcastMessage(ChatColor.GOLD + String.valueOf(counter) + ". " + ChatColor.YELLOW + entry.getKey() + ChatColor.GOLD + " - " + ChatColor.GRAY + entry.getValue());
						
						ConfigurationSection playerDataConfig = mainClass.getPlayerData();
						ConfigurationSection playerUUID = playerDataConfig.getConfigurationSection(playerUUIDString);
						ConfigurationSection statsSection = playerUUID.getConfigurationSection("stats");
						ConfigurationSection smallEventsSection = statsSection.getConfigurationSection("small-events");
						
						if(counter == 1) {
							int score = smallEventsSection.getInt(dailyEvents.winningEvent);
							score += 1;
							smallEventsSection.set(dailyEvents.winningEvent, score);
							sponges = first;
						} else if(counter == 2) {
							sponges = second;
						} else if(counter == 3) {
							sponges = third;
						}
						
						counter++;

				}
				mainClass.savePlayerDataFile();
			}
		}
		
		counter = 1;
		for(Map.Entry<String, Integer> entry : topScores1.entrySet()) {
			Player p = Bukkit.getServer().getPlayer(entry.getKey());
			if(counter == 1) {
				sponges = first;
			} else if(counter == 2) {
				sponges = second;
			} else if(counter == 3) {
				sponges = third;
			}
			
			if(p != null) {
				ConfigurationSection winningSection = mainClass.getSmallEvents().getConfigurationSection(dailyEvents.winningEvent);
				int requiredScore = winningSection.getInt("requiredscore");
				if(entry.getValue() > requiredScore) {
						p.sendMessage(ChatColor.GOLD + "You earned " + mainClass.spongeColor + sponges + " sponges " + ChatColor.GOLD + "from the event! " + ChatColor.GOLD +
								"They have been deposited into your bank! " + ChatColor.YELLOW + "/bank");
						mainClass.econ.depositPlayer(p, sponges);

						String playerUUIDString = Bukkit.getPlayer(entry.getKey()).getUniqueId().toString();
						ConfigurationSection playerDataConfig = mainClass.getPlayerData();
						ConfigurationSection playerUUID = playerDataConfig.getConfigurationSection(playerUUIDString);
						ConfigurationSection statsSection = playerUUID.getConfigurationSection("stats");
						ConfigurationSection highScoreSection = statsSection.getConfigurationSection("high-scores");
						if(highScoreSection == null){
							statsSection.createSection("high-scores").set(dailyEvents.winningEvent, entry.getValue());
							p.sendMessage(ChatColor.GREEN + String.valueOf(ChatColor.BOLD) + "You now have a high score of " + entry.getValue() + " for " + dailyEvents.winningEvent);
						} else if(highScoreSection.getInt(dailyEvents.winningEvent) < entry.getValue()){
							highScoreSection.set(dailyEvents.winningEvent, entry.getValue());
							p.sendMessage(ChatColor.GREEN + String.valueOf(ChatColor.BOLD) + "You now have a high score of " + entry.getValue() + " for " + dailyEvents.winningEvent);
						}

						mainClass.savePlayerDataFile();

						counter++;
				} else {
					Bukkit.getPlayer(entry.getKey()).sendMessage(ChatColor.RED + "You did not meet the required score of "
							+ requiredScore + " and did not receive any sponges!");
				}
			}
		}
		
		try {
			//unregister event
			startEventClass.unregisterEvent(startEventClass.getEvent());
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//send a new task in 20 minutes 
		SendDailyEventVote sendDailyEventVote = new SendDailyEventVote();
		sendDailyEventVote.runTaskLater(mainClass, 24000);
		
		//remove scoreboard
		removeScoreboard();
	
		//clearing previous votes and removing the scoreboard from players
		dev1.clearParticipationList(mainClass);
		dev1.clearVotes(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
		
		mainClass.saveEventDataFile();
		}
	
	public void removeScoreboard() {
		for(String s : mainClass.getEventData().getStringList("participants")){
			Player p = Bukkit.getPlayer(s);
			
			if(p != null) {
				p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			}
		}
	}
}
