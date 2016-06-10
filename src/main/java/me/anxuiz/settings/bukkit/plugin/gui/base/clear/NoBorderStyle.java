package me.anxuiz.settings.bukkit.plugin.gui.base.clear;

import me.anxuiz.settings.bukkit.plugin.gui.BorderStyle;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * A {@link BorderStyle} that has no border
 */
public class NoBorderStyle implements BorderStyle {

    public void populateTop(Inventory inventory) {

    }

    public void populateBottom(Inventory inventory) {

    }

    public void populateLeft(Inventory inventory) {

    }

    public void populateRight(Inventory inventory) {

    }

    public Set<ItemStack> getBoarderIcons() {
        return null;
    }
}
