package net.battlenexus.bukkit.economy.commands;

import net.battlenexus.bukkit.economy.Api;

import org.bukkit.command.CommandSender;

public class Set extends BNCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 2){
			sender.sendMessage("/be set <username> <amount>");
			return;
		}
		
		double amount = Double.parseDouble(args[1]);
		
		if(Api.setMoney(args[0], amount)) {
			sender.sendMessage(args[0]+" balance was set to "+Api.formatMoney(Double.parseDouble(args[1])));
		}else{
			sender.sendMessage("This world doesn't have an economy");
		}
	}

}
