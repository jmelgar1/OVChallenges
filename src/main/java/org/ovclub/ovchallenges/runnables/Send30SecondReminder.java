package org.ovclub.ovchallenges.runnables;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.util.EventUtility;

import net.md_5.bungee.api.ChatColor;


public class Send30SecondReminder extends BukkitRunnable{

	private final Plugin plugin;

	public Send30SecondReminder(Plugin plugin) {
		this.plugin = plugin;
	}

	public void run() {
		//replace with players who signed up
		for(Player p : plugin.getData().getParticipants()) {
			p.sendMessage(ChatColor.LIGHT_PURPLE + "The event starts in 30 seconds!");
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);	
		}	
	}
}
