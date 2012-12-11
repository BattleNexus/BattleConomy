package net.battlenexus.bukkit.economy;

import java.lang.reflect.Constructor;

import net.battlenexus.bukkit.economy.commands.BNCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleCommands implements CommandExecutor  {
	
	public BattleCommands(BattleConomy battleconomy)
	{
		//I'm sure this will come in use at sometime.
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		//I are too lazy to write the dynamic commands system
		if(args.length < 1){
			String[] newargs = {"help"};
			if(sender instanceof Player)
			{
				newargs[0] = "balance"; 
			}
			return onCommand(sender, cmd, label, newargs);
		}
		
		sender.sendMessage("--------- BEGIN DEBUG INFO ---------");
		sender.sendMessage("Label Used");
		sender.sendMessage(label);
		sender.sendMessage("Arguments:");
		sender.sendMessage(args);
		sender.sendMessage("---------  END DEBUG INFO  ---------");
		sender.sendMessage("");
		
		try {
			Class<?> class_ = Class.forName(args[0]); //Command names are now case sensitive. So Command1.java will equal /b Command1, and command1.java will equal /b command1
			Class<? extends BNCommand> runClass = class_.asSubclass(BNCommand.class);
			Constructor<? extends BNCommand> constructor = runClass.getConstructor();
			BNCommand command = constructor.newInstance();
			String[] newargs = new String[args.length - 1];
			System.arraycopy(args, 1, args, 0, args.length - 2);
			command.execute(sender, newargs);
		} catch (Exception e) {
			sender.sendMessage("Command not found.");
		}
		
		return true;
	}
}