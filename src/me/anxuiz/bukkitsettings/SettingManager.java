package me.anxuiz.bukkitsettings;

import javax.annotation.Nullable;


public interface SettingManager {
    @Nullable Setting getSetting(String search, boolean includeAliases);

    Setting findSetting(String search, boolean includeAliases) throws IllegalArgumentException;

    void register(Setting setting) throws IllegalArgumentException;

    boolean unregister(Setting setting);


    @Nullable Object getValue(String search) throws IllegalArgumentException;

    <T> T getValue(String search, Class<T> typeClass) throws IllegalArgumentException;

    @Nullable Object getValue(Setting setting) throws IllegalArgumentException;

    <T> T getValue(Setting setting, Class<T> typeClass) throws IllegalArgumentException;

    boolean setValue(String search, Object value) throws IllegalArgumentException;

    boolean setValue(Setting setting, Object value) throws IllegalArgumentException;
}
