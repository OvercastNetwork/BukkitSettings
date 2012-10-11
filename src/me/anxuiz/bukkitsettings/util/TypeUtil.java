package me.anxuiz.bukkitsettings.util;

import com.google.common.base.Preconditions;

public class TypeUtil {
    @SuppressWarnings("unchecked")
    public static <T> T getValue(Object value, Class<T> typeClass) throws IllegalArgumentException {
        Preconditions.checkArgument(value.getClass().isAssignableFrom(typeClass), "value may not be cast to %s" + typeClass.getName());
        return (T) value;
    }

    private TypeUtil() {
    }
}
