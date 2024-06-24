package org.ovclub.ovchallenges.commands;

import java.util.UUID;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.managers.InventoryManager;

import net.md_5.bungee.api.ChatColor;
import org.ovclub.ovchallenges.object.Challenge;

public class ovevote extends InventoryManager implements CommandExecutor {

    private static Inventory inv;

    private final Plugin plugin;

    public ovevote(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("ovevote")) {
            UUID playerUUID = p.getUniqueId();

            if(plugin.getData().isVotingEnabled()) {
                if(!plugin.getData().checkIfSignedUp(p)){
                    inv = Bukkit.createInventory(p, 9, "OVClub | Challenges Vote");
                    plugin.getData().addInventory(playerUUID, inv);
                    openInventory(p, plugin.getData().getInventories().get(playerUUID));
                    initalizeItems();
                } else {
                    p.sendMessage(ChatColor.RED + "You already voted!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Voting for this event has ended!");
            }
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public void initalizeItems() {

        int inventorySlot = 3;

        //gets all events from list
        for(Challenge challenge : plugin.getData().getChallenges().subList(0, 3)) {
//
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
                    createGuiItem(challenge.getItem().getType(), Component.text(challenge.getName()).color(challenge.getColor()),
                            Component.text(""),
                    Component.text("Description").color(NamedTextColor.YELLOW).decorate(TextDecoration.UNDERLINED),
                    Component.text(challenge.getDescription()).color(NamedTextColor.GOLD),
                    Component.text(""),
                    Component.text("Duration").color(NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED),
                    Component.text(challenge.getDuration() + " minutes").color(NamedTextColor.GOLD),
                    Component.text(""),
                    Component.text("Rewards").color(NamedTextColor.YELLOW).decorate(TextDecoration.UNDERLINED),
                    Component.text("1st: ").color(NamedTextColor.GRAY)
                        .append(Component.text(String.valueOf(challenge.getPlacements.get(1)))).color(NamedTextColor.GOLD),
                    Component.text("2nd: ").color(NamedTextColor.GRAY)
                        .append(Component.text(String.valueOf(challenge.getPlacements.get(2)))).color(NamedTextColor.GOLD),
                    Component.text("3rd: ").color(NamedTextColor.GRAY)
                        .append(Component.text(String.valueOf(challenge.getPlacements.get(3)))).color(NamedTextColor.GOLD),
                    Component.text(""),
                    Component.text("Required score: ").color(NamedTextColor.DARK_PURPLE)
                        .append(Component.text(String.valueOf(challenge.getRequiredScore())).color(NamedTextColor.LIGHT_PURPLE))));
            inventorySlot++;
        }
    }

    public void openInventory(final HumanEntity ent, Inventory inv) {
        ent.openInventory(inv);
    }
}
