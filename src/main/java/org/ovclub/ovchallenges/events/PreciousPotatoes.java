package org.ovclub.ovchallenges.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Event;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;
import org.ovclub.ovchallenges.smalleventmanager.DailyEvents;

import java.util.Map;

public class PreciousPotatoes implements Listener {

	private final Plugin plugin;

	public PreciousPotatoes(Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void breakPotato(BlockBreakEvent e) {
		Block b = e.getBlock();
		BlockData bData = b.getBlockData();
		Player p = e.getPlayer();
		
		Material material = b.getType();

		if(bData instanceof Ageable) {
			if(material == Material.POTATOES) {
				Ageable age = (Ageable) bData;
				if(age.getAge() == age.getMaximumAge()) {
					
					boolean contains = plugin.getData().getParticipants().contains(p);
					Event event = plugin.getData().getWinningEvent();

					if(contains) {
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
