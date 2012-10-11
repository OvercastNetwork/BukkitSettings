package me.anxuiz.bukkitsettings;

import java.util.Map;

import javax.annotation.Nullable;

import me.anxuiz.bukkitsettings.util.TypeUtil;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class SimpleSettingManager implements SettingManager {
    protected final BukkitSettings plugin;
    protected final Map<Setting, Object> settings = Maps.newHashMap();

    public SimpleSettingManager(BukkitSettings plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable Setting getSetting(String search, boolean includeAliases) {
        Preconditions.checkNotNull(search);

        for(Setting setting : this.settings.keySet()) {
            if(setting.getName().equalsIgnoreCase(search)) {
                return setting;
            }
        }

        // look through aliases afterward so the name match can be found first
        if(includeAliases) {
            for(Setting setting : this.settings.keySet()) {
                for(String alias : setting.getAliases()) {
                    if(alias.equalsIgnoreCase(search)) {
                        return setting;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Setting findSetting(String search, boolean includeAliases) {
        Setting setting = this.getSetting(search, includeAliases);
        if(setting != null) {
            return setting;
        } else {
            throw new IllegalArgumentException("failed to find setting for '" + search + "'");
        }
    }

    @Override
    public void register(Setting setting) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting);
        Preconditions.checkArgument(this.getSetting(setting.getName(), false) == null, "setting is already registered for the name '%s'", setting.getName());
        this.settings.put(setting, setting.getDefaultValue());
    }

    @Override
    public boolean unregister(Setting setting) {
        return this.settings.remove(setting) != null;
    }

    @Override
    @Nullable public Object getValue(String search) throws IllegalArgumentException {
        return this.getValue(this.findSetting(search, true));
    }

    @Override
    public <T> T getValue(String search, Class<T> typeClass) throws IllegalArgumentException {
        return this.getValue(this.findSetting(search, true), typeClass);
    }

    @Override
    @Nullable public Object getValue(Setting setting) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting);
        return this.settings.get(setting);
    }

    @Override
    public <T> T getValue(Setting setting, Class<T> typeClass) throws IllegalArgumentException {
        Object value = this.getValue(setting);
        Preconditions.checkArgument(value != null, "setting '%s' has not been registered", setting.getName());
        return TypeUtil.getValue(value, typeClass);
    }

    @Override
    public boolean setValue(String search, Object value) throws IllegalArgumentException {
        return this.setValue(this.findSetting(search, true), value);
    }

    @Override
    public boolean setValue(Setting setting, Object value) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting);
        Preconditions.checkNotNull(value);
        Preconditions.checkArgument(this.settings.containsKey(setting), "setting '%s' has not been registered", setting.getName());
        Preconditions.checkArgument(setting.getType().isInstance(value), "value (%s) and setting (%s) have incompatible types", value.getClass().getName(), setting.getType().getName());
        // TODO
        return false;
    }
}
