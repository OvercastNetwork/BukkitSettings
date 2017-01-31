package me.anxuiz.settings.bukkit.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

    private InventoryUtil() {}

    public static boolean setItem(ItemStack itemStack, Inventory inventory, int i, int j, boolean force) {
        if ((inventory.getItem(locationToLinearSlot(i,j)) != null && force) || inventory.getItem(locationToLinearSlot(i,j)) == null) {
            inventory.setItem(locationToLinearSlot(i,j), itemStack);
            return true;
        }
        return false;
    }

    public static int locationToLinearSlot(int i, int j) {
        return j * 9 + i;
    }

}

