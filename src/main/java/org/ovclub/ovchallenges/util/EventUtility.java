package org.ovclub.ovchallenges.util;

import java.util.*;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.ovclub.ovchallenges.object.Challenge;

public class EventUtility {
	public static void ShuffleEvents(List<Challenge> challenges) {
		Collections.shuffle(challenges);
	}
	
	public static void RotateEvents(List<Challenge> challenges){
		//loop per # of rotations
		for(int i = 0; i < 3; i++) {
			
			//store last element in list
			Challenge temp = challenges.getLast();
			
			//traverse list and move elements to the right
			for(int j = 14; j > 0; j--) {
				challenges.set(j, challenges.get(j - 1));
			}
			challenges.set(0, temp);
		}
		challenges.subList(0, 3);
	}

	public static List<Player> getNonParticipatingPlayers(List<Player> participantList){
        List<Player> nonParticipatingPlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        nonParticipatingPlayers.removeIf(p -> !participantList.contains(p));
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
		List<Challenge> topChallenges = new ArrayList<>();
		int topVote = 0;
		int totalVotes = 0;
		
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

			totalVotes += votes;
		}

		// If less than 2 players vote or no challenges have votes, return "NONE"
		if (totalVotes < 2 || topChallenges.isEmpty()) {
			return null;
		}

		// If there's a tie, randomly select one of the top challenges
		if (topChallenges.size() > 1) {
			Random random = new Random();
			int randomIndex = random.nextInt(topChallenges.size());
			return topChallenges.get(randomIndex);
		}

		// If there's only one top event, return it
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
}
