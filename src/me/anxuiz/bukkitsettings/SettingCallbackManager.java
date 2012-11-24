package me.anxuiz.bukkitsettings;

import java.util.List;

public interface SettingCallbackManager {
    List<SettingCallback> getCallbacks(Setting setting);

    int getNumCallbacks(Setting setting);

    boolean hasCallbacks(Setting setting);

    boolean addCallback(Setting setting, SettingCallback callback);

    int clearCallbacks(Setting setting);

    boolean removeCallback(Setting setting, SettingCallback callback);
}
