package org.ovclub.ovchallenges.runnables;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.util.EventUtility;

import net.md_5.bungee.api.ChatColor;

public class EndEvent extends BukkitRunnable{

	private final Plugin plugin;

	public EndEvent(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {

		Challenge challenge = plugin.getData().getWinningChallenge();
		Map<UUID, Integer> topScores = EventUtility.sortByValue(challenge.getScores());
		
		Bukkit.broadcastMessage(ChatColor.GRAY + "----- " + ChatColor.LIGHT_PURPLE + challenge.getName() + " Results!" + ChatColor.GRAY + " -----");
		
		int counter = 1;
		int sponges = 0;
		for(Map.Entry<UUID, Integer> entry : topScores.entrySet()) {
			
			Player p = Bukkit.getPlayer(entry.getKey());
			
			if(p != null) {
				if(counter < 4) {
					Bukkit.broadcastMessage(ChatColor.GOLD + String.valueOf(counter) + ". " + ChatColor.YELLOW + entry.getKey() + ChatColor.GOLD + " - " + ChatColor.GRAY + entry.getValue());

					if(counter == 1) {
						challenge.addScore(p, 1);
						sponges = challenge.getPlacements.get(1);
					} else if(counter == 2) {
						sponges = challenge.getPlacements.get(2);
					} else if(counter == 3) {
						sponges = challenge.getPlacements.get(3);
					}
					counter++;
				}
			}
		}
		
		counter = 1;
		for(Map.Entry<UUID, Integer> entry : topScores.entrySet()) {
			Player p = Bukkit.getServer().getPlayer(entry.getKey());
			if(counter == 1) {
				sponges = challenge.getPlacements.get(1);
			} else if(counter == 2) {
				sponges = challenge.getPlacements.get(2);
			} else if(counter == 3) {
				sponges = challenge.getPlacements.get(3);
			}
			
			if(p != null) {
				int requiredScore = challenge.getRequiredScore();
				if(entry.getValue() >= requiredScore) {
					p.sendMessage(ConfigManager.EARNED_SPONGE
							.replaceText(builder -> builder.matchLiteral("{amount}").replacement(String.valueOf(requiredScore))));
					plugin.getEconomy().depositPlayer(p, sponges);
//
//						String playerUUIDString = Bukkit.getPlayer(entry.getKey()).getUniqueId().toString();
//						ConfigurationSection playerDataConfig = pluginClass.getPlayerData();
//						ConfigurationSection playerUUID = playerDataConfig.getConfigurationSection(playerUUIDString);
//						ConfigurationSection statsSection = playerUUID.getConfigurationSection("stats");
//						ConfigurationSection highScoreSection = statsSection.getConfigurationSection("high-scores");
//						if(highScoreSection == null){
//							statsSection.createSection("high-scores").set(dailyEvents.winningEvent, entry.getValue());
//							p.sendMessage(ChatColor.GREEN + String.valueOf(ChatColor.BOLD) + "You now have a high score of " + entry.getValue() + " for " + dailyEvents.winningEvent);
//						} else if(highScoreSection.getInt(dailyEvents.winningEvent) < entry.getValue()){
//							highScoreSection.set(dailyEvents.winningEvent, entry.getValue());
//							p.sendMessage(ChatColor.GREEN + String.valueOf(ChatColor.BOLD) + "You now have a high score of " + entry.getValue() + " for " + dailyEvents.winningEvent);
//						}
//
//						pluginClass.savePlayerDataFile();

					counter++;
				} else {
					p.sendMessage(ConfigManager.DID_NOT_MEET_SCORE
							.replaceText(builder -> builder.matchLiteral("{amount}").replacement(String.valueOf(requiredScore))));
				}
			}
		}
		
		try {
			//unregister challenge
			challenge.unregisterEvents();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//send a new task in 20 minutes 
		SendDailyEventVote sendDailyEventVote = new SendDailyEventVote(plugin);
		sendDailyEventVote.runTaskLater(plugin, 24000);
		
		//remove scoreboard
		removeScoreboard();
	
		//clearing previous votes and removing the scoreboard from players
		plugin.getData().clearParticipants();
		EventUtility.clearVotes(plugin.getData().getChallenges());
		}
	
	public void removeScoreboard() {
		for(Player p : plugin.getData().getParticipants()){
			if(p != null) {
				p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			}
		}
	}
}
