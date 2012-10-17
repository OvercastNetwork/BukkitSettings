package me.anxuiz.bukkitsettings;

import java.util.Collection;
import java.util.Set;

import me.anxuiz.bukkitsettings.impl.SimpleSetting;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

public class SettingBuilder {
    protected String name = null;
    protected final Set<String> aliases = Sets.newLinkedHashSet();
    protected SettingScope scope = SettingScope.PLAYER;
    protected String summary = null;
    protected String description = null;
    protected Type type = null;
    protected Object defaultValue = null;

    public SettingBuilder() {
    }

    public SettingBuilder name(String name) {
        Preconditions.checkNotNull(name);
        this.name = name;
        return this;
    }

    public SettingBuilder alias(String alias) {
        Preconditions.checkNotNull(alias);
        this.aliases.add(alias);
        return this;
    }

    public SettingBuilder aliases(Collection<String> aliases) {
        Preconditions.checkNotNull(aliases);
        this.aliases.clear();
        this.aliases.addAll(aliases);
        return this;
    }

    public SettingBuilder scope(SettingScope scope) {
        Preconditions.checkNotNull(scope);
        this.scope = scope;
        return this;
    }

    public SettingBuilder summary(String summary) {
        Preconditions.checkNotNull(summary);
        this.summary = summary;
        return this;
    }

    public SettingBuilder description(String description) {
        Preconditions.checkNotNull(description);
        this.description = description;
        return this;
    }

    public SettingBuilder type(Type type) {
        Preconditions.checkNotNull(type);
        // clear the default value if it does not work with the new type
        if(this.defaultValue != null && !type.isInstance(this.defaultValue)) {
            this.defaultValue = null;
        }
        this.type = type;
        return this;
    }

    public SettingBuilder defaultValue(Object defaultValue) {
        Preconditions.checkNotNull(defaultValue);
        Preconditions.checkArgument(this.type != null && this.type.isInstance(defaultValue), "default value must be an instance of the type specified");
        this.defaultValue = defaultValue;
        return this;
    }

    public Setting get() throws IllegalStateException {
        Preconditions.checkState(this.name != null, "setting must have name");
        Preconditions.checkState(this.scope != null, "setting must have scope");
        Preconditions.checkState(this.summary != null, "setting must have summary");
        Preconditions.checkState(this.type != null, "setting must have type");
        Preconditions.checkState(this.defaultValue != null, "setting must have a default value");

        return new SimpleSetting(this.name, this.aliases, this.scope, this.summary, this.description, this.type, this.defaultValue);
    }
}
