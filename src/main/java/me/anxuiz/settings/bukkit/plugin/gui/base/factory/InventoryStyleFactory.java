package me.anxuiz.settings.bukkit.plugin.gui.base.factory;

import me.anxuiz.settings.bukkit.plugin.gui.InventoryStyle;
import org.bukkit.entity.Player;

public interface InventoryStyleFactory {

    InventoryStyle createStyle(Player player);

}
