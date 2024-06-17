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

public class Lumberjack implements Listener {

	private final Plugin plugin;

	public Lumberjack(Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void breakLog(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		
		Material material = b.getType();
		
		if((material == Material.OAK_LOG ||
				material == Material.JUNGLE_LOG ||
				material == Material.SPRUCE_LOG ||
				material == Material.ACACIA_LOG ||
				material == Material.DARK_OAK_LOG ||
				material == Material.BIRCH_LOG ||
				material == Material.MANGROVE_LOG ||
				material == Material.CRIMSON_HYPHAE ||
				material == Material.WARPED_HYPHAE ||
				material == Material.STRIPPED_OAK_LOG || 
				material == Material.STRIPPED_JUNGLE_LOG ||
				material == Material.STRIPPED_SPRUCE_LOG ||
				material == Material.STRIPPED_ACACIA_LOG ||
				material == Material.STRIPPED_DARK_OAK_LOG ||
				material == Material.STRIPPED_BIRCH_LOG ||
				material == Material.STRIPPED_MANGROVE_LOG ||
				material == Material.STRIPPED_CRIMSON_HYPHAE ||
				material == Material.STRIPPED_WARPED_HYPHAE ||
				material == Material.CHERRY_LOG ||
				material == Material.STRIPPED_CHERRY_LOG)) {

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
