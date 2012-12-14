package net.battlenexus.bukkit.economy.commands;

import net.battlenexus.bukkit.economy.Api;

import org.bukkit.command.CommandSender;

public class Add extends BNCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 0) {
			sender.sendMessage("/be add <username> <amount> [world]");
		}
		
		if(Api.accountExists(args[0])){
			double amount = Double.parseDouble(args[1]);
			
			if(args.length == 2 ? Api.addMoney(args[0], amount) : Api.addMoney(args[0], amount, Api.getEconKey(args[2]))) {				sender.sendMessage("You added "+Api.formatMoney(amount)+" to "+args[0]+"'s account. They now have "+Api.formatMoney(Api.getBalance(args[0])));
			}else{
				sender.sendMessage("This world doesn't have an economy");
			}
		}
	}

}
