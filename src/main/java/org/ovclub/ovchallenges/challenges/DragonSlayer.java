package org.ovclub.ovchallenges.challenges;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;

import net.md_5.bungee.api.ChatColor;

public class DragonSlayer implements Listener{

	private final Plugin plugin;

	public DragonSlayer(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void dealDamageToEnderDragon(EntityDamageByEntityEvent e) {
		
		Entity entity = e.getEntity();
		Player p;
		int damage = (int)Math.ceil(e.getDamage());

		//ADD ABILITY TO COUNT BROKEN END CRYSTALS AS DAMAGE

		if(entity.getType() == EntityType.ENDER_DRAGON) {
			if((e.getDamager() instanceof Arrow a)) {
                p = (Player) a.getShooter();
			} else if(e.getDamager() instanceof Trident t) {
                p = (Player) t.getShooter();
			} else {
				p = (Player) e.getDamager();
			}

			if(p == null) {return;}

			boolean contains = plugin.getData().getParticipants().contains(p.getUniqueId());
			Challenge challenge = plugin.getData().getWinningChallenge();

			if(contains) {
				challenge.addScore(p, damage);
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
				p.sendMessage(ConfigManager.DEALT_DAMAGE
						.replaceText(builder -> builder.matchLiteral("{damage}")
								.replacement(Component.text(String.valueOf(damage)).color(TextColor.fromHexString("#df3227"))))
						.replaceText(builder -> builder.matchLiteral("{mob}")
								.replacement(Component.text("Ender Dragon").color(challenge.getColor()))));
				
				UpdateScoreboard updateScoreboard = new UpdateScoreboard(plugin);
				updateScoreboard.run();
			}
		}
	}
}
