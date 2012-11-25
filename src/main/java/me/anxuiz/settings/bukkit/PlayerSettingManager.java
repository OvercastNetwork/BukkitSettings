package me.anxuiz.settings.bukkit;

import java.util.List;

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
    protected final Plugin parent;
    protected final Player player;
    protected final SimpleSettingCallbackManager callbackManager;

    public PlayerSettingManager(SimpleSettingCallbackManager callbackManager, Plugin parent, Player player) {
        Preconditions.checkNotNull(callbackManager, "callback manager");
        Preconditions.checkNotNull(parent, "parent plugin");
        Preconditions.checkNotNull(player, "player");

        this.parent = parent;
        this.player = player;
        this.callbackManager = callbackManager;
    }

    public Object getValue(Setting setting) {
        Preconditions.checkNotNull(setting, "setting");

        Object value = this.getMetadataValue(getMetadataKey(setting));

        if(value != null && setting.getType().isInstance(value)) {
            return value;
        } else {
            return null;
        }
    }

    public void setValue(Setting setting, Object value) {
        Preconditions.checkNotNull(setting, "setting");
        Preconditions.checkNotNull(value, "value");
        Preconditions.checkArgument(setting.getType().isInstance(value), "value is not the correct type");

        Object oldValue = this.getValue(setting);

        this.callbackManager.notifyChange(this, setting, oldValue, value);

        this.player.setMetadata(getMetadataKey(setting), new FixedMetadataValue(this.parent, value));
    }

    public void deleteValue(Setting setting) {
        Preconditions.checkNotNull(setting, "setting");

        this.player.removeMetadata(getMetadataKey(setting), this.parent);
    }

    private static String getMetadataKey(Setting setting) {
        return "setting." + setting.getName().toLowerCase();
    }

    private Object getMetadataValue(String key) {
        List<MetadataValue> values = this.player.getMetadata(key);
        for(MetadataValue value : values) {
            if(value.getOwningPlugin() == this.parent) {
                return value.value();
            }
        }
        return null;
    }

    public SettingCallbackManager getCallbackManager() {
        return this.callbackManager;
    }
}
