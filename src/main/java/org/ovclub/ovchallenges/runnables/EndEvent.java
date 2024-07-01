package org.ovclub.ovchallenges.runnables;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.object.PlayerData;
import org.ovclub.ovchallenges.util.ChallengeUtility;
import org.ovclub.ovchallenges.util.ChatUtility;

public class EndEvent extends BukkitRunnable{

	private final Plugin plugin;

	public EndEvent(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		Challenge challenge = plugin.getData().getWinningChallenge();
		PlayerData data = plugin.getData();
		Map<UUID, Integer> topScores = ChallengeUtility.sortByValue(challenge.getScores());

		Bukkit.broadcast(ChatUtility.createResultsMessage(challenge, topScores));
		Map<Integer, List<UUID>> scoreToPlayers = new HashMap<>();
		for (Map.Entry<UUID, Integer> entry : topScores.entrySet()) {
			scoreToPlayers.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
		}

		List<Integer> sortedScores = new ArrayList<>(scoreToPlayers.keySet());
		sortedScores.sort(Collections.reverseOrder());

		int counter = 1;
		for (int score : sortedScores) {
			List<UUID> playersWithSameScore = scoreToPlayers.get(score);
			int sponges = counter <= 3 ? challenge.getPlacements().get(counter) : 0;
			int spongesPerPlayer = sponges / playersWithSameScore.size();

			for (UUID playerId : playersWithSameScore) {
				OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(playerId);
				boolean isFirstPlace = counter == 1;
				data.getProfile(p, plugin).handleChallengeResult(challenge, p, score, isFirstPlace);

				if(p.isOnline()) {
					Player player = p.getPlayer();
					int requiredScore = challenge.getRequiredScore();
					if (player != null) {
						if (score >= requiredScore) {
							player.sendMessage(ConfigManager.EARNED_SPONGE
									.replaceText(builder -> builder.matchLiteral("{amount}").replacement(String.valueOf(spongesPerPlayer))));
						} else {
							player.sendMessage(ConfigManager.DID_NOT_MEET_SCORE
									.replaceText(builder -> builder.matchLiteral("{amount}").replacement(String.valueOf(requiredScore))));
						}
					}
				}
				plugin.getEconomy().depositPlayer(p, spongesPerPlayer);
			}
			counter++;
		}

		try {
			challenge.unregisterEvents();
		} catch (Exception e) {
			System.out.println(e);
		}

		removeScoreboard();
		data.clearParticipants();
		ChallengeUtility.clearVotesAndScores(plugin.getData().getChallenges());
		challenge.setIsActive(false);

		SendDailyEventVote sendDailyEventVote = new SendDailyEventVote(plugin);
		sendDailyEventVote.runTaskLater(plugin, 6000);
	}

	public void removeScoreboard() {
		for(UUID uuid : plugin.getData().getParticipants()) {
			Player p = Bukkit.getPlayer(uuid);
			if(p != null) {
				p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			}
		}
	}
}
