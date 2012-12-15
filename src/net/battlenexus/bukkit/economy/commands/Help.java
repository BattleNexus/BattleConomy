package net.battlenexus.bukkit.economy.commands;

import org.bukkit.command.CommandSender;

public class Help extends BNCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("/be send <username> <amount> [world] - Send some of your money to a user");
        sender.sendMessage("/be balance - See your balance for the current world");
        if (sender.hasPermission("be.admin.take") || sender.isOp())
            sender.sendMessage("/be take <username> <amount> [world] - Take money from a user's balance");
        if (sender.hasPermission("be.admin.add") || sender.isOp())
            sender.sendMessage("/be add <username> <amount> [world] - Adds money to a user's balance");
        if (sender.hasPermission("be.admin.set") || sender.isOp())
            sender.sendMessage("/be set <username> <amount> [world] - Sets a users balance");
    }

}
