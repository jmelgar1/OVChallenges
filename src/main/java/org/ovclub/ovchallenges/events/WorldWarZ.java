package org.ovclub.ovchallenges.events;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;

import net.md_5.bungee.api.ChatColor;

public class WorldWarZ implements Listener{

	private final Plugin plugin;

	public WorldWarZ(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void killZombie(EntityDeathEvent e) {
		
		LivingEntity entity = e.getEntity();
		Player p = entity.getKiller();
		if(p == null) {return;}

		if(!(entity.getKiller() == null)) {
			
			if(entity.getType() == EntityType.ZOMBIE ||
			   entity.getType() == EntityType.ZOMBIE_VILLAGER) {

				boolean contains = plugin.getData().getParticipants().contains(p);
				Challenge challenge = plugin.getData().getWinningEvent();

				if(contains) {
					if(entity.getType() == EntityType.ZOMBIE) {
						challenge.addScore(p, 1);
					} else if(entity.getType() == EntityType.ZOMBIE_VILLAGER) {
						challenge.addScore(p, 2);
						p.sendMessage(ChatColor.LIGHT_PURPLE + "You killed a villager zombie! " + ChatColor.GOLD + "+2 Points!");
					}
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
					
					UpdateScoreboard updateScoreboard = new UpdateScoreboard(plugin);
					updateScoreboard.run();
				}
			}
		}
	}
}
