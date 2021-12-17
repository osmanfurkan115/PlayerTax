package me.heymrau.playertax.utils;

import me.heymrau.playertax.PlayerTax;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Utils {
    private static final PlayerTax plugin = JavaPlugin.getPlugin(PlayerTax.class);

    public static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(path, "&cThere is a problem with the message"));
    }
}
