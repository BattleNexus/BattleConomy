package net.battlenexus.bukkit.economy.sql;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL extends SqlClass {
	
	@Override
	public boolean connect(String host, String port, String database, String username, String password) {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		    conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, username, password);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			//If it had problems, it was already disconnected
		}
		conn = null;
	}

	@Override
	public ResultSet query(String sql) {
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
}
