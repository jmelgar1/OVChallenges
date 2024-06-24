package org.ovclub.ovchallenges.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Challenge;

import java.io.File;

public class ChallengesFile {
    private final Plugin plugin;
    private File challengesFile;
    private FileConfiguration eventsData;

    public ChallengesFile(final Plugin plugin){
        this.plugin = plugin;
    }

    public void loadEventsIntoMemory() {
        challengesFile = new File(plugin.getDataFolder(), "challenges.yml");
        if (!challengesFile.exists()) {
            plugin.saveResource("challenges.yml", false);
        }
        eventsData = YamlConfiguration.loadConfiguration(challengesFile);
        loadEvents();
    }

    private void loadEvents() {
        plugin.getData().getChallenges().clear();
        eventsData = YamlConfiguration.loadConfiguration(challengesFile);
        if(!eventsData.getKeys(false).isEmpty()){
            for(String key : eventsData.getKeys(false)){
                Challenge loadedChallenge = Challenge.deserialize(key, eventsData.getConfigurationSection(key).getValues(false), plugin);
                plugin.getData().addChallenge(loadedChallenge);
            }
        }
//        for (String key : eventsData.getKeys(false)) {
//            if (key != null && !key.isEmpty()) {
//                UUID uuid = UUID.fromString(key);
//                int unclaimed = rewardsData.getInt(key + ".unclaimed", 0);
//                plugin.getData().getRewards().put(uuid, unclaimed);
//                Challenge event = new Challenge();
//                plugin.getData().getEvents().
//            }
//        }
    }
}
