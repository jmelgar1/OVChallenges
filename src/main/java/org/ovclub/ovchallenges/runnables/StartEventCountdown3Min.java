package org.ovclub.ovchallenges.runnables;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.ovclub.ovchallenges.Plugin;

import net.md_5.bungee.api.ChatColor;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;

import java.util.UUID;

public class StartEventCountdown3Min extends BukkitRunnable {

	private final Plugin plugin;

	public StartEventCountdown3Min(Plugin plugin) {
		this.plugin = plugin;
	}

	//TODO: CHANGE TO 180
	int seconds = 30;

    boolean validStart = true;

	public void run() {
		Challenge challenge = plugin.getData().getWinningChallenge();
		seconds = Math.max(0, seconds - 1);

		for(UUID uuid : plugin.getData().getParticipants()) {
			Player p = Bukkit.getPlayer(uuid);
			if (seconds <= 5) {
				if(p != null) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);
				}
			}

			if(seconds == 0) {
				if(p != null && !p.isOnline()) {
					Bukkit.broadcast(ConfigManager.LEFT_THE_GAME
							.replaceText(builder -> builder.matchLiteral("{player}")
							.replacement(p.getName())));

					plugin.getData().removeParticipant(p);

					if(plugin.getData().getParticipants().isEmpty()) {
						Bukkit.broadcast(ConfigManager.CHALLENGE_CANCELLED);

						validStart = false;
						SendDailyEventVote sendVote = new SendDailyEventVote(plugin);
						sendVote.runTaskLater(plugin, 3000);
						challenge.setBossBarVisibility(false);
						cancel();
					}
				}

				if(validStart) {
					StartEvent startEvent = new StartEvent(plugin);
					startEvent.run();
					challenge.setBossBarVisibility(false);
					cancel();
				}
			} else {
				challenge.setCountdownBar(seconds);
			}
		}
		if(seconds <= 5) {
			for(UUID uuid : plugin.getData().getParticipants()) {
				Player p = Bukkit.getPlayer(uuid);
				if(p != null) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);
				}
			}
		}
	}
}
