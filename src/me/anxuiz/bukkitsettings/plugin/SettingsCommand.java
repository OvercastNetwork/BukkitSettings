package me.anxuiz.bukkitsettings.plugin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.anxuiz.bukkitsettings.PlayerSettings;
import me.anxuiz.bukkitsettings.Setting;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;

public class SettingsCommand implements CommandExecutor {
    public static int RESULTS_PER_PAGE = 8;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<Setting> settings = getSortedPlayerSettings();

        int maxPage = Math.max(1, settings.size() / RESULTS_PER_PAGE); // minimum max page is 1

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

        sender.sendMessage(ChatColor.GOLD + "Settings (Page " + page + " of " + maxPage + ")");

        for(int i = RESULTS_PER_PAGE * (page - 1); i < RESULTS_PER_PAGE * page && i < settings.size(); i++) {
            Setting setting = settings.get(i);
            sender.sendMessage(setting.getName());
        }

        return true;
    }

    public static List<Setting> getSortedPlayerSettings() {
        List<Setting> settings = Lists.newArrayList(PlayerSettings.getRegistry().getSettings());
        Collections.sort(settings, new SettingNameComparator());
        return settings;
    }

    private static class SettingNameComparator implements Comparator<Setting> {
        @Override
        public int compare(Setting s1, Setting s2) {
            return s1.getName().compareTo(s2.getName());
        }
    }
}
