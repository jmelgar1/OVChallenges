package org.ovclub.ovchallenges.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class ChatUtility {
    public static TextColor plugin_color = TextColor.fromHexString("#AB56B6");
    public static TextColor icon_color = TextColor.fromHexString("#de69ed");
    public static TextColor label_color = TextColor.fromHexString("#ab29bc");

    public static TextColor sponge_color = TextColor.fromHexString("#dfff00");

    public static TextComponent createSpongeIcon(TextColor color) {
        return Component.text("[⧠] ").color(color);
    }
    public static TextComponent createChallengeIcon() {
        return Component.text("[").color(plugin_color)
                .append(Component.text("🪓 ")).color(icon_color)
                .append(Component.text("Challenges")).color(label_color)
                .append(Component.text(" ⛏")).color(icon_color)
                .append(Component.text("] ").color(plugin_color));
    }
    public static TextComponent createXIcon(TextColor color) {
        return Component.text("[✘] ").color(color);
    }


    public static String getPlainTextFromComponent(Component component){
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
}
