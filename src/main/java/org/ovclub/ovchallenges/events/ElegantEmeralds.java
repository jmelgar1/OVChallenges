package org.ovclub.ovchallenges.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Event;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;

public class ElegantEmeralds implements Listener {

	private final Plugin plugin;

	public ElegantEmeralds(Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void breakEmerald(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		
		Material material = b.getType();
		
		if((material == Material.EMERALD_ORE || material == Material.DEEPSLATE_EMERALD_ORE)) {

			boolean contains = plugin.getData().getParticipants().contains(p);
			Event event = plugin.getData().getWinningEvent();
			
			if(!b.getMetadata("placed").isEmpty()) {
				return;
			}
			
			if(contains) {
				event.addScore(p, 1);
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);

				UpdateScoreboard updateScoreboard = new UpdateScoreboard();
				updateScoreboard.run();
			}		
		}
	}
}
