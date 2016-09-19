package me.anxuiz.settings.bukkit.plugin;

import java.util.UUID;
import javax.annotation.Nullable;

import com.google.common.util.concurrent.FutureCallback;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public abstract class CommandFutureCallback<T> implements FutureCallback<T> {

    private final Server server;
    private final @Nullable UUID uuid;

    public CommandFutureCallback(CommandSender sender) {
        this.server = sender.getServer();
        this.uuid = sender instanceof OfflinePlayer ? ((OfflinePlayer) sender).getUniqueId()
                                                  : null;
    }

    protected @Nullable CommandSender getSender() {
        return uuid == null ? server.getConsoleSender()
                            : server.getPlayer(uuid);
    }

    @Override
    public void onFailure(Throwable t) {
        final CommandSender sender = getSender();
        if(sender != null) {
            sender.sendMessage(ChatColor.RED + "There was an internal error while running your command");
        }
    }
}
