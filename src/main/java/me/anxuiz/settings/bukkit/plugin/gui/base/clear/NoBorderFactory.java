package me.anxuiz.settings.bukkit.plugin.gui.base.clear;

import me.anxuiz.settings.bukkit.plugin.gui.BorderStyle;
import me.anxuiz.settings.bukkit.plugin.gui.base.factory.BorderStyleFactory;

public class NoBorderFactory implements BorderStyleFactory {

    public BorderStyle createStyle() {
        return new NoBorderStyle();
    }
}
