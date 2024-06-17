package org.ovclub.ovchallenges.runnables;

import java.util.*;
import java.util.stream.Collectors;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Event;
import org.ovclub.ovchallenges.smalleventmanager.DailyEvents;

import net.md_5.bungee.api.ChatColor;

public class UpdateScoreboard extends BukkitRunnable {
	
	//Plugin instance
	private Plugin pluginClass = Plugin.getInstance();
	
	DailyEvents dailyEvents = new DailyEvents();

	private final Plugin plugin;

	public UpdateScoreboard(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {

		Event event = plugin.getData().getWinningEvent();
//		HashMap<Player, Integer> topScores = new HashMap<>();
//
//		for(Player p : plugin.getData().getParticipants()) {
//			if(p != null) {
//				topScores.put(p, event.);
//			}
//		}
		
		//get greatest to least 
		Map<UUID, Integer> topScores = sortByValue(event.getScores());
		
		for(Player p : plugin.getData().getParticipants()){
			if(p != null) {
				Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
				TextComponent title = Component.text(event.getName()).color(event.getColor()).decorate(TextDecoration.BOLD);
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
						obj.getScore(dailyEvents.eventData.getString("current-countdown")).setScore(6);
						obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(5);
						obj.getScore(ChatColor.GRAY + "Required Score: " + ChatColor.GOLD + pluginClass.getSmallEvents().getConfigurationSection(dailyEvents.winningEventSection.getName()).getInt("requiredscore")).setScore(4);
						obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(3);
						obj.getScore(ChatColor.GREEN + "play.thefruitbox.net").setScore(2);
					} else {
						obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(7);
						obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Time Left:").setScore(6);
						obj.getScore(dailyEvents.eventData.getString("current-countdown")).setScore(5);
						obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(4);
						obj.getScore(ChatColor.GRAY + "Required Score: " + ChatColor.GOLD + pluginClass.getSmallEvents().getConfigurationSection(dailyEvents.winningEventSection.getName()).getInt("requiredscore")).setScore(3);
						obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(2);
						obj.getScore(ChatColor.GREEN + "play.thefruitbox.net").setScore(1);
					}
				}
				p.setScoreboard(board);
			}
		}
	}

	static Map<UUID, Integer> sortByValue(Map<UUID, Integer> topScores) {

        return topScores.entrySet().stream()
		        .sorted(Comparator.comparingInt(e -> -e.getValue()))
		        .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (a, b) -> { throw new AssertionError(); },
		                LinkedHashMap::new
		        ));
	}
	
	public void setCountdown(Event event) {
		CountdownTimerLong timer = new CountdownTimerLong(pluginClass,
		        event.getDuration(), 0, (t) -> dailyEvents.eventData.set("current-countdown", (t.getMinute() + ":" + (t.getSecondsLeft())))
		);
		timer.scheduleTimer();
	}
}
