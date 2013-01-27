package net.battlenexus.bukkit.economy.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import net.battlenexus.bukkit.economy.BattleConomy;
import net.battlenexus.bukkit.economy.api.events.PlayerBalanceUpdateEvent;
import net.battlenexus.bukkit.economy.api.events.PlayerBalanceUpdatedEvent;
import net.battlenexus.bukkit.economy.sql.SqlClass;

public class Api {

    public static String prefix = "";
    public static String singular = "";
    public static String plural = "";
    public static FileConfiguration config;
    protected static DecimalFormat format = new DecimalFormat("#,###.00");
    public static SqlClass sql;
    public static HashMap<String, List<String>> economies = new HashMap<String, List<String>>();

    public static boolean accountExists(String username) {
        sql.build("SELECT COUNT(id) AS num FROM " + sql.prefix
                + "players WHERE username=?");
        String[] params = { username.toLowerCase() };

        ResultSet results = sql.executePreparedQuery(params);

        try {
            while (results.next()) {
                if (results.getInt("num") > 0)
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public static void setupEconomies() {
        sql.build("SELECT * FROM " + sql.prefix + "economies");
        ResultSet results = sql.executeQuery();
        try {
            while (results.next()) {
                List<String> worlds = new ArrayList<String>();
                for (String world : results.getString("econ_worlds").split(",")) {
                    worlds.add(world);
                }
                Api.economies.put(results.getString("econ_key"), worlds);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }

    public static boolean balanceExists(String username, String econKey) {
        sql.build("SELECT COUNT(user_id) AS num FROM " + sql.prefix
                + "balances b INNER JOIN " + sql.prefix + "players WHERE username=? AND economy_key=?");
        String[] params = { username.toLowerCase(), econKey };
        ResultSet results = sql.executePreparedQuery(params);

        try {
            while (results.next()) {
                if (results.getInt("num") > 0)
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getEconomyKeyByPlayerWorld(String username) {
        return getEconomyKeyByWorld(Bukkit.getServer().getPlayer(username)
                .getWorld().getName());
    }

    public static String getEconomyKeyByWorld(String world) {
        for (String economy : economies.keySet()) {
            for (String worlds : economies.get(economy)) {
                if (worlds.equalsIgnoreCase(world))
                    return economy;
            }
        }
        return null;
    }

    public static boolean createAccount(String username) {
        sql.build("INSERT IGNORE INTO " + sql.prefix + "players SET username=?");
        String[] params = { username.toLowerCase() };
        if (sql.executePreparedUpdate(params) > 0)
            return true;
        return false;
    }

    public static boolean createBalance(String username, String econKey) {
        return createBalance(username, config.getDouble("economies." + econKey + ".starting-balance"), econKey);
    }
    
    public static boolean createBalance(String username, double money, String econKey) {
        sql.build("INSERT IGNORE INTO " + sql.prefix
                + "balances SELECT ?,id,? FROM " + sql.prefix
                + "players WHERE username=?");
        String[] params = { econKey.toLowerCase(), Double.toString(money), username.toLowerCase() };
        if (sql.executePreparedUpdate(params) > 0)
            return true;
        return false;        
    }

    public static double getBalance(String username) {
        return getBalance(username, getEconomyKeyByPlayerWorld(username));
    }

    public static double getBalance(String username, String econKey) {
        if (econKey == null)
            return 0;
        double balance = 0;
        sql.build("SELECT * FROM " + sql.prefix + "balances b INNER JOIN "
                + sql.prefix + "players p ON p.id=b.user_id WHERE p.username='"
                + username.toLowerCase() + "' AND b.economy_key='" + econKey
                + "'");
        ResultSet results = sql.executeQuery();
        try {
            while (results.next()) {
                balance = results.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public static boolean hasEnough(String username, double amount) {
        return hasEnough(username, amount, getEconomyKeyByPlayerWorld(username));
    }

    public static boolean hasEnough(String username, double amount,
            String econKey) {
        if (econKey == null)
            return false;
        double balance = getBalance(username, econKey);

        if (balance - amount > 0.0)
            return true;
        return false;
    }

    public static boolean addMoney(String username, double amount) {
        return addMoney(username, amount, getEconomyKeyByPlayerWorld(username));
    }

    public static boolean addMoney(String username, double amount,
            String econKey) {
        return setMoney(username, getBalance(username) + amount, econKey);
    }

    public static boolean takeMoney(String username, double amount) {
        return takeMoney(username, amount, getEconomyKeyByPlayerWorld(username));
    }

    public static boolean takeMoney(String username, double amount,
            String econKey) {
        return setMoney(username, getBalance(username) - amount, econKey);
    }

    public static boolean setMoney(String username, double amount) {
        return setMoney(username, amount, getEconomyKeyByPlayerWorld(username));
    }

    public static boolean setMoney(String username, double amount,
            String econKey) {
        if (econKey == null)
            return false;
        PlayerBalanceUpdateEvent event = new PlayerBalanceUpdateEvent(BattleConomy.INSTANCE, username);
        BattleConomy.INSTANCE.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled())
            return false;
        sql.build("UPDATE "
                + sql.prefix
                + "balances b INNER JOIN "
                + sql.prefix
                + "players p ON p.id=b.user_id SET b.balance=? WHERE p.username=? AND b.economy_key=?");
        String[] params = { Double.toString(amount), username.toLowerCase(),
                econKey };
        if (sql.executePreparedUpdate(params) > 0) {
            PlayerBalanceUpdatedEvent event1 = new PlayerBalanceUpdatedEvent(BattleConomy.INSTANCE, username);
            BattleConomy.INSTANCE.getServer().getPluginManager().callEvent(event1);
            return true;
        }
        return false;
    }
    
    public static LinkedHashMap<String, Double> topPlayers(String economyKey) {
        sql.build("SELECT b.balance, p.username FROM " + sql.prefix
                + "players p INNER JOIN " + sql.prefix + "balances b ON p.id=b.user_id WHERE economy_key=? ORDER BY b.balance DESC LIMIT 0,5");
        String[] params = { economyKey };

        ResultSet results = sql.executePreparedQuery(params);
        LinkedHashMap<String, Double> players = new LinkedHashMap<String, Double>();
        try {
            while (results.next()) {
                players.put(results.getString("username"), results.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return players;
    }

    public static String formatMoney(Double amount) {
        if (amount > 1.0) {
            return prefix + format.format(amount);
        } else {
            return prefix + "0"+format.format(amount);
        }
    }
}
