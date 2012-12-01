package me.anxuiz.settings.bukkit.plugin;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.bukkit.PlayerSettings;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import com.google.common.base.Strings;

public class Commands {
    public static final String PLAYERS_ONLY = ChatColor.RED + "Settings are only available to players.";
    public static final String SETTING_NOT_FOUND = ChatColor.RED + "No settings matched query.";
    public static final String NO_PERMISSION = ChatColor.RED + "No permission";

    public static String formatHeader(String title) {
        return formatHeader(title, ChatColor.RED);
    }

    public static String formatHeader(String title, ChatColor paddingColor) {
        int titleLen = ChatColor.stripColor(title).length();
        int padLen = (ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH - titleLen) / 2 - 2;
        String padding = paddingColor.toString() + ChatColor.STRIKETHROUGH + Strings.repeat("-", padLen);
        return padding + ChatColor.RESET + " " + title + " " + padding;
    }

    public static void sendSettingValue(CommandSender sender, Setting setting) {
        if(sender instanceof Player) {
            sendSettingValue(sender, PlayerSettings.getManager((Player) sender), setting);
        }
    }

    public static void sendSettingValue(CommandSender sender, SettingManager settingManager, Setting setting) {
        Object value = settingManager.getValue(setting);
        sender.sendMessage(ChatColor.YELLOW + setting.getName() + ": " + ChatColor.RESET + setting.getType().print(value));
    }
}
