package org.ovclub.ovchallenges.challenges;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;
import org.ovclub.ovchallenges.util.ChallengeUtility;

public class BestBaker implements Listener {

	private final Plugin plugin;

	public BestBaker(Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void craftBread(CraftItemEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) return;

		Player p = (Player) e.getWhoClicked();
		ItemStack craftedItem = e.getInventory().getResult();

		if (craftedItem == null || craftedItem.getType() != Material.BREAD) return;

		boolean contains = plugin.getData().getParticipants().contains(p.getUniqueId());
		Challenge challenge = plugin.getData().getWinningChallenge();

		if (!contains) return;

		int amountToCraft = craftedItem.getAmount();
		if (e.getClick().isShiftClick()) {
			int inventorySpace = ChallengeUtility.calculateInventorySpace(p, craftedItem.getType());
			amountToCraft = Math.min(amountToCraft, inventorySpace);
		}

		if (amountToCraft > 0) {
			challenge.addScore(p, amountToCraft);
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);

			UpdateScoreboard updateScoreboard = new UpdateScoreboard(plugin);
			updateScoreboard.run();
		}
	}
}
