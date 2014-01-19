package me.anxuiz.settings.bukkit.commands.util;

import me.anxuiz.settings.Setting;
import org.bukkit.command.CommandSender;

public interface PermissionsChecker {
    boolean check(CommandSender sender, Setting setting);
}
