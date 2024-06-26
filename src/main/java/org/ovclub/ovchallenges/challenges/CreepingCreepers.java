package org.ovclub.ovchallenges.challenges;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
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
						challenge.addScore(p, 15);
						p.sendMessage(ConfigManager.KILLED_BABY_MOB
								.replaceText(builder -> builder.matchLiteral("{mob}")
										.replacement(Component.text("charged creeper").color(challenge.getColor())))
								.replaceText(builder -> builder.matchLiteral("{points}")
										.replacement(Component.text("15").color(NamedTextColor.GOLD))));
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
