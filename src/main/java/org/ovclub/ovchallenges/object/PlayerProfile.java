package org.ovclub.ovchallenges.object;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
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

    public void handleChallengeResult(Challenge challenge, OfflinePlayer p, int score, boolean isWinner) {
        String challengeName = challenge.getName();
        boolean found = false;

        for (ChallengeDTO challengeDto : challenges) {
            if (challengeDto.getName().equals(challengeName)) {
                challengeDto.checkToReplaceHighScore(p, score);
                if (isWinner) {
                    challengeDto.addWin();
                }
                found = true;
                break;
            }
        }

        if (!found) {
            ChallengeDTO newChallenge = new ChallengeDTO(challengeName, 0, 0);
            newChallenge.checkToReplaceHighScore(p, score);
            if (isWinner) {
                newChallenge.addWin();
            }
            challenges.add(newChallenge);
        }
    }

    //Constructor for new profile
    public PlayerProfile(final Player p, final Plugin data) {
        this.uuid = p.getUniqueId().toString();
        this.plugin = data;
        this.plugin.getData().addPlayerProfile(this);
    }

    //constructor for loaded profile
    public PlayerProfile(String uuid, Map<String, Object> map, final Plugin data) {
        this.uuid = uuid;
        this.plugin = data;

        Object eventsObj = map.get("events");
        loadEvents(eventsObj);

        Object challengesObj = map.get("challenges");
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
                if (eventName instanceof String && results instanceof Map<?, ?> resultsMap) {
                    try {
                        Map<String, Integer> typedResults = castMap(resultsMap);
                        this.events.add(new EventDTO((String) eventName, typedResults));
                    } catch (ClassCastException e) {
                        System.err.println("Error processing event: " + eventName);
                        System.err.println("Map type error: " + e.getMessage());
                    }
                }
            });
        }
    }

    private void loadChallenges(Object challengeData) {
        if (challengeData instanceof Map<?, ?> rawChallengesMap) {
            rawChallengesMap.forEach((key, value) -> {
                if (key instanceof String challengeName && value instanceof Map<?, ?> resultsMap) {
                    try {
                        Map<String, Integer> typedResultsMap = castMap(resultsMap);
                        int wins = typedResultsMap.getOrDefault("wins", 0);
                        int highScore = typedResultsMap.getOrDefault("high-score", 0);
                        challenges.add(new ChallengeDTO(challengeName, wins, highScore));
                    } catch (ClassCastException e) {
                        System.err.println("Error processing challenge: " + challengeName);
                        System.err.println("Map type error: " + e.getMessage());
                    }
                }
            });
        }
    }

    private Map<String, Integer> castMap(Map<?, ?> rawMap) {
        Map<String, Integer> typedMap = new HashMap<>();
        rawMap.forEach((key, value) -> {
            if (key instanceof String) {
                if (value instanceof Integer) {
                    typedMap.put((String) key, (Integer) value);
                } else if (value instanceof Double) {
                    typedMap.put((String) key, ((Double) value).intValue());
                } else {
                    throw new ClassCastException("Expected Integer or Double as value. Found: " + value.getClass().getSimpleName());
                }
            } else {
                throw new ClassCastException("Key should be String. Found key type: " + key.getClass().getSimpleName());
            }
        });
        return typedMap;
    }
}
