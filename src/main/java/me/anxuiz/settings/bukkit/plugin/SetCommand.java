package me.anxuiz.settings.bukkit.plugin;

import java.util.Arrays;
import javax.annotation.Nullable;

import com.google.common.util.concurrent.Futures;
import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.TypeParseException;
import me.anxuiz.settings.bukkit.PlayerSettings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

public class SetCommand implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Commands.PLAYERS_ONLY);
            return true;
        }

        if(args.length < 2) {
            return false;
        }

        Player player = (Player) sender;

        final Setting setting = PlayerSettings.getRegistry().get(args[0], true);

        if(setting != null && Permissions.hasViewPermission(sender, setting)) {
            if(Permissions.hasSetPermission(sender, setting)) {
                // try to parse
                String raw = Joiner.on(' ').join(Arrays.asList(args).subList(1, args.length));
                Object value;
                try {
                    value = setting.getType().parse(raw);
                } catch (TypeParseException e) {
                    sender.sendMessage(ChatColor.RED + "Failed to parse: " + raw + " (type: " + setting.getType().getName() + ")");
                    sender.sendMessage(ChatColor.RED + "Error: " + e.getMessage());
                    return true;
                }
                final SettingManager manager = PlayerSettings.getManager(player);
                Futures.addCallback(manager.setValue(setting, value), new CommandFutureCallback<Object>(sender) {
                    @Override
                    public void onSuccess(@Nullable Object result) {
                        Commands.sendSettingValue(getSender(), manager, setting);
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
