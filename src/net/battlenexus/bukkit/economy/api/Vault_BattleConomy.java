package net.battlenexus.bukkit.economy.api;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.battlenexus.bukkit.economy.BattleConomy;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;


public class Vault_BattleConomy implements Economy {
    private static final Logger log = Logger.getLogger("Minecraft");

    private final String name = "BattleConomy";
    private Plugin plugin = null;
    protected BattleConomy economy = null;

    public Vault_BattleConomy(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(new EconomyServerListener(this), plugin);

        if (economy == null) {
            Plugin ec = plugin.getServer().getPluginManager().getPlugin("BattleConomy");
            if (ec != null && ec.isEnabled()) {
                economy = (BattleConomy) ec;
                log.info(String.format("[%s] Vault Support enabled.", plugin.getDescription().getName(), name));
            }
        }
    }

    public class EconomyServerListener implements Listener {
        Vault_BattleConomy economy = null;

        public EconomyServerListener(Vault_BattleConomy economy) {
            this.economy = economy;
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginEnable(PluginEnableEvent event) {
            if (economy.economy == null) {
                Plugin ec = plugin.getServer().getPluginManager().getPlugin("BattleConomy");
                if (ec != null && ec.isEnabled()) {
                    economy.economy = (BattleConomy) ec;
                    log.info(String.format("[%s] Vault Support enabled.", plugin.getDescription().getName(), economy.name));
                }
            }
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginDisable(PluginDisableEvent event) {
            if (economy.economy != null) {
                if (event.getPlugin().getDescription().getName().equals("iConomy")) {
                    economy.economy = null;
                    log.info(String.format("[%s][Economy] %s unhooked.", plugin.getDescription().getName(), economy.name));
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        if (economy == null) {
            return false;
        } else {
            return economy.isEnabled();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String format(double amount) {
        return Api.formatMoney(amount);
    }

    @Override
    public String currencyNameSingular() {
        return Api.singular;
    }

    @Override
    public String currencyNamePlural() {
        return Api.plural;
    }

    @Override
    public double getBalance(String playerName) {
        if (Api.accountExists(playerName)) {
            return Api.getBalance(playerName);
        } else {
            return 0;
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot withdraw negative funds");
        }
        
        if(!Api.accountExists(playerName)) {
        	return new EconomyResponse(0, 0, ResponseType.FAILURE, "Account doesn't exist");
        }

        if (Api.hasEnough(playerName, amount)) {
            Api.takeMoney(playerName, amount);
            return new EconomyResponse(amount, Api.getBalance(playerName), ResponseType.SUCCESS, null);
        } else {
            return new EconomyResponse(0, Api.getBalance(playerName), ResponseType.FAILURE, "Insufficient funds");
        }
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot desposit negative funds");
        }

        Api.addMoney(playerName, amount);
        return new EconomyResponse(amount, Api.getBalance(playerName), ResponseType.SUCCESS, null);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "BattleConomy does not support bank accounts!");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "BattleConomy does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "BattleConomy does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "BattleConomy does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "BattleConomy does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "BattleConomy does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "BattleConomy does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0.0D, 0.0D, ResponseType.NOT_IMPLEMENTED, "BattleConomy does not support bank accounts!");
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<String>();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public boolean hasAccount(String playerName) {
        return Api.accountExists(playerName);
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        if (hasAccount(playerName)) {
            return false;
        }
        return Api.createAccount(playerName);
    }

	@Override
	public int fractionalDigits() {
		return -1;
	}
}
