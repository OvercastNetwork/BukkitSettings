package me.anxuiz.settings.bukkit.plugin.gui;

import com.google.common.collect.Sets;
import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MenuBuilder {

    private static final Set<Setting> settings = Sets.newLinkedHashSet(PlayerSettings.getRegistry().getSettings());

    private MenuBuilder() {}

    /**
     * Creates an customised {@link Inventory}
     * @param borderStyle this is a custom defined way the border is presented
     * @param inventoryStyle this is a custom defined way the settings inside the inventory are presented
     * @return the {@link Inventory} that is created
     */
    public static Inventory buildSettingsMenu(BorderStyle borderStyle, InventoryStyle inventoryStyle) {
        int slots = (int) ((Math.round((settings.size() / (borderStyle.getBoarderIcons() != null ? 7 : 9) + 0.5)) + (borderStyle.getBoarderIcons() != null ? 2 : 0)) * 9);
        Inventory inventory = Bukkit.createInventory(null, slots, "Customize Your Settings!");

        borderStyle.populateTop(inventory);
        borderStyle.populateLeft(inventory);
        borderStyle.populateRight(inventory);
        borderStyle.populateBottom(inventory);

        inventoryStyle.populateSlots(inventory);

        if (borderStyle.getBoarderIcons() != null) {
            List<ItemStack> boarders = new ArrayList<ItemStack>(borderStyle.getBoarderIcons());

            new Clickable(inventory, boarders) {
                @Override
                public void click(InventoryClickEvent event) {
                    if (!event.isCancelled())
                        event.setCancelled(true);
                }
            };
        }

        return inventory;
    }




}
