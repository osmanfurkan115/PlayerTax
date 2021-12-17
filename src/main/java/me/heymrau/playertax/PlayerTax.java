package me.heymrau.playertax;

import lombok.Getter;
import lombok.Setter;
import me.heymrau.playertax.command.TaxCommand;
import me.heymrau.playertax.hooks.PlaceholderAPIHook;
import me.heymrau.playertax.hooks.VaultHook;
import me.heymrau.playertax.manager.TaxManager;
import me.heymrau.playertax.tasks.TaxTask;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class PlayerTax extends JavaPlugin {

    private final int interval = getConfig().getInt("interval");

    private Economy economy;
    private Permission permission;


    private TaxManager taxManager;

    private TaxTask taxTask;

    @Setter
    private long taxMillis;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        final VaultHook vaultHook = new VaultHook();
        economy = vaultHook.getEconomy();
        permission = vaultHook.getPermission();

        taxManager = new TaxManager(this);

        taxTask = new TaxTask(this);
        taxTask.start();

        getCommand("playertax").setExecutor(new TaxCommand(taxManager));

        new PlaceholderAPIHook(this).setupExpansions();
        new Metrics(this,13616);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
