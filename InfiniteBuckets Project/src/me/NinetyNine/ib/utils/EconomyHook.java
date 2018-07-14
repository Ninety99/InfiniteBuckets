package me.NinetyNine.ib.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class EconomyHook implements Listener {
	
	public static Economy economy = setupEconomy();

	private static Economy setupEconomy() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
			RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager()
					.getRegistration(Economy.class);
			if (rsp != null)
				economy = rsp.getProvider();
		}
		return economy;
	}
}
