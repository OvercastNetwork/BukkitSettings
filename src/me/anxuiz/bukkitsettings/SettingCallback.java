package me.anxuiz.bukkitsettings;

public interface SettingCallback {
    void notifyChange(Setting setting, Object oldValue, Object newValue);
}
