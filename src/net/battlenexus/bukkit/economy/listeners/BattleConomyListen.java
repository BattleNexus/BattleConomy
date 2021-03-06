package net.battlenexus.bukkit.economy.listeners;

import net.battlenexus.bukkit.economy.BattleConomy;
import net.battlenexus.bukkit.economy.api.Api;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class BattleConomyListen implements Listener {
    public BattleConomyListen(BattleConomy plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerLogin(PlayerLoginEvent event) {
        Api.createAccount(event.getPlayer().getName());
        String econKey = Api.getEconomyKeyByWorld(event.getPlayer().getWorld()
                .getName());
        if (econKey != null)
            Api.createBalance(event.getPlayer().getName(), econKey);
    }

    @EventHandler
    public void playerWorldChange(PlayerChangedWorldEvent event) {
        String econKey = Api.getEconomyKeyByWorld(event.getPlayer().getWorld()
                .getName());
        if (econKey != null)
            Api.createBalance(event.getPlayer().getName(), econKey);
    }
}
