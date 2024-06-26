package org.ovclub.ovchallenges.challenges;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
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

public class SpookySkeletons implements Listener {

	private final Plugin plugin;

	public SpookySkeletons(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void killCow(EntityDeathEvent e) {
		
		LivingEntity entity = e.getEntity();
		Player p = entity.getKiller();
		if(p == null) {return;}

		if(!(entity.getKiller() == null)) {
			
			if(entity.getType() == EntityType.SKELETON ||
			    entity.getType() == EntityType.WITHER_SKELETON ||
				entity.getType() == EntityType.STRAY ||
				entity.getType() == EntityType.BOGGED) {

				boolean contains = plugin.getData().getParticipants().contains(p.getUniqueId());
				Challenge challenge = plugin.getData().getWinningChallenge();

				if(contains) {
					if(entity.getEquipment() != null) {
						challenge.addScore(p, 3);
						p.sendMessage(ConfigManager.KILLED_MOB_WITH_ARMOR
								.replaceText(builder -> builder.matchLiteral("{mob}")
										.replacement(Component.text("skeleton").color(challenge.getColor())))
								.replaceText(builder -> builder.matchLiteral("{points}")
										.replacement(Component.text("3").color(NamedTextColor.GOLD))));
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
