package org.ovclub.ovchallenges.commands.admin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.ovclub.ovchallenges.Plugin;

import net.md_5.bungee.api.ChatColor;
import org.ovclub.ovchallenges.commands.ovprofile;

public class configureStatsBC implements CommandExecutor, Listener{
	
	//OVProfile Instance
	ovprofile ovp1 = new ovprofile();
	
	private static Inventory inv;
	
	String IGN = "";
	
	//Plugin instance
	private Plugin pluginClass = Plugin.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("configurestatsbc")) {
        	if(p.hasPermission("ove.configurestatsbc")) {
	        	IGN = args[0];
	    		inv = Bukkit.createInventory(null, 9, IGN);
	        	openInventory(p);
	        	initializeBuildCompetitionItems(IGN);
        	}
        }	
		return true;
	}
	
	//create champions tour inventory
	@SuppressWarnings("deprecation")
	public static void initializeBuildCompetitionItems(String IGN) {
	//player head info
	ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
	SkullMeta meta = (SkullMeta) skull.getItemMeta();
	meta.setOwner(IGN);
	skull.setItemMeta(meta);
		
	inv.setItem(0, createGuiSkull(skull, ChatColor.GRAY + "" + ChatColor.BOLD + "USER: " + ChatColor.WHITE + IGN, 
			ChatColor.GRAY + "" + ChatColor.BOLD + "Rank: " + ChatColor.WHITE + ovprofile.getPlayerGroup(IGN).toUpperCase(),
			ChatColor.GRAY + "" + ChatColor.BOLD + "Date Joined: " + ChatColor.WHITE + "Coming Soon"));
	
	inv.setItem(2, createGuiItem(Material.GOLD_BLOCK, ChatColor.GOLD + "1st Place Finish", 
													  ChatColor.GREEN + "Left click to add 1",
													  ChatColor.RED + "Right click to remove 1"));
	
	inv.setItem(3, createGuiItem(Material.IRON_BLOCK, ChatColor.GRAY + "2nd Place Finish", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));	
	
	inv.setItem(4, createGuiItem(Material.COPPER_BLOCK, ChatColor.YELLOW + "3rd Place Finish", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));
	
	inv.setItem(8, createGuiItem(Material.GOLD_INGOT, ChatColor.GOLD + "Participations", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));
	}
	
	protected static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();
		
		//set the name of item
		meta.setDisplayName(name);
		
		//set lore of item
		meta.setLore(Arrays.asList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
    protected static ItemStack createGuiSkull(final ItemStack skull, final String name, final String... lore) {
    	final ItemMeta meta = skull.getItemMeta();
    	
    	meta.setDisplayName(name);
    	
    	meta.setLore(Arrays.asList(lore));
    	
    	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	skull.setItemMeta(meta);
    	
    	return skull;
    }
	
	public void openInventory(final HumanEntity ent) {
		ent.openInventory(inv);
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event) {
		if(!event.getInventory().equals(inv)) return;
		
		event.setCancelled(true);
		
		final ItemStack clickedItem = event.getCurrentItem();
		
		String IGN = event.getView().getTitle();
		
		//get player who clicked on item
		final Player p = (Player) event.getWhoClicked();
		
		//ConfigurationSection buildCompetition = epp1.getBigEventSection(IGN, "build-competitions");
		
//		if(clickedItem.getType() == Material.GOLD_BLOCK) {
//			epp1.checkClickType(event, buildCompetition, "1st", p, IGN);
//		} else if(clickedItem.getType() == Material.IRON_BLOCK) {
//			epp1.checkClickType(event, buildCompetition, "2nd", p, IGN);
//		} else if(clickedItem.getType() == Material.COPPER_BLOCK) {
//			epp1.checkClickType(event, buildCompetition, "3rd", p, IGN);
//		} else if(clickedItem.getType() == Material.GOLD_INGOT) {
//			epp1.checkClickType(event, buildCompetition, "Participations", p, IGN);
//		} else if(clickedItem.getType() == Material.PLAYER_HEAD) {
//			p.performCommand("configurestats " + IGN);
//		}
		
		//verify current item is not null
		if (clickedItem == null || clickedItem.getType().isAir()) return;
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent event) {
		if(event.getInventory().equals(inv)) {
			event.setCancelled(true);
		}
	}
}
