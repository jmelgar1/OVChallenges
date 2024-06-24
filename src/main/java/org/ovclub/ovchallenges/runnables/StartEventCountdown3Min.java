package org.ovclub.ovchallenges.runnables;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.ovclub.ovchallenges.Plugin;

import net.md_5.bungee.api.ChatColor;

public class StartEventCountdown3Min extends BukkitRunnable {

	private final Plugin plugin;

	public StartEventCountdown3Min(Plugin plugin) {
		this.plugin = plugin;
	}
	
	int seconds = 180;

	BossBar bar = Bukkit.createBossBar("countdown", BarColor.PINK, BarStyle.SEGMENTED_10);
	
    DecimalFormat dFormat = new DecimalFormat("00");
    
    boolean validStart = true;

	public void run() {
		for(Player p : plugin.getData().getParticipants()) {
			if (seconds <= 5) {
				if(p != null) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);
				}
			}

			if((seconds -= 1) == 0) {
				if(p != null && !p.isOnline()) {
					Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " left the game before the event started and will be removed from the event!");

					plugin.getData().removeParticipant(p);

					if(plugin.getData().getParticipants().size() < 2) {
						Bukkit.broadcastMessage(ChatColor.RED + "The event will be cancelled since there are less than two players in the event.");

						validStart = false;
						SendDailyEventVote sendVote = new SendDailyEventVote(plugin);
						sendVote.runTaskLater(plugin, 3000);
						bar.removeAll();
						cancel();
					}
				}

				if(validStart) {
					StartEvent startEvent = new StartEvent(plugin);
					startEvent.run();
					bar.removeAll();
					cancel();
				}
			} else {
				bar.setProgress(seconds / 180D);
				String minutesTimer = String.valueOf(seconds/60);
				String secondsTimer = dFormat.format(seconds % 60);
				bar.setTitle(plugin.getData().getWinningChallenge().getName() + " starts in " + minutesTimer + ":" + secondsTimer);
			}
		}
		if(seconds <= 5) {
			for(Player p : plugin.getData().getParticipants()) {
				if(p != null) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);
				}
			}
		}
	}
	
	public void addPlayer(Player p) {
		bar.addPlayer(p);
	}
	
	@EventHandler
	void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		plugin.getData().addBossBar(p, Bukkit.createBossBar("Challenge will begin shortly", BarColor.PINK, BarStyle.SEGMENTED_10));
	}
	
	@EventHandler
	void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		BossBar bar = plugin.getData().getPlayerBossBar(p);
		bar.addPlayer(p);
	}
}
