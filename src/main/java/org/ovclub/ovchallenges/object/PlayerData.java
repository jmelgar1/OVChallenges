package org.ovclub.ovchallenges.object;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
        isVotingPeriod = false;
    }

    private boolean isVotingPeriod;
    public void enableVotingPeriod() {isVotingPeriod = true;}
    public void disableVotingPeriod() {isVotingPeriod = false;}
    public boolean isVotingEnabled() {return isVotingPeriod;}

    private final List<Challenge> challenges;
    public List<Challenge> getChallenges() { return challenges; }
    public Challenge getChallengeByName(Component name) {
        for(Challenge challenge : challenges) {
            if(challenge.getName().equals(name.toString())) {
                return challenge;
            }
        }
        return null;
    }

    public void addChallenge(Challenge challenge) {
        challenges.add(challenge);}

    private Challenge winningChallenge;
    public void setWinningChallenge(Challenge challenge) { winningChallenge = challenge; }
    public Challenge getWinningChallenge() { return winningChallenge; }

    private final List<Player> participants;
    public List<Player> getParticipants() { return participants; }
    public void addParticipant(Player p) { participants.add(p); }
    public void removeParticipant(Player p) { participants.remove(p); }
    public void clearParticipants() { participants.clear(); }

    private final HashMap<UUID, Inventory> inventories;
    public HashMap<UUID, Inventory> getInventories(){
        return inventories;
    }
    public void addInventory(UUID uuid, Inventory inv) {
        inventories.putIfAbsent(uuid, inv);
    }

    private final HashMap<Player, BossBar> playerBossBars;
    public HashMap<Player, BossBar> getAllPlayerBossBars(){
        return playerBossBars;
    }
    public BossBar getPlayerBossBar(Player p){
        return playerBossBars.get(p);
    }
    public void addBossBar(Player p, BossBar bossBar) { playerBossBars.put(p, bossBar); }

    //check if player is in event list
    public boolean checkIfSignedUp(Player p) {
        return(participants.contains(p));
    }

    public void clearAllInventories() {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            UUID playerUUID = p.getUniqueId();
            Inventory voteInv = inventories.get(playerUUID);
            if(voteInv != null) {
                voteInv.clear();
            }
        }
    }
}
