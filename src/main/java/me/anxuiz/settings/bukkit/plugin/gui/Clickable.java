package me.anxuiz.settings.bukkit.plugin.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class Clickable implements Listener {

    private ItemStack itemStack;
    private Inventory inventory;

    public Clickable(ItemStack itemStack, Inventory inventory) {
        this.itemStack = itemStack;
        this.inventory = inventory;
        this.register();
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void unregister() {
        InventoryManager.getListenerRegistry().unregisterListener(this, this.inventory);
    }

    public void register() {
        InventoryManager.getListenerRegistry().registerListener(this, this.inventory);
    }

    public abstract void click(InventoryClickEvent event);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(this.inventory) && event.getCurrentItem() != null && event.getCurrentItem().equals(this.itemStack)) {
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
