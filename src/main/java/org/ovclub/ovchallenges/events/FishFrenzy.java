package org.ovclub.ovchallenges.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Event;
import org.ovclub.ovchallenges.runnables.UpdateScoreboard;

import net.md_5.bungee.api.ChatColor;

public class FishFrenzy implements Listener {

	private final Plugin plugin;

	public FishFrenzy(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void catchFish(PlayerFishEvent e) {
		
		Player p = e.getPlayer();

		if(e.getState() == State.CAUGHT_FISH) {

			boolean contains = plugin.getData().getParticipants().contains(p);
			Event event = plugin.getData().getWinningEvent();

			if(contains) {
				Item item = (Item) e.getCaught();

				if (item.getItemStack().getType() == Material.COD) {
					event.addScore(p, 1);
					p.sendMessage(ChatColor.LIGHT_PURPLE + "You caught cod! " + ChatColor.GOLD + "+1 point");
				} else if (item.getItemStack().getType() == Material.SALMON) {
					event.addScore(p, 2);
					p.sendMessage(ChatColor.LIGHT_PURPLE + "You caught salmon! " + ChatColor.GOLD + "+2 points");
				} else if (item.getItemStack().getType() == Material.PUFFERFISH) {
					event.addScore(p, 3);
					p.sendMessage(ChatColor.LIGHT_PURPLE + "You caught pufferfish! " + ChatColor.GOLD + "+3 points");
				} else if (item.getItemStack().getType() == Material.TROPICAL_FISH) {
					event.addScore(p, 4);
					p.sendMessage(ChatColor.LIGHT_PURPLE + "You caught tropical fish! " + ChatColor.GOLD + "+4 points");
					
				//if player catches treasure
				} else if (item.getItemStack().getType() == Material.ENCHANTED_BOOK ||
						  item.getItemStack().getType() == Material.FISHING_ROD ||
						  item.getItemStack().getType() == Material.NAME_TAG ||
						  item.getItemStack().getType() == Material.NAUTILUS_SHELL ||
						  item.getItemStack().getType() == Material.SADDLE) {
					event.addScore(p, 7);
					p.sendMessage(ChatColor.LIGHT_PURPLE + "You caught treasure! " + ChatColor.GOLD + "+7 points");
				} 

				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
				
				UpdateScoreboard updateScoreboard = new UpdateScoreboard();
				updateScoreboard.run();
			}
		}
	}
}
