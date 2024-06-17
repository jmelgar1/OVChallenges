package org.ovclub.ovchallenges.bukkitevents;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.ovclub.ovchallenges.Plugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class PlayerEvents implements Listener {

    //Plugin instance
    private Plugin pluginClass = Plugin.getInstance();

    //Get playerdataconfig
    FileConfiguration playerDataConfig = pluginClass.getPlayerData();

    //Luckperms api
    static LuckPerms api = LuckPermsProvider.get();

    //when a player joins the server
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();
        String uniqueIDString = p.getUniqueId().toString();

        FileConfiguration eventList = pluginClass.getSmallEvents();

        if(playerDataConfig.get(uniqueIDString) == null) {

            //send message to console
            System.out.println("[DailyEvents] Creating new user for " + p.getName());

            //create new player
            ConfigurationSection playerUUID = playerDataConfig.createSection(uniqueIDString);

            //create sections playername section apart from UUID
            playerUUID.createSection("player-name");
            playerUUID.set("player-name", p.getName());

            playerUUID.createSection("totalSponges");
            playerUUID.set("totalSponges", 0);

            //create stats section for big/small events
            playerUUID.createSection("stats");
            ConfigurationSection statsSection = playerUUID.getConfigurationSection("stats");
            statsSection.createSection("big-events");
            statsSection.createSection("small-events");
            statsSection.createSection("high-scores");

            //create section for individual big events
            ConfigurationSection bigEventsSection = statsSection.getConfigurationSection("big-events");
            bigEventsSection.createSection("champions-tour");
            bigEventsSection.createSection("build-competitions");
            bigEventsSection.createSection("map-art-contests");
            bigEventsSection.createSection("red-vs-blue");

            //create section for individual small events
            ConfigurationSection smallEventsSection = statsSection.getConfigurationSection("small-events");
            ConfigurationSection highScoreSection = statsSection.getConfigurationSection("high-scores");
            for(String events : eventList.getKeys(false)) {
                smallEventsSection.set(events, 0);
                highScoreSection.set(events, 0);
            }

            playerUUID.createSection("rank");
            playerUUID.set("rank", getPlayerGroup(p.getName()));
            playerUUID.createSection("date-joined");
            pluginClass.savePlayerDataFile();
        } else {
            updatePlayerData(eventList, uniqueIDString);
            //check if all events in players file match up with config, if there are unknown remove them, if there are
            //some missing add them

            //get existing user
            ConfigurationSection existingPlayerUUID = playerDataConfig.getConfigurationSection(uniqueIDString);

            //if rank in config does not equal new rank change it
            if(!existingPlayerUUID.getString("rank").equals(getPlayerGroup(p.getName()))) {
                existingPlayerUUID.set("rank", getPlayerGroup(p.getName()));
                pluginClass.savePlayerDataFile();
            }
        }
    }

    //get player rank
    @SuppressWarnings("deprecation")
    private static String getPlayerGroup(String username) {
        //Find user group
        UUID userUUID = Bukkit.getOfflinePlayer(username).getUniqueId();
        User user = api.getUserManager().loadUser(userUUID).join();

        //return player group
        return user.getPrimaryGroup();
    }

    public void updatePlayerData(FileConfiguration eventList, String uniqueIDString){
        ConfigurationSection playerUUID = playerDataConfig.getConfigurationSection(uniqueIDString);
        ConfigurationSection statsSection = playerUUID.getConfigurationSection("stats");
        ConfigurationSection smallEventsSection = statsSection.getConfigurationSection("small-events");
        ConfigurationSection highScoreSection = statsSection.getConfigurationSection("high-scores");

        //if no high scores section exists.
        if(highScoreSection == null){
            statsSection.createSection("high-scores");
            pluginClass.savePlayerDataFile();
            for(String events : eventList.getKeys(false)) {
                statsSection.getConfigurationSection("high-scores").set(events, 0);
            }
        }

        //create new list of events that are in user data
        List<String> existingEvents = new ArrayList<>();
        for(String events : smallEventsSection.getKeys(false)){
            existingEvents.add(events);
        }

        //add any new events to user list
        for(String events : eventList.getKeys(false)){
            if(!existingEvents.contains(events)){
                smallEventsSection.set(events, 0);
            }
        }

        //remove any instance of TBD
        if(existingEvents.contains("TBD")){
            smallEventsSection.set("TBD", null);
        }

        pluginClass.savePlayerDataFile();
    }
}
