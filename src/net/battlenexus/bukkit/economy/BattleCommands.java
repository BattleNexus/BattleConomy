package net.battlenexus.bukkit.economy;

import java.lang.reflect.Constructor;
import java.sql.SQLException;

import net.battlenexus.bukkit.economy.commands.BNCommand;
import net.battlenexus.bukkit.economy.sql.SqlClass;

import org.apache.commons.lang.WordUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleCommands implements CommandExecutor {

	SqlClass sql;

	public BattleCommands(SqlClass sql) {
		this.sql = sql;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// If no arguments are entered, show balance if player or help if
		// console
		if(args.length < 1){
			String[] newargs = {"help"};
			if(sender instanceof Player){
				newargs[0] = "balance";
			}
			return onCommand(sender, cmd, label, newargs);
		}

		try {
			Class<?> class_ = Class.forName("net.battlenexus.bukkit.economy.commands."+WordUtils.capitalizeFully(args[0]));
			Class<? extends BNCommand> runClass = class_.asSubclass(BNCommand.class);
			Constructor<? extends BNCommand> constructor = runClass.getConstructor();
			BNCommand command = constructor.newInstance();
			String[] newargs = new String[args.length - 1];
			if (newargs.length >= 2)
				System.arraycopy(args, 1, newargs, 0, newargs.length);
			else if(newargs.length == 1)
				newargs[0] = args[1];
			command.sql = sql;
			command.execute(sender, newargs);
		} catch (Exception e) {
			if(e instanceof ClassNotFoundException)
				sender.sendMessage("Command not found.");
			else if(e instanceof SQLException) {}
			else
				sender.sendMessage("There was error an running command.");
			e.printStackTrace();
		}

		return true;
	}
}