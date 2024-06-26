package org.ovclub.ovchallenges.object;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.ovclub.ovchallenges.util.ChatUtility;

import java.util.*;

public class PlayerData {
    public PlayerData() {
        challenges = new ArrayList<>();
        participants = new ArrayList<>();
        inventories = new HashMap<>();
        winningChallenge = null;
        isVotingPeriod = false;
    }

    private boolean isVotingPeriod;
    private final List<Challenge> challenges;
    private Challenge winningChallenge;
    private final List<UUID> participants;
    private final HashMap<UUID, Inventory> inventories;

    public Challenge getWinningChallenge() { return winningChallenge; }
    public List<Challenge> getChallenges() { return challenges; }
    public List<UUID> getParticipants() {return participants;}
    public HashMap<UUID, Inventory> getInventories(){return inventories;}
    public Challenge getChallengeByName(Component itemName) {
        String name = ChatUtility.getPlainTextFromComponent(itemName);
        for(Challenge challenge : challenges) {
            if(challenge.getName().equals(name)) {
                return challenge;
            }
        }
        return null;
    }

    public void enableVotingPeriod() {isVotingPeriod = true;}
    public void disableVotingPeriod() {isVotingPeriod = false;}
    public boolean isVotingEnabled() {return isVotingPeriod;}


    public void addChallenge(Challenge challenge) {challenges.add(challenge);}
    public void shuffleChallenges() {Collections.shuffle(challenges);}

    public void setWinningChallenge(Challenge challenge) { winningChallenge = challenge; }

    public void addParticipant(Player p) {
        participants.add(p.getUniqueId());
    }
    public void removeParticipant(OfflinePlayer p) {
        participants.remove(p.getUniqueId());
    }
    public void clearParticipants() {
        participants.clear();
    }

    public void addInventory(UUID uuid, Inventory inv) {
        if(inventories.get(uuid) != null) {
            inventories.remove(uuid);
        }
        inventories.putIfAbsent(uuid, inv);
    }

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
