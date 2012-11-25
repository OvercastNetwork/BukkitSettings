package me.anxuiz.bukkitsettings.type;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

import javax.annotation.Nullable;

import me.anxuiz.bukkitsettings.Toggleable;
import me.anxuiz.bukkitsettings.Type;
import me.anxuiz.bukkitsettings.TypeParseException;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;

@SuppressWarnings("rawtypes")
public class EnumType<T extends Enum> implements Type, Toggleable {
    private final String name;
    private final Class<T> enumClass;
    private final BiMap<T, String> nameMapping;

    public EnumType(String name, Class<T> enumClass) {
        Preconditions.checkNotNull(name, "name may not be null");
        Preconditions.checkNotNull(enumClass, "enum may not be null");
        Preconditions.checkArgument(enumClass.isEnum(), "enum must be enum");
        Preconditions.checkArgument(enumClass.getEnumConstants().length > 0, "enum must have at least one constant");

        this.name = name;
        this.enumClass = enumClass;

        // generate mapping
        BiMap<T, String> tempMapping = HashBiMap.create();
        for(Field field : enumClass.getFields()) {
            if(field.isEnumConstant()) {
                @SuppressWarnings("unchecked")
                T value = (T) Enum.valueOf(this.enumClass, field.getName());

                Name declaredEnumName = field.getAnnotation(Name.class);
                if(declaredEnumName != null) {
                    tempMapping.put(value, declaredEnumName.value());
                } else {
                    tempMapping.put(value, field.getName());
                }
            }
        }
        this.nameMapping = ImmutableBiMap.copyOf(tempMapping);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isInstance(Object obj) {
        return this.enumClass.isInstance(obj);
    }

    @Override
    public String print(Object obj) throws IllegalArgumentException {
        Preconditions.checkNotNull(obj, "object may not be null");

        String name = this.nameMapping.get(obj);
        if(name == null) {
            name = obj.toString();
        }

        return name;
    }

    @Override
    public String serialize(Object obj) throws IllegalArgumentException {
        Preconditions.checkNotNull(obj, "object may not be null");

        return obj.toString();
    }

    @Override
    public Object parse(String raw) throws TypeParseException {
        Preconditions.checkNotNull(raw, "raw may not be null");

        T obj = this.findByName(raw);

        if(obj != null) {
            return obj;
        } else {
            throw new TypeParseException("unknown option '" + raw + "'");
        }
    }

    private @Nullable T findByName(String search) {
        for(Map.Entry<T, String> entry : this.nameMapping.entrySet()) {
            if(entry.getValue().equalsIgnoreCase(search)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public Object getNextState(Object previous) throws IllegalArgumentException {
        Preconditions.checkNotNull(previous, "previous may not be null");

        T[] constants = this.enumClass.getEnumConstants();

        int index = Arrays.asList(this.enumClass.getEnumConstants()).indexOf(previous);
        if(index < 0) {
            throw new IllegalArgumentException("previous is not an enum constant");
        }

        int newIndex = index + 1;
        if(newIndex >= constants.length) {
            newIndex = 0;
        }

        return constants[newIndex];
    }
}
