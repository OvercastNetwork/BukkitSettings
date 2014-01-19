package me.anxuiz.settings.bukkit.plugin;

import javax.annotation.Nullable;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.commands.core.GetCommand;
import me.anxuiz.settings.bukkit.commands.core.SetCommand;
import me.anxuiz.settings.bukkit.commands.core.SettingCommand;
import me.anxuiz.settings.bukkit.commands.core.SettingsCommand;
import me.anxuiz.settings.bukkit.commands.core.ToggleCommand;
import me.anxuiz.settings.bukkit.commands.util.GenericTabCompleter;
import me.anxuiz.settings.bukkit.commands.util.Permissions;
import me.anxuiz.settings.bukkit.commands.util.PermissionsChecker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

public final class BukkitSettingsPlugin extends JavaPlugin {

    private static BukkitSettingsPlugin inst = null;
    public static @Nullable BukkitSettingsPlugin get() {
        return inst;
    }

    private CommandsManager<CommandSender> commands;
    
    @Override
    public void onDisable() {
        inst = null;
    }

    @Override
    public void onEnable() {
        inst = this;

        // Assign Tab Completers
        this.getCommand("get").setTabCompleter(new GenericTabCompleter(
                new PermissionsChecker() {
                    public boolean check(CommandSender sender, Setting setting) {
                        return Permissions.hasViewPermission(sender, setting) && Permissions.hasGetPermission(sender, setting);
                    }
                }
        ));
        GenericTabCompleter setTabCompleter = new GenericTabCompleter(
                new PermissionsChecker() {
                    public boolean check(CommandSender sender, Setting setting) {
                        return Permissions.hasViewPermission(sender, setting) && Permissions.hasSetPermission(sender, setting);
                    }
                }
        );
        this.getCommand("set").setTabCompleter(setTabCompleter);
        this.getCommand("toggle").setTabCompleter(setTabCompleter);
        this.getCommand("setting").setTabCompleter(new GenericTabCompleter(
                new PermissionsChecker() {
                    public boolean check(CommandSender sender, Setting setting) {
                        return Permissions.hasViewPermission(sender, setting);
                    }
                }
        ));
    }
    
    private void setupCommands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String perm) {
                return sender.hasPermission(perm) | sender instanceof ConsoleCommandSender;
            }
        };
        
        CommandsManagerRegistration register = new CommandsManagerRegistration(this, this.commands);
        register.register(GetCommand.class);
        register.register(SetCommand.class);
        register.register(SettingCommand.class);
        register.register(SettingsCommand.class);
        register.register(ToggleCommand.class);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }    
}
