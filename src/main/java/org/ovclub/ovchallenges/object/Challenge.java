package org.ovclub.ovchallenges.object;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.ovclub.ovchallenges.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Challenge implements Listener, ConfigurationSerializable {
    private Plugin data;

    private String name;
    public String getName() { return name; }

    private String description;

    private ItemStack item;
    private int duration;
    public int getDuration() { return duration; }

    private String countdownLabel;
    public void setCountdownLabel(String newLabel) { countdownLabel = newLabel; }
    public String getCountdownLabel() { return countdownLabel; }

    private TextColor color;
    public TextColor getColor() { return color; }

//    private boolean isActive = false;
//    public boolean getIsActive() { return isActive; }
//    public void setIsActive(boolean value) { isActive = value; }

    private int votes;
    public int getVotes() { return votes; }
    public void setVotes(int newVote) { votes = newVote; }

    private int requiredScore;
    public int getRequiredScore() { return requiredScore; }

    private Map<Integer, Integer> placements;
    public Map<Integer, Integer> getPlacements;

    private Map<UUID, Integer> scores;
    public Map<UUID, Integer> getScores() { return scores; }
    public int getScore(Player p) {
        return scores.get(p.getUniqueId());
    }
    public void addScore(Player p, int value) {
        UUID uuid = p.getUniqueId();
        scores.merge(uuid, value, Integer::sum);
    }

    private Class<? extends Listener> classType;
    public Class<? extends Listener> getClassType() { return classType; }
    public void setClassType(Class<? extends Listener> newType) { classType = newType; }

    public Challenge(String name, Map<String, Object> map, final Plugin data) {
        this.name = name;
        this.data = data;
        this.description = (String) map.get("description");
        this.item = new ItemStack(Material.getMaterial((String) map.get("item")), 1);
        this.duration = (int) map.get("duration");
        this.color = TextColor.fromHexString((String) map.get("color"));
        this.votes = 0;
        this.requiredScore = (int) map.get("requiredScore");
        this.placements = new HashMap<>();
        Map<String, Object> placementsMap = (Map<String, Object>) map.get("placements");
        if (placementsMap != null) {
            for (Map.Entry<String, Object> entry : placementsMap.entrySet()) {
                try {
                    int key = Integer.parseInt(entry.getKey());
                    int value = (int) entry.getValue();
                    this.placements.put(key, value);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid key format: " + entry.getKey());
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
                Listener listenerInstance = classType.getDeclaredConstructor().newInstance();
                Bukkit.getServer().getPluginManager().registerEvents(listenerInstance, data);
            } else {
                System.out.println("The class does not implement Listener.");
            }
        } catch (Exception e) {
            System.out.println("Failed to register events: " + e);
        }
    }

    public void unregisterEvents() {
        try {
            if (Listener.class.isAssignableFrom(classType)) {
                Listener listenerInstance = classType.getDeclaredConstructor().newInstance();
                HandlerList.unregisterAll(listenerInstance);
            } else {
                System.out.println("The class does not implement Listener.");
            }
        } catch (Exception e) {
            System.out.println("Failed to register events: " + e);
        }
    }

    private Class<? extends Listener> convertNameToClass(String name) {
        try {
            name = name.replace(" ", "");
            String fullyQualifiedName = "org.ovclub.ovchallenges.events." + name;
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
