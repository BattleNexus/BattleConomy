package net.battlenexus.bukkit.economy.commands;

import net.battlenexus.bukkit.economy.Api;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Balance extends BNCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ConsoleCommandSender && args.length < 1) {
			sender.sendMessage("This command can only be run by players");
			return;
		}
						
		String username = sender.getName();
		String world = null;
		if(args.length > 0) {
			world = args[0];
			if(!Api.balanceExists(username, Api.getEconKey(world))){
				sender.sendMessage("You do not have a balance in this world, visit it to create one!");
				return;
			}
		}else if(args.length > 1) {
			username = args[0];
			if(Api.accountExists(username)) {
				sender.sendMessage("The account '"+args[0]+"' doesn't exist");
				return;
			}
			world = args[1];
		}
		if(world != null) world = Api.getEconKey(world);
		String balance = Api.formatMoney(world == null ? Api.getBalance(username) : Api.getBalance(username, world));

		if (!sender.getName().equalsIgnoreCase(username))
			sender.sendMessage(username + " has: " + balance);
		else
			sender.sendMessage("You have: " + balance);
	}
}