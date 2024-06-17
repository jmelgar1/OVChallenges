package org.ovclub.ovchallenges.smalleventmanager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.util.EventUtility;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

public class DailyEvents implements Listener {
	
	//Plugin instance
	protected Plugin pluginClass = Plugin.getInstance();
	
	public EventUtility dev1 = new EventUtility();
	
	//get winning event 
	public String winningEvent = pluginClass.dev1.getVotedEvent(pluginClass);
	
	//event data file from main class
	public FileConfiguration eventData = pluginClass.getEventData();
	
	ConfigurationSection currentEventSection = eventData.getConfigurationSection("current-event");
	public ConfigurationSection winningEventSection = currentEventSection.getConfigurationSection(winningEvent);
	
//	//register and unregister events1
//	public void registerEvents() {
//		try{
//			Bukkit.getServer().getPluginManager().registerEvents(this, pluginClass);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
//
//	public void unregisterEvents() {
//		try{
//			HandlerList.unregisterAll(this);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
	
	//FOR MINING EVENTS
	protected CoreProtectAPI getCoreProtect() {
        org.bukkit.plugin.Plugin plugin = pluginClass.getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (!(plugin instanceof CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 9) {
            return null;
        }

        return CoreProtect;
	}

	public boolean isWithin50Blocks(Block block){
		// Get the location of the block
		Location blockLocation = block.getLocation();

		// Create a location at (0, 0, 0) in the same world
		Location origin = new Location(Bukkit.getWorld("world"), 0, 0, 0);

		// Calculate the distance between the block and the origin
		double distance = blockLocation.distance(origin);

		// Check if the distance is less than or equal to 50 blocks
		return distance <= 75;
	}
}
