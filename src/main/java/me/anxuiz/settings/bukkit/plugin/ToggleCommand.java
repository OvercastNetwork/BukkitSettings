package me.anxuiz.settings.bukkit.plugin;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.Toggleable;
import me.anxuiz.settings.bukkit.PlayerSettings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Commands.PLAYERS_ONLY);
            return true;
        }

        if(args.length < 1) {
            return false;
        }

        Player player = (Player) sender;

        Setting setting = PlayerSettings.getRegistry().get(args[0], true);

        if(setting != null) {
            if(setting.getType() instanceof Toggleable) {
                SettingManager manager = PlayerSettings.getManager(player);
                Object newValue = ((Toggleable) setting.getType()).getNextState(manager.getValue(setting));
                manager.setValue(setting, newValue);
                Commands.sendSettingValue(sender, manager, setting);
            } else {
                sender.sendMessage(ChatColor.RED + setting.getName() + " is not toggleable");
            }
        } else {
            sender.sendMessage(Commands.SETTING_NOT_FOUND);
        }

        return true;
    }
}
