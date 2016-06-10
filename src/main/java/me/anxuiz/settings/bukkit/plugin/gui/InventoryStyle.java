package me.anxuiz.settings.bukkit.plugin.gui;

import org.bukkit.inventory.Inventory;

public interface InventoryStyle {

    /**
     * A method to set where the settings will be placed
     * @param inventory where the settngs are being placed
     */
    void populateSlots(Inventory inventory);

}
