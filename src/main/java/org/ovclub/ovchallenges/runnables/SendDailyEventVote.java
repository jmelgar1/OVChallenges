package org.ovclub.ovchallenges.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.util.EventUtility;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

public class SendDailyEventVote extends BukkitRunnable {
    
	//Plugin instance
	private final Plugin plugin;

	public SendDailyEventVote(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		int onlinePlayers = Bukkit.getServer().getOnlinePlayers().size();
		if(onlinePlayers >= 2) {

			//clear previous participation list
			List<Challenge> challenges = plugin.getData().getEvents();
			plugin.getData().clearParticipants();
			EventUtility.clearVotes(challenges);
			//rotate the challenges
			EventUtility.RotateEvents(challenges);
			
			//generate random eventid
//			int eventID = (int)(Math.random()*10000);
//			plugin.getEventData().set("eventid", eventID);
//			plugin.saveEventDataFile();
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				
				//p.getWorld().playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_1, 0.5F, 1.2F);
				
		    	p.sendMessage(ChatColor.GRAY + "----- " + ChatColor.LIGHT_PURPLE + "Daily Events!" + ChatColor.GRAY + " -----");
		    	
		    	TextComponent message = new TextComponent(" ✦ Click Here To Vote! ✦");
		    	message.setColor(ChatColor.RED);
		    	message.setItalic(true);
		    	message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ovevote"));
		    	p.spigot().sendMessage(message);
			}
			
			SendVoteFinished voteFinished = new SendVoteFinished(plugin);
			voteFinished.runTaskLater(plugin, 2400);
		} else {
			plugin.runEventNotif20Minutes();
		}
	}
}
