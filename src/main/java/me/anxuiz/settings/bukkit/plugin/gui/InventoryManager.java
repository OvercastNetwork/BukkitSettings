package me.anxuiz.settings.bukkit.plugin.gui;

import me.anxuiz.settings.bukkit.plugin.BukkitSettingsPlugin;

import javax.annotation.Nonnull;

public class InventoryManager {

    private static final ListenerRegistry listenerRegistry = new ListenerRegistry(BukkitSettingsPlugin.get());

    public static @Nonnull ListenerRegistry getListenerRegistry() {
        return listenerRegistry;
    }

}
