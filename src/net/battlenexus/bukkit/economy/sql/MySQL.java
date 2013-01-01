package net.battlenexus.bukkit.economy.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Mysql extends SqlClass {

    @Override
    public boolean connect(String host, String port, String database, String username, String password) {
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("com.mysql.jdbc.Driver");
            cpds.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
            cpds.setUser(username);
            cpds.setPassword(password);
            conn = cpds.getConnection();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        /*DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        conn = DriverManager.getConnection("jdbc:mysql://" + host + ":"
                + port + "/" + database, username, password);*/
        return true;
    }

    @Override
    public void disconnect() {
        cpds.close();
    }

    @Override
    public ResultSet executeRawPreparedQuery(String sql, String[] parameters) {
        PreparedStatement preparedStatement;
        ResultSet results = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            int i = 1;
            for (String parameter : parameters) {
                preparedStatement.setString(i, parameter);
                i++;
            }
            results = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public int executeRawPreparedUpdate(String sql, String[] parameters) {
        PreparedStatement preparedStatement;
        int results = 0;
        try {
            preparedStatement = conn.prepareStatement(sql);
            int i = 1;
            for (String parameter : parameters) {
                preparedStatement.setString(i, parameter);
                i++;
            }
            results = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public ResultSet executeRawQuery(String sql) {
        PreparedStatement preparedStatement;
        ResultSet results = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            results = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public int executeRawUpdate(String sql) {
        PreparedStatement preparedStatement;
        int results = 0;
        try {
            preparedStatement = conn.prepareStatement(sql);
            results = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
