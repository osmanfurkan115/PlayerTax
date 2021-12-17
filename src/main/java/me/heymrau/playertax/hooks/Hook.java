package me.heymrau.playertax.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public abstract class Hook {
    private final String pluginName;
    private final boolean required;
    private boolean enabled;

    public Hook(String pluginName, boolean required) {
        this.pluginName = pluginName;
        this.required = required;
        checkHook();
    }

    private void checkHook() {
        final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        enabled = pluginManager.isPluginEnabled(pluginName);
        if(enabled) Bukkit.getLogger().info(pluginName + " plugin initialized.");
        else if(required) {
            pluginManager.disablePlugin(pluginManager.getPlugin("PlayerTax"));
            Bukkit.getLogger().severe(pluginName + " plugin is required!");
        }
    }

    public boolean isEnabled() {
        return enabled;
    }
}
