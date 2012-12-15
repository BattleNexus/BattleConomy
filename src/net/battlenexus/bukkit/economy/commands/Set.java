package net.battlenexus.bukkit.economy.commands;

import net.battlenexus.bukkit.economy.api.Api;

import org.bukkit.command.CommandSender;

public class Set extends BNCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!sender.hasPermission("be.admin.set") || !sender.isOp()){
			sender.sendMessage("You do not have permission to run this command");
			return;
		}
		if(args.length < 2){
			sender.sendMessage("/be set <username> <amount> [world]");
			return;
		}
		if(!Api.accountExists(args[0])) {
			sender.sendMessage("The account '"+args[0]+"' doesn't exist");
			return;
		}
		
		double amount = Double.parseDouble(args[1]);
		
		if(args.length == 2 ? Api.setMoney(args[0], amount) : Api.setMoney(args[0], amount, Api.getEcononmyKeyByWorld(args[2]))) {			sender.sendMessage(args[0]+" balance was set to "+Api.formatMoney(Double.parseDouble(args[1])));
		}else{
			sender.sendMessage("This world doesn't have an economy");
		}
	}

}
