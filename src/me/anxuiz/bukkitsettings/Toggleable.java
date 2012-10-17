package me.anxuiz.bukkitsettings;

public interface Toggleable {
    Object getNextState(Object previous) throws IllegalArgumentException;
}
