package org.thefruitbox.fbevents.events.killingevents;

import org.bukkit.Sound;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.thefruitbox.fbevents.runnables.UpdateScoreboard;
import org.thefruitbox.fbevents.smalleventmanager.DailyEvents;

import net.md_5.bungee.api.ChatColor;

public class CowTipper extends DailyEvents implements Listener {
	
	@EventHandler
	public void killCow(EntityDeathEvent event) {
		
		LivingEntity entity = event.getEntity();
		Player p = entity.getKiller();
		boolean baby;

		//ensure mob was killed by a player
		if(!(entity.getKiller() == null)) {
			
			if(entity.getType() == EntityType.COW ||
			    entity.getType() == EntityType.MUSHROOM_COW) {
				
				Ageable age = (Ageable) entity;
			    baby = !age.isAdult();
				
				boolean contains = dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants")).contains(p);
				
				if(contains) {
					int score = winningEventSection.getInt(p.getName());
					
					if(baby) {
						score += 2;
						p.sendMessage(ChatColor.LIGHT_PURPLE + "You killed a baby cow! " + ChatColor.GOLD + "+2 Points!");
					} else {
						score += 1;
					}
					
					winningEventSection.set(p.getName(), score);
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
					mainClass.saveEventDataFile();
					
					UpdateScoreboard updateScoreboard = new UpdateScoreboard();
					updateScoreboard.run();
				}
			}
		}
	}
}
