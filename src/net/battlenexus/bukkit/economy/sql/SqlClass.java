package net.battlenexus.bukkit.economy.sql;

import java.sql.Connection;
import java.sql.ResultSet;

public abstract class SqlClass implements SqlInterface{
	
	protected Connection conn = null; 
		
	public String prefix = "";
	
	public String current_query = "";
	
	public ResultSet results;
	
	public void build(String sql)
	{
		current_query += sql;
	}
	
	public ResultSet execute()
	{
		ResultSet query = query(current_query);
		current_query = "";
		return query;
	}
	
	public boolean isConnected()
	{
		return conn.equals(null) ? false : true;
	}
	
}
