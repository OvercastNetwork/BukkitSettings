package me.anxuiz.bukkitsettings.plugin;

import javax.annotation.Nullable;

import me.anxuiz.bukkitsettings.Setting;
import me.anxuiz.bukkitsettings.SettingScope;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public final class BukkitSettingsPlugin extends JavaPlugin {
    protected final Multimap<SettingScope, Setting> settings = ArrayListMultimap.create();

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
