package org.ovclub.ovchallenges.events;

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
import org.ovclub.ovchallenges.object.Event;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;

public class ScrumptiousStew implements Listener {

	private final Plugin plugin;

	public ScrumptiousStew(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void craftBread(CraftItemEvent e) {
		
		Player p = (Player) e.getWhoClicked();
		
		ItemStack craftedItem = e.getInventory().getResult();
		
		Inventory inv = e.getInventory();
		
		ClickType clickType = e.getClick();
		
		int realAmount = craftedItem.getAmount();

		boolean contains = plugin.getData().getParticipants().contains(p);
		Event event = plugin.getData().getWinningEvent();

		if(contains) {

			if(craftedItem.getType() == Material.SUSPICIOUS_STEW ||
			craftedItem.getType() == Material.MUSHROOM_STEW ||
			craftedItem.getType() == Material.RABBIT_STEW) {

				if(clickType.isShiftClick()) {
					int lowerAmount = craftedItem.getMaxStackSize() + 1000;
					for(ItemStack actualItem : inv.getContents()) {
						if(!actualItem.getType().isAir() && lowerAmount > actualItem.getAmount() && !actualItem.getType().equals(craftedItem.getType())) {
							lowerAmount = actualItem.getAmount();
						}
						realAmount = lowerAmount * craftedItem.getAmount();
					}
				}
				event.addScore(p, realAmount);
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
				UpdateScoreboard updateScoreboard = new UpdateScoreboard();
				updateScoreboard.run();
			}
		}
	}
}
