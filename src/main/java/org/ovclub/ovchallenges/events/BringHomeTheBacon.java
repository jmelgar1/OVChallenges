package org.ovclub.ovchallenges.events;

import org.bukkit.Sound;
import org.bukkit.entity.Ageable;
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

public class BringHomeTheBacon implements Listener {

	private final Plugin plugin;

	public BringHomeTheBacon(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void killPig(EntityDeathEvent e) {
		
		LivingEntity entity = e.getEntity();
		Player p = entity.getKiller();
		if(p == null) {return;}

		boolean baby;
		if(!(entity.getKiller() == null)) {
			if(entity.getType() == EntityType.PIG) {
				
				Ageable age = (Ageable) entity;
			    baby = !age.isAdult();

				boolean contains = plugin.getData().getParticipants().contains(p);
				Challenge challenge = plugin.getData().getWinningEvent();

				if(contains) {
					if(baby) {
						challenge.addScore(p, 2);
						p.sendMessage(ChatColor.LIGHT_PURPLE + "You killed a baby pig! " + ChatColor.GOLD + "+2 Points!");
					} else {
						challenge.addScore(p, 1);
					}
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
					UpdateScoreboard updateScoreboard = new UpdateScoreboard(plugin);
					updateScoreboard.run();
				}
			}
		}
	}
}
