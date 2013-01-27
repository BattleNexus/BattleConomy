package net.battlenexus.bukkit.economy.converters;

import net.battlenexus.bukkit.economy.BattleConomy;

public abstract class ConverterClass {

    public final BattleConomy bc = BattleConomy.INSTANCE;
    
    abstract public boolean flatFile();
    //abstract public boolean database();
    
}
