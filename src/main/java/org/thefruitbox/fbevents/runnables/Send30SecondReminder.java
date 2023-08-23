package org.thefruitbox.fbevents.runnables;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.thefruitbox.fbevents.Main;
import org.thefruitbox.fbevents.managers.DetermineEventData;

import net.md_5.bungee.api.ChatColor;


public class Send30SecondReminder extends BukkitRunnable{
    
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();

	public void run() {
		//replace with players who signed up
		for(Player p : dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants"))) {		
			p.sendMessage(ChatColor.LIGHT_PURPLE + "The event starts in 30 seconds!");
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);	
		}	
	}
}
