package org.thefruitbox.fbevents.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.thefruitbox.fbevents.Main;

import net.md_5.bungee.api.ChatColor;

public class fbereload implements CommandExecutor{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("fbereload")) {
        	p.sendMessage(ChatColor.GREEN + "Reloaded DailyEvents Config");
        	mainClass.reloadPlayerDataFile();
        	mainClass.reloadEventDataFile();
        	mainClass.reloadConfig();
        }	
		return true;
	}
}
