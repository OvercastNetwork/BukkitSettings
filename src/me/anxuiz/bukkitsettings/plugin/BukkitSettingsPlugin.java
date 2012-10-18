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

    public BukkitSettingsPlugin() {
        inst = this;
    }

    public static @Nullable BukkitSettingsPlugin get() {
        return inst;
    }

    @Override
    public void onEnable() {
        // Copy the default configuration
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    @Override
    public void onDisable() {
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
