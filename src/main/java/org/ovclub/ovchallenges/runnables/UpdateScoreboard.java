package org.ovclub.ovchallenges.runnables;

import java.util.*;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Challenge;

import net.md_5.bungee.api.ChatColor;
import org.ovclub.ovchallenges.util.EventUtility;

public class UpdateScoreboard extends BukkitRunnable {

	private final Plugin plugin;

	public UpdateScoreboard(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {

		Challenge challenge = plugin.getData().getWinningChallenge();
//		HashMap<Player, Integer> topScores = new HashMap<>();
//
//		for(Player p : plugin.getData().getParticipants()) {
//			if(p != null) {
//				topScores.put(p, challenge.);
//			}
//		}
		
		//get greatest to least 
		Map<UUID, Integer> topScores = EventUtility.sortByValue(challenge.getScores());
		
		for(Player p : plugin.getData().getParticipants()){
			if(p != null) {
				Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
				TextComponent title = Component.text(challenge.getName()).color(challenge.getColor()).decorate(TextDecoration.BOLD);
				Objective obj = board.registerNewObjective("OVChallenges", "dummy", title);

				obj.setDisplaySlot(DisplaySlot.SIDEBAR);

				obj.getScore(ChatColor.RESET.toString()).setScore(12);
				obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Scores:").setScore(11);;

				int counter = 10;
				for(Map.Entry<UUID, Integer> entry : topScores.entrySet()) {
					if(counter >= 8) {
						obj.getScore(entry.getKey() + ": " + ChatColor.YELLOW + entry.getValue()).setScore(counter);
						counter--;
					}
					if(topScores.size() < 3) {
						obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(8);
						obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Time Left:").setScore(7);
						obj.getScore(challenge.getCountdownLabel()).setScore(6);
						obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(5);
						obj.getScore(ChatColor.GRAY + "Required Score: " + ChatColor.GOLD + challenge.getRequiredScore()).setScore(4);
						obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(3);
						obj.getScore(ChatColor.GREEN + "play.thefruitbox.net").setScore(2);
					} else {
						obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(7);
						obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Time Left:").setScore(6);
						obj.getScore(challenge.getCountdownLabel()).setScore(5);
						obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(4);
						obj.getScore(ChatColor.GRAY + "Required Score: " + ChatColor.GOLD + challenge.getRequiredScore()).setScore(3);
						obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(2);
						obj.getScore(ChatColor.GREEN + "play.thefruitbox.net").setScore(1);
					}
				}
				p.setScoreboard(board);
			}
		}
	}
	
	public void setCountdown(Challenge challenge) {
		CountdownTimerLong timer = new CountdownTimerLong(plugin,
		        challenge.getDuration(), 0, (t) -> challenge.setCountdownLabel(t.getMinute() + ":" + (t.getSecondsLeft())));
		timer.scheduleTimer();
	}
}
