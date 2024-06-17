package org.ovclub.ovchallenges.object;

import org.ovclub.ovchallenges.Plugin;

import java.util.UUID;

public class PlayerProfile {
    //private final Plugin plugin;

    private String uuid;


    public String getUuid() {return uuid;}
    public void setUuid() {this.uuid = UUID.randomUUID().toString();}


}
