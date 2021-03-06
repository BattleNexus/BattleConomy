package net.battlenexus.bukkit.economy.commands;

import net.battlenexus.bukkit.economy.api.Api;

import org.bukkit.command.CommandSender;

public class Take extends BNCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("bc.admin.take") || !sender.isOp()) {
            sender.sendMessage("You do not have permission to run this command");
            return;
        }
        if (args.length < 0) {
            sender.sendMessage("/bc take <username> <amount> [world]");
        }

        if (!Api.accountExists(args[0])) {
            sender.sendMessage("The account '" + args[0] + "' doesn't exist");
            return;
        }

        double amount = Double.parseDouble(args[1]);

        if (args.length == 2 ? Api.addMoney(args[0], amount) : Api.addMoney(
                args[0], amount, Api.getEconomyKeyByWorld(args[2]))) {
            sender.sendMessage("You removed " + Api.formatMoney(amount)
                    + " to " + args[0] + "'s account. They now have "
                    + Api.formatMoney(Api.getBalance(args[0])));
        } else {
            sender.sendMessage("This world doesn't have an economy");
        }
    }

}
