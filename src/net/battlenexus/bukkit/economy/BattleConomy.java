package net.battlenexus.bukkit.economy;

import org.bukkit.plugin.java.JavaPlugin;

public class BattleConomy extends JavaPlugin {	
	@Override
	public void onEnable()
	{
		getCommand("be").setExecutor(new BattleCommands(this));	
		
		getLogger().info("BattleConomy loaded successfully");
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("BattleConomy disabled successfully");
	}
}
