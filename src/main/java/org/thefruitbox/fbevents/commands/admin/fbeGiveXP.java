package org.thefruitbox.fbevents.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.thefruitbox.fbevents.Main;

public class fbeGiveXP implements CommandExecutor{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	ConfigurationSection playerDataConfig = mainClass.getPlayerData();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("fbegivexp")) {
        	if(sender.hasPermission("fbe.givexp")) {	
        		if(!args[0].isEmpty() && !args[1].isEmpty()) {
        			String playerName = args[0];
        			String XP = args[1];
        			
        			String playerUUIDString = "";
        			ConfigurationSection playerUUID = playerDataConfig.getConfigurationSection("none");
        			Player p = Bukkit.getServer().getPlayer(playerName);
        			
        			if(p != null) {
        				playerUUIDString = Bukkit.getServer().getPlayer(playerName).getUniqueId().toString();
        				playerUUID = playerDataConfig.getConfigurationSection(playerUUIDString);
        			} else {
        				playerUUIDString = Bukkit.getServer().getOfflinePlayer(playerName).getUniqueId().toString();
        				playerUUID = playerDataConfig.getConfigurationSection(playerUUIDString);
        			}

        	    	
        	    	if(sender instanceof Player) {
        	    		sender.sendMessage(mainClass.prefix + "Added " + XP + " to " + playerName);
        	    	}
        		}
        	}
        }	
		return true;
	}
}
