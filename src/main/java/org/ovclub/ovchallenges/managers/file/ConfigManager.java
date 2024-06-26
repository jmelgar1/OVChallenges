package org.ovclub.ovchallenges.managers.file;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.ovclub.ovchallenges.util.ChatUtility;

import java.util.Objects;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class ConfigManager {
    public static void loadConfig(final FileConfiguration config) {
        EARNED_SPONGE = ChatUtility.createSpongeIcon(ChatUtility.sponge_color).append(LegacyComponentSerializer.legacyAmpersand().deserialize(translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("earned-sponge")))));
        DID_NOT_MEET_SCORE = ChatUtility.createXIcon(TextColor.color(255,0,0)).append(LegacyComponentSerializer.legacyAmpersand().deserialize(translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("did-not-meet-score")))));
        LEFT_THE_GAME = ChatUtility.createXIcon(TextColor.color(255,0,0)).append(LegacyComponentSerializer.legacyAmpersand().deserialize(translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("left-the-game")))));
        CHALLENGE_CANCELLED = ChatUtility.createXIcon(TextColor.color(255,0,0)).append(LegacyComponentSerializer.legacyAmpersand().deserialize(translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("challenge-cancelled")))));
        NOT_ENOUGH_PLAYERS = ChatUtility.createXIcon(TextColor.color(255,0,0)).append(LegacyComponentSerializer.legacyAmpersand().deserialize(translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("not-enough-players")))));
        PLAYER_VOTE = ChatUtility.createXIcon(TextColor.color(255,0,0)).append(LegacyComponentSerializer.legacyAmpersand().deserialize(translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("player-vote")))));
        CHALLENGE_WON_VOTE = ChatUtility.createChallengeIcon().append(LegacyComponentSerializer.legacyAmpersand().deserialize(translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("challenge-won-vote")))));
        DID_NOT_VOTE = ChatUtility.createXIcon(TextColor.color(255,0,0)).append(LegacyComponentSerializer.legacyAmpersand().deserialize(translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("did-not-vote")))));
        TIME_EXPIRED = ChatUtility.createChallengeIcon().append(LegacyComponentSerializer.legacyAmpersand().deserialize(translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("time-expired")))));

    }

    public static TextComponent EARNED_SPONGE;
    public static TextComponent DID_NOT_MEET_SCORE;
    public static TextComponent LEFT_THE_GAME;
    public static TextComponent CHALLENGE_CANCELLED;
    public static TextComponent NOT_ENOUGH_PLAYERS;
    public static TextComponent PLAYER_VOTE;
    public static TextComponent CHALLENGE_WON_VOTE;
    public static TextComponent DID_NOT_VOTE;
    public static TextComponent TIME_EXPIRED;
}
