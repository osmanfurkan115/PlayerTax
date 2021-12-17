package me.heymrau.playertax.command;

import me.heymrau.playertax.manager.TaxManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TaxCommand implements CommandExecutor {
    private final TaxManager taxManager;

    public TaxCommand(TaxManager taxManager) {
        this.taxManager = taxManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            if(!sender.hasPermission("tax.admin")) {
                sender.sendMessage(ChatColor.RED + "Insufficient permission");
                return true;
            }

            if(args.length == 1 && args[0].equalsIgnoreCase("taxall")) {
                taxManager.taxPlayers();
                sender.sendMessage(ChatColor.GREEN + "You successfully taxed all players");
            } else {
                sender.sendMessage(ChatColor.GREEN + "Usage: /playertax taxall");
            }
        }
        return true;
    }
}
