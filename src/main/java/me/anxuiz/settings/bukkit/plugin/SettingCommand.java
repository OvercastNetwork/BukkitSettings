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

        if(setting != null) {
            this.sendSettingInfo(sender, setting);
        } else {
            sender.sendMessage(Commands.SETTING_NOT_FOUND);
        }

        return true;
    }

    public void sendSettingInfo(CommandSender sender, Setting setting) {
        sender.sendMessage(ChatColor.GOLD + setting.getName());
        sender.sendMessage(ChatColor.RED + "Type: " + setting.getType().getName());
        sender.sendMessage(ChatColor.RED + setting.getSummary());
        if(setting.hasDescription()) {
            for(String line : Splitter.on('\n').split(setting.getDescription())) {
                sender.sendMessage(ChatColor.GREEN + line);
            }
        }
        sender.sendMessage("Default: " + setting.getType().print(setting.getDefaultValue()));
        if(sender instanceof Player) {
            Player player = (Player) sender;
            sender.sendMessage("Current value: " + setting.getType().print(PlayerSettings.getManager(player).getValue(setting)));
        }
    }
}
