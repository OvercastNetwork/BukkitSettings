package me.anxuiz.bukkitsettings;

import java.util.Set;

public interface Setting {
    String getName();

    Set<String> getAliases();

    SettingScope getScope();

    String getSummary();

    String getDescription();

    Type getType();

    Object getDefaultValue();

    void setDefaultValue(Object newDefault) throws IllegalArgumentException;
}
