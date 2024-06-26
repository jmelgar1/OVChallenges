package org.ovclub.ovchallenges.commands.subcommands;

import java.util.UUID;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.commands.SubCommand;
import org.ovclub.ovchallenges.managers.InventoryManager;

import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.util.ChatUtility;

public class voteCommand implements SubCommand {

    private static Inventory inv;

    private final Plugin plugin;

    public voteCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getDescription() {
        return "Open challenges vote GUI";
    }

    @Override
    public String getSyntax() {
        return "/ch vote";
    }

    @Override
    public String getPermission() {
        return "challenges.player.vote";
    }

    @Override
    public void perform(Player p, String[] args, Plugin plugin) {
        UUID playerUUID = p.getUniqueId();
        if(!plugin.getData().isVotingEnabled()) {
            p.sendMessage(ConfigManager.VOTING_ENDED);
            return;
        }

        if(plugin.getData().checkIfSignedUp(p)) {
            p.sendMessage(ConfigManager.ALREADY_VOTED);
            return;
        }

        inv = Bukkit.createInventory(p, 9, "OVClub | Challenges Vote");
        plugin.getData().addInventory(playerUUID, inv);
        openInventory(p, plugin.getData().getInventories().get(playerUUID));
        initializeItems();
    }

    public void initializeItems() {

        int inventorySlot = 0;
        for(Challenge challenge : plugin.getData().getChallenges().subList(0,5)) {
//            ConfigurationSection eventName = pluginClass.getSmallEvents().getConfigurationSection(names);
//
//            String description = eventName.getString("description");
//            String item = eventName.getString("item");
//            String name = eventName.getName();
//            String color = eventName.getString("color");
//            int duration = eventName.getInt("duration");
//            int requiredScore = eventName.getInt("requiredscore");
//
//            //add event to eventVote HashMap with 0 votes
//            eventVote.put(name, eventName);
//
//            ConfigurationSection eventPlacement = eventName.getConfigurationSection("placements");
//            int firstPlaceSponges = eventPlacement.getInt("first");
//            int secondPlaceSponges = eventPlacement.getInt("second");
//            int thirdPlaceSponges = eventPlacement.getInt("third");

            inv.setItem(inventorySlot,
                    InventoryManager.createGuiItem(challenge.getItem().getType(),
                            Component.text(challenge.getName()).color(challenge.getColor()).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false),
                            Component.text(""),
                            Component.text("Description:").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                            Component.text(challenge.getDescription()).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                            Component.text(""),
                            Component.text("Duration: ").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                            Component.text(challenge.getDuration() + " minutes").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                            Component.text(""),
                            Component.text("Rewards:").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                            Component.text("1st: ").color(ChatUtility.first_place_color).decoration(TextDecoration.ITALIC, false)
                                    .append(Component.text(ChatUtility.sponge_icon + challenge.getPlacements().get(1)).color(ChatUtility.sponge_color).decoration(TextDecoration.ITALIC, false)),
                            Component.text("2nd: ").color(ChatUtility.second_place_color).decoration(TextDecoration.ITALIC, false)
                                    .append(Component.text(ChatUtility.sponge_icon + challenge.getPlacements().get(2)).color(ChatUtility.sponge_color).decoration(TextDecoration.ITALIC, false)),
                            Component.text("3rd: ").color(ChatUtility.third_place_color).decoration(TextDecoration.ITALIC, false)
                                    .append(Component.text(ChatUtility.sponge_icon + challenge.getPlacements().get(3)).color(ChatUtility.sponge_color).decoration(TextDecoration.ITALIC, false)),
                            Component.text(""),
                            Component.text("Required score: ").color(NamedTextColor.DARK_PURPLE)
                                    .append(Component.text(String.valueOf(challenge.getRequiredScore())).color(NamedTextColor.LIGHT_PURPLE))
                                    .decoration(TextDecoration.ITALIC, false)));
            inventorySlot+=2;
        }
    }

    public void openInventory(final HumanEntity ent, Inventory inv) {
        ent.openInventory(inv);
    }
}
