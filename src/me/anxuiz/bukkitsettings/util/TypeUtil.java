package me.anxuiz.bukkitsettings.util;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

public class TypeUtil {
    @SuppressWarnings("unchecked")
    public static @Nullable <T> T getValue(@Nullable Object value, Class<T> typeClass) throws IllegalArgumentException {
        Preconditions.checkNotNull(typeClass);
        if(value != null) {
            Preconditions.checkArgument(value.getClass().isAssignableFrom(typeClass), "value may not be cast to %s", typeClass.getName());
            return (T) value;
        } else {
            return null;
        }
    }

    private TypeUtil() {
    }
}
