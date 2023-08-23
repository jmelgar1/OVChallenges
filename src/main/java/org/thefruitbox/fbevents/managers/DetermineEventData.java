package org.thefruitbox.fbevents.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.thefruitbox.fbevents.Main;

public class DetermineEventData {
	
	public DetermineEventData instance = this;
	
	//create smallevent empty list
	ArrayList<String> smallEventNames = new ArrayList<String>();
	
	public void ShuffleEvents(FileConfiguration smallEvents) {
		//get small events in smallevents.yml and shuffle for randomized vote opportunities
		for(String smallEvent : smallEvents.getConfigurationSection("").getKeys(false)) {
			String rawName = smallEvent;
			
			//add names to a list
			smallEventNames.add(rawName);
		}
		
		Collections.shuffle(smallEventNames);
	}
	
	public void RotateEvents(List<String> smallEventNames){
		//loop per # of rotations
		for(int i = 0; i < 3; i++) {
			
			//store last element in list
			String temp = smallEventNames.get(14);
			
			//traverse list and move elements to the right
			for(int j = 14; j > 0; j--) {
				smallEventNames.set(j, smallEventNames.get(j - 1));
			}
			smallEventNames.set(0, temp);
		}
		smallEventNames.subList(0, 3);
	}
	
	public List<String> getList(){
		return smallEventNames;
	}
	
	public void clearParticipationList(Main mainClass) {
		//clear participant list
    	try{
    		List<String> clearedList = mainClass.getEventData().getStringList("participants");
    		clearedList.clear();
    		mainClass.getEventData().set("participants", clearedList);
    		mainClass.saveEventDataFile();
    		System.out.println("Cleared participant list!");
    	} catch (Exception e) {
    		System.out.println(e);
    	}
	}
	
	public List<Player> getPlayerParticipants(List<String> participantList){
		
		List<Player> playerParticipants = new ArrayList<>();
		
		for(String s : participantList) {
			Player p = Bukkit.getServer().getPlayer(s);
			playerParticipants.add(p);
		}
		
		return playerParticipants;
	}
	
	public List<Player> getNonParticipatingPlayers(List<String> participantList){
		List<Player> nonParticipatingPlayers = new ArrayList<>();
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			nonParticipatingPlayers.add(p);
		}
		
		//returning null??
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			for(String p2 : participantList) {
				if (p.getName().equals(p2)){
				nonParticipatingPlayers.remove(Bukkit.getServer().getPlayer(p2));
			}
		}	
	}
		return nonParticipatingPlayers;
	}
	
	public void clearVotes(FileConfiguration smallEvents, List<String> smallEventNames, Main mainClass) {
    	//gets all events from list
    	for(String names : smallEventNames) {	
    		ConfigurationSection eventsSection = smallEvents.getConfigurationSection(names);
    		
    		try {
    			eventsSection.set("votes", 0);
    		} catch(Exception e) {
    			System.out.println("[OVEvents] Could not clear " + names + " votes!");
    		}
    	}  	
    	System.out.println("[OVEvents] Votes cleared!");
    	mainClass.saveSmallEventsFile();
    	
	}
	
	public String determineEvent(FileConfiguration smallEvents, List<String> smallEventNames) {
		List<String> topEvents = new ArrayList<>();
		int topVote = 0;
		int totalVotes = 0;
		
	   	//gets all events from list
		for (String eventName : smallEventNames) {
			ConfigurationSection eventsSection = smallEvents.getConfigurationSection(eventName);
			int votes = eventsSection.getInt("votes");

			if (votes > topVote) {
				topVote = votes;
				topEvents.clear();
				topEvents.add(eventName);
			} else if (votes == topVote) {
				topEvents.add(eventName);
			}

			totalVotes += votes;
		}

		// If less than 2 players vote or no events have votes, return "NONE"
		if (totalVotes < 2 || topEvents.isEmpty()) {
			return "NONE";
		}

		// If there's a tie, randomly select one of the top events
		if (topEvents.size() > 1) {
			Random random = new Random();
			int randomIndex = random.nextInt(topEvents.size());
			return topEvents.get(randomIndex);
		}

		// If there's only one top event, return it
		return topEvents.get(0);
	}

	public String getVotedEvent(Main mainClass) {
		List<String> winningEventList = mainClass.getEventData().getConfigurationSection("current-event").getKeys(false)
				.stream().collect(Collectors.toList());

		return winningEventList.get(0);
	}
	
	public DetermineEventData getInstance() {
		return instance;
	}
}
