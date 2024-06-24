package org.ovclub.ovchallenges.runnables;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.Challenge;

import net.md_5.bungee.api.ChatColor;

public class StartEvent extends BukkitRunnable{
	
	//Plugin instance
	private static StartEvent instance;

	private final Plugin plugin;

	public StartEvent(Plugin plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		instance = this;
		
		EndEvent endEvent = new EndEvent(plugin);

		//initializeEventClassMap();
		Challenge challenge = plugin.getData().getWinningChallenge();
		Class<? extends Listener> eventClass = challenge.getClassType();
		if(eventClass != null){
			TextColor color = challenge.getColor();
			String name = challenge.getName();
			int duration = challenge.getDuration();
			int minimum = challenge.getRequiredScore();
			createScoreboard(challenge, minimum);

			if(allOnline){
				challenge.registerEvents();
			} else {
				SendDailyEventVote sendDailyEventVote = new SendDailyEventVote(plugin);
				plugin.getData().clearParticipants();
				sendDailyEventVote.runTaskLater(plugin, 200);
			}
			endEvent.runTaskLater(plugin, (long) duration *60*20);
		} else {
			Bukkit.broadcastMessage("Challenge is null. Please report this error.");
		}
	}
	
	//boolean to check if all players are online
	boolean allOnline = true;
	
	//function to create scoreboard
	void createScoreboard(Challenge challenge, int minimum) {
		Component scoreboardTitle = Component.text(challenge.getName().toUpperCase())
				.color(challenge.getColor())
				.decorate(TextDecoration.BOLD);
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("OVChallenges", "dummy", scoreboardTitle);
		
		obj.getScore(ChatColor.RESET.toString()).setScore(12);
		obj.getScore(ChatColor.LIGHT_PURPLE + String.valueOf(ChatColor.BOLD) + "Scores:").setScore(11);
		
		if(plugin.getData().getParticipants().size() < 3) {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(7);
			obj.getScore(ChatColor.LIGHT_PURPLE + String.valueOf(ChatColor.BOLD) + "Time Left:").setScore(6);
			obj.getScore("time left").setScore(5);
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(4);
			obj.getScore(ChatColor.GRAY + "Required Score: " + ChatColor.GOLD + minimum).setScore(3);
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(2);
			obj.getScore(ChatColor.GREEN + "play.thefruitbox.net").setScore(1);
		} else {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(8);
			obj.getScore(ChatColor.LIGHT_PURPLE + String.valueOf(ChatColor.BOLD) + "Time Left:").setScore(7);
			obj.getScore("time left").setScore(6);
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(5);
			obj.getScore(ChatColor.GRAY + "Required Score: " + ChatColor.GOLD + minimum).setScore(4);
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(3);
			obj.getScore(ChatColor.GREEN + "play.thefruitbox.net").setScore(2);
		}
		
		int counter = 8;
		for(Player p : plugin.getData().getParticipants()){
//			String group = "in_event";
//			User user = api.getPlayerAdapter(Player.class).getUser(p);
//			user.data().add(InheritanceNode.builder(group).value(true).build());
//			api.getUserManager().saveUser(user);
			
			//if player leaves before challenge starts REMOVE FROM LIST!
			if(!p.isOnline()) {
				
				Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " left the game before the challenge started and will be removed from the challenge!");
				
				plugin.getData().removeParticipant(p);
				
				if(plugin.getData().getParticipants().size() < 2) {
					Bukkit.broadcastMessage(ChatColor.RED + "The challenge will be cancelled since there are less than two players in the challenge.");
					
					//cancel runnable if player is found not to be online
					instance.cancel();
					
					//set boolean to false
					allOnline = false;
					
					SendDailyEventVote sendVote = new SendDailyEventVote(plugin);
					sendVote.runTaskLater(plugin, 3000);
					this.cancel();
				}
			} else {
				System.out.println("WINNING EVENT: " + challenge.getName());
				obj.getScore(p.getName() + ": " + ChatColor.YELLOW + challenge.getScore(p)).setScore(counter);
				counter--;
			
				if(counter == 5) {
					break;
				}
			}
		}
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		//if all players are online set scoreboard for all players
		if(allOnline) {
			for(Player p : plugin.getData().getParticipants()){
				p.setScoreboard(board);
			}
		}
		
		UpdateScoreboard updateScoreboard = new UpdateScoreboard(plugin);
		updateScoreboard.setCountdown(challenge);
		updateScoreboard.run();
	}

//	private void initializeEventClassMap(){
//		//Block Break Events
//		eventClassMap.put("Coal Digger", CoalDigger.class);
//		eventClassMap.put("Crazy Carrots", CrazyCarrots.class);
//		eventClassMap.put("Deep Diamonds", DeepDiamonds.class);
//		eventClassMap.put("Iron Worker", Ironworker.class);
//		eventClassMap.put("Lumber jack", Lumberjack.class);
//		eventClassMap.put("Nasty Netherite", NastyNetherite.class);
//		eventClassMap.put("Precious Potatoes", PreciousPotatoes.class);
//		eventClassMap.put("Elegant Emeralds", ElegantEmeralds.class);
//		eventClassMap.put("Quality Quartz", QualityQuartz.class);
//
//		//Crafting Events
//		eventClassMap.put("Best Baker", BestBaker.class);
//		eventClassMap.put("Cookie Clicker", CookieClicker.class);
//		eventClassMap.put("Countless Cakes", CountlessCakes.class);
//		eventClassMap.put("Scrumptious Stew", ScrumptiousStew.class);
//
//		//Damage Events
//		eventClassMap.put("Dragon Slayer", DragonSlayer.class);
//
//		//Fishing Events
//		eventClassMap.put("Fish Frenzy", FishFrenzy.class);
//
//		//Killing Events
//		eventClassMap.put("Bad Bats", BadBats.class);
//		eventClassMap.put("Bring Home The Bacon", BringHomeTheBacon.class);
//		eventClassMap.put("Cow Tipper", CowTipper.class);
//		eventClassMap.put("Ghast Hunter", GhastHunter.class);
//		eventClassMap.put("Hilarious Homicide", HilariousHomicide.class);
//		eventClassMap.put("Spooky Skeletons", SpookySkeletons.class);
//		eventClassMap.put("Shulker Hunt", ShulkerHunt.class);
//		eventClassMap.put("Warden Warrior", WardenWarrior.class);
//		eventClassMap.put("Wheres Wither", WheresWither.class);
//		eventClassMap.put("World War Z", WorldWarZ.class);
//		eventClassMap.put("Creeping Creepers", CreepingCreepers.class);
//		eventClassMap.put("Endless Endermen", EndlessEndermen.class);
//	}
}
