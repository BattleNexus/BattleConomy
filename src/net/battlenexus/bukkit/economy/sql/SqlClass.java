package net.battlenexus.bukkit.economy.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SqlClass implements SqlInterface {

	protected Connection conn = null;

	public String prefix = "";

	public String current_query = "";

	public ResultSet results;
	

	public void build(String sql) {
		current_query += sql;
	}

	public ResultSet executeQuery() {
		ResultSet query = executeRawQuery(current_query);
		current_query = "";
		return query;
	}
	
	public int executeUpdate() {
		int query = executeRawUpdate(current_query);
		current_query = "";
		return query;
	}

	public boolean isConnected() {
		try {
			return conn.isValid(10) ? true : false;
		} catch (SQLException e) {
			return false;
		}
	}

}