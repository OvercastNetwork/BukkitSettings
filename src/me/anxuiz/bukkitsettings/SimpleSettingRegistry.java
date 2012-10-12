package me.anxuiz.bukkitsettings;

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class SimpleSettingRegistry implements SettingRegistry {
    protected final Set<Setting> settings = Sets.newLinkedHashSet();

    @Override
    public Setting get(String search, boolean includeAliases) {
        Preconditions.checkNotNull(search);

        for(Setting setting : this.settings) {
            if(setting.getName().equalsIgnoreCase(search)) {
                return setting;
            }
        }

        // look through aliases afterward so the name match can be found first
        if(includeAliases) {
            for(Setting setting : this.settings) {
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
    public Setting find(String search, boolean includeAliases) throws IllegalArgumentException {
        Setting setting = this.get(search, includeAliases);
        if(setting != null) {
            return setting;
        } else {
            throw new IllegalArgumentException("failed to find setting for '" + search + "'");
        }
    }

    @Override
    public Set<Setting> getSettings() {
        return ImmutableSet.copyOf(this.settings);
    }

    @Override
    public boolean isRegistered(Setting setting) {
        Preconditions.checkNotNull(setting);
        return this.settings.contains(setting);
    }

    @Override
    public void register(Setting setting) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting);
        Preconditions.checkArgument(this.get(setting.getName(), false) == null, "setting already registered to name '%s'", setting.getName());
        this.settings.add(setting);
    }

    @Override
    public boolean unregister(Setting setting) {
        // TODO Auto-generated method stub
        return false;
    }

}
