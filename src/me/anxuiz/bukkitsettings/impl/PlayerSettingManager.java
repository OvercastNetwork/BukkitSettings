package me.anxuiz.bukkitsettings.impl;

import java.util.List;

import me.anxuiz.bukkitsettings.Setting;
import me.anxuiz.bukkitsettings.SettingCallbackManager;
import me.anxuiz.bukkitsettings.SettingChangeEvent;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;

public class PlayerSettingManager extends AbstractSettingManager {
    protected final Plugin parent;
    protected final Player player;
    protected final SimpleSettingCallbackManager callbackManager = new SimpleSettingCallbackManager();

    public PlayerSettingManager(Plugin parent, Player player) {
        Preconditions.checkNotNull(parent);
        Preconditions.checkNotNull(player);

        this.parent = parent;
        this.player = player;
    }

    @Override
    public Object getValue(Setting setting) {
        Preconditions.checkNotNull(setting);

        Object value = this.getMetadataValue(getMetadataKey(setting));

        if(value != null && setting.getType().isInstance(value)) {
            return value;
        } else {
            return null;
        }
    }

    @Override
    public void setValue(Setting setting, Object value) {
        Preconditions.checkNotNull(setting);
        Preconditions.checkNotNull(value);
        Preconditions.checkArgument(setting.getType().isInstance(value));

        Object oldValue = this.getValue(setting);

        SettingChangeEvent event = new SettingChangeEvent(setting, oldValue, value);
        this.parent.getServer().getPluginManager().callEvent(event);

        if(!event.isCancelled()) {
            this.player.setMetadata(getMetadataKey(setting), new FixedMetadataValue(this.parent, value));
        }
    }

    @Override
    public void deleteValue(Setting setting) {
        Preconditions.checkNotNull(setting);

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

    @Override
    public SettingCallbackManager getCallbackManager() {
        return this.callbackManager;
    }
}
