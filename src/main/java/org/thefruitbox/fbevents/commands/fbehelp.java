package org.thefruitbox.fbevents.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class fbehelp implements CommandExecutor {
    String cmd1 = "fbehelp";

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(cmd.getName().equalsIgnoreCase(cmd1)) {
                p.sendMessage(ChatColor.GRAY + "------" + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "FBEvents" + ChatColor.GRAY + "------");
                p.sendMessage(ChatColor.YELLOW + "/fbehelp (This page)");
                p.sendMessage(ChatColor.YELLOW + "/fbprofile [username] (View OnlyVanilla profiles)");
            }
        }
        return true;
    }
}