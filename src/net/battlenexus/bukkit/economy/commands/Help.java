package net.battlenexus.bukkit.economy.commands;

import org.bukkit.command.CommandSender;

public class Help extends BNCommand {
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage("Help? You think we gonna help you? You crazy ");
	}
	
}
