package org.ovclub.ovchallenges.commands;

import org.bukkit.entity.Player;
import org.ovclub.ovchallenges.Plugin;

public interface SubCommand {

    abstract String getDescription();
    abstract String getSyntax();
    abstract String getPermission();
    abstract void perform(Player p, String[] args, Plugin plugin);
}
