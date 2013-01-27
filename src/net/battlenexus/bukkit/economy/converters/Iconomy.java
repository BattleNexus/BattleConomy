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
            
            int i = 0;
            
            while((line = file.readLine()) != null) {
                String[] miniDb = line.split(" ");
                
                Api.sql.build("INSERT IGNORE INTO " + Api.sql.prefix + "players SET username="+miniDb[0]+";"+"INSERT IGNORE INTO " + Api.sql.prefix
                        + "balances SELECT '"+miniDb[0]+"',id,'"+Double.parseDouble(miniDb[1].replace("balance:", ""))+"' FROM " + Api.sql.prefix
                        + "players WHERE username='"+miniDb[0]+"';");
                i++;
                if(i > 499){
                    Api.sql.executeUpdate();
                    i = 0;
                }
            }
            file.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("ABANDON SHIP, EVERYTHING HAS GONE TO CRAP");
            return false;
        }
        Api.sql.executeUpdate();
        
        return true;
    }
    
}
