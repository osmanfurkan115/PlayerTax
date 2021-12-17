package me.heymrau.playertax.tasks;

import me.heymrau.playertax.PlayerTax;
import org.bukkit.scheduler.BukkitRunnable;

public class TaxTask extends BukkitRunnable {

    private final PlayerTax plugin;

    public TaxTask(PlayerTax plugin) {
        this.plugin = plugin;
    }

    public void start() {
        plugin.getTaxManager().restartTaxTimestamp();
        this.runTaskTimer(plugin, 20L * 60 * plugin.getInterval(), 20L * 60 * plugin.getInterval());
    }

    @Override
    public void run() {
        plugin.getTaxManager().taxPlayers();
        plugin.getTaxManager().restartTaxTimestamp();
    }
}
