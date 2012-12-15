package net.battlenexus.bukkit.economy.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.battlenexus.bukkit.economy.BattleConomy;

import lib.PatPeter.SQLibrary.SQLite;

public class Sqlite extends SqlClass {

    private SQLite sql;

    @Override
    public boolean connect(String host, String port, String database,
            String username, String password) {
        sql = new SQLite(BattleConomy.INSTANCE.getLogger(),
                BattleConomy.INSTANCE.getName(), "battle",
                BattleConomy.INSTANCE.getDataFolder().getAbsolutePath());
        try {
            sql.open();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void disconnect() {
        sql.close();
    }

    @Override
    public ResultSet executeRawQuery(String command) {
        return sql.query(command);
    }

    @Override
    public int executeRawUpdate(String command) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = sql.prepare(command);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ResultSet executeRawPreparedQuery(String command, String[] parameters) {
        PreparedStatement preparedStatement;
        ResultSet results = null;
        try {
            preparedStatement = sql.prepare(command);
            int i = 1;
            for (String parameter : parameters) {
                preparedStatement.setString(i, parameter);
                i++;
            }
            results = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public int executeRawPreparedUpdate(String command, String[] parameters) {
        PreparedStatement preparedStatement;
        int results = 0;
        try {
            preparedStatement = sql.prepare(command);
            results = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

}
