package me.anxuiz.settings.bukkit.plugin;

import javax.annotation.Nullable;

import com.google.common.util.concurrent.Futures;
import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.PlayerSettings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCommand implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Commands.PLAYERS_ONLY);
            return true;
        }

        if (args.length < 1) {
            return false;
        }

        final Setting setting = PlayerSettings.getRegistry().get(args[0], true);

        if (setting != null) {
            if (Permissions.hasSetPermission(sender, setting)) {
                Futures.addCallback(PlayerSettings.getManager((Player)sender).deleteValue(setting), new CommandFutureCallback<Object>(sender) {
                    @Override
                    public void onSuccess(@Nullable Object result) {
                        final CommandSender sender = getSender();
                        if(sender != null) {
                            sender.sendMessage("Successfully reset " + ChatColor.YELLOW + setting.getName() + ChatColor.RESET + " to its default value.");
                            Commands.sendSettingValue(sender, setting);
                        }
                    }
                });
            } else {
                sender.sendMessage(Commands.NO_PERMISSION);
            }
        } else {
            sender.sendMessage(Commands.SETTING_NOT_FOUND);
        }

        return true;
    }
}
