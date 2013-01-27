package net.battlenexus.bukkit.economy.commands;

import net.battlenexus.bukkit.economy.converters.ConverterClass;
import net.battlenexus.bukkit.economy.converters.Iconomy;

import org.bukkit.command.CommandSender;

public class Convert extends BNCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 1){
            sender.sendMessage("Too few args");
            return;
        }
        
        ConverterClass converter = null;
        
        switch(args[0]){
        case "iconomy":
            converter = new Iconomy();
            break;
        }
        sender.sendMessage("Attempting Conversion of flatfile...");
        if(!converter.equals(null))
            converter.flatFile();
        sender.sendMessage("Converting complete");
    }

}
