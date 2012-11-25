package me.anxuiz.settings.bukkit.plugin;

import javax.annotation.Nullable;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitSettingsPlugin extends JavaPlugin {

    private static BukkitSettingsPlugin inst = null;
    public static @Nullable BukkitSettingsPlugin get() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;

        this.registerEvents(new PlayerListener());
        this.getCommand("get").setExecutor(new GetCommand(this));
        this.getCommand("set").setExecutor(new SetCommand(this));
        this.getCommand("setting").setExecutor(new SettingCommand());
        this.getCommand("settings").setExecutor(new SettingsCommand());
        this.getCommand("toggle").setExecutor(new ToggleCommand());
    }

    @Override
    public void onDisable() {
        inst = null;
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
