package org.ovclub.ovchallenges.object;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class PlayerData {
    public PlayerData() {
        challenges = new ArrayList<>();
        participants = new ArrayList<>();
        inventories = new HashMap<>();
        winningChallenge = null;
        playerBossBars = new HashMap<>();
    }

    private final List<Challenge> challenges;
    public List<Challenge> getEvents() { return challenges; }
    public void addEvent(Challenge challenge) {
        challenges.add(challenge);}

    private Challenge winningChallenge;
    public void setWinningEvent(Challenge challenge) { winningChallenge = challenge; }
    public Challenge getWinningEvent() { return winningChallenge; }

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
