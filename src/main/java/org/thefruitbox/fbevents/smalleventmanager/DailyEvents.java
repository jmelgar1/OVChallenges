package org.thefruitbox.fbevents.smalleventmanager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.thefruitbox.fbevents.Main;
import org.thefruitbox.fbevents.managers.DetermineEventData;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

public class DailyEvents implements Listener {
	
	//Main instance
	protected Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	//get winning event 
	public String winningEvent = mainClass.dev1.getVotedEvent(mainClass);
	
	//event data file from main class
	public FileConfiguration eventData = mainClass.getEventData();
	
	ConfigurationSection currentEventSection = eventData.getConfigurationSection("current-event");
	public ConfigurationSection winningEventSection = currentEventSection.getConfigurationSection(winningEvent);
	
	//register and unregister events1
	public void registerEvents() {
		try{
			Bukkit.getServer().getPluginManager().registerEvents(this, mainClass);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void unregisterEvents() {
		try{
			HandlerList.unregisterAll(this);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//FOR MINING EVENTS
	protected CoreProtectAPI getCoreProtect() {
        Plugin plugin = mainClass.getServer().getPluginManager().getPlugin("CoreProtect");

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
