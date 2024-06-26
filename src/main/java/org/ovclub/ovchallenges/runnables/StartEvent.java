package org.ovclub.ovchallenges.runnables;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.runnables.countdown.CountdownTimerLong;

import java.util.Iterator;
import java.util.UUID;

public class StartEvent extends BukkitRunnable{
	private final Plugin plugin;
	public StartEvent(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		EndEvent endEvent = new EndEvent(plugin);
		Challenge challenge = plugin.getData().getWinningChallenge();
		if (challenge != null) {
			int duration = challenge.getDuration();
			UpdateScoreboard updateScoreboard = new UpdateScoreboard(plugin);
			setCountdown(challenge);
			updateScoreboard.run();

			Iterator<UUID> iterator = plugin.getData().getParticipants().iterator();
			while (iterator.hasNext()) {
				UUID uuid = iterator.next();
				OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
				if (p.isOnline() && p.getPlayer() != null) {
					Player onlinePlayer = p.getPlayer();
					challenge.addScore(onlinePlayer, 0);
				} else {
					assert p.getName() != null : "Player name should not be null here";
					Bukkit.broadcast(ConfigManager.LEFT_THE_GAME
							.replaceText(builder -> builder.matchLiteral("{player}").replacement(p.getName())));
					//Bukkit.broadcast(ChatColor.RED + p.getName() + " left the game before the challenge started and will be removed from the challenge!");
					iterator.remove();

					if (plugin.getData().getParticipants().isEmpty()) {
						Bukkit.broadcast(ConfigManager.CHALLENGE_CANCELLED);
						SendDailyEventVote sendVote = new SendDailyEventVote(plugin);
						sendVote.runTaskLater(plugin, 200);
						this.cancel();
						break;
					}
				}
			}

			challenge.registerEvents();
			challenge.setIsActive(true);
			endEvent.runTaskLater(plugin, (long) duration * 60 * 20);
		} else {
			Bukkit.broadcastMessage("Challenge is null. Please report this error. Something fucking broke.");
		}
	}

	public void setCountdown(Challenge challenge) {
		CountdownTimerLong timer = new CountdownTimerLong(plugin,
				challenge.getDuration(), 0, (t) -> challenge.setCountdownLabel(t.getMinute() + ":" + (t.getSecondsLeft())));
		timer.scheduleTimer();
	}
}
