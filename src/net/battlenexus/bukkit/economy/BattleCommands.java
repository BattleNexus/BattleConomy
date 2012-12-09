package net.battlenexus.bukkit.economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleCommands implements CommandExecutor  {
	
	Player player;
	
	public BattleCommands(BattleConomy battleconomy) {
		//I'm sure this will come in use at sometime.
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		player = (Player) sender;
		
		//TODO Add commands
		
		return false;
	}
}