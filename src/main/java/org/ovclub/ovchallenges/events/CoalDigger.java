package org.ovclub.ovchallenges.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;

public class CoalDigger implements Listener {

	private final Plugin plugin;

	public CoalDigger(Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void breakCoal(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		
		Material material = b.getType();

		if((material == Material.COAL_ORE || material == Material.DEEPSLATE_COAL_ORE)) {
			boolean contains = plugin.getData().getParticipants().contains(p);
			Challenge challenge = plugin.getData().getWinningEvent();

			if(!b.getMetadata("placed").isEmpty()) {
				return;
			}
			
			if(contains) {
				challenge.addScore(p, 1);
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
				UpdateScoreboard updateScoreboard = new UpdateScoreboard(plugin);
				updateScoreboard.run();
			}		
		}
	}
}
