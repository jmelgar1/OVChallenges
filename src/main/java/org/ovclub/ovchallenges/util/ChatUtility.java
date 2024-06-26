package org.ovclub.ovchallenges.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.ovclub.ovchallenges.object.Challenge;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChatUtility {
    public static TextColor plugin_color = TextColor.fromHexString("#AB56B6");
    public static TextColor icon_color = TextColor.fromHexString("#de69ed");
    public static TextColor label_color = TextColor.fromHexString("#ab29bc");

    public static TextColor first_place_color = TextColor.fromHexString("#FBC02D");
    public static TextColor second_place_color = TextColor.fromHexString("#9E9E9E");
    public static TextColor third_place_color = TextColor.fromHexString("#A87143");

    public static TextColor sponge_color = TextColor.fromHexString("#dfff00");

    public static String axe_icon = "🪓";
    public static String pickaxe_icon = "⛏";
    public static String sword_icon = "🗡";
    public static String bow_icon = "🏹";
    public static String sponge_icon = "⧠";

    public static TextComponent createSpongeIcon(TextColor color) {
        return Component.text("[⧠] ").color(color);
    }
    public static TextComponent createTimerIcon(TextColor color) {
        return Component.text("(⌚) ").color(color);
    }
    public static TextComponent createChallengeTitle() {
        return Component.text("┌───[").color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD)
                .append(Component.text(sword_icon).color(icon_color).decoration(TextDecoration.BOLD, false))
                .append(Component.text(" Challenges ").color(label_color).decoration(TextDecoration.BOLD, false))
                .append(Component.text(bow_icon).color(icon_color).decoration(TextDecoration.BOLD, false))
                .append(Component.text("]───┐").color(NamedTextColor.GRAY)).decorate(TextDecoration.BOLD);
    }
    public static TextComponent createChallengeHeader() {
        return Component.text("(").color(NamedTextColor.GRAY)
                .append(Component.text(axe_icon).color(icon_color))
                .append(Component.text(sword_icon).color(plugin_color))
                .append(Component.text(pickaxe_icon).color(label_color))
                .append(Component.text(") ").color(NamedTextColor.GRAY));
    }
    public static TextComponent createResultsMessage(Challenge challenge, Map<UUID, Integer> topScores) {
        // Create the header
        TextComponent header = Component.text("┌─[").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, true)
                .append(Component.text(challenge.getName()).color(challenge.getColor()).decoration(TextDecoration.BOLD, true))
                .append(Component.text(" RESULTS").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true))
                .append(Component.text("]─┐").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, true))
                .append(Component.newline().append(Component.text("│ ")).color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, true));

        // Sort the map by scores in descending order and limit to the top 3 entries
        List<Map.Entry<UUID, Integer>> sortedEntries = topScores.entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .limit(3)
                .toList();

        // Define the colors for the medal positions
        NamedTextColor[] colors = { NamedTextColor.GOLD, NamedTextColor.GRAY, NamedTextColor.DARK_RED };

        // Add a line for each of the top scores
        int position = 0;
        for (Map.Entry<UUID, Integer> entry : sortedEntries) {
            String playerName = Bukkit.getOfflinePlayer(entry.getKey()).getName();  // Fetch the player's name using the UUID
            if (playerName == null) playerName = "Unknown Player";  // Handle cases where the player's name cannot be retrieved

            String positionText = (position == 0 ? "1st" : position == 1 ? "2nd" : "3rd");

            header = header.append(Component.newline())
                    .append(Component.text("│ ").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, true))
                    .append(Component.text(positionText + " - ").color(colors[position]).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(playerName).color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(" - ").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(String.valueOf(entry.getValue())).color(NamedTextColor.YELLOW).decoration(TextDecoration.BOLD, false));

            position++;
        }

        // Close the box with a bold line
        header = header.append(Component.newline().append(Component.text("│ ")).color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, true))
                .append(Component.newline().append(Component.text("└─────────────────┘").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, true)));

        return header;
    }

    public static TextComponent createXIcon(TextColor color) {
        return Component.text("[✘] ").color(color);
    }


    public static String getPlainTextFromComponent(Component component){
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
}
