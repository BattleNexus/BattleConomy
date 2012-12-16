package net.battlenexus.bukkit.economy.listeners;

import java.lang.reflect.Constructor;

import net.battlenexus.bukkit.economy.BattleConomy;
import net.battlenexus.bukkit.economy.api.Api;
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
        if (args.length < 1) {
            String[] newargs = { "help" };
            if (sender instanceof Player) {
                newargs[0] = "balance";
            }
            return onCommand(sender, cmd, label, newargs);
        }

        try {
            if (sender instanceof Player) {
                final Player p = (Player)sender;
                if (Api.getEconomyKeyByWorld(p.getWorld().getName()) == null) {
                    p.sendMessage("Economy is not enabled on this world!");
                    return true;
                }
            }
            Class<?> class_ = Class
                    .forName("net.battlenexus.bukkit.economy.commands."
                            + WordUtils.capitalizeFully(args[0]));
            Class<? extends BNCommand> runClass = class_
                    .asSubclass(BNCommand.class);
            Constructor<? extends BNCommand> constructor = runClass
                    .getConstructor();
            BNCommand command = constructor.newInstance();
            String[] newargs = new String[args.length - 1];
            if (newargs.length >= 2)
                System.arraycopy(args, 1, newargs, 0, newargs.length);
            else if (newargs.length == 1)
                newargs[0] = args[1];
            command.sql = sql;
            try {
                command.execute(sender, newargs);
            } catch (Exception e) { sender.sendMessage("There was an error running the command."); e.printStackTrace(); }
        } catch (Exception e) {
            sender.sendMessage("Command not found.");
        }

        return true;
    }
}