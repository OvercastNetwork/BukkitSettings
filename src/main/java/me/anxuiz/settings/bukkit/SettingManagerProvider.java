package me.anxuiz.settings.bukkit;

import me.anxuiz.settings.SettingManager;
import org.bukkit.entity.Player;

public interface SettingManagerProvider {
    SettingManager getManager(Player player);
}
