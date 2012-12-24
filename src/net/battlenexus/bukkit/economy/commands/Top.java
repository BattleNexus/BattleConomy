package net.battlenexus.bukkit.economy.commands;

import java.util.LinkedHashMap;

import net.battlenexus.bukkit.economy.api.Api;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Top extends BNCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ConsoleCommandSender && args.length < 1) {
            sender.sendMessage("This command can only be run by players");
            return;
        }
        sender.sendMessage("Top 5 Players for this economy:");
        LinkedHashMap<String, Double> players = Api.topPlayers(Api.getEconomyKeyByPlayerWorld(sender.getName()));
        for(String player : players.keySet()) {
            sender.sendMessage(player + " with " + Api.formatMoney(players.get(player)));
        }
    }

}
