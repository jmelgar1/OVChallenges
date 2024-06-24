package org.ovclub.ovchallenges.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class ChatUtility {
    public static TextColor plugin_color = TextColor.fromHexString("#AB56B6");
    public static TextColor sponge_color = TextColor.fromHexString("#dfff00");

    public static TextComponent createSpongeIcon(TextColor color) {
        return Component.text("[⧠] ").color(color);
    }
    public static TextComponent createXIcon(TextColor color) {
        return Component.text("[✘] ").color(color);
    }


    public static TextComponent prefix = Component.text("[").color(NamedTextColor.LIGHT_PURPLE)
            .append(Component.text("C")).color(plugin_color)
            .append(Component.text("[").color(NamedTextColor.LIGHT_PURPLE));
}
