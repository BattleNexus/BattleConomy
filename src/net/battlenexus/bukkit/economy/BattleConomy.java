package net.battlenexus.bukkit.economy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.battlenexus.bukkit.economy.sql.MySQL;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class BattleConomy extends JavaPlugin {
	FileConfiguration config;	
	MySQL sql = new MySQL();
	boolean connected =  false;

	@Override
	public void onEnable() {		
		File config = new File(this.getDataFolder(), "config.yml");
		if (!config.exists()) {
		    saveDefaultConfig();
		    getLogger().info("Configuration file created, please edit it before attempting to load this plugin");
		    getServer().getPluginManager().disablePlugin(this);
		}		
		getConfig();
		if (sql.connect(getConfig().getString("mysql.host"), getConfig().getString("mysql.port"), getConfig().getString("mysql.database"), getConfig().getString("mysql.username"), getConfig().getString("mysql.password"))){
			connected = true;
			Api.sql = sql;
			getLogger().info("Connected to mysql!");
		}else{
			getLogger().info("Couldn't connect to mysql");
			getLogger().info("Plugin not loaded");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Api.config = getConfig();
		Api.prefix = getConfig().getString("currency.prefix");
		Api.singular = getConfig().getString("currency.singular");
		Api.plural = getConfig().getString("currency.plural");
		
		for(String econKey : getConfig().getConfigurationSection("economies").getKeys(false)) {
			List<String> worlds = new ArrayList<String>();
			for(String world : getConfig().getStringList("economies."+econKey+".worlds")) {
				worlds.add(world);
			}
			Api.economies.put(econKey, worlds);
		}
		
		setupVault();
		new BattleConomyListen(this);
		getCommand("be").setExecutor(new BattleCommands(sql));

		getLogger().info("BattleConomy loaded successfully");
	}

	private void setupVault(){
		Plugin vault = getServer().getPluginManager().getPlugin("Vault");
		
		if (vault == null){
			return;
		}
		
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
		
		if (economyProvider != null){
			getServer().getServicesManager().unregister(economyProvider.getProvider());
		}
		
		getServer().getServicesManager().register(Economy.class, new Economy_BattleConomy(this), this, ServicePriority.Highest);
	}

	@Override
	public void onDisable() {
		if(connected){
			sql.disconnect();
			if (!sql.isConnected())
				getLogger().info("Disconnected from mysql");
			else
				getLogger().info(
						"There were errors trying to disconnect from mysql");
		}
		Api.economies.clear();
		getLogger().info("BattleConomy disabled successfully");
	}
}