package org.thefruitbox.fbevents.runnables;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.thefruitbox.fbevents.Main;
import org.thefruitbox.fbevents.events.blockbreakevents.*;
import org.thefruitbox.fbevents.events.craftingevents.BestBaker;
import org.thefruitbox.fbevents.events.craftingevents.CookieClicker;
import org.thefruitbox.fbevents.events.damageevents.DragonSlayer;
import org.thefruitbox.fbevents.events.fishingevents.FishFrenzy;
import org.thefruitbox.fbevents.events.killingevents.*;
import org.thefruitbox.fbevents.managers.DetermineEventData;
import org.thefruitbox.fbevents.smalleventmanager.DailyEvents;

import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class StartEvent extends BukkitRunnable{
	
	//Main instance
	private static StartEvent instance;
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	//get winning event 
	public String winningEvent = mainClass.dev1.getVotedEvent(mainClass.getSmallEvents(), mainClass.dev1.getList());
	String winningEventNS = winningEvent.replaceAll("\\s", "");
	
	//initilize KillMobEvent class variable
	DailyEvents eventWinnerClass;
	
	//create empty scoreboard title name
	String scoreboardTitle;

	private static final Map<String, Class<? extends DailyEvents>> eventClassMap = new HashMap<>();
	
	@Override
	public void run() {
		
		instance = this;
		
		EndEvent endEvent = new EndEvent();

		initializeEventClassMap();
		Class<? extends DailyEvents> eventClass = eventClassMap.get(winningEventNS);

		if(eventClass != null){
			try{
				DailyEvents eventWinnerClass = eventClass.getDeclaredConstructor().newInstance();

				ConfigurationSection winningEventSection = mainClass.getSmallEvents().getConfigurationSection(winningEvent);
				String color = winningEventSection.getString("color");
				String name = winningEventSection.getName();
				int duration = winningEventSection.getInt("duration");

				scoreboardTitle = ChatColor.valueOf(color) + String.valueOf(ChatColor.BOLD) + name.toUpperCase();
				createScoreboard(eventWinnerClass);

				if(allOnline){
					eventWinnerClass.registerEvents();
				} else {
					SendDailyEventVote sendDailyEventVote = new SendDailyEventVote();
					sendDailyEventVote.dev1.clearParticipationList(mainClass);
					sendDailyEventVote.runTaskLater(mainClass, 200);
				}

				endEvent.runTaskLater(mainClass, (long) duration *60*20);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Bukkit.broadcastMessage("null event fix now");
		}
	}
	
	//boolean to check if all players are online
	boolean allOnline = true;
	
	//function to create scoreboard
	void createScoreboard(DailyEvents className) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("FBEvents", "dummy", scoreboardTitle);
		
		obj.getScore(ChatColor.RESET.toString()).setScore(10);
		obj.getScore(ChatColor.LIGHT_PURPLE + String.valueOf(ChatColor.BOLD) + "Scores:").setScore(9);
		
		if(mainClass.getEventData().getStringList("participants").size() < 3) {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(5);
			obj.getScore(ChatColor.LIGHT_PURPLE + String.valueOf(ChatColor.BOLD) + "Time Left:").setScore(4);
			obj.getScore("time left").setScore(3);
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(2);
			obj.getScore(ChatColor.GREEN + "play.thefruitbox.net").setScore(1);
		} else {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(6);
			obj.getScore(ChatColor.LIGHT_PURPLE + String.valueOf(ChatColor.BOLD) + "Time Left:").setScore(5);
			obj.getScore("time left").setScore(4);
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(3);
			obj.getScore(ChatColor.GREEN + "play.thefruitbox.net").setScore(2);
		}
		
		int counter = 8;
		for(String s : mainClass.getEventData().getStringList("participants")){
			Player p = Bukkit.getPlayer(s);
			
//			String group = "in_event";
//			User user = api.getPlayerAdapter(Player.class).getUser(p);
//			user.data().add(InheritanceNode.builder(group).value(true).build());
//			api.getUserManager().saveUser(user);
			
			//if player leaves before event starts REMOVE FROM LIST!
			if(p == null) {
				
				Bukkit.broadcastMessage(ChatColor.RED + s + " left the game before the event started and will be removed from the event!");
				
				mainClass.getEventData().set("participants", mainClass.getEventData().getStringList("participants").remove(s));
				
				mainClass.saveEventDataFile();
				
				if(mainClass.getEventData().getStringList("participants").size() < 2) {
					Bukkit.broadcastMessage(ChatColor.RED + "The event will be cancelled since there are less than two players in the event.");
					
					//cancel runnable if player is found not to be online
					instance.cancel();
					
					//set boolean to false
					allOnline = false;
					
					SendDailyEventVote sendVote = new SendDailyEventVote();
					sendVote.runTaskLater(mainClass, 3000);
					this.cancel();
				}
			} else {
				obj.getScore(p.getName() + ": " + ChatColor.YELLOW + className.winningEventSection.getInt(p.getName())).setScore(counter);
				counter--;
			
				if(counter == 5) {
					break;
				}
			}
		}
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		//if all players are online set scoreboard for all players
		if(allOnline) {
			for(String s : mainClass.getEventData().getStringList("participants")){
				Player p = Bukkit.getPlayer(s);
				p.setScoreboard(board);
			}
		}
		
		UpdateScoreboard updateScoreboard = new UpdateScoreboard();
		updateScoreboard.setCountdown();
		updateScoreboard.run();
	}

	private void initializeEventClassMap(){
		//Block Break Events
		eventClassMap.put("CoalDigger", CoalDigger.class);
		eventClassMap.put("CrazyCarrots", CrazyCarrots.class);
		eventClassMap.put("DeepDiamonds", DeepDiamonds.class);
		eventClassMap.put("IronWorker", Ironworker.class);
		eventClassMap.put("Lumberjack", Lumberjack.class);
		eventClassMap.put("NastyNetherite", NastyNetherite.class);
		eventClassMap.put("PreciousPotatoes", PreciousPotatoes.class);

		//Crafting Events
		eventClassMap.put("BestBaker", BestBaker.class);
		eventClassMap.put("CookieClicker", CookieClicker.class);

		//Damage Events
		eventClassMap.put("DragonSlayer", DragonSlayer.class);

		//Fishing Events
		eventClassMap.put("FishFrenzy", FishFrenzy.class);

		//Killing Events
		eventClassMap.put("BadBats", BadBats.class);
		eventClassMap.put("BringHomeTheBacon", BringHomeTheBacon.class);
		eventClassMap.put("CowTipper", CowTipper.class);
		eventClassMap.put("GhastHunter", GhastHunter.class);
		eventClassMap.put("HilariousHomicide", HilariousHomicide.class);
		eventClassMap.put("ScarySkeletons", ScarySkeletons.class);
		eventClassMap.put("ShulkerHunt", ShulkerHunt.class);
		eventClassMap.put("WardenWarrior", WardenWarrior.class);
		eventClassMap.put("WheresWither", WheresWither.class);
		eventClassMap.put("WorldWarZ", WorldWarZ.class);
	}
	
	public void unregisterEvent(DailyEvents className) {
		className.unregisterEvents();
	}
	
	//Main instance
	public static StartEvent getInstance() {
		return instance;
	}
	
	public DailyEvents getEvent() {
		return eventWinnerClass;
	}
}
