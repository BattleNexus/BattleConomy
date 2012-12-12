package net.battlenexus.bukkit.economy.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.battlenexus.bukkit.economy.Utils;

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

		sql.build("SELECT * FROM " + sql.prefix + "balances w INNER JOIN "
				+ sql.prefix + "players p ON p.id=w.user_id WHERE p.username='"
				+ username.toLowerCase() + "'");

		sender.sendMessage(sql.current_query);
		ResultSet results = sql.execute();
		try {
			while (results.next()) {
				String m = Utils.parseMoney(results.getString("balance"));

				if (!sender.getName().equalsIgnoreCase(username))
					sender.sendMessage(username + " has: " + m);
				else
					sender.sendMessage("You have: " + m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}