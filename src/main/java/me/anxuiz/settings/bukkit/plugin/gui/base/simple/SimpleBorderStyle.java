package me.anxuiz.settings.bukkit.plugin.gui.base.simple;

import com.google.common.collect.Sets;
import me.anxuiz.settings.bukkit.plugin.gui.BorderStyle;
import me.anxuiz.settings.bukkit.util.ItemBuilder;
import me.anxuiz.settings.bukkit.util.InventoryUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class SimpleBorderStyle implements BorderStyle {

    private final ItemStack BORDER = ItemBuilder.newItem(Material.STAINED_GLASS_PANE).setDamage((short) 7).setName(ChatColor.GRAY + "#").createItem();

    public SimpleBorderStyle() {
    }

    public void populateTop(Inventory inventory) {
        for (int i = 0; i < 9; i++) {
            InventoryUtil.setItem(this.BORDER, inventory, i, 0, true);
        }
    }

    public void populateBottom(Inventory inventory) {
        for (int j = 0; j < inventory.getSize() / 9; j++) {
            for (int i = 0; i < 9; i++) {
                if (j == (inventory.getSize() / 9) - 1) {
                    InventoryUtil.setItem(this.BORDER, inventory, i, (inventory.getSize()  / 9) - 1, true);
                }
            }
        }
    }

    public void populateLeft(Inventory inventory) {
        for (int j = 0; j < inventory.getSize() / 9; j++) {
            for (int i = 0; i < 9; i++) {
                if (i == 0) {
                    InventoryUtil.setItem(this.BORDER, inventory, 0, j, true);

                }
            }
        }
    }

    public void populateRight(Inventory inventory) {
        for (int j = 0; j < inventory.getSize() / 9; j++) {
            for (int i = 0; i < 9; i++) {
                InventoryUtil.setItem(this.BORDER, inventory, 8, j, true);

            }
        }
    }



    public Set<ItemStack> getBoarderIcons() {
        return Sets.newHashSet(this.BORDER /* Put other boarders in here */);
    }
}