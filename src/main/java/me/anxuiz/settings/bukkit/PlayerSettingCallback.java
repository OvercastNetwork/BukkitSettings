package me.anxuiz.settings.bukkit;

import javax.annotation.Nonnull;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingCallback;
import me.anxuiz.settings.SettingManager;

import org.bukkit.entity.Player;

public abstract class PlayerSettingCallback implements SettingCallback {
    public abstract void notifyChange(@Nonnull Player player, @Nonnull Setting setting, @Nonnull Object oldValue, @Nonnull Object newValue);

    public void notifyChange(@Nonnull SettingManager manager, @Nonnull Setting setting, @Nonnull Object oldValue, @Nonnull Object newValue) {
        if(manager instanceof PlayerSettingManager) {
            Player player = ((PlayerSettingManager) manager).getPlayer();
            this.notifyChange(player, setting, oldValue, newValue);
        }
    }
}
