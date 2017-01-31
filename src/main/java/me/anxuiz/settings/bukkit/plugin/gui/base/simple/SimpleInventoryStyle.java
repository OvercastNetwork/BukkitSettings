package me.anxuiz.settings.bukkit.plugin.gui.base.simple;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.plugin.SettingsCommand;
import me.anxuiz.settings.bukkit.plugin.gui.InventoryStyle;
import me.anxuiz.settings.bukkit.util.IconUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public class SimpleInventoryStyle implements InventoryStyle {

    private Player player;

    public SimpleInventoryStyle(@Nonnull Player player) {
        this.player = player;
    }

    public void populateSlots(Inventory inventory) {
        for (Setting setting : SettingsCommand.getSortedPlayerSettings(this.player)) {
            inventory.addItem(IconUtil.makeIcon(setting, this.player, inventory));
        }
    }
}
