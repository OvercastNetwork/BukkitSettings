package me.anxuiz.settings.bukkit.plugin.gui.base.simple;

import me.anxuiz.settings.bukkit.plugin.gui.BorderStyle;
import me.anxuiz.settings.bukkit.plugin.gui.base.factory.BorderStyleFactory;

public class SimpleBorderFactory implements BorderStyleFactory {

    public BorderStyle createStyle() {
        return new SimpleBorderStyle();
    }
}
