package org.ovclub.ovchallenges.events;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Event;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;

public class BadBats implements Listener {

	private final Plugin plugin;

	public BadBats(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void killBat(EntityDeathEvent e) {
		
		LivingEntity entity = e.getEntity();
		Player p = entity.getKiller();
		
		//ensure mob was killed by a player
		if(!(entity.getKiller() == null)) {
			if(entity.getType() == EntityType.BAT) {

				boolean contains = plugin.getData().getParticipants().contains(p);
				Event event = plugin.getData().getWinningEvent();

				if(contains) {
					if(p != null) {
						event.addScore(p, 1);
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
						UpdateScoreboard updateScoreboard = new UpdateScoreboard();
						updateScoreboard.run();
					}
				}
			}
		}
	}
}
