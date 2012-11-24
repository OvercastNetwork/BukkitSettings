package me.anxuiz.bukkitsettings.impl;

import java.util.List;

import me.anxuiz.bukkitsettings.Setting;
import me.anxuiz.bukkitsettings.SettingCallback;
import me.anxuiz.bukkitsettings.SettingCallbackManager;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;

public class SimpleSettingCallbackManager implements SettingCallbackManager {
    protected final SetMultimap<Setting, SettingCallback> callbacks = Multimaps.synchronizedSetMultimap(LinkedHashMultimap.<Setting, SettingCallback>create());

    @Override
    public List<SettingCallback> getCallbacks(Setting setting) {
        Preconditions.checkNotNull(setting, "setting may not be null");

        return ImmutableList.copyOf(this.callbacks.get(setting));
    }

    @Override
    public int getNumCallbacks(Setting setting) {
        Preconditions.checkNotNull(setting, "setting may not be null");

        return this.callbacks.get(setting).size();
    }

    @Override
    public boolean hasCallbacks(Setting setting) {
        Preconditions.checkNotNull(setting, "setting may not be null");

        return this.callbacks.containsKey(setting);
    }

    @Override
    public boolean addCallback(Setting setting, SettingCallback callback) {
        Preconditions.checkNotNull(setting, "setting may not be null");
        Preconditions.checkNotNull(callback, "callback may not be null");

        return this.callbacks.put(setting, callback);
    }

    @Override
    public int clearCallbacks(Setting setting) {
        Preconditions.checkNotNull(setting, "setting may not be null");

        return this.callbacks.removeAll(setting).size();
    }

    @Override
    public boolean removeCallback(Setting setting, SettingCallback callback) {
        Preconditions.checkNotNull(setting, "setting may not be null");
        Preconditions.checkNotNull(callback, "callback may not be null");

        return this.callbacks.remove(setting, callback);
    }

    public void notify(Setting setting, Object oldValue, Object newValue) {
        Preconditions.checkNotNull(setting, "setting may not be null");
        Preconditions.checkNotNull(oldValue, "old value may not be null");
        Preconditions.checkNotNull(newValue, "new value may not be null");

        // don't notify if the values are the same
        if(oldValue.equals(newValue)) {
            return;
        }

        List<SettingCallback> callbacks = this.getCallbacks(setting); // get a copy of the callbacks
        for(SettingCallback callback : callbacks) {
            try {
                callback.notifyChange(setting, oldValue, newValue);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
