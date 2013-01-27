package net.battlenexus.bukkit.economy.converters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import net.battlenexus.bukkit.economy.BattleConomy;
import net.battlenexus.bukkit.economy.api.Api;

public class Iconomy extends ConverterClass {
    
    public boolean flatFile(){
        File accounts = new File(bc.getDataFolder().getParentFile(), "iConomy/accounts.mini");
        
        if(!accounts.exists())
            return false;
        
        try{
            BufferedReader file = new BufferedReader(new FileReader(accounts));
            
            String line = null;
            
            while((line = file.readLine()) != null) {
                String[] miniDb = line.split(" ");
                
                Api.createAccount(miniDb[0]);
                Api.createBalance(miniDb[0], Double.parseDouble(miniDb[1]), "main");
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("ABANDON SHIP, EVERYTHING HAS GONE TO CRAP");
        }
        
        return true;
    }
    
}
