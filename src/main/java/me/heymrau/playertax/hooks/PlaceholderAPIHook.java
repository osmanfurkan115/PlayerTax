package me.heymrau.playertax.hooks;

import me.heymrau.playertax.PlayerTax;
import me.heymrau.playertax.placeholder.TaxExpansion;

public class PlaceholderAPIHook extends Hook {
    private final PlayerTax plugin;

    public PlaceholderAPIHook(PlayerTax plugin) {
        super("PlaceholderAPI", false);
        this.plugin = plugin;
    }

    public void setupExpansions() {
        if(!isEnabled()) return;
        new TaxExpansion(plugin).register();
    }
}
