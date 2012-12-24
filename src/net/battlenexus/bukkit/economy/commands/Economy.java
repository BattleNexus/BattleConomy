package net.battlenexus.bukkit.economy.commands;

import net.battlenexus.bukkit.economy.api.Api;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Economy extends BNCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage("This command can only be used by players");
            return;
        }
        sender.sendMessage("The current world economy key is: " + Api.getEconomyKeyByPlayerWorld(sender.getName()));
    }

}
