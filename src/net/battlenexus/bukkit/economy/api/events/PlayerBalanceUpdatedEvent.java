package net.battlenexus.bukkit.economy.api.events;

import org.bukkit.event.HandlerList;

import net.battlenexus.bukkit.economy.BattleConomy;

public class PlayerBalanceUpdatedEvent extends BattleConomyEvent {

    private static HandlerList events = new HandlerList();
    
    public PlayerBalanceUpdatedEvent(BattleConomy plugin, String username) {
        super(plugin, username);
    }

    @Override
    public HandlerList getHandlers() {
       return events;
    }
    
    public static HandlerList getHandlerList() {
        return events;
    }

}
