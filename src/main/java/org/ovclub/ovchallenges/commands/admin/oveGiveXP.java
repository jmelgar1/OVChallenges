package org.ovclub.ovchallenges.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.ovclub.ovchallenges.Plugin;

public class oveGiveXP implements CommandExecutor{
	
	//Plugin instance
	private Plugin pluginClass = Plugin.getInstance();
	
	ConfigurationSection playerDataConfig = pluginClass.getPlayerData();
	
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
        	    		sender.sendMessage(pluginClass.prefix + "Added " + XP + " to " + playerName);
        	    	}
        		}
        	}
        }	
		return true;
	}
}
