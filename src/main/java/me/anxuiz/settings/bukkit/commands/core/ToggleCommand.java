package me.anxuiz.settings.bukkit.commands.core;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.Toggleable;
import me.anxuiz.settings.bukkit.PlayerSettings;
import me.anxuiz.settings.bukkit.commands.util.Commands;
import me.anxuiz.settings.bukkit.commands.util.Permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class ToggleCommand {

    @Command(
        aliases = {"toggle"},
        usage = "<setting>",
        desc = "Toggles the setting for a specific setting",
        max = 1
    )
    public static void toggle(CommandContext args, CommandSender sender) throws CommandException {
        if(!(sender instanceof Player)) {
            throw new CommandException(Commands.PLAYERS_ONLY);
        }

        Player player = (Player) sender;

        Setting setting = PlayerSettings.getRegistry().get(args.getString(0), true);

        if(setting != null && Permissions.hasViewPermission(sender, setting)) {
            if(setting.getType() instanceof Toggleable) {
                if(Permissions.hasSetPermission(sender, setting)) {
                    SettingManager manager = PlayerSettings.getManager(player);
                    Object newValue = ((Toggleable) setting.getType()).getNextState(manager.getValue(setting));
                    manager.setValue(setting, newValue);
                    Commands.sendSettingValue(sender, manager, setting);
                } else {
                    throw new CommandException(Commands.NO_PERMISSION);
                }
            } else {
                throw new CommandException(setting.getName() + " is not toggleable");
            }
        } else {
            throw new CommandException(Commands.SETTING_NOT_FOUND);
        }
    }
}
