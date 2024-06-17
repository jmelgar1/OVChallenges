package org.ovclub.ovchallenges.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Event;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;
import org.ovclub.ovchallenges.smalleventmanager.DailyEvents;

public class CreepingCreepers extends DailyEvents implements Listener {

	private final Plugin plugin;

	public CreepingCreepers(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void killCreeper(EntityDeathEvent e) {
		
		LivingEntity entity = e.getEntity();
		Player p = entity.getKiller();

		if(!(entity.getKiller() == null)) {
			if(entity instanceof Creeper) {

				boolean contains = plugin.getData().getParticipants().contains(p);
				Event event = plugin.getData().getWinningEvent();

				if(contains) {
					int score = winningEventSection.getInt(p.getName());
					
					if(((Creeper) entity).isPowered()) {
						event.addScore(p, 5);
						p.sendMessage(ChatColor.LIGHT_PURPLE + "You killed a charged creeper! " + ChatColor.GOLD + "+5 Points!");
					} else {
						event.addScore(p, 1);
					}
					
					winningEventSection.set(p.getName(), score);
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
					pluginClass.saveEventDataFile();
					
					UpdateScoreboard updateScoreboard = new UpdateScoreboard();
					updateScoreboard.run();
				}
			}
		}
	}
}
