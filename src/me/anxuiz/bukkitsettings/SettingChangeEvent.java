package me.anxuiz.bukkitsettings;

import javax.annotation.Nullable;

import me.anxuiz.bukkitsettings.util.TypeUtil;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class SettingChangeEvent extends Event implements Cancellable {
    private static HandlerList handlers = new HandlerList();

    protected final Setting setting;
    protected final @Nullable Object oldValue;
    protected final @Nullable Object newValue;

    protected boolean cancelled = false;

    public SettingChangeEvent(Setting setting, @Nullable Object oldValue, @Nullable Object newValue) {
        Preconditions.checkNotNull(setting);
        Preconditions.checkArgument(Objects.equal(oldValue, newValue), "old value may not equal new value");
        if(oldValue != null) Preconditions.checkArgument(setting.getType().isInstance(oldValue));
        if(newValue != null) Preconditions.checkArgument(setting.getType().isInstance(newValue));

        this.setting = setting;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Setting getSetting() {
        return this.setting;
    }

    public @Nullable <T> T getOldValue(Class<T> typeClass) throws IllegalArgumentException {
        return TypeUtil.getValue(this.oldValue, typeClass);
    }

    public @Nullable <T> T getNewValue(Class<T> typeClass) throws IllegalArgumentException {
        return TypeUtil.getValue(this.newValue, typeClass);
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
