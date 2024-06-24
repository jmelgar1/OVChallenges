package org.ovclub.ovchallenges.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.util.ChatUtility;

import java.util.UUID;

public class PlayerEvents implements Listener {

    private final Plugin plugin;
    public PlayerEvents(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        final Player p = (Player) e.getWhoClicked();
        UUID playerUUID = p.getUniqueId();
        if(!e.getInventory().equals(plugin.getData().getInventories().get(playerUUID))) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        Component itemName = clickedItem.getItemMeta().displayName();
        Challenge challenge = plugin.getData().getChallengeByName(itemName);

        challenge.setVotes(challenge.getVotes() + 1);
        plugin.getData().addParticipant(p);

        Bukkit.broadcast(Component.text(p.getName() + " voted for " + itemName).color(ChatUtility.plugin_color));
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1F, 1F);
        p.closeInventory();
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if(e.getInventory().equals(plugin.getData().getInventories().get(e.getWhoClicked().getUniqueId()))) {
            e.setCancelled(true);
        }
    }
}
