package me.anxuiz.settings.bukkit.plugin;

import com.google.common.collect.Lists;
import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.PlayerSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collection;
import java.util.List;

public class GenericTabCompleter implements TabCompleter {
    PermissionsChecker checker;
    public GenericTabCompleter(PermissionsChecker checker) {
        this.checker = checker;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        Collection<Setting> settings = PlayerSettings.getRegistry().getSettings();

        List<String> settingNames = Lists.newArrayList();
        for (Setting setting : settings) {
            if (setting.getName().toLowerCase().startsWith(strings[strings.length - 1].toLowerCase()) && this.checker.check(commandSender, setting)) {
                settingNames.add(setting.getName());
            }
        }

        return settingNames;
    }
}
