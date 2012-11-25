package me.anxuiz.settings.bukkit.plugin;

import java.util.Arrays;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.TypeParseException;
import me.anxuiz.settings.bukkit.PlayerSettings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

public class SetCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Commands.PLAYERS_ONLY);
            return true;
        }

        if(args.length < 2) {
            return false;
        }

        Player player = (Player) sender;

        Setting setting = PlayerSettings.getRegistry().get(args[0], true);

        if(setting != null) {
            // try to parse
            String raw = Joiner.on(' ').join(Arrays.asList(args).subList(1, args.length));
            Object value;
            try {
                value = setting.getType().parse(raw);
            } catch (TypeParseException e) {
                sender.sendMessage(ChatColor.RED + "Failed to parse: " + raw);
                sender.sendMessage(ChatColor.RED + "Error: " + raw);
                return true;
            }
            PlayerSettings.getManager(player).setValue(setting, value);
            sender.sendMessage(ChatColor.GREEN + "Set " + setting.getName() + " to " + setting.getType().print(value));
        } else {
            sender.sendMessage(Commands.SETTING_NOT_FOUND);
        }

        return true;
    }
}
