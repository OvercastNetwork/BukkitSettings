package me.anxuiz.settings.bukkit.plugin;

import javax.annotation.Nullable;

import me.anxuiz.settings.Setting;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitSettingsPlugin extends JavaPlugin {

    private static BukkitSettingsPlugin inst = null;
    public static @Nullable BukkitSettingsPlugin get() {
        return inst;
    }

    @Override
    public void onDisable() {
        inst = null;
    }

    @Override
    public void onEnable() {
        inst = this;

        // Assign Executors
        this.getCommand("get").setExecutor(new GetCommand());
        this.getCommand("set").setExecutor(new SetCommand());
        this.getCommand("setting").setExecutor(new SettingCommand());
        this.getCommand("settings").setExecutor(new SettingsCommand());
        this.getCommand("toggle").setExecutor(new ToggleCommand());
        this.getCommand("reset").setExecutor(new ResetCommand());

        // Assign Tab Completers
        this.getCommand("get").setTabCompleter(new GenericTabCompleter(
                new PermissionsChecker() {
                    public boolean check(CommandSender sender, Setting setting) {
                        return Permissions.hasViewPermission(sender, setting) && Permissions.hasGetPermission(sender, setting);
                    }
                }
        ));
        GenericTabCompleter setTabCompleter = new GenericTabCompleter(
                new PermissionsChecker() {
                    public boolean check(CommandSender sender, Setting setting) {
                        return Permissions.hasViewPermission(sender, setting) && Permissions.hasSetPermission(sender, setting);
                    }
                }
        );
        this.getCommand("set").setTabCompleter(setTabCompleter);
        this.getCommand("toggle").setTabCompleter(setTabCompleter);
        this.getCommand("setting").setTabCompleter(new GenericTabCompleter(
                new PermissionsChecker() {
                    public boolean check(CommandSender sender, Setting setting) {
                        return Permissions.hasViewPermission(sender, setting);
                    }
                }
        ));
        this.getCommand("reset").setTabCompleter(setTabCompleter);
    }
}
