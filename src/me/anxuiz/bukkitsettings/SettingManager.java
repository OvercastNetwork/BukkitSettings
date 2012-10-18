package me.anxuiz.bukkitsettings;

import javax.annotation.Nullable;


public interface SettingManager {
    boolean hasValue(Setting setting);

    @Nullable Object getValue(Setting setting);

    Object getValue(Setting setting, Object defaultValue) throws IllegalArgumentException;

    <T> T getValue(Setting setting, Class<T> typeClass) throws IllegalArgumentException;

    <T> T getValue(Setting setting, Class<T> typeClass, T defaultValue) throws IllegalArgumentException;

    boolean setValue(Setting setting, Object value);

    boolean deleteValue(Setting setting);
}
