package net.battlenexus.bukkit.economy.commands;

import java.util.logging.Logger;

import net.battlenexus.bukkit.economy.sql.MySQL;

import org.bukkit.command.CommandSender;

public abstract class BNCommand {
    protected static final Logger log = Logger.getLogger("Minecraft");
	public MySQL sql;

	public abstract void execute(CommandSender sender, String[] args);

}