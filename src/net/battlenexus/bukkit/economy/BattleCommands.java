package net.battlenexus.bukkit.economy;

import net.battlenexus.bukkit.economy.commands.BNCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleCommands implements CommandExecutor  {
	
	BNCommand[] commands = new BNCommand[] {
			
	};
	public BattleCommands(BattleConomy battleconomy)
	{
		//I'm sure this will come in use at sometime.
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		//I are too lazy to write the dynamic commands system
		if(args.length < 1){
			String[] newargs = {"help"};
			if(sender instanceof Player)
			{
				newargs[0] = "balance"; 
			}
			return onCommand(sender, cmd, label, newargs);
		}
		
		sender.sendMessage("--------- BEGIN DEBUG INFO ---------");
		sender.sendMessage("Label Used");
		sender.sendMessage(label);
		sender.sendMessage("Arguments:");
		sender.sendMessage(args);
		sender.sendMessage("---------  END DEBUG INFO  ---------");
		sender.sendMessage("");
		
		String messageToSend = "Invalid Arguement";
		
		if(args[0].equalsIgnoreCase("balance")){
			messageToSend = "You have £20.34";
		}
		
		if(args[0].equalsIgnoreCase("help")){
			messageToSend = "Help? What Help? Your on your own for this one.";
		}
		
		sender.sendMessage(messageToSend);
		
		return true;
	}
}