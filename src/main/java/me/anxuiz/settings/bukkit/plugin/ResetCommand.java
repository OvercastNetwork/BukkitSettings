package me.anxuiz.settings.bukkit.plugin;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.PlayerSettings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Commands.PLAYERS_ONLY);
            return true;
        }

        if (args.length < 1) {
            return false;
        }

        Setting setting = PlayerSettings.getRegistry().get(args[0], true);

        if (setting != null) {
            if (Permissions.hasSetPermission(sender, setting)) {
                PlayerSettings.getManager((Player)sender).deleteValue(setting);
                sender.sendMessage("Successfully reset " + ChatColor.YELLOW + setting.getName() + ChatColor.RESET + " to its default value.");
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
