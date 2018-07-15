package me.NinetyNine.ib.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import me.NinetyNine.ib.InfiniteBuckets;

public class IBConfig implements Listener {

	public static void loadConfig() {
		if (InfiniteBuckets.plugin.getDataFolder().exists())
			return;

		getConfig().options().copyDefaults(true);
	}

	public static void save() {
		InfiniteBuckets.plugin.saveConfig();
	}

	public static void set(String path, int number) {
		getConfig().set(path, number);
		save();
	}

	public static FileConfiguration getConfig() {
		return InfiniteBuckets.plugin.getConfig();
	}
}