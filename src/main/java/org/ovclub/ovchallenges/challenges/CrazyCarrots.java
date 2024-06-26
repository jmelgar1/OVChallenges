package org.ovclub.ovchallenges.challenges;

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
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;

public class CrazyCarrots implements Listener {

	private final Plugin plugin;

	public CrazyCarrots(Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void breakCarrot(BlockBreakEvent e) {
		Block b = e.getBlock();
		BlockData bData = b.getBlockData();
		Player p = e.getPlayer();
		Material material = b.getType();
		
		if(bData instanceof Ageable) {
			if(material == Material.CARROTS) {
				Ageable age = (Ageable) bData;
				if(age.getAge() == age.getMaximumAge()) {

					boolean contains = plugin.getData().getParticipants().contains(p.getUniqueId());
					Challenge challenge = plugin.getData().getWinningChallenge();

					if(contains) {
						challenge.addScore(p, 1);
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);

						UpdateScoreboard updateScoreboard = new UpdateScoreboard(plugin);
						updateScoreboard.run();
					}
				}
			}		
		}
	}
}
