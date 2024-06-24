package org.ovclub.ovchallenges.challenges;

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

public class CowTipper implements Listener {

	private final Plugin plugin;

	public CowTipper(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void killCow(EntityDeathEvent e) {
		
		LivingEntity entity = e.getEntity();
		Player p = entity.getKiller();
		if(p == null) {return;}
		boolean baby;

		if(!(entity.getKiller() == null)) {
			if(entity.getType() == EntityType.COW ||
			    entity.getType() == EntityType.MOOSHROOM) {
				
				Ageable age = (Ageable) entity;
			    baby = !age.isAdult();

				boolean contains = plugin.getData().getParticipants().contains(p);
				Challenge challenge = plugin.getData().getWinningChallenge();
				
				if(contains) {
					if(baby) {
						challenge.addScore(p, 2);
						p.sendMessage(ChatColor.LIGHT_PURPLE + "You killed a baby cow! " + ChatColor.GOLD + "+2 Points!");
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
