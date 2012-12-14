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
						
		String username = args.length > 0 ? args[0] : sender.getName();

		String balance = Api.formatMoney(Api.getBalance(username));

		if (!sender.getName().equalsIgnoreCase(username))
			sender.sendMessage(username + " has: " + balance);
		else
			sender.sendMessage("You have: " + balance);
	}
}