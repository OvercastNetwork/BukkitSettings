package me.anxuiz.settings.bukkit.plugin;

import me.anxuiz.settings.Setting;

import org.bukkit.command.CommandSender;

public class Permissions {
    public static boolean hasGetPermission(CommandSender sender, Setting setting) {
        return hasPermission(sender, setting) || sender.hasPermission(node(setting) + ".get");
    }

    public static boolean hasSetPermission(CommandSender sender, Setting setting) {
        return hasPermission(sender, setting) || sender.hasPermission(node(setting) + ".set");
    }

    public static boolean hasViewPermission(CommandSender sender, Setting setting) {
        return hasPermission(sender, setting) || sender.hasPermission(node(setting) + ".view");
    }

    private static final String LIST_PERMISSION = "setting.list";

    public static boolean hasListPermission(CommandSender sender) {
        return !sender.isPermissionSet(LIST_PERMISSION) || sender.hasPermission(LIST_PERMISSION);
    }

    private static boolean hasPermission(CommandSender sender, Setting setting) {
        String node = node(setting);
        return !sender.isPermissionSet(node) || sender.hasPermission(node);
    }

    private static String node(Setting setting) {
        String simpleName = setting.getName().toLowerCase();
        return "setting." + simpleName;
    }
}
