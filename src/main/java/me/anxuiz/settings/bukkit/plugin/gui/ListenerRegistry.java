package me.anxuiz.settings.bukkit.plugin.gui;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.anxuiz.settings.bukkit.plugin.BukkitSettingsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;

import java.util.Map;
import java.util.Set;

public class ListenerRegistry {

    private final BukkitSettingsPlugin plugin;
    private final PluginManager pluginManager;

    private final Map<Inventory, Set<Listener>> listeners;

    public ListenerRegistry(BukkitSettingsPlugin plugin) {
        this.plugin = plugin;
        this.pluginManager = Bukkit.getPluginManager();
        this.listeners = Maps.newLinkedHashMap();
        this.cleanListeners();
    }

    public void registerListener(Listener listener, Inventory inventory) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(inventory);
        Set<Listener> values = Sets.newHashSet();
        if (this.listeners.containsKey(inventory))
            values = this.listeners.get(inventory);
        values.add(listener);
        this.listeners.put(inventory, values);
        this.pluginManager.registerListener(this.plugin, listener);
    }

    public void unregisterListener(Listener listener, Inventory inventory) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(inventory);
        this.listeners.get(inventory).remove(listener);
        this.pluginManager.unregisterListener(listener);
    }

    private void cleanListeners() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            public void run() {
                for (Map.Entry<Inventory, Set<Listener>> map : listeners.entrySet()) {
                    if (!(map.getKey().getViewers().size() > 0)) {
                        for (Listener listener : map.getValue()) {
                            unregisterListener(listener, map.getKey());
                        }
                    }
                }
            }
        }, 0L, 20L * 60); // Repeats every minute
    }

}
