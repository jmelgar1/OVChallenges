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
        loadChallengesFromFile();
    }

    private void loadChallengesFromFile() {
        plugin.getData().getChallenges().clear();
        eventsData = YamlConfiguration.loadConfiguration(challengesFile);
        if(!eventsData.getKeys(false).isEmpty()){
            for(String key : eventsData.getKeys(false)){
                Challenge loadedChallenge = Challenge.deserialize(key, eventsData.getConfigurationSection(key).getValues(false), plugin);
                plugin.getData().addChallenge(loadedChallenge);
            }
        }
        plugin.getData().shuffleChallenges();
    }
}
