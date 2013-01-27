package net.battlenexus.bukkit.economy.api.events;

import net.battlenexus.bukkit.economy.BattleConomy;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerBalanceUpdateEvent extends BattleConomyEvent implements Cancellable {

    private boolean cancel;
    
    private static HandlerList events = new HandlerList();
    
    public PlayerBalanceUpdateEvent(BattleConomy plugin, String username) {
        super(plugin, username);
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return events;
    }
    
    public static HandlerList getHandlerList() {
        return events;
    }

}
