package net.battlenexus.bukkit.economy.commands;

import net.battlenexus.bukkit.economy.sql.MySQL;

import org.bukkit.command.CommandSender;

public abstract class BNCommand {

	public MySQL sql;

	public abstract void execute(CommandSender sender, String[] args);

}