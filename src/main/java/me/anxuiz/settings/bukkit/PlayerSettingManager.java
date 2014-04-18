package me.anxuiz.settings.bukkit;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingCallbackManager;
import me.anxuiz.settings.base.AbstractSettingManager;
import me.anxuiz.settings.base.SimpleSettingCallbackManager;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;

public class PlayerSettingManager extends AbstractSettingManager {
    protected final @Nullable Plugin parent;
    protected final @Nonnull Player player;
    protected final @Nonnull SimpleSettingCallbackManager callbackManager;

    public PlayerSettingManager(@Nonnull SimpleSettingCallbackManager callbackManager, @Nullable Plugin parent, @Nonnull Player player) {
        Preconditions.checkNotNull(callbackManager, "callback manager");
        Preconditions.checkNotNull(player, "player");

        this.parent = parent;
        this.player = player;
        this.callbackManager = callbackManager;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public Object getRawValue(Setting setting) {
        Preconditions.checkNotNull(setting, "setting");

        Object value = this.getMetadataValue(getMetadataKey(setting));

        if(value != null && setting.getType().isInstance(value)) {
            return value;
        } else {
            return null;
        }
    }

    public void setValue(Setting setting, Object value, boolean notifyGlobal) {
        Preconditions.checkNotNull(setting, "setting");
        Preconditions.checkNotNull(value, "value");
        Preconditions.checkArgument(setting.getType().isInstance(value), "value is not the correct type");

        if(this.parent == null) {
            return;
        }

        Object oldValue = this.getValue(setting);

        this.callbackManager.notifyChange(this, setting, oldValue, value, notifyGlobal);

        this.player.setMetadata(getMetadataKey(setting), new FixedMetadataValue(this.parent, value));
    }

    public void deleteValue(Setting setting) {
        Preconditions.checkNotNull(setting, "setting");

        if(this.parent == null) {
            return;
        }

        this.callbackManager.notifyChange(this, setting, this.getValue(setting), setting.getDefaultValue(), true);

        this.player.removeMetadata(getMetadataKey(setting), this.parent);
    }

    private static String getMetadataKey(Setting setting) {
        return "setting." + setting.getName().toLowerCase();
    }

    private Object getMetadataValue(String key) {
        if(this.parent == null) {
            return null;
        }

        List<MetadataValue> values = this.player.getMetadata(key);
        for(MetadataValue value : values) {
            if(value.getOwningPlugin().equals(this.parent)) {
                return value.value();
            }
        }

        return null;
    }

    public SettingCallbackManager getCallbackManager() {
        return this.callbackManager;
    }
}
