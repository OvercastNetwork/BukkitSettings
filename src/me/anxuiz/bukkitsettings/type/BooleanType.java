package me.anxuiz.bukkitsettings.type;


import me.anxuiz.bukkitsettings.Toggleable;
import me.anxuiz.bukkitsettings.Type;
import me.anxuiz.bukkitsettings.TypeParseException;
import me.anxuiz.bukkitsettings.util.TypeUtil;

public class BooleanType implements Type, Toggleable {
    @Override
    public String getName() {
        return "bool";
    }

    @Override
    public boolean isInstance(Object obj) {
        return obj instanceof Boolean;
    }

    @Override
    public String print(Object obj) throws IllegalArgumentException {
        Boolean value = TypeUtil.getValue(obj, Boolean.class);
        return value ? "on" : "off";
    }

    @Override
    public String serialize(Object obj) throws IllegalArgumentException {
        Boolean value = TypeUtil.getValue(obj, Boolean.class);
        return value ? "true" : "false";
    }

    @Override
    public Object parse(String raw) throws TypeParseException {
        raw = raw.toLowerCase().trim();
        if(raw.equals("on") || raw.equals("true") || raw.equals("yes")) {
            return new Boolean(true);
        } else if (raw.equals("off") || raw.equals("false") || raw.equals("no")) {
            return new Boolean(false);
        } else {
            throw new TypeParseException();
        }
    }

    @Override
    public Object getNextState(Object previous) throws IllegalArgumentException {
        boolean value = TypeUtil.getValue(previous, Boolean.class);
        return new Boolean(!value);
    }
}
