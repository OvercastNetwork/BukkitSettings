package me.anxuiz.settings.bukkit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingCallback;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.base.SimpleSettingCallbackManager;
import org.bukkit.entity.Player;

public abstract class PlayerSettingCallback implements SettingCallback {
    public abstract void notifyChange(@Nonnull Player player, @Nonnull Setting setting, @Nullable Object oldValue, @Nullable Object newValue);

    public void notifyChange(@Nonnull SettingManager manager, @Nonnull Setting setting, @Nullable Object oldValue, @Nullable Object newValue) {
        if(manager instanceof PlayerSettingManager) {
            Player player = ((PlayerSettingManager) manager).getPlayer();
            this.notifyChange(player, setting, oldValue, newValue);
        }
    }

    protected void yield() {
        SimpleSettingCallbackManager.yield();
    }
}
