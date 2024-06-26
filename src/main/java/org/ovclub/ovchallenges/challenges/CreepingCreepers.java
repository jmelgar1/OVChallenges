package org.ovclub.ovchallenges.challenges;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;

public class CreepingCreepers implements Listener {

	private final Plugin plugin;

	public CreepingCreepers(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void killCreeper(EntityDeathEvent e) {
		
		LivingEntity entity = e.getEntity();
		Player p = entity.getKiller();
		if(p == null) {return;}

		if(!(entity.getKiller() == null)) {
			if(entity instanceof Creeper) {

				boolean contains = plugin.getData().getParticipants().contains(p.getUniqueId());
				Challenge challenge = plugin.getData().getWinningChallenge();

				if(contains) {
					if(((Creeper) entity).isPowered()) {
						challenge.addScore(p, 5);
						p.sendMessage(ChatColor.LIGHT_PURPLE + "You killed a charged creeper! " + ChatColor.GOLD + "+5 Points!");
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
