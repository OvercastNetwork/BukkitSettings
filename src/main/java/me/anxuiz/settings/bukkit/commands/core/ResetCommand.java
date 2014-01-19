package me.anxuiz.settings.bukkit.commands.core;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.bukkit.PlayerSettings;
import me.anxuiz.settings.bukkit.commands.util.Commands;
import me.anxuiz.settings.bukkit.commands.util.Permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class ResetCommand {

    @Command(
        aliases = {"reset"},
        usage = "<setting>",
        desc = "Resets a setting",
        max = 1
    )
    public static void reset(CommandContext args, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) {
            throw new CommandException(Commands.PLAYERS_ONLY);
        }
        
        Player player = (Player) sender;

        Setting setting = PlayerSettings.getRegistry().get(args.getString(0), true);

        if(setting != null && Permissions.hasViewPermission(sender, setting)) {
            SettingManager manager = PlayerSettings.getManager(player);
            manager.setValue(setting, setting.getDefaultValue());
            Commands.sendSettingValue(sender, manager, setting);
        }
    }
}
