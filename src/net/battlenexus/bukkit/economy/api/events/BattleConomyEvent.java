package net.battlenexus.bukkit.economy.api.events;

import net.battlenexus.bukkit.economy.BattleConomy;
import net.battlenexus.bukkit.economy.api.Api;

import org.bukkit.event.Event;

public abstract class BattleConomyEvent extends Event {
    
    private BattleConomy plugin;
    
    private String username;
    
    public BattleConomyEvent(BattleConomy plugin, String username) {
        this.plugin = plugin;
    }
    
    public BattleConomy getPlugin() {
        return plugin;
    }
    
    public boolean setMoney(double amount) {
        return Api.setMoney(username, amount);
    }
    
    public boolean takeMoney(double amount) {
        return Api.takeMoney(username, amount);
    }
    
    public boolean addMoney(double amount) {
        return Api.addMoney(username, amount);
    }
    
    public double getBalance() {
        return Api.getBalance(username);
    }
    
    public boolean hasEnough(double amount) {
        return Api.hasEnough(username, amount);
    }

}
