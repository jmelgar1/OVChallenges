package org.ovclub.ovchallenges.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Event;

import java.io.File;

public class EventsFile {
    private final Plugin plugin;
    private File eventsFile;
    private FileConfiguration eventsData;

    public EventsFile(final Plugin plugin){
        this.plugin = plugin;
    }

    public void loadEventsIntoMemory() {
        eventsFile = new File(plugin.getDataFolder(), "events.yml");
        if (!eventsFile.exists()) {
            plugin.saveResource("events.yml", false);
        }
        eventsData = YamlConfiguration.loadConfiguration(eventsFile);
        loadEvents();
    }

    private void loadEvents() {
        plugin.getData().getEvents().clear();
        eventsData = YamlConfiguration.loadConfiguration(eventsFile);
        if(!eventsData.getKeys(false).isEmpty()){
            for(String key : eventsData.getKeys(false)){
                Event loadedEvent = Event.deserialize(key, eventsData.getConfigurationSection(key).getValues(false), plugin);
                plugin.getData().addEvent(loadedEvent);
            }
        }
//        for (String key : eventsData.getKeys(false)) {
//            if (key != null && !key.isEmpty()) {
//                UUID uuid = UUID.fromString(key);
//                int unclaimed = rewardsData.getInt(key + ".unclaimed", 0);
//                plugin.getData().getRewards().put(uuid, unclaimed);
//                Event event = new Event();
//                plugin.getData().getEvents().
//            }
//        }
    }
}
