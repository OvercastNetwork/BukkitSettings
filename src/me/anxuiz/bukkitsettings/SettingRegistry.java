package me.anxuiz.bukkitsettings;

import java.util.Set;

import javax.annotation.Nullable;

public interface SettingRegistry {
    @Nullable Setting get(String search, boolean includeAliases);
    Setting find(String search, boolean includeAliases) throws IllegalArgumentException;

    Set<Setting> getSettings();

    boolean isRegistered(Setting setting);
    void register(Setting setting) throws IllegalArgumentException;
    boolean unregister(Setting setting);
}
