package me.NinetyNine.ib.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import me.NinetyNine.ib.InfiniteBuckets;

public class IBConfig implements Listener {

	public static void loadConfig() {
		if (InfiniteBuckets.plugin.getDataFolder().exists())
			return;

		getConfig().addDefault("price", 10);
		getConfig().addDefault("pricePerUse", 10);
		getConfig().options().copyDefaults(true);
		save();
	}

	public static void save() {
		String old = getConfig().getString("price");
		
		if (IBUtils.isInt(old))
			getConfig().set("price", old);

		String old2 = getConfig().getString("pricePerUse");
		
		if (IBUtils.isInt(old2))
			getConfig().set("pricePerUse", old2);
		
		InfiniteBuckets.plugin.saveConfig();
	}

	public static void set(String path, String number) {
		getConfig().set(path, number);
		save();
	}

	public static FileConfiguration getConfig() {
		return InfiniteBuckets.plugin.getConfig();
	}
}