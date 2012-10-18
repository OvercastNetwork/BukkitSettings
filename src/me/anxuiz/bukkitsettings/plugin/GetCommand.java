package me.anxuiz.bukkitsettings.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GetCommand implements CommandExecutor {
    private final BukkitSettingsPlugin parent;

    public GetCommand(BukkitSettingsPlugin parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO
        return false;
    }
}
