package org.ovclub.ovchallenges.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.commands.subcommands.voteCommand;
import org.ovclub.ovchallenges.managers.file.ConfigManager;
import org.ovclub.ovchallenges.util.ChatUtility;

import java.util.*;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final Map<String, SubCommand> subCommands = new LinkedHashMap<>();

    private void sendHelp(Player p, String icon, String helpTitle, TextColor titleColor, Map<String, SubCommand> commandListType, String[] args) {
        int commandsPerPage = 5;
        int page = 1;
        int lastPage = (int) Math.ceil((double) commandListType.size() / commandsPerPage);

        if (args.length > 1) {
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                p.sendMessage(Component.text("Invalid page number.", NamedTextColor.RED));
                return;
            }
        }

        int startIndex = (page - 1) * commandsPerPage;
        int endIndex = Math.min(startIndex + commandsPerPage, commandListType.size());

        if (startIndex >= commandListType.size()) {
            p.sendMessage(Component.text("Page not found.", NamedTextColor.RED));
            return;
        }

        sendChallengesGuide(p, icon, helpTitle, titleColor, page, lastPage);

        List<Map.Entry<String, SubCommand>> entries = new ArrayList<>(commandListType.entrySet());
        for (int i = startIndex; i < endIndex; i++) {
            Component commandText = getComponent(entries, i);

            p.sendMessage(commandText);
        }

        // Pagination logic
        Component pagination = Component.empty();
        if (page > 1) {
            Component previousPage = Component.text("< Previous", TextColor.fromHexString("#DCE775"))
                    .clickEvent(ClickEvent.runCommand("/ch help " + (page - 1)))
                    .hoverEvent(HoverEvent.showText(Component.text("Go to page " + (page - 1), NamedTextColor.YELLOW)))
                    .decorate(TextDecoration.BOLD);
            pagination = pagination.append(previousPage);
        }
        if (page > 1 && page < lastPage) {
            pagination = pagination.append(Component.text(" | ", NamedTextColor.DARK_GRAY));
        }
        if (page < lastPage) {
            Component nextPage = Component.text("Next >", TextColor.fromHexString("#AED581"))
                    .clickEvent(ClickEvent.runCommand("/ch help " + (page + 1)))
                    .hoverEvent(HoverEvent.showText(Component.text("Go to page " + (page + 1), NamedTextColor.YELLOW)))
                    .decorate(TextDecoration.BOLD);
            pagination = pagination.append(nextPage);
        }
        if (!pagination.equals(Component.empty())) {
            p.sendMessage(pagination);
        }
    }

    private static @NotNull Component getComponent(List<Map.Entry<String, SubCommand>> entries, int i) {
        Map.Entry<String, SubCommand> entry = entries.get(i);
        SubCommand command = entry.getValue();
        String commandName = entry.getKey();

        return Component.text((i + 1) + ". ", NamedTextColor.DARK_GRAY)
                .append(Component.text("/ch " + commandName + " - ", ChatUtility.plugin_color))
                .append(Component.text(command.getDescription(), NamedTextColor.WHITE))
                .clickEvent(ClickEvent.suggestCommand("/ch " + commandName))
                .hoverEvent(HoverEvent.showText(Component.text("/ch " + commandName, NamedTextColor.GRAY)));
    }

    private final Plugin plugin;
    public CommandManager(final Plugin inst) {
        this.plugin = inst;
        subCommands.put("vote", new voteCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (args.length == 0) {
            sendHelp(p, ChatUtility.sword_icon,"Challenges Guide", ChatUtility.label_color, subCommands, args);
            return true;
        }
        String argument = args[0].toLowerCase();
        String[] pass = Arrays.copyOfRange(args, 1, args.length);
        if (!subCommands.containsKey(argument)) {
            sendHelp(p, ChatUtility.sword_icon,"Challenges Guide", ChatUtility.label_color, subCommands, args);
            return true;
        }
        try {
            SubCommand subCmd = subCommands.get(argument);
            if (p.hasPermission("challenges.player.*")) {
                subCmd.perform(p, pass, plugin);
            } else {
                p.sendMessage(ConfigManager.NO_PERMISSION);
            }
        } catch(Exception ignored) {}
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            subCommands.keySet().forEach(cmd -> {
                if (sender.hasPermission("challenges.player." + cmd)) {
                    completions.add(cmd);
                }
            });
            return completions;
        } else if (args.length > 1) {
            String subCmdKey = args[0].toLowerCase();
            SubCommand subCmd = subCommands.get(subCmdKey);
            if (subCmd instanceof TabCompleter) {
                return ((TabCompleter) subCmd).onTabComplete(sender, command, alias, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        return null;
    }

    public void sendChallengesGuide(Player p, String icon, String guideTitle, TextColor titleColor, int page, int lastPage) {
        TextComponent message = Component.text()
                .append(Component.text("-----[ ", NamedTextColor.GRAY))
                .append(Component.text(icon, ChatUtility.icon_color))
                .append(Component.text(guideTitle + " ", titleColor))
                .append(Component.text(icon, ChatUtility.icon_color))
                .append(Component.text(" (", TextColor.fromHexString("#7B7B7B")))
                .append(Component.text(page + "/" + lastPage, TextColor.fromHexString("#FFFFFF")))
                .append(Component.text(") ]-----", NamedTextColor.GRAY))
                .build();
        p.sendMessage(message);
    }

}
