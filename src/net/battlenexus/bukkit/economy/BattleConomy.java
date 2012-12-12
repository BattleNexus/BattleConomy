package net.battlenexus.bukkit.economy;

import net.battlenexus.bukkit.economy.sql.MySQL;

import org.bukkit.plugin.java.JavaPlugin;

public class BattleConomy extends JavaPlugin {	
	MySQL sql = new MySQL();
	
	@Override
	public void onEnable()
	{	
		if(sql.connect("host","3306","database","username","password"))
			getLogger().info("Connected to mysql!");
		else{
			getLogger().info("Couldn't connect to mysql");
			getLogger().info("Plugin not loaded");
            getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		getCommand("be").setExecutor(new BattleCommands(sql));	
		
		getLogger().info("BattleConomy loaded successfully");
	}
	
	@Override
	public void onDisable()
	{
		sql.disconnect();
		if(!sql.isConnected())
			getLogger().info("Disconnected from mysql");
		else
			getLogger().info("There were errors trying to disconnect from mysql");
		
		getLogger().info("BattleConomy disabled successfully");
	}
}