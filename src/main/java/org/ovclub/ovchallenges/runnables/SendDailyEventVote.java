package org.ovclub.ovchallenges.runnables;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.object.Challenge;
import org.ovclub.ovchallenges.runnables.countdown.CountdownTimerShort;
import org.ovclub.ovchallenges.util.ChallengeUtility;
import org.ovclub.ovchallenges.util.ChatUtility;

import java.util.List;
import java.util.function.Consumer;

public class SendDailyEventVote extends BukkitRunnable {
	private final Plugin plugin;
	public SendDailyEventVote(Plugin plugin) {this.plugin = plugin;}

	@Override
	public void run() {
		int onlinePlayers = Bukkit.getServer().getOnlinePlayers().size();
		//TODO: CHANGE THIS TO 2
		if(onlinePlayers >= 1) {
			List<Challenge> challenges = plugin.getData().getChallenges();
			plugin.getData().clearParticipants();
			ChallengeUtility.clearVotesAndScores(challenges);
			ChallengeUtility.rotateChallenges(challenges);
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.getWorld().playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_1, 0.25F, 1.2F);
				TextComponent component = ChatUtility.createChallengeTitle()
						.append(Component.newline())
						.append(Component.text("│").color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD))
						.append(Component.text(" ✦ Click Here To Vote! ✦ ")
								.color(NamedTextColor.RED)
								.decorate(TextDecoration.ITALIC)
								.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/ch vote"))
								.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("Click to vote!"))))
						.append(Component.text(" │").color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD))
						.append(Component.newline())
						.append(Component.text("└───────────────┘").color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD));
				p.sendMessage(component);
			}

			startVoteFinishTimer();

			plugin.getData().enableVotingPeriod();
		} else {
			plugin.OriginalVoteRunnable();
		}
	}

	public void startVoteFinishTimer() {
		Runnable beforeTimer = () -> {};
		Runnable afterTimer = () -> {
			SendVoteFinished voteFinished = new SendVoteFinished(plugin);
			voteFinished.run();
		};

		Consumer<CountdownTimerShort> everySecond = timer -> {
			if(timer.getSecondsLeft() == 60 || timer.getSecondsLeft() == 30 || timer.getSecondsLeft() == 15 || timer.getSecondsLeft() == 10 || timer.getSecondsLeft() <= 3) {
				Bukkit.broadcast(ConfigManager.TIME_TILL_VOTING_ENDS
						.replaceText(builder -> builder.matchLiteral("{seconds}").replacement(String.valueOf(timer.getSecondsLeft()))));
			}
		};
		CountdownTimerShort timer = new CountdownTimerShort(plugin, 60, beforeTimer, afterTimer, everySecond);
		timer.scheduleTimer();
	}
}
