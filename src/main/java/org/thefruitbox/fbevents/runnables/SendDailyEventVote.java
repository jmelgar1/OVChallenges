package org.thefruitbox.fbevents.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.thefruitbox.fbevents.Main;
import org.thefruitbox.fbevents.managers.DetermineEventData;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class SendDailyEventVote extends BukkitRunnable {
    
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();

	@Override
	public void run() {
		
		int onlinePlayers = Bukkit.getServer().getOnlinePlayers().size();
		
		if(Bukkit.getServer().getPlayer("ADMIN_10") != null) {
			onlinePlayers -= 1;
		}
		
		if(onlinePlayers >= 2) {
			
			//clear previous participation list
			mainClass.dev1.clearParticipationList(mainClass);
			
			mainClass.dev1.clearVotes(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
	
			//rotate the events
			mainClass.dev1.RotateEvents(mainClass.dev1.getList());
			
			//generate random eventid
			int eventID = (int)(Math.random()*10000);
			mainClass.getEventData().set("eventid", eventID);
			mainClass.saveEventDataFile();
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				
				//p.getWorld().playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_1, 0.5F, 1.2F);
				
		    	p.sendMessage(ChatColor.GRAY + "----- " + ChatColor.LIGHT_PURPLE + "Daily Events!" + ChatColor.GRAY + " -----");
		    	
		    	TextComponent message = new TextComponent(" ✦ Click Here To Vote! ✦");
		    	message.setColor(ChatColor.RED);
		    	message.setItalic(true);
		    	message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fbevote"));
		    	p.spigot().sendMessage(message);
			}
			
			sendVoteFinished voteFinished = new sendVoteFinished();
			voteFinished.runTaskLater(mainClass, 2400);
			
		} else {
			mainClass.runEventNotif20Minutes();
		}
	}
}
