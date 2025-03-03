package org.ovclub.ovchallenges.challenges;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;

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

				boolean contains = plugin.getData().getParticipants().contains(p.getUniqueId());
				Challenge challenge = plugin.getData().getWinningChallenge();

				if(contains) {
					if(entity.getType() == EntityType.ZOMBIE) {
						challenge.addScore(p, 1);
					} else if(entity.getType() == EntityType.ZOMBIE_VILLAGER) {
						challenge.addScore(p, 2);
						p.sendMessage(ConfigManager.KILLED_BABY_MOB
								.replaceText(builder -> builder.matchLiteral("{mob}")
										.replacement(Component.text("villager zombie").color(challenge.getColor())))
								.replaceText(builder -> builder.matchLiteral("{points}")
										.replacement(Component.text("2").color(NamedTextColor.GOLD))));
					}
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
					
					UpdateScoreboard updateScoreboard = new UpdateScoreboard(plugin);
					updateScoreboard.run();
				}
			}
		}
	}
}
