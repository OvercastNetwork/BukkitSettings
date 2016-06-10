package me.anxuiz.settings.bukkit.util;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.Toggleable;
import me.anxuiz.settings.bukkit.PlayerSettings;
import me.anxuiz.settings.bukkit.plugin.BukkitSetting;
import me.anxuiz.settings.bukkit.plugin.Commands;
import me.anxuiz.settings.bukkit.plugin.Permissions;
import me.anxuiz.settings.bukkit.plugin.gui.Clickable;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class IconUtil {

    private IconUtil() {}

    public static ItemStack makeIcon(final Setting setting, final Player player, final Inventory inventory) {
        if (setting instanceof BukkitSetting) {
            BukkitSetting bukkitSetting = (BukkitSetting) setting;
            ItemStack icon = new ItemBuilder(bukkitSetting.getIcon())
                    .setName(ChatColor.YELLOW + setting.getName())
                    .addLore(ChatColor.GREEN + "Current Value: " + ChatColor.RESET + setting.getType().print(PlayerSettings.getManager(player).getValue(setting)))
                    .addLore(ChatColor.AQUA + "Default Value: " + ChatColor.RESET + setting.getType().print(setting.getDefaultValue()))
                    .addLore(ChatColor.YELLOW + "Summary: " + ChatColor.RESET + setting.getSummary())
                    .addLore(ChatColor.RED + "" + ChatColor.BOLD + setting.getDescription())
                    .addLore(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click to cycle values!")
                    .createItem();
            // Each item has its own click handler:
            return new Clickable(inventory, Collections.singletonList(icon)) {
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
            }.getItemStacks().get(0);
        }
        return null;
    }

}
