package me.anxuiz.bukkitsettings.impl;

import java.util.Set;

import javax.annotation.Nullable;

import me.anxuiz.bukkitsettings.Setting;
import me.anxuiz.bukkitsettings.SettingScope;
import me.anxuiz.bukkitsettings.Type;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSortedSet;

public class SimpleSetting implements Setting {
    protected final String name;
    protected final Set<String> aliases;
    protected final SettingScope scope;
    protected final String summary;
    protected final String description;

    protected final Type type;
    protected Object defaultValue;

    public SimpleSetting(String name,
            Set<String> aliases,
            SettingScope scope,
            String summary,
            @Nullable String description,
            Type type,
            Object defaultValue) {
        this.name = name;
        this.aliases = ImmutableSortedSet.copyOf(aliases);
        this.scope = scope;
        this.summary = summary;
        this.description = description;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Set<String> getAliases() {
        return this.aliases;
    }

    @Override
    public SettingScope getScope() {
        return this.scope;
    }

    @Override
    public String getSummary() {
        return this.summary;
    }

    @Override
    public String getDescription() {
        if(this.description != null) {
            return this.description;
        } else {
            return this.summary;
        }
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public void setDefaultValue(Object newDefault) throws IllegalArgumentException {
        Preconditions.checkNotNull(newDefault);
        Preconditions.checkArgument(this.type.isInstance(newDefault));

        synchronized(this) {
            this.defaultValue = newDefault;
        }
    }

    @Override
    public String toString() {
        return "SimpleSetting{name='" + this.name + "',type='" + this.type.getName() + "'}";
    }
}
