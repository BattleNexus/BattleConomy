package net.battlenexus.bukkit.economy.sql;

import java.sql.ResultSet;

public interface SqlInterface {

	public boolean connect(String host, String port, String database, String username, String password);
	public void disconnect();
	public void build(String sql);
	public ResultSet execute();
	public ResultSet query(String sql);
	
}