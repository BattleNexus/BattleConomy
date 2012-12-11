package net.battlenexus.bukkit.economy.commands;

import org.bukkit.command.CommandSender;

public class Balance extends BNCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage("You have £20.04");
	}
}