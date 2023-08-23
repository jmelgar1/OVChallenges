package org.thefruitbox.fbevents.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.thefruitbox.fbevents.runnables.SendDailyEventVote;
import org.thefruitbox.fbevents.runnables.Send30SecondReminder;
import org.thefruitbox.fbevents.runnables.SendVoteFinished;

import net.md_5.bungee.api.ChatColor;

public class fbeEndVote implements CommandExecutor{
	
	SendDailyEventVote dailyVote = new SendDailyEventVote();
	Send30SecondReminder secondReminder = new Send30SecondReminder();
	SendVoteFinished voteFinished = new SendVoteFinished();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("fbeendvote")) {
        	if(p.hasPermission("fbe.endvote")) {
        		p.sendMessage("broken");
        		if(Bukkit.getScheduler().isQueued(dailyVote.getTaskId()) == true){
        			dailyVote.cancel();
        			p.sendMessage(ChatColor.RED + "Event vote cancelled!");
        		} else if (Bukkit.getScheduler().isQueued(voteFinished.getTaskId()) == true){
        			voteFinished.cancel();
        			p.sendMessage(ChatColor.RED + "Event vote cancelled!");
        		} else if (Bukkit.getScheduler().isQueued(secondReminder.getTaskId()) == true){
        			secondReminder.cancel();
        			p.sendMessage(ChatColor.RED + "Event vote cancelled!");
        		} else {
        			p.sendMessage(ChatColor.RED + "There is not event vote running right now.");
        		}
        	}
        }	
		return true;
	}
}
