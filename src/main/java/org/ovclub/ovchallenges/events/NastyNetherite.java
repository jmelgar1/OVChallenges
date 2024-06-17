package org.ovclub.ovchallenges.events;

import java.util.List;

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

public class NastyNetherite implements Listener {

	private final Plugin plugin;

	public NastyNetherite(Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void breakNetherite(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		
		Material material = b.getType();
		
		//check for block type (aka. emerald_ore, diamond ore, etc)
		if(material == Material.ANCIENT_DEBRIS) {

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
