package org.ovclub.ovchallenges.challenges;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;
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

			boolean contains = plugin.getData().getParticipants().contains(p.getUniqueId());
			Challenge challenge = plugin.getData().getWinningChallenge();

			if(contains) {
				Item item = (Item) e.getCaught();

				if (item.getItemStack().getType() == Material.COD) {
					challenge.addScore(p, 1);
					p.sendMessage(ConfigManager.CAUGHT_ITEM
							.replaceText(builder -> builder.matchLiteral("{item}")
									.replacement(Component.text("cod").color(TextColor.fromHexString("#659285"))))
							.replaceText(builder -> builder.matchLiteral("{points}")
									.replacement(Component.text("1").color(NamedTextColor.GOLD))));
				} else if (item.getItemStack().getType() == Material.SALMON) {
					challenge.addScore(p, 2);
					p.sendMessage(ConfigManager.CAUGHT_ITEM
							.replaceText(builder -> builder.matchLiteral("{item}")
									.replacement(Component.text("salmon").color(TextColor.fromHexString("#ff9999"))))
							.replaceText(builder -> builder.matchLiteral("{points}")
									.replacement(Component.text("2").color(NamedTextColor.GOLD))));
				} else if (item.getItemStack().getType() == Material.PUFFERFISH) {
					challenge.addScore(p, 3);
					p.sendMessage(ConfigManager.CAUGHT_ITEM
							.replaceText(builder -> builder.matchLiteral("{item}")
									.replacement(Component.text("pufferfish").color(TextColor.fromHexString("#f1c232"))))
							.replaceText(builder -> builder.matchLiteral("{points}")
									.replacement(Component.text("3").color(NamedTextColor.GOLD))));
				} else if (item.getItemStack().getType() == Material.TROPICAL_FISH) {
					challenge.addScore(p, 4);
					p.sendMessage(ConfigManager.CAUGHT_ITEM
							.replaceText(builder -> builder.matchLiteral("{item}")
									.replacement(Component.text("tropical fish").color(TextColor.fromHexString("#ff5000"))))
							.replaceText(builder -> builder.matchLiteral("{points}")
									.replacement(Component.text("4").color(NamedTextColor.GOLD))));
					
				//if player catches treasure
				} else if (item.getItemStack().getType() == Material.ENCHANTED_BOOK ||
						  item.getItemStack().getType() == Material.FISHING_ROD ||
						  item.getItemStack().getType() == Material.NAME_TAG ||
						  item.getItemStack().getType() == Material.NAUTILUS_SHELL ||
						  item.getItemStack().getType() == Material.SADDLE) {
					challenge.addScore(p, 7);
					p.sendMessage(ConfigManager.CAUGHT_ITEM
							.replaceText(builder -> builder.matchLiteral("{item}")
									.replacement(Component.text("treasure").color(TextColor.fromHexString("#f0d66a"))))
							.replaceText(builder -> builder.matchLiteral("{points}")
									.replacement(Component.text("7").color(NamedTextColor.GOLD))));
				} 

				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
				
				UpdateScoreboard updateScoreboard = new UpdateScoreboard(plugin);
				updateScoreboard.run();
			}
		}
	}
}
