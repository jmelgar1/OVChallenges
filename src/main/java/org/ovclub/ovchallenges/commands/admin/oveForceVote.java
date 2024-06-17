package org.ovclub.ovchallenges.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.runnables.SendDailyEventVote;

public class oveForceVote implements CommandExecutor{
	
	//Plugin instance
	private Plugin pluginClass = Plugin.getInstance();
	
	SendDailyEventVote dailyVote = new SendDailyEventVote();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("fbeforcevote")) {
        	if(p.hasPermission("fbe.forcevote")) {	
        		dailyVote.runTaskTimer(pluginClass, 0, 30000);
        		p.sendMessage(pluginClass.prefix + "Vote started successfully!");
        	}
        }	
		return true;
	}
}
