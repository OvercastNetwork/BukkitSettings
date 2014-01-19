package me.anxuiz.settings.bukkit.commands.core;

import java.util.ArrayList;
import java.util.List;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.PlayerSettings;
import me.anxuiz.settings.bukkit.commands.util.Commands;
import me.anxuiz.settings.bukkit.commands.util.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Splitter;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class SettingCommand {

    @Command(
        aliases = {"get"},
        usage = "<setting>",
        desc = "Gets the option you have selected for a specific setting",
        max = 1
    )
    public static void setting(CommandContext args, CommandSender sender) throws CommandException {
        Setting setting = PlayerSettings.getRegistry().get(args.getString(0), true);

        if(setting != null && Permissions.hasViewPermission(sender, setting)) {
            sender.sendMessage((String[]) getSettingInfo(sender, setting).toArray());
        } else {
            throw new CommandException(Commands.SETTING_NOT_FOUND);
        }
    }

    public static List<String> getSettingInfo(CommandSender sender, Setting setting) {
        List<String> info = new ArrayList<String>();
        info.add(Commands.formatHeader(ChatColor.YELLOW + setting.getName()));
        info.add(ChatColor.YELLOW + "Summary: " + ChatColor.RESET + setting.getSummary());
        if(setting.hasDescription()) {
            for(String line : Splitter.on('\n').split(setting.getDescription())) {
                info.add(ChatColor.YELLOW + line);
            }
        }
        info.add(ChatColor.YELLOW + "Type: " + ChatColor.RESET + setting.getType().getName());
        info.add(ChatColor.YELLOW + "Default value: " + ChatColor.RESET + setting.getType().print(setting.getDefaultValue()));
        if(sender instanceof Player) {
            Object value = PlayerSettings.getManager((Player) sender).getValue(setting);
            info.add(ChatColor.YELLOW + "Current value: " + ChatColor.RESET + setting.getType().print(value));
        }

        return info;
    }
}
