package me.anxuiz.settings.bukkit.plugin.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public interface BorderStyle {

    /**
     * Define where to put the top border items in <code>inventory</code>
     * @param inventory That the items are be put in to
     */
    void populateTop(Inventory inventory);

    /**
     * Define where to put the bottom border items in <code>inventory</code>
     * @param inventory That the items are be put in to
     */
    void populateBottom(Inventory inventory);

    /**
     * Define where to put the left border items in <code>inventory</code>
     * @param inventory That the items are be put in to
     */
    void populateLeft(Inventory inventory);

    /**
     * Define where to put the right border items in <code>inventory</code>
     * @param inventory That the items are be put in to
     */
    void populateRight(Inventory inventory);

    /**
     * A set of all {@link ItemStack} that are used for the border
     * @return
     */
    Set<ItemStack> getBoarderIcons();

}
