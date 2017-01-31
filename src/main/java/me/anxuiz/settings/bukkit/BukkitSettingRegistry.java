package me.anxuiz.settings.bukkit;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingRegistry;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class BukkitSettingRegistry implements SettingRegistry {
    protected final Map<Setting, ItemStack> settings = Maps.newLinkedHashMap();

    public Setting get(String search, boolean includeAliases) {
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

    public Setting find(String search, boolean includeAliases) throws IllegalArgumentException {
        Setting setting = this.get(search, includeAliases);
        if(setting != null) {
            return setting;
        } else {
            throw new IllegalArgumentException("failed to find setting for '" + search + "'");
        }
    }

    public Set<Setting> getSettings() {
        return ImmutableSet.copyOf(this.settings.keySet());
    }

    public boolean isRegistered(Setting setting) {
        Preconditions.checkNotNull(setting);

        return this.settings.keySet().contains(setting);
    }

    public void register(Setting setting) {
        this.register(setting, null);
    }

    public void register(Setting setting, @Nullable ItemStack icon) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting);
        Preconditions.checkArgument(this.get(setting.getName(), false) == null, "setting already registered to name '%s'", setting.getName());

        this.settings.put(setting, icon);
    }

    public boolean unregister(Setting setting) {
        this.settings.remove(setting);
        return this.settings.containsKey(setting);
    }

    public ItemStack getIcon(Setting setting) {
        return this.settings.get(setting);
    }
}
