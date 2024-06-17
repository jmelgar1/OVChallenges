package org.ovclub.ovchallenges.util;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.ovclub.ovchallenges.object.Event;

public class EventUtility {
	public static void ShuffleEvents(List<Event> events) {
		Collections.shuffle(events);
	}
	
	public static void RotateEvents(List<Event> events){
		//loop per # of rotations
		for(int i = 0; i < 3; i++) {
			
			//store last element in list
			Event temp = events.getLast();
			
			//traverse list and move elements to the right
			for(int j = 14; j > 0; j--) {
				events.set(j, events.get(j - 1));
			}
			events.set(0, temp);
		}
		events.subList(0, 3);
	}

	
//	public static void clearParticipationList(Plugin pluginClass) {
//		//clear participant list
//    	try{
//    		List<String> clearedList = pluginClass.getEventData().getStringList("participants");
//    		clearedList.clear();
//    		pluginClass.getEventData().set("participants", clearedList);
//    		pluginClass.saveEventDataFile();
//    		System.out.println("Cleared participant list!");
//    	} catch (Exception e) {
//    		System.out.println(e);
//    	}
//	}
//
//	public static List<Player> getPlayerParticipants(List<String> participantList){
//
//		List<Player> playerParticipants = new ArrayList<>();
//
//		for(String s : participantList) {
//			Player p = Bukkit.getServer().getPlayer(s);
//			playerParticipants.add(p);
//		}
//
//		return playerParticipants;
//	}

	public static List<Player> getNonParticipatingPlayers(List<Player> participantList){
        List<Player> nonParticipatingPlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        nonParticipatingPlayers.removeIf(p -> !participantList.contains(p));
		return nonParticipatingPlayers;
	}
	
	public static void clearVotes(List<Event> events) {
    	for(Event event : events) {
			if(event.getVotes() != 0) {
				event.setVotes(0);
			}
    	}  	
    	System.out.println("[OVChallenges] Votes cleared!");
	}
	
	public static Event determineEvent(List<Event> events) {
		List<Event> topEvents = new ArrayList<>();
		int topVote = 0;
		int totalVotes = 0;
		
	   	//gets all events from list
		for (Event event : events) {
			int votes = event.getVotes();

			if (votes > topVote) {
				topVote = votes;
				topEvents.clear();
				topEvents.add(event);
			} else if (votes == topVote) {
				topEvents.add(event);
			}

			totalVotes += votes;
		}

		// If less than 2 players vote or no events have votes, return "NONE"
		if (totalVotes < 2 || topEvents.isEmpty()) {
			return null;
		}

		// If there's a tie, randomly select one of the top events
		if (topEvents.size() > 1) {
			Random random = new Random();
			int randomIndex = random.nextInt(topEvents.size());
			return topEvents.get(randomIndex);
		}

		// If there's only one top event, return it
		return topEvents.getFirst();
	}

//	public static String getVotedEvent(Plugin pluginClass) {
//		List<String> winningEventList = pluginClass.getEventData().getConfigurationSection("current-event").getKeys(false)
//				.stream().collect(Collectors.toList());
//
//		return winningEventList.getFirst();
//	}
}
