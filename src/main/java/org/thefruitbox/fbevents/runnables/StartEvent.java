package org.thefruitbox.fbevents.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.thefruitbox.fbevents.Main;
import org.thefruitbox.fbevents.events.blockbreakevents.CoalDigger;
import org.thefruitbox.fbevents.events.blockbreakevents.CrazyCarrots;
import org.thefruitbox.fbevents.events.blockbreakevents.DeepDiamonds;
import org.thefruitbox.fbevents.events.blockbreakevents.Ironworker;
import org.thefruitbox.fbevents.events.blockbreakevents.Lumberjack;
import org.thefruitbox.fbevents.events.blockbreakevents.NastyNetherite;
import org.thefruitbox.fbevents.events.blockbreakevents.PreciousPotatoes;
import org.thefruitbox.fbevents.events.craftingevents.BestBaker;
import org.thefruitbox.fbevents.events.craftingevents.CookieClicker;
import org.thefruitbox.fbevents.events.damageevents.DragonSlayer;
import org.thefruitbox.fbevents.events.fishingevents.FishFrenzy;
import org.thefruitbox.fbevents.events.killingevents.BadBats;
import org.thefruitbox.fbevents.events.killingevents.BringHomeTheBacon;
import org.thefruitbox.fbevents.events.killingevents.CowTipper;
import org.thefruitbox.fbevents.events.killingevents.GhastHunter;
import org.thefruitbox.fbevents.events.killingevents.HilariousHomicide;
import org.thefruitbox.fbevents.events.killingevents.ShulkerHunt;
import org.thefruitbox.fbevents.events.killingevents.WardenWarrior;
import org.thefruitbox.fbevents.events.killingevents.WorldWarZ;
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
	public String winningEvent = mainClass.dev1.getVotedEvent(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
	String winningEventNS = winningEvent.replaceAll("\\s", "");
	
	//initilize KillMobEvent class variable
	DailyEvents eventWinnerClass;
	
	//create empty scoreboard title name
	String scoreboardTitle;
	
	long tenMinutes = 12000;
	long twentyMinutes = 24000;
	long thirtyMinutes = 36000;
	
	//Luckperms api
	static LuckPerms api = LuckPermsProvider.get();
	
	@Override
	public void run() { 
		
		instance = this;
		
		EndEvent endEvent = new EndEvent();
		
		if(winningEventNS.equals("GhastHunter")) {
			GhastHunter ghastHunter = new GhastHunter();
			
			eventWinnerClass = ghastHunter;
			
			scoreboardTitle = ChatColor.WHITE + "" + ChatColor.BOLD + "GHAST HUNTER";
			createScoreboard(ghastHunter);
			
			if(allOnline == true) {
				ghastHunter.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Ghast Hunter").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("ShulkerHunt")) {
			ShulkerHunt shulkerHunt = new ShulkerHunt();
			
			eventWinnerClass = shulkerHunt;
			
			scoreboardTitle = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "SHULKER HUNT";
			createScoreboard(shulkerHunt);
			
			if(allOnline == true) {
				shulkerHunt.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Shulker Hunt").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("BadBats")) {
			BadBats badBats = new BadBats();
			
			eventWinnerClass = badBats;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "BAD BATS";
			createScoreboard(badBats);
			
			if(allOnline == true) {
				badBats.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Bad Bats").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("HilariousHomicide")) {
			HilariousHomicide hilariousHomicide = new HilariousHomicide();
			
			eventWinnerClass = hilariousHomicide;
			
			scoreboardTitle = ChatColor.DARK_RED + "" + ChatColor.BOLD + "HILARIOUS HOMICIDE";
			createScoreboard(hilariousHomicide);
			
			if(allOnline == true) {
				hilariousHomicide.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Hilarious Homicide").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("WorldWarZ")) {
			WorldWarZ worldWarZ = new WorldWarZ();
			
			eventWinnerClass = worldWarZ;
			
			scoreboardTitle = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "WORLD WAR Z";
			createScoreboard(worldWarZ);
			
			if(allOnline == true) {
				worldWarZ.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("World War Z").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("WardenWarrior")) {
			WardenWarrior wardenWarrior = new WardenWarrior();
			
			eventWinnerClass = wardenWarrior;
			
			scoreboardTitle = ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "WARDEN WARRIOR";
			createScoreboard(wardenWarrior);
			
			if(allOnline == true) {
				wardenWarrior.registerEvents();
			}
			
			//end event after 2 hours
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Warden Warrior").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("FishFrenzy")){
			FishFrenzy fishFrenzy = new FishFrenzy();
			
			eventWinnerClass = fishFrenzy;
			
			scoreboardTitle = ChatColor.BLUE + "" + ChatColor.BOLD + "FISH FRENZY";
			createScoreboard(fishFrenzy);
			
			if(allOnline == true) {
				fishFrenzy.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Fish Frenzy").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("CowTipper")){
			CowTipper cowTipper = new CowTipper();
		
			eventWinnerClass = cowTipper;
			
			scoreboardTitle = ChatColor.WHITE + "" + ChatColor.BOLD + "COW TIPPER";
			createScoreboard(cowTipper);
			
			if(allOnline == true) {
				cowTipper.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Cow Tipper").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("BringHomeTheBacon")){
			BringHomeTheBacon bringHomeTheBacon = new BringHomeTheBacon();
			
			eventWinnerClass = bringHomeTheBacon;
			
			scoreboardTitle = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "BRING HOME THE BACON";
			createScoreboard(bringHomeTheBacon);

			if(allOnline == true) {
				bringHomeTheBacon.registerEvents();
			}
			
			//end event after 10 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Bring Home The Bacon").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("CookieClicker")){
			CookieClicker cookieClicker = new CookieClicker();
			
			eventWinnerClass = cookieClicker;
			
			scoreboardTitle = ChatColor.YELLOW + "" + ChatColor.BOLD + "COOKIE CLICKER";
			createScoreboard(cookieClicker);
			
			if(allOnline == true) {
				cookieClicker.registerEvents();
			}
			
			//end event after 10 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Cookie Clicker").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("DragonSlayer")){
			DragonSlayer dragonSlayer = new DragonSlayer();
			
			eventWinnerClass = dragonSlayer;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "DRAGON SLAYER";
			createScoreboard(dragonSlayer);
			
			if(allOnline == true) {
				dragonSlayer.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Dragon Slayer").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("DeepDiamonds")){
			DeepDiamonds deepDiamonds = new DeepDiamonds();
			
			eventWinnerClass = deepDiamonds;
			
			scoreboardTitle = ChatColor.AQUA + "" + ChatColor.BOLD + "DEEP DIAMONDS";
			createScoreboard(deepDiamonds);
			
			if(allOnline == true) {
				deepDiamonds.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Deep Diamonds").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("NastyNetherite")){
			NastyNetherite nastyNetherite = new NastyNetherite();
			
			eventWinnerClass = nastyNetherite;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "NASTY NETHERITE";
			createScoreboard(nastyNetherite);
			
			if(allOnline == true) {
				nastyNetherite.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Nasty Netherite").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("CrazyCarrots")){
			CrazyCarrots crazyCarrots = new CrazyCarrots();
			
			eventWinnerClass = crazyCarrots;
			
			scoreboardTitle = ChatColor.YELLOW + "" + ChatColor.BOLD + "CRAZY CARROTS";
			createScoreboard(crazyCarrots);
			
			if(allOnline == true) {
				crazyCarrots.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Crazy Carrots").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("PreciousPotatoes")){
			PreciousPotatoes preciousPotatoes = new PreciousPotatoes();
			
			eventWinnerClass = preciousPotatoes;
			
			scoreboardTitle = ChatColor.YELLOW + "" + ChatColor.BOLD + "PRECIOUS POTATOES";
			createScoreboard(preciousPotatoes);
			
			if(allOnline == true) {
				preciousPotatoes.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Precious Potatoes").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("CoalDigger")){
			CoalDigger coalDigger = new CoalDigger();
			
			eventWinnerClass = coalDigger;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "COAL DIGGER";
			createScoreboard(coalDigger);
			
			if(allOnline == true) {
				coalDigger.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Coal Digger").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("BestBaker")){
			BestBaker bestBaker = new BestBaker();
			
			eventWinnerClass = bestBaker;
			
			scoreboardTitle = ChatColor.GOLD + "" + ChatColor.BOLD + "BEST BAKER";
			createScoreboard(bestBaker);
			
			if(allOnline == true) {
				bestBaker.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Best Baker").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("Lumberjack")){
			Lumberjack lumberjack = new Lumberjack();
			
			eventWinnerClass = lumberjack;
			
			scoreboardTitle = ChatColor.BLUE + "" + ChatColor.BOLD + "LUMBERJACK";
			createScoreboard(lumberjack);
			
			if(allOnline == true) {
				lumberjack.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Lumberjack").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
			
		} else if (winningEventNS.equals("IronWorker")){
			Ironworker ironworker = new Ironworker();
			
			eventWinnerClass = ironworker;
			
			scoreboardTitle = ChatColor.GRAY + "" + ChatColor.BOLD + "IRONWORKER";
			createScoreboard(ironworker);
			
			if(allOnline == true) {
				ironworker.registerEvents();
			}
			
			//end event after 20 minutes
			int ticksTillEnd = mainClass.getSmallEvents().getConfigurationSection("Iron Worker").getInt("duration");
			endEvent.runTaskLater(mainClass, ticksTillEnd*60*20);
		}
		
		if(allOnline == false) {
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
		obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Scores:").setScore(9);
		
		if(mainClass.getEventData().getStringList("participants").size() < 3) {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(5);
			obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Time Left:").setScore(4);
			obj.getScore("time left").setScore(3);
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(2);
			obj.getScore(ChatColor.GREEN + "play.thefruitbox.net").setScore(1);
		} else {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(6);
			obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Time Left:").setScore(5);
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
		if(allOnline == true) {
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
