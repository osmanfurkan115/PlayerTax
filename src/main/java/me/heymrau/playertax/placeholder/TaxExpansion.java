package me.heymrau.playertax.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.heymrau.playertax.PlayerTax;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class TaxExpansion extends PlaceholderExpansion {
    private final PlayerTax plugin;

    public TaxExpansion(PlayerTax plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "playertax";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "HeyMrau";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier) {

        if (identifier.equalsIgnoreCase("minute")) {
            return plugin.getTaxManager().getRemainingMinutes() + 1 + "";
        }
        return null;
    }
}
