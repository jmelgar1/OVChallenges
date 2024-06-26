package org.ovclub.ovchallenges.util;

import java.util.*;
import java.util.stream.Collectors;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;

public class ChallengeUtility {
	public static void rotateChallenges(List<Challenge> challenges) {
		// Check if there are at least 5 elements to rotate
		if (challenges.size() >= 5) {
			List<Challenge> firstFive = new ArrayList<>(challenges.subList(0, 5));
			challenges.removeAll(firstFive);
			challenges.addAll(firstFive);
		}

		// Filter challenges based on the specified durations
		List<Challenge> duration5 = challenges.stream()
				.filter(c -> c.getDuration() == 5)
				.limit(2)
				.collect(Collectors.toList());
		List<Challenge> duration10 = challenges.stream()
				.filter(c -> c.getDuration() == 10)
				.limit(2)
				.collect(Collectors.toList());
		List<Challenge> duration20 = challenges.stream()
				.filter(c -> c.getDuration() == 20)
				.limit(1)
				.collect(Collectors.toList());

		// Clear the top 5 and re-add the selected challenges
		challenges.removeAll(duration5);
		challenges.removeAll(duration10);
		challenges.removeAll(duration20);

		List<Challenge> newTopFive = new ArrayList<>();
		newTopFive.addAll(duration5);
		newTopFive.addAll(duration10);
		newTopFive.addAll(duration20);

		// Add back the selected challenges at the top
		challenges.addAll(0, newTopFive);
	}

	public static List<Player> getNonParticipatingPlayers(List<Player> participantList) {
		List<Player> nonParticipatingPlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
		nonParticipatingPlayers.removeAll(participantList);
		return nonParticipatingPlayers;
	}
	
	public static void clearVotes(List<Challenge> challenges) {
    	for(Challenge challenge : challenges) {
			if(challenge.getVotes() != 0) {
				challenge.setVotes(0);
			}
    	}  	
    	System.out.println("[OVChallenges] Votes cleared!");
	}

	public static Challenge determineEvent(List<Challenge> challenges) {
		ArrayList<Challenge> topChallenges = new ArrayList<>();
		int topVote = 0;

        //gets all challenges from list
		for (Challenge challenge : challenges) {
			int votes = challenge.getVotes();
			if (votes > topVote) {
				topVote = votes;
				topChallenges.clear();
				topChallenges.add(challenge);
			} else if (votes == topVote) {
				topChallenges.add(challenge);
			}
        }

		// If there's a tie, randomly select one of the top challenges
		if (topChallenges.size() > 1) {
			Random random = new Random();
			int randomIndex = random.nextInt(topChallenges.size());
			return topChallenges.get(randomIndex);
		}
		return topChallenges.getFirst();
	}

	public static Map<UUID, Integer> sortByValue(Map<UUID, Integer> topScores) {

		return topScores.entrySet().stream()
				.sorted(Comparator.comparingInt(e -> -e.getValue()))
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(a, b) -> { throw new AssertionError(); },
						LinkedHashMap::new
				));
	}
	public void sendBonusPointsMessage(Player p, TextComponent type, Challenge challenge, String subjectPlaceholder, String subject, String pointsPlaceholder, String points) {
		p.sendMessage(type
				.replaceText(builder -> builder.matchLiteral(subjectPlaceholder)
						.replacement(Component.text(subject).color(challenge.getColor())))
				.replaceText(builder -> builder.matchLiteral(pointsPlaceholder)
						.replacement(Component.text(points).color(NamedTextColor.GOLD))));
	}
}
