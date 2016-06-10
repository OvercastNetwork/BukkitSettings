package me.anxuiz.settings.bukkit.plugin.gui;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.anxuiz.settings.bukkit.plugin.BukkitSettingsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

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

    /**
     * This will register a listener to an inventory, as an inventory can have multiple listeners for individual item handling
     * @param listener {@link Listener} that is being registered to <code>inventory</code>
     * @param inventory {@link Inventory} that is having <code>listener</code> being registered to it
     */
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

    /**
     * This will unregister a listener that is assigned to an inventory
     * @param listener {@link Listener} that is being unregistered from <code>inventory</code>
     * @param inventory {@link Inventory} that is having <code>listener</code> being unregistered from it
     */
    public void unregisterListener(Listener listener, Inventory inventory) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(inventory);
        this.pluginManager.unregisterListener(listener);
        this.listeners.get(inventory).remove(listener);
        if (this.listeners.get(inventory).size() == 0) this.listeners.remove(inventory);
    }

    /**
     * This method is run every hour to ensure that no listeners are left and not disposed of, this is a safety measure that shouldn't be necessary
     */
    private void cleanListeners() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            public void run() {
                for (Map.Entry<Inventory, Set<Listener>> map : listeners.entrySet()) {
                    if (!(map.getKey().getViewers().size() > 0)) {
                        for (Listener listener : map.getValue()) {
                            Bukkit.broadcastMessage("unregister");
                        }
                    }
                }
            }
        }, 0L, 20L * 3600); // Repeats every hour
    }


}
