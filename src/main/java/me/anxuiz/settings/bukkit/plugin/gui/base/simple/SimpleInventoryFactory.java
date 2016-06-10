package me.anxuiz.settings.bukkit.plugin.gui.base.simple;

import me.anxuiz.settings.bukkit.plugin.gui.InventoryStyle;
import me.anxuiz.settings.bukkit.plugin.gui.base.factory.InventoryStyleFactory;
import org.bukkit.entity.Player;

public class SimpleInventoryFactory implements InventoryStyleFactory {

    public InventoryStyle createStyle(Player player) {
        return new SimpleInventoryStyle(player);
    }
}
