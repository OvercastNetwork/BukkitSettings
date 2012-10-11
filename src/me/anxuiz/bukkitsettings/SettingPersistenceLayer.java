package me.anxuiz.bukkitsettings;

import java.util.Map;

import org.bukkit.entity.Player;

public interface SettingPersistenceLayer {
    Map<Setting, Object> getSettings(Player player);

    void saveSetting(Player player, Setting setting, Object value);

    void saveSettings(Player player, Map<Setting, Object> settings);
}
