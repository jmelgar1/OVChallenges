package org.thefruitbox.fbevents.runnables;

import org.bukkit.Bukkit;
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

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;

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
	
	//Luckperms api
	static LuckPerms api = LuckPermsProvider.get();
	
	@Override
	public void run() { 
		
		instance = this;
		
		EndEvent endEvent = new EndEvent();
		
		if(winningEventNS.equals("GhastHunter")) {
			GhastHunter ghastHunter = new GhastHunter();
			
			eventWinnerClass = ghastHunter;
			
			scoreboardTitle = ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + "GHAST HUNTER";
			createScoreboard(ghastHunter);
			
			if(allOnline) {
				ghastHunter.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Ghast Hunter").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("ShulkerHunt")) {
			ShulkerHunt shulkerHunt = new ShulkerHunt();
			
			eventWinnerClass = shulkerHunt;
			
			scoreboardTitle = ChatColor.DARK_PURPLE + String.valueOf(ChatColor.BOLD) + "SHULKER HUNT";
			createScoreboard(shulkerHunt);
			
			if(allOnline) {
				shulkerHunt.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Shulker Hunt").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);

		} else if (winningEventNS.equals("ScarySkeletons")) {
			ScarySkeletons scarySkeletons = new ScarySkeletons();

			eventWinnerClass = scarySkeletons;

			scoreboardTitle = ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + "SCARY SKELETONS";
			createScoreboard(scarySkeletons);

			if(allOnline) {
				scarySkeletons.registerEvents();
			}

			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Shulker Hunt").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("BadBats")) {
			BadBats badBats = new BadBats();
			
			eventWinnerClass = badBats;
			
			scoreboardTitle = ChatColor.DARK_GRAY + String.valueOf(ChatColor.BOLD) + "BAD BATS";
			createScoreboard(badBats);
			
			if(allOnline) {
				badBats.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Bad Bats").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);

		} else if (winningEventNS.equals("WheresWither")) {
			WheresWither wheresWither = new WheresWither();

			eventWinnerClass = wheresWither;

			scoreboardTitle = ChatColor.BLUE + String.valueOf(ChatColor.BOLD) + "WHERE'S WITHER";
			createScoreboard(wheresWither);

			if(allOnline) {
				wheresWither.registerEvents();
			}

			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Wheres Wither").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("HilariousHomicide")) {
			HilariousHomicide hilariousHomicide = new HilariousHomicide();
			
			eventWinnerClass = hilariousHomicide;
			
			scoreboardTitle = ChatColor.DARK_RED + String.valueOf(ChatColor.BOLD) + "HILARIOUS HOMICIDE";
			createScoreboard(hilariousHomicide);
			
			if(allOnline) {
				hilariousHomicide.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Hilarious Homicide").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("WorldWarZ")) {
			WorldWarZ worldWarZ = new WorldWarZ();
			
			eventWinnerClass = worldWarZ;
			
			scoreboardTitle = ChatColor.DARK_GREEN + String.valueOf(ChatColor.BOLD) + "WORLD WAR Z";
			createScoreboard(worldWarZ);
			
			if(allOnline) {
				worldWarZ.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("World War Z").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("WardenWarrior")) {
			WardenWarrior wardenWarrior = new WardenWarrior();
			
			eventWinnerClass = wardenWarrior;
			
			scoreboardTitle = ChatColor.DARK_BLUE + String.valueOf(ChatColor.BOLD) + "WARDEN WARRIOR";
			createScoreboard(wardenWarrior);
			
			if(allOnline) {
				wardenWarrior.registerEvents();
			}
			
			//end event after 2 hours
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Warden Warrior").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("FishFrenzy")){
			FishFrenzy fishFrenzy = new FishFrenzy();
			
			eventWinnerClass = fishFrenzy;
			
			scoreboardTitle = ChatColor.BLUE + String.valueOf(ChatColor.BOLD) + "FISH FRENZY";
			createScoreboard(fishFrenzy);
			
			if(allOnline) {
				fishFrenzy.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Fish Frenzy").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("CowTipper")){
			CowTipper cowTipper = new CowTipper();
		
			eventWinnerClass = cowTipper;
			
			scoreboardTitle = ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + "COW TIPPER";
			createScoreboard(cowTipper);
			
			if(allOnline) {
				cowTipper.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Cow Tipper").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("BringHomeTheBacon")){
			BringHomeTheBacon bringHomeTheBacon = new BringHomeTheBacon();
			
			eventWinnerClass = bringHomeTheBacon;
			
			scoreboardTitle = ChatColor.LIGHT_PURPLE + String.valueOf(ChatColor.BOLD) + "BRING HOME THE BACON";
			createScoreboard(bringHomeTheBacon);

			if(allOnline) {
				bringHomeTheBacon.registerEvents();
			}
			
			//end event after 10 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Bring Home The Bacon").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("CookieClicker")){
			CookieClicker cookieClicker = new CookieClicker();
			
			eventWinnerClass = cookieClicker;
			
			scoreboardTitle = ChatColor.YELLOW + String.valueOf(ChatColor.BOLD) + "COOKIE CLICKER";
			createScoreboard(cookieClicker);
			
			if(allOnline) {
				cookieClicker.registerEvents();
			}
			
			//end event after 10 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Cookie Clicker").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("DragonSlayer")){
			DragonSlayer dragonSlayer = new DragonSlayer();
			
			eventWinnerClass = dragonSlayer;
			
			scoreboardTitle = ChatColor.DARK_GRAY + String.valueOf(ChatColor.BOLD) + "DRAGON SLAYER";
			createScoreboard(dragonSlayer);
			
			if(allOnline) {
				dragonSlayer.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Dragon Slayer").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("DeepDiamonds")){
			DeepDiamonds deepDiamonds = new DeepDiamonds();
			
			eventWinnerClass = deepDiamonds;
			
			scoreboardTitle = ChatColor.AQUA + String.valueOf(ChatColor.BOLD) + "DEEP DIAMONDS";
			createScoreboard(deepDiamonds);
			
			if(allOnline) {
				deepDiamonds.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Deep Diamonds").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("NastyNetherite")){
			NastyNetherite nastyNetherite = new NastyNetherite();
			
			eventWinnerClass = nastyNetherite;
			
			scoreboardTitle = ChatColor.DARK_GRAY + String.valueOf(ChatColor.BOLD) + "NASTY NETHERITE";
			createScoreboard(nastyNetherite);
			
			if(allOnline) {
				nastyNetherite.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Nasty Netherite").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("CrazyCarrots")){
			CrazyCarrots crazyCarrots = new CrazyCarrots();
			
			eventWinnerClass = crazyCarrots;
			
			scoreboardTitle = ChatColor.YELLOW + String.valueOf(ChatColor.BOLD) + "CRAZY CARROTS";
			createScoreboard(crazyCarrots);
			
			if(allOnline) {
				crazyCarrots.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Crazy Carrots").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("PreciousPotatoes")){
			PreciousPotatoes preciousPotatoes = new PreciousPotatoes();
			
			eventWinnerClass = preciousPotatoes;
			
			scoreboardTitle = ChatColor.YELLOW + String.valueOf(ChatColor.BOLD) + "PRECIOUS POTATOES";
			createScoreboard(preciousPotatoes);
			
			if(allOnline) {
				preciousPotatoes.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Precious Potatoes").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("CoalDigger")){
			CoalDigger coalDigger = new CoalDigger();
			
			eventWinnerClass = coalDigger;
			
			scoreboardTitle = ChatColor.DARK_GRAY + String.valueOf(ChatColor.BOLD) + "COAL DIGGER";
			createScoreboard(coalDigger);
			
			if(allOnline) {
				coalDigger.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Coal Digger").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("BestBaker")){
			BestBaker bestBaker = new BestBaker();
			
			eventWinnerClass = bestBaker;
			
			scoreboardTitle = ChatColor.GOLD + String.valueOf(ChatColor.BOLD) + "BEST BAKER";
			createScoreboard(bestBaker);
			
			if(allOnline) {
				bestBaker.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Best Baker").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("Lumberjack")){
			Lumberjack lumberjack = new Lumberjack();
			
			eventWinnerClass = lumberjack;
			
			scoreboardTitle = ChatColor.BLUE + String.valueOf(ChatColor.BOLD) + "LUMBERJACK";
			createScoreboard(lumberjack);
			
			if(allOnline) {
				lumberjack.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Lumberjack").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
			
		} else if (winningEventNS.equals("IronWorker")){
			Ironworker ironworker = new Ironworker();
			
			eventWinnerClass = ironworker;
			
			scoreboardTitle = ChatColor.GRAY + String.valueOf(ChatColor.BOLD) + "IRONWORKER";
			createScoreboard(ironworker);
			
			if(allOnline) {
				ironworker.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Iron Worker").getInt("duration");
			endEvent.runTaskLater(mainClass, (long) ticksTillEnd *60*20);
		}
		
		if(!allOnline) {
			SendDailyEventVote sendDailyEventVote = new SendDailyEventVote();
			sendDailyEventVote.dev1.clearParticipationList(mainClass);
			sendDailyEventVote.runTaskLater(mainClass, 200);
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
