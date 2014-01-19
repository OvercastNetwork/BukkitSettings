package me.anxuiz.settings.bukkit.commands.core;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.TypeParseException;
import me.anxuiz.settings.bukkit.PlayerSettings;
import me.anxuiz.settings.bukkit.commands.util.Commands;
import me.anxuiz.settings.bukkit.commands.util.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class SetCommand {

    @Command(
        aliases = {"set"},
        usage = "<setting>, <value>",
        desc = "Gets the option you have selected for a specific setting",
        min = 2
    )
    public static void set(CommandContext args, CommandSender sender) throws CommandException {
        if(!(sender instanceof Player)) {
            throw new CommandException(Commands.PLAYERS_ONLY);
        }

        Player player = (Player) sender;

        Setting setting = PlayerSettings.getRegistry().get(args.getString(0), true);

        if(setting != null && Permissions.hasViewPermission(sender, setting)) {
            if(Permissions.hasSetPermission(sender, setting)) {
                // try to parse
                String raw = args.getJoinedStrings(1);
                Object value;
                try {
                    value = setting.getType().parse(raw);
                } catch (TypeParseException e) {
                    throw new CommandException("Failed to parse: " + raw + " (type: " + setting.getType().getName() + ")" + "\n" +
                                                ChatColor.RED + "Error: " + e.getMessage());
                }
                SettingManager manager = PlayerSettings.getManager(player);
                manager.setValue(setting, value);
                Commands.sendSettingValue(sender, manager, setting);
            } else {
                throw new CommandException(Commands.NO_PERMISSION);
            }
        } else {
            throw new CommandException(Commands.SETTING_NOT_FOUND);
        }
   }
}
