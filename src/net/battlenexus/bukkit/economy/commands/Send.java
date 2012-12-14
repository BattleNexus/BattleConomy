package net.battlenexus.bukkit.economy.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import net.battlenexus.bukkit.economy.Api;

public class Send extends BNCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ConsoleCommandSender){
			sender.sendMessage("This command can only be used by players");
			return;
		}
		
		String username = args[0];
		double amount = Double.parseDouble(args[1]);		

		if(Api.hasEnough(sender.getName(), amount)) {					
			if(Api.accountExists(username)){
				Api.addMoney(username, amount);
				Api.takeMoney(sender.getName(), amount);
				sender.sendMessage("You sent "+ Api.formatMoney(amount) +" to "+ username);
			}else
				sender.sendMessage("User '"+username+"' doesn't exist, money has not been taken from your balance");
		}else
			sender.sendMessage("You do not have enough to complete this transaction. No money has been taken.");
	}

}
