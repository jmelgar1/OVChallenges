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
    }

    public static TextComponent EARNED_SPONGE;
    public static TextComponent DID_NOT_MEET_SCORE;
}
