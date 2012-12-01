package me.anxuiz.settings.bukkit.plugin;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.PlayerSettings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Splitter;

public class SettingCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1) {
            return false;
        }

        Setting setting = PlayerSettings.getRegistry().get(args[0], true);

        if(setting != null && Permissions.hasViewPermission(sender, setting)) {
            this.sendSettingInfo(sender, setting);
        } else {
            sender.sendMessage(Commands.SETTING_NOT_FOUND);
        }

        return true;
    }

    public void sendSettingInfo(CommandSender sender, Setting setting) {
        sender.sendMessage(Commands.formatHeader(ChatColor.YELLOW + setting.getName()));
        sender.sendMessage(ChatColor.YELLOW + "Summary: " + ChatColor.RESET + setting.getSummary());
        if(setting.hasDescription()) {
            for(String line : Splitter.on('\n').split(setting.getDescription())) {
                sender.sendMessage(ChatColor.YELLOW + line);
            }
        }
        sender.sendMessage(ChatColor.YELLOW + "Type: " + ChatColor.RESET + setting.getType().getName());
        sender.sendMessage(ChatColor.YELLOW + "Default value: " + ChatColor.RESET + setting.getType().print(setting.getDefaultValue()));
        if(sender instanceof Player) {
            Object value = PlayerSettings.getManager((Player) sender).getValue(setting);
            sender.sendMessage(ChatColor.YELLOW + "Current value: " + ChatColor.RESET + setting.getType().print(value));
        }
    }
}
