package org.ovclub.ovchallenges.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlayerEvents implements Listener {

    private final Plugin plugin;
    public PlayerEvents(Plugin plugin) {
        this.plugin = plugin;
    }

    List<Material> blocks = Arrays.asList(Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE,
            Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.ANCIENT_DEBRIS,
            Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE, Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE,
            Material.OAK_LOG, Material.JUNGLE_LOG, Material.SPRUCE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG,Material.BIRCH_LOG,
            Material.MANGROVE_LOG, Material.CRIMSON_HYPHAE, Material.WARPED_HYPHAE, Material.STRIPPED_OAK_LOG, Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_SPRUCE_LOG, Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_DARK_OAK_LOG, Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_MANGROVE_LOG, Material.STRIPPED_CRIMSON_HYPHAE, Material.STRIPPED_WARPED_HYPHAE, Material.CHERRY_LOG, Material.STRIPPED_CHERRY_LOG);

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        final Player p = (Player) e.getWhoClicked();
        UUID playerUUID = p.getUniqueId();
        if(!e.getInventory().equals(plugin.getData().getInventories().get(playerUUID))) return;
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        Component itemName = clickedItem.getItemMeta().displayName();
        Challenge challenge = plugin.getData().getChallengeByName(itemName);

        challenge.setVotes(challenge.getVotes() + 1);
        plugin.getData().addParticipant(p);

        Bukkit.broadcast(ConfigManager.PLAYER_VOTE
                .replaceText(builder -> builder.matchLiteral("{player}")
                .replacement(p.getName()))
                .replaceText(builder -> builder.matchLiteral("{challenge}")
                .replacement(Component.text(challenge.getName()).color(challenge.getColor()))));

        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1F, 1F);
        p.closeInventory();
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if(e.getInventory().equals(plugin.getData().getInventories().get(e.getWhoClicked().getUniqueId()))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Challenge challenge = plugin.getData().getWinningChallenge();
        if(challenge != null && plugin.getData().getParticipants().contains(p.getUniqueId()) && !challenge.getIsActive()) {
            BossBar bar = challenge.getCountdownBar();
            bar.addPlayer(p);
        }
    }

    @EventHandler
    public void blockPlaced(BlockPlaceEvent e){
        Block b = e.getBlock();
        Material material = b.getType();

        if(blocks.contains(material)) {
            b.setMetadata("challenge", new FixedMetadataValue(plugin, true));
        }
    }
}
