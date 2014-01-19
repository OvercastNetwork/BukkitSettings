package me.anxuiz.settings.bukkit.commands.core;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.PlayerSettings;
import me.anxuiz.settings.bukkit.commands.util.Commands;
import me.anxuiz.settings.bukkit.commands.util.Permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class GetCommand {
    
    @Command(
        aliases = {"get"},
        usage = "<setting>",
        desc = "Gets the option you have selected for a specific setting",
        min = 1,
        max = 1
    )
    public static void get(CommandContext args, CommandSender sender) throws CommandException {
        if(!(sender instanceof Player)) {
            throw new CommandException(Commands.PLAYERS_ONLY);
        }

        Setting setting = PlayerSettings.getRegistry().get(args.getString(0), true);
        if(setting != null && Permissions.hasViewPermission(sender, setting)) {
            if(Permissions.hasGetPermission(sender, setting)) {
                Commands.sendSettingValue(sender, setting);
            } else {
                throw new CommandException(Commands.NO_PERMISSION);
            }
        } else {
            throw new CommandException(Commands.SETTING_NOT_FOUND);
        }
    }
}
