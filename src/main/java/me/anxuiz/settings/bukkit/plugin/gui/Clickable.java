package me.anxuiz.settings.bukkit.plugin.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public abstract class Clickable implements Listener {

    private List<ItemStack> itemStack;
    private Inventory inventory;

    /**
     * Creates a wrapped class for item listeners that is inventory specific
     * @param inventory Inventory the listener will belong to
     * @param itemStack a {@link List} of {@link ItemStack} that the listener looks for
     */
    public Clickable(Inventory inventory, List<ItemStack> itemStack) {
        this.itemStack = itemStack;
        this.inventory = inventory;
        this.register();
    }

    /**
     * @return A {@link List} of {@link ItemStack} that the listener looks for
     */
    public List<ItemStack> getItemStacks() {
        return this.itemStack;
    }

    /**
     * Set the {@link List} of {@link ItemStack}
     * @param itemStack list to set
     */
    public void setItemStack(List<ItemStack> itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * @return {@link Inventory} that this listener will be looking in
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Unregisters this {@link Clickable} using {@link ListenerRegistry#unregisterListener(Listener, Inventory)} method
     */
    public void unregister() {
        InventoryManager.getListenerRegistry().unregisterListener(this, this.inventory);
    }

    /**
     * Registers this {@link Clickable} using {@link ListenerRegistry#registerListener(Listener, Inventory)} method
     */
    public void register() {
        InventoryManager.getListenerRegistry().registerListener(this, this.inventory);
    }

    /**
     * An abstract method that is created as an anonymous class to define what happens when one of the border items is clickd
     * @param event {@link InventoryClickEvent}
     */
    public abstract void click(InventoryClickEvent event);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(this.inventory) && event.getCurrentItem() != null && this.itemStack.contains(event.getCurrentItem())) {
            click(event);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(this.inventory)) {
            this.unregister();
        }
    }
}
