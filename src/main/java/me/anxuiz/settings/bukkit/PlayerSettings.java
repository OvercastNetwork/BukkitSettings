package me.anxuiz.settings.bukkit;

import me.anxuiz.settings.SettingCallbackManager;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.SettingRegistry;
import me.anxuiz.settings.base.SimpleSettingCallbackManager;
import me.anxuiz.settings.base.SimpleSettingRegistry;
import me.anxuiz.settings.bukkit.plugin.BukkitSettingsPlugin;

import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;

public class PlayerSettings {
    private static SettingRegistry registry = new SimpleSettingRegistry();
    private static SimpleSettingCallbackManager callbackManager = new SimpleSettingCallbackManager();

    public static SettingRegistry getRegistry() {
        return registry;
    }

    public static SettingCallbackManager getCallbackManager() {
        return callbackManager;
    }

    public static SettingManager getManager(Player player) throws IllegalStateException {
        Preconditions.checkNotNull(player);

        BukkitSettingsPlugin plugin = BukkitSettingsPlugin.get();
        Preconditions.checkState(plugin != null, "BukkitSettings plugin has not been loaded");

        return new PlayerSettingManager(callbackManager, plugin, player);
    }
}
