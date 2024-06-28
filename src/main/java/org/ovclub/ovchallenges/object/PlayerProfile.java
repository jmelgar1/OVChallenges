package org.ovclub.ovchallenges.object;

import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.events.ChallengeDTO;
import org.ovclub.ovchallenges.object.events.EventDTO;

import java.util.*;

public class PlayerProfile {
    private final Plugin plugin;

    private String uuid;
    private final List<EventDTO> events = new ArrayList<>();;
    private final List<ChallengeDTO> challenges = new ArrayList<>();;

    public String getUuid() {return uuid;}
    public void setUuid() {this.uuid = UUID.randomUUID().toString();}

    public List<EventDTO> getEvents() { return events; }
    public List<ChallengeDTO> getChallenges() { return challenges; }

    public ChallengeDTO getChallengeDtoFromChallenge(Challenge challenge) {
        for(ChallengeDTO challengeDto : challenges) {
            if(challengeDto.getName().equals(challenge.getName())) {
                return challengeDto;
            }
        }
        return null;
    }

    public PlayerProfile(String uuid, Map<String, Object> map, final Plugin data) {
        this.uuid = uuid;
        this.plugin = data;

        Object eventsObj = map.get("events");
        loadEvents(eventsObj);

        Object challengesObj = map.get("enforcers");
        loadChallenges(challengesObj);
    }

//    @Override
//    public Map<String, Object> serialize() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", this.name);
//        map.put("item", this.item.getType().name());
//        map.put("duration", this.duration);
//        map.put("description", this.description);
//        map.put("color", String.valueOf(this.color));
//        map.put("votes", this.votes);
//        map.put("requiredScore", this.requiredScore);
//        map.put("placements", this.placements);
//        return map;
//    }

    public static PlayerProfile deserialize(String name, Map<String, Object> map, Plugin data) {
        return new PlayerProfile(name, map, data);
    }

    private void loadEvents(Object eventData) {
        if (eventData instanceof Map<?, ?> eventsMap) {
            eventsMap.forEach((eventName, results) -> {
                if (results instanceof Map<?, ?> resultsMap) {
                    this.events.add(new EventDTO((String) eventName, (Map<String, Integer>) resultsMap));
                }
            });
        }
    }

    private void loadChallenges(Object challengeData) {
        if (challengeData instanceof Map<?, ?> rawChallengesMap) {
            Map<String, Map<String, Integer>> challengesMap = (Map<String, Map<String, Integer>>) rawChallengesMap;
            challengesMap.forEach((challengeName, resultsMap) -> {
                int wins = resultsMap.getOrDefault("wins", 0);
                int highScore = resultsMap.getOrDefault("high-score", 0);
                this.challenges.add(new ChallengeDTO(challengeName, wins, highScore));
            });
        }
    }
}
