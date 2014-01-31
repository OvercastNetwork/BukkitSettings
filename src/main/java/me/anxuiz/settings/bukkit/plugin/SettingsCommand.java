package me.anxuiz.settings.bukkit.plugin;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.PlayerSettings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;

public class SettingsCommand implements CommandExecutor {
    public static int RESULTS_PER_PAGE = 8;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!Permissions.hasListPermission(sender)) {
            sender.sendMessage(Commands.NO_PERMISSION);
            return true;
        }

        List<Setting> settings = getSortedPlayerSettings(sender);

        int maxPage = (settings.size() - 1) / RESULTS_PER_PAGE + 1;

        int page = 1;
        if(args.length > 0) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Unable to parse page number");
                return true;
            }
        }
        page = Math.min(maxPage, Math.max(page, 1)); // constrain page to valid number

        String title = ChatColor.YELLOW + "Settings (Page " + page + " of " + maxPage + ")";
        sender.sendMessage(Commands.formatHeader(title));

        for(int i = RESULTS_PER_PAGE * (page - 1); i < RESULTS_PER_PAGE * page && i < settings.size(); i++) {
            Setting setting = settings.get(i);
            sender.sendMessage(ChatColor.YELLOW + setting.getName() + ": " + ChatColor.RESET + setting.getSummary());
        }

        return true;
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
