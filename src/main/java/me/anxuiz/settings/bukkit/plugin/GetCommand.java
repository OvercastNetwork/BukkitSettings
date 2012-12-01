package me.anxuiz.settings.bukkit.plugin;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.PlayerSettings;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Commands.PLAYERS_ONLY);
            return true;
        }

        if(args.length < 1) {
            return false;
        }

        Setting setting = PlayerSettings.getRegistry().get(args[0], true);
        if(setting != null && Permissions.hasViewPermission(sender, setting)) {
            if(Permissions.hasGetPermission(sender, setting)) {
                Commands.sendSettingValue(sender, setting);
            } else {
                sender.sendMessage(Commands.NO_PERMISSION);
            }
        } else {
            sender.sendMessage(Commands.SETTING_NOT_FOUND);
        }

        return true;
    }
}
