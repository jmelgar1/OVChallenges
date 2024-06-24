package org.ovclub.ovchallenges.challenges;

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

			boolean contains = plugin.getData().getParticipants().contains(p);
			Challenge challenge = plugin.getData().getWinningChallenge();

			if(contains) {
				challenge.addScore(p, damage);
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
				p.sendMessage(ChatColor.LIGHT_PURPLE + "You dealt " + damage + " damage to the Ender Dragon!");
				
				UpdateScoreboard updateScoreboard = new UpdateScoreboard(plugin);
				updateScoreboard.run();
			}
		}
	}
}
