package me.anxuiz.settings.bukkit;

import javax.annotation.Nonnull;

import me.anxuiz.settings.SettingCallbackManager;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.base.SimpleSettingCallbackManager;
import me.anxuiz.settings.bukkit.plugin.BukkitSettingsPlugin;

import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;

public class PlayerSettings {
    private static final BukkitSettingRegistry registry = new BukkitSettingRegistry();
    private static final SimpleSettingCallbackManager callbackManager = new SimpleSettingCallbackManager();

    public static @Nonnull
    BukkitSettingRegistry getRegistry() {
        return registry;
    }

    public static @Nonnull SettingCallbackManager getCallbackManager() {
        return callbackManager;
    }

    public static @Nonnull SettingManager getManager(@Nonnull Player player) {
        Preconditions.checkNotNull(player, "player");

        return new PlayerSettingManager(callbackManager, BukkitSettingsPlugin.get(), player);
    }
}
