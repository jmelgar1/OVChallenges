package org.ovclub.ovchallenges.challenges;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.InheritanceAwareMap;
import org.bukkit.Sound;
import org.bukkit.entity.Ageable;
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

				boolean contains = plugin.getData().getParticipants().contains(p.getUniqueId());
				Challenge challenge = plugin.getData().getWinningChallenge();

				if(contains) {
					if(baby) {
						challenge.addScore(p, 2);
						p.sendMessage(ConfigManager.KILLED_BABY_MOB
								.replaceText(builder -> builder.matchLiteral("{mob}")
										.replacement(Component.text("baby pig").color(challenge.getColor())))
								.replaceText(builder -> builder.matchLiteral("{points}")
										.replacement(Component.text("2").color(NamedTextColor.GOLD))));
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
