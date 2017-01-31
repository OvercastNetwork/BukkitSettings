package me.anxuiz.settings.bukkit.plugin.gui;

import com.google.common.collect.Sets;
import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.Toggleable;
import me.anxuiz.settings.bukkit.PlayerSettings;
import me.anxuiz.settings.bukkit.plugin.Commands;
import me.anxuiz.settings.bukkit.plugin.Permissions;
import me.anxuiz.settings.bukkit.plugin.SettingsCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class SettingsMenuBuilder {

    private static final Set<Setting> settings = Sets.newLinkedHashSet(PlayerSettings.getRegistry().getSettings());

    private static final ItemStack BORDER_ICON = new ItemBuilder(Material.STAINED_GLASS_PANE).setDamage((short) 7).setName(ChatColor.GRAY + "#").createItem();

    private static Inventory makeInventory() {
        int size = settings.size();
        int slots = (int) ((Math.round((size / 7) + 0.5) + 2) * 9);
        return makeBorder(Bukkit.createInventory(null, slots, "Customize Your Settings!"));

    }

    /**
     *                      INVENTORY
     *
     * Based on vector i and j notation.
     * 0i through 8i horizontally
     * 1j through <code>n</code>j vertically, where <code>n</code> is number of rows
     *
     */
    private static Inventory makeBorder(Inventory inventory) {
        int k = 0;
        for (int j = 1; j <= inventory.getSize() / 9; j++) {
            for (int i = 0; i < 9; i++) {
                if (i == 0 || i == 8 || j == 1 || j == inventory.getSize() / 9)
                    inventory.setItem(k, BORDER_ICON);
                k++;
            }
        }
        // Create single click handler for all border items
        new Clickable(BORDER_ICON, inventory) {
            @Override
            public void click(InventoryClickEvent event) {
                if (!event.isCancelled())
                    event.setCancelled(true);
            }
        };
        return inventory;
    }

    private static Inventory populateSettings(Inventory inventory, Player player) {
        for (Setting setting : SettingsCommand.getSortedPlayerSettings(player)) {
            inventory.addItem(makeIcon(setting, player, inventory));
        }
        return inventory;
    }

    private static ItemStack makeIcon(final Setting setting, final Player player, final Inventory inventory) {
         ItemStack icon = new ItemBuilder(PlayerSettings.getRegistry().getIcon(setting))
                .setName(ChatColor.YELLOW + setting.getName())
                .addLore(ChatColor.GREEN + "Current Value: " + ChatColor.RESET + setting.getType().print(PlayerSettings.getManager(player).getValue(setting)))
                .addLore(ChatColor.AQUA + "Default Value: " + ChatColor.RESET + setting.getType().print(setting.getDefaultValue()))
                .addLore(ChatColor.YELLOW + "Summary: " + ChatColor.RESET + setting.getSummary())
                .addLore(ChatColor.RED + "" + ChatColor.BOLD + setting.getDescription())
                .addLore(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click to cycle values!")
                .createItem();
        // Each item has its own click handler:
        return new Clickable(icon, inventory) {
            @Override
            public void click(InventoryClickEvent event) {
                Player player = event.getActor();
                if (Permissions.hasViewPermission(player, setting) && Permissions.hasSetPermission(player, setting)) {
                    // Update setting
                    SettingManager manager = PlayerSettings.getManager(player);
                    manager.setValue(setting, setting.getType() instanceof Toggleable ? ((Toggleable) setting.getType()).getNextState(manager.getValue(setting)) : manager.getValue(setting));
                    Commands.sendSettingValue(player, manager, setting);
                    // Add item to inventory
                    event.getInventory().setItem(event.getSlot(), makeIcon(setting, player, inventory));
                    player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
                    // Unregister old listener ready for new item listener
                    this.unregister();
                } else player.sendMessage(Commands.NO_PERMISSION);
            }
        }.getItemStack();
    }

    public static Inventory build(Player player) {
        return populateSettings(makeInventory(), player);
    }
}
