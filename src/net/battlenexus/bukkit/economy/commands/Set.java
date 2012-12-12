package net.battlenexus.bukkit.economy.commands;

import net.battlenexus.bukkit.economy.Utils;

import org.bukkit.command.CommandSender;

public class Set extends BNCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 2){
			sender.sendMessage("You have entered 2 few arguments");
			return;
		}
		
		String user = args[0];
		String amount = args[1];
		
		sql.build("UPDATE " + sql.prefix + "balances w INNER JOIN "
				+ sql.prefix + "players p ON p.id=w.user_id SET w.balance="+amount+" WHERE p.username='"
				+ user + "'");
		sender.sendMessage(sql.current_query);
		sql.execute();
		
		sender.sendMessage(user+" balance was set to "+Utils.parseMoney(args[1]));
	}

}
