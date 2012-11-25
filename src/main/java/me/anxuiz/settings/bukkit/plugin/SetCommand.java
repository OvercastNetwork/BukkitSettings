package me.anxuiz.settings.bukkit.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetCommand implements CommandExecutor {
    private final BukkitSettingsPlugin parent;

    public SetCommand(BukkitSettingsPlugin parent) {
        this.parent = parent;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO
        return false;
    }
}
