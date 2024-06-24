//package org.ovclub.ovchallenges.commands.admin;
//
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.ovclub.ovchallenges.Plugin;
//
//import net.md_5.bungee.api.ChatColor;
//
//public class oveReload implements CommandExecutor{
//
//	//Plugin instance
//	private Plugin pluginClass = Plugin.getInstance();
//
//	@Override
//	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
//		Player p = (Player) sender;
//        if(cmd.getName().equalsIgnoreCase("oveReload")) {
//        	p.sendMessage(ChatColor.GREEN + "Reloaded DailyEvents Config");
//        	pluginClass.reloadPlayerDataFile();
//        	pluginClass.reloadEventDataFile();
//        	pluginClass.reloadConfig();
//        }
//		return true;
//	}
//}
