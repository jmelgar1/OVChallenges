package org.ovclub.ovchallenges.object;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class PlayerData {
    public PlayerData() {
        events = new ArrayList<>();
        participants = new ArrayList<>();
        inventories = new HashMap<>();
        winningEvent = null;
        playerBossBars = new HashMap<>();
    }

    private final List<Event> events;
    public List<Event> getEvents() { return events; }
    public void addEvent(Event event) {events.add(event);}

//    public static void clearVotes() {
//        for(Event event : this.events) {
//            if(event.getVotes() != 0) {
//                event.setVotes(0);
//            }
//        }
//        System.out.println("[OVChallenges] Votes cleared!");
//    }

    private Event winningEvent;
    public void setWinningEvent(Event event) { winningEvent = event; }
    public Event getWinningEvent() { return winningEvent; }

    private final List<Player> participants;
    public List<Player> getParticipants() { return participants; }
    public void removeParticipant(Player p) { participants.remove(p); }
    public void clearParticipants() { participants.clear(); }

    private final HashMap<UUID, Inventory> inventories;
    public HashMap<UUID, Inventory> getInventories(){
        return inventories;
    }

    private final HashMap<Player, BossBar> playerBossBars;
    public HashMap<Player, BossBar> getAllPlayerBossBars(){
        return playerBossBars;
    }
    public BossBar getPlayerBossBar(Player p){
        return playerBossBars.get(p);
    }
    public void addBossBar(Player p, BossBar bossBar) { playerBossBars.put(p, bossBar); }
}
