package me.anxuiz.settings.bukkit.commands.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.PlayerSettings;
import me.anxuiz.settings.bukkit.commands.util.Commands;
import me.anxuiz.settings.bukkit.commands.util.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class SettingsCommand {
    public static int RESULTS_PER_PAGE = 8;

    @Command(
        aliases = {"settings"},
        usage = "[page]",
        desc = "Shows all settings",
        min = 0,
        max = 1
    )
    public static void settings(CommandContext args, CommandSender sender) throws CommandException {
        if(!Permissions.hasListPermission(sender)) {
            throw new CommandException(Commands.NO_PERMISSION);
        }

        List<Setting> settings = getSortedPlayerSettings(sender);

        int maxPage = Math.max(1, settings.size() / RESULTS_PER_PAGE); // minimum max page is 1

        int page = 1;
        if(args.argsLength() > 0) {
            try {
                page = args.getInteger(0);
            } catch (NumberFormatException e) {
                // Already caught in the main plugin, do nothing
            }
        }
        page = Math.min(maxPage, Math.max(page, 1)); // constrain page to valid number

        String title = ChatColor.YELLOW + "Settings (Page " + page + " of " + maxPage + ")";
        sender.sendMessage(Commands.formatHeader(title));

        for(int i = RESULTS_PER_PAGE * (page - 1); i < RESULTS_PER_PAGE * page && i < settings.size(); i++) {
            Setting setting = settings.get(i);
            sender.sendMessage(ChatColor.YELLOW + setting.getName() + ": " + ChatColor.RESET + setting.getSummary());
        }
    }

    public static List<Setting> getSortedPlayerSettings() {
        List<Setting> settings = Lists.newArrayList(PlayerSettings.getRegistry().getSettings());
        Collections.sort(settings, SETTING_NAME_COMPARATOR);
        return settings;
    }

    public static List<Setting> getSortedPlayerSettings(CommandSender sender) {
        List<Setting> settings = getSortedPlayerSettings();
        for(Iterator<Setting> it = settings.iterator(); it.hasNext(); ) {
            Setting setting = it.next();
            if(!Permissions.hasViewPermission(sender, setting)) {
                it.remove();
            }
        }
        return settings;
    }

    private static final Comparator<Setting> SETTING_NAME_COMPARATOR = new Comparator<Setting>() {
        public int compare(Setting s1, Setting s2) {
            return s1.getName().compareTo(s2.getName());
        }
    };
}
