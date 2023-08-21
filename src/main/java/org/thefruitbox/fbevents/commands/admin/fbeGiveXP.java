package org.thefruitbox.fbevents.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.thefruitbox.fbevents.Main;
//import org.thefruitbox.fbevents.bukkitevents.EditPlayerPoints;
import org.thefruitbox.fbevents.bukkitevents.playerEvents;
import org.thefruitbox.fbevents.runnables.SendDailyEventVote;

public class fbeGiveXP implements CommandExecutor{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	//EditPlayerPoints epp1 = new EditPlayerPoints();
	
	SendDailyEventVote dailyVote = new SendDailyEventVote();
	
	ConfigurationSection playerDataConfig = mainClass.getPlayerData();
	
	playerEvents pe1 = new playerEvents();
	
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
        			
        			//epp1.addLevelXP(p, Integer.valueOf(XP), playerUUID);
        			
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
