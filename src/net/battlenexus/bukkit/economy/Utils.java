package net.battlenexus.bukkit.economy;

import java.text.DecimalFormat;

public class Utils {
	
	public static String prefix = "$";
	
	public static String parseMoney(String m)
	{
		double money = Double.parseDouble(m);
		DecimalFormat format = new DecimalFormat("#,###.00");
		return prefix+format.format(money);
	}

}
