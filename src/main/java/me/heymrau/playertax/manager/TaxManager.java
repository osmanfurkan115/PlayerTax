package me.heymrau.playertax.manager;

import me.heymrau.playertax.PlayerTax;
import me.heymrau.playertax.api.event.TaxEvent;
import me.heymrau.playertax.utils.Utils;
import me.heymrau.playertax.utils.XSound;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class TaxManager {
    private final PlayerTax plugin;
    private int minAmount;
    private double percentage;
    private XSound xSound;

    private OfflinePlayer depositAccount = null;
    private int taxedPlayers = 0;

    public TaxManager(PlayerTax plugin) {
        this.plugin = plugin;
        if (plugin.getConfig().getBoolean("tax.depositToAccount"))
            depositAccount = Bukkit.getOfflinePlayer(plugin.getConfig().getString("tax.taxAccount"));
    }

    public void taxPlayers() {
        minAmount = plugin.getConfig().getInt("tax.minimum-money");
        percentage = plugin.getConfig().getDouble("tax.rate");
        taxedPlayers = 0;
        xSound = XSound.matchXSound(plugin.getConfig().getString("tax.sound")).orElse(XSound.ENTITY_PLAYER_LEVELUP);

        final TaxEvent event = new TaxEvent();
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        CompletableFuture.runAsync(() -> {
            if (plugin.getConfig().getBoolean("tax-only-online-players"))
                Bukkit.getOnlinePlayers().forEach(this::taxPlayer);
            else Arrays.stream(Bukkit.getOfflinePlayers()).forEach(this::taxPlayer);
            if(plugin.getConfig().getBoolean("tax.log")) {
                Bukkit.getLogger().info("Taxed " + taxedPlayers + " players");
            }

        });


    }

    private void taxPlayer(OfflinePlayer player) {
        if(plugin.getPermission().playerHas(Bukkit.getWorlds().get(0).getName(), player, "tax.admin")) return;
        if (plugin.getEconomy().has(player, minAmount)) {
            final double percentageOfMoney = getPercentageOfMoney(player, percentage);
            plugin.getEconomy().withdrawPlayer(player, percentageOfMoney);
            depositToAccount(percentageOfMoney);
            taxedPlayers++;
            if (!player.isOnline()) return;
            final Player onlinePlayer = Bukkit.getPlayer(player.getUniqueId());
            onlinePlayer.sendMessage(Utils.getMessage("tax.message").replace("%tax%", percentageOfMoney + ""));
            xSound.play(onlinePlayer, 1, 1);
        }
    }

    private void depositToAccount(double amount) {
        if (depositAccount != null && depositAccount.hasPlayedBefore()) {
            plugin.getEconomy().depositPlayer(depositAccount, amount);
        }
    }

    private double getPercentageOfMoney(OfflinePlayer player, double percentage) {
        final double moneyOfPlayer = plugin.getEconomy().getBalance(player);
        return moneyOfPlayer * percentage / 100;
    }

    public int getRemainingMinutes() {
        return (int) TimeUnit.MILLISECONDS.toMinutes(plugin.getTaxMillis() - System.currentTimeMillis());
    }

    public void restartTaxTimestamp() {
        plugin.setTaxMillis(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(plugin.getInterval()));
    }
}
