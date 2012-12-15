package net.battlenexus.bukkit.economy.commands;

import org.bukkit.command.CommandSender;

public class Help extends BNCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("/bc send <username> <amount> [world] - Send some of your money to a user");
        sender.sendMessage("/bc balance - See your balance for the current world");
        if (sender.hasPermission("bc.admin.take") || sender.isOp())
            sender.sendMessage("/bc take <username> <amount> [world] - Take money from a user's balance");
        if (sender.hasPermission("bc.admin.add") || sender.isOp())
            sender.sendMessage("/bc add <username> <amount> [world] - Adds money to a user's balance");
        if (sender.hasPermission("bc.admin.set") || sender.isOp())
            sender.sendMessage("/bc set <username> <amount> [world] - Sets a users balance");
    }

}
