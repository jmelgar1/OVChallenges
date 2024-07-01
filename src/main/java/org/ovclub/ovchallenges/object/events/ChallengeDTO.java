package org.ovclub.ovchallenges.object.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.ovclub.ovchallenges.managers.file.ConfigManager;

public class ChallengeDTO {
    protected String name;
    protected int wins;
    protected int highScore;

    public ChallengeDTO(String name, int wins, int highScore) {
        this.name = name;
        this.wins = wins;
        this.highScore = highScore;
    }

    public String getName() { return name; }
    public int getWins() { return wins; }
    public void addWin() { wins += 1; }
    public int getHighScore() { return highScore; }
    public void checkToReplaceHighScore(OfflinePlayer p, int value) {
        if(value > highScore) {
            highScore = value;
            if(p.isOnline()) {
                Player pl = p.getPlayer();
                if(pl != null) {
                    pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 0.7F, 0.7F);
                    pl.sendMessage(ConfigManager.NEW_HIGH_SCORE
                            .replaceText(builder -> builder.matchLiteral("{score}")
                                    .replacement(Component.text(highScore).color(NamedTextColor.YELLOW).decoration(TextDecoration.BOLD, true))));
                }
            }
        }
    }
}
