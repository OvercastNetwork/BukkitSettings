package me.anxuiz.bukkitsettings;

import java.text.ParseException;

public interface Type {
    String getName();
    boolean isInstance(Object obj);
    Object parse(String raw) throws ParseException;
    String print(Object obj) throws IllegalArgumentException;
}
