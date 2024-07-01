package org.ovclub.ovchallenges.object;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.ovclub.ovchallenges.Plugin;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Challenge implements Listener, ConfigurationSerializable {
    /**
     * VARIABLES
     **/
    private final Plugin plugin;
    private final String name;
    private final String description;
    private final ItemStack item;
    private final int duration;
    private String countdownLabel;
    private final TextColor color;
    private final BossBar countdownBar = Bukkit.createBossBar("countdown", BarColor.PINK, BarStyle.SEGMENTED_10);
    private int votes;
    private final int requiredScore;
    private final Map<Integer, Integer> placements;
    private final Map<UUID, Integer> scores = new HashMap<>();
    private boolean isActive = false;
    private Class<? extends Listener> classType;
    private final Map<Class<? extends Listener>, Listener> activeListeners = new HashMap<>();

    /**
     * GETTERS
     **/
    public String getName() { return name; }
    public String getDescription() {return description;}
    public ItemStack getItem() {
        return item;
    }
    public int getDuration() { return duration; }
    public String getCountdownLabel() { return countdownLabel; }
    public TextColor getColor() { return color; }
    public BossBar getCountdownBar() { return countdownBar; }
    public int getVotes() { return votes; }
    public int getRequiredScore() { return requiredScore; }
    public Map<Integer, Integer> getPlacements() { return placements; };
    public Map<UUID, Integer> getScores() { return scores; }
    public boolean getIsActive() { return isActive; }
    public Class<? extends Listener> getClassType() { return classType; }

    /**
     * SETTERS
     **/
    public void setCountdownLabel(String newLabel) { countdownLabel = newLabel; }
    public void setVotes(int newVote) { votes = newVote; }
    public void setClassType(Class<? extends Listener> newType) { classType = newType; }
    public void setIsActive(boolean value) { isActive = value; }
    public void setCountdownBar(int totalSeconds) {
        DecimalFormat dFormat = new DecimalFormat("00");
        countdownBar.setProgress(totalSeconds / (double) totalSeconds);
        String minutes = String.valueOf(totalSeconds/60);
        String seconds = dFormat.format(totalSeconds % 60);
        countdownBar.setTitle(name + " starts in " + minutes + ":" + seconds);
    }

    public void setBossBarVisibility(boolean value) {
        if (value) {
            for (UUID uuid : plugin.getData().getParticipants()) {
                Player p = Bukkit.getPlayer(uuid);
                if (p != null) {
                    countdownBar.addPlayer(p);
                }
            }
        } else {
            countdownBar.removeAll();
        }
    }

    public void addScore(Player p, int score) {
        UUID uuid = p.getUniqueId();
        scores.compute(uuid, (key, oldScore) -> (oldScore == null) ? score : oldScore + score);
    }

    public Challenge(String name, Map<String, Object> map, final Plugin plugin) {
        this.name = name;
        this.plugin = plugin;
        this.description = (String) map.get("description");
        this.item = new ItemStack(Material.getMaterial((String) map.get("item")), 1);
        this.duration = (int) map.get("duration");
        this.color = TextColor.fromHexString((String) map.get("color"));
        this.votes = 0;
        this.requiredScore = (int) map.get("requiredScore");
        this.placements = new HashMap<>();
        if (map.get("placements") instanceof ConfigurationSection placementsSection) {
            for (String key : placementsSection.getKeys(false)) {
                try {
                    int intKey = Integer.parseInt(key);
                    int value = placementsSection.getInt(key);
                    this.placements.put(intKey, value);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid key format: " + key);
                }
            }
        }
        this.classType = convertNameToClass(name);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("item", this.item.getType().name());
        map.put("duration", this.duration);
        map.put("description", this.description);
        map.put("color", String.valueOf(this.color));
        map.put("votes", this.votes);
        map.put("requiredScore", this.requiredScore);
        map.put("placements", this.placements);
        return map;
    }

    public static Challenge deserialize(String name, Map<String, Object> map, Plugin data) {
        return new Challenge(name, map, data);
    }

    public void registerEvents() {
        try {
            if (Listener.class.isAssignableFrom(classType)) {
                if (!activeListeners.containsKey(classType)) {
                    Listener listenerInstance = classType.getDeclaredConstructor(Plugin.class).newInstance(plugin);
                    Bukkit.getServer().getPluginManager().registerEvents(listenerInstance, plugin);
                    activeListeners.put(classType, listenerInstance);
                    System.out.println("Registered events for " + classType.getSimpleName());
                } else {
                    System.out.println("Events are already registered for " + classType.getSimpleName());
                }
            } else {
                System.out.println("The class does not implement Listener.");
            }
        } catch (Exception e) {
            System.out.println("Failed to register events: " + e);
        }
    }

    public void unregisterEvents() {
        try {
            if (activeListeners.containsKey(classType)) {
                Listener listenerInstance = activeListeners.get(classType);
                HandlerList.unregisterAll(listenerInstance);
                activeListeners.remove(classType);
                System.out.println("Unregistered events for " + classType.getSimpleName());
            } else {
                System.out.println("No events found to unregister for " + classType.getSimpleName());
            }
        } catch (Exception e) {
            System.out.println("Failed to unregister events: " + e);
        }
    }

    private Class<? extends Listener> convertNameToClass(String name) {
        try {
            name = name.replace(" ", "");
            String fullyQualifiedName = "org.ovclub.ovchallenges.challenges." + name;
            Class<?> clazz = Class.forName(fullyQualifiedName);
            if (Listener.class.isAssignableFrom(clazz)) {
                classType = clazz.asSubclass(Listener.class);
                return classType;
            } else {
                System.out.println("The loaded class does not implement Listener interface.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
