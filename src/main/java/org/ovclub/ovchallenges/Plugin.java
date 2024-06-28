package org.ovclub.ovchallenges;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.ovclub.ovchallenges.commands.CommandManager;
import org.ovclub.ovchallenges.file.ChallengesFile;
import org.ovclub.ovchallenges.file.PlayerProfileFile;
import org.ovclub.ovchallenges.listeners.PlayerEvents;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.PlayerData;
import org.ovclub.ovchallenges.runnables.EndEvent;
import org.ovclub.ovchallenges.runnables.SendDailyEventVote;

import net.milkbowl.vault.economy.Economy;

public class Plugin extends JavaPlugin implements Listener{

    private PlayerData playerData;
    public PlayerData getData(){return this.playerData;}

    private ChallengesFile eventsFile;

    private PlayerProfileFile profilesFile;

    //vault instance
    public static Economy econ = null;
    public Economy getEconomy() {return econ;}
//
//    //main config.yml
//    FileConfiguration config;
//    File cfile;
////
//    //player data file
//    private File playerDataFile;
////    private FileConfiguration playerData;
//
//    //small events file
//    private File smallEventsFile;
//    private FileConfiguration smallEvents;
//
//    //event data file
//    private File eventDataFile;
//    private FileConfiguration eventData;

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        econ = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        return true;
    }

    @Override
    public void onEnable() {
        System.out.println("[OVChallenges Enabled]");
        this.playerData = new PlayerData();

        eventsFile = new ChallengesFile(this);
        eventsFile.loadEventsIntoMemory();

        profilesFile = new PlayerProfileFile(this);
        profilesFile.loadProfiles();

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        ConfigManager.loadConfig(this.getConfig());

        if (!setupEconomy()) {
            getLogger().severe("Vault dependency not found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //this.getCommand("ovprofile").setExecutor(new ovprofile());
        getCommand("challenges").setExecutor(new CommandManager(this));

        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);

        //OLD SHIT

        //get main config
//        this.saveDefaultConfig();
//        config = getConfig();
//        config.options().copyDefaults(true);
//        saveConfig();
//        cfile = new File(getDataFolder(), "config.yml");
//
//        //create playerdatafiles
//        createPlayerDataFile();
//
//        //create smalleventfiles
//        createSmallEventsFile();
//
//        //create eventdata
//        createEventDataFile();
//
//        //create eventdata
//        createEventDataFile();

        //shuffle events and clear participation list
        //ChallengeUtility.ShuffleEvents(smallEvents);
        //ChallengeUtility.clearParticipationList(instance);

        //admin commands
//        this.getCommand("configurestats").setExecutor(new configureStats());
//        this.getCommand("configurestatsbc").setExecutor(new configureStatsBC());
//        this.getCommand("configurestatsct").setExecutor(new configureStatsCT());
//        this.getCommand("configurestatsma").setExecutor(new configureStatsMA());
//        this.getCommand("configurestatsrvb").setExecutor(new configureStatsRVB());
//        this.getCommand("oveReload").setExecutor(new oveReload());
//        this.getCommand("fbeforcevote").setExecutor(new oveForceVote());
//        this.getCommand("fbeendvote").setExecutor(new oveEndVote());
//        this.getCommand("fbegivexp").setExecutor(new oveGiveXP());
//        this.getCommand("fbesetxp").setExecutor(new oveSetXP());

        //register events
        //getServer().getPluginManager().registerEvents(new ovprofile(), this);

        //admin events
//        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
//        getServer().getPluginManager().registerEvents(new configureStats(), this);
//        getServer().getPluginManager().registerEvents(new configureStatsCT(), this);
//        getServer().getPluginManager().registerEvents(new configureStatsBC(), this);
//        getServer().getPluginManager().registerEvents(new configureStatsMA(), this);
//        getServer().getPluginManager().registerEvents(new configureStatsRVB(), this);

        //create new runnable to start the event cycle
        SendDailyEventVote dailyVote = new SendDailyEventVote(this);
        dailyVote.run();
    }

    @Override
    public void onDisable() {
        System.out.println("(!) OVChallenges Disabled");
        profilesFile.saveProfiles();
        //end event cycle
        EndEvent endEvent = new EndEvent(this);
        endEvent.run();

    }
//
//    //PLAYER DATA FILE
//    public void savePlayerDataFile() {
//        try {
//            playerData.save(playerDataFile);
//        } catch (IOException e) {
//            Bukkit.getConsoleSender().sendMessage("Couldn't save playerdata.yml");
//        }
//    }
//
//    public void reloadPlayerDataFile() {
//        playerData = YamlConfiguration.loadConfiguration(playerDataFile);
//
//    }
//
//    public FileConfiguration getPlayerData() {
//        return this.playerData;
//    }
//
//    private void createPlayerDataFile() {
//        playerDataFile = new File(getDataFolder(), "playerdata.yml");
//        if(!playerDataFile.exists()) {
//            playerDataFile.getParentFile().mkdirs();
//            saveResource("playerdata.yml", false);
//            System.out.println("(!) playerdata.yml created");
//        }
//
//        playerData = new YamlConfiguration();
//        try {
//            playerData.load(playerDataFile);
//            System.out.println("(!) playerdata.yml loaded");
//        } catch(IOException | InvalidConfigurationException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //SMALL EVENTS FILE
//    public void saveSmallEventsFile() {
//        try {
//            smallEvents.save(smallEventsFile);
//        } catch (IOException e) {
//            Bukkit.getConsoleSender().sendMessage("Couldn't save smallevents.yml");
//        }
//    }
//
//    private void createSmallEventsFile() {
//        smallEventsFile = new File(getDataFolder(), "smallevents.yml");
//        if(!smallEventsFile.exists()) {
//            smallEventsFile.getParentFile().mkdirs();
//            saveResource("smallevents.yml", false);
//            System.out.println("(!) smallevents.yml created");
//        }
//
//        smallEvents = new YamlConfiguration();
//        try {
//            smallEvents.load(smallEventsFile);
//            System.out.println("(!) smallevents.yml loaded");
//        } catch(IOException | InvalidConfigurationException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public FileConfiguration getSmallEvents() {
//        return this.smallEvents;
//    }
//
//    //EVENT DATA FILE
//    public void saveEventDataFile() {
//        try {
//            eventData.save(eventDataFile);
//        } catch (IOException e) {
//            Bukkit.getConsoleSender().sendMessage("Couldn't save eventdata.yml");
//        }
//    }
//
//    public void reloadEventDataFile() {
//        eventData = YamlConfiguration.loadConfiguration(eventDataFile);
//    }
//
//    private void createEventDataFile() {
//        eventDataFile = new File(getDataFolder(), "eventdata.yml");
//        if(!eventDataFile.exists()) {
//            eventDataFile.getParentFile().mkdirs();
//            saveResource("eventdata.yml", false);
//            System.out.println("(!) eventdata.yml created");
//        }
//
//        eventData = new YamlConfiguration();
//        try {
//            eventData.load(eventDataFile);
//            System.out.println("(!) eventdata.yml loaded");
//        } catch(IOException | InvalidConfigurationException e) {
//            e.printStackTrace();
//        }
//    }
//    public FileConfiguration getEventData() {
//        return this.eventData;
//    }

    //send event notification every 20 minutes
    public void OriginalVoteRunnable() {
        SendDailyEventVote dailyVote = new SendDailyEventVote(this);

        //checks every 30 seconds
        dailyVote.runTaskLater(this, 600);
    }
}