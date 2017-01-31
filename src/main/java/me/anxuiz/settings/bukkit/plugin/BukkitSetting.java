package me.anxuiz.settings.bukkit.plugin;

import me.anxuiz.settings.Type;
import me.anxuiz.settings.base.SimpleSetting;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class BukkitSetting extends SimpleSetting {

    private @Nullable ItemStack icon;

    public BukkitSetting(@Nonnull String name, @Nonnull Set<String> aliases, @Nonnull String summary, @Nullable String description, @Nonnull Type type, @Nullable Object defaultValue, @Nullable ItemStack icon) {
        super(name, aliases, summary, description, type, defaultValue);
        this.icon = icon;
    }


    public @Nullable ItemStack getIcon() {
        return this.icon;
    }

    public void setIcon(@Nullable ItemStack icon) {
        this.icon = icon;
    }
}
