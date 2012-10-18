package me.anxuiz.bukkitsettings;

import me.anxuiz.bukkitsettings.impl.PlayerSettingManager;
import me.anxuiz.bukkitsettings.impl.SimpleSettingRegistry;
import me.anxuiz.bukkitsettings.plugin.BukkitSettingsPlugin;

import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;

public class PlayerSettings {
    private static SettingRegistry registry = new SimpleSettingRegistry();

    public static SettingRegistry getRegistry() {
        return registry;
    }

    public static SettingManager getManager(Player player) throws IllegalStateException {
        Preconditions.checkNotNull(player);

        BukkitSettingsPlugin plugin = BukkitSettingsPlugin.get();
        Preconditions.checkState(plugin != null, "BukkitSettings plugin has not been loaded");

        return new PlayerSettingManager(plugin, player);
    }
}
