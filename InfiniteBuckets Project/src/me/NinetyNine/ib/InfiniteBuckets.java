package me.NinetyNine.ib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.NinetyNine.ib.commands.IBCommands;
import me.NinetyNine.ib.listeners.IBInventoryListener;
import me.NinetyNine.ib.listeners.IBPlayerBucketEmpty;
import me.NinetyNine.ib.utils.EconomyHook;
import me.NinetyNine.ib.utils.IBConfig;
import me.NinetyNine.ib.utils.IBUtils;

public class InfiniteBuckets extends JavaPlugin {
	
	public static InfiniteBuckets plugin;
	
	@Override
	public void onEnable() {
		plugin = this;

		registerListeners();
		registerCommands();
		IBConfig.loadConfig();
		IBUtils.sendCons("Enabled");
	}
	
	@Override
	public void onDisable() {
		IBConfig.save();
		IBUtils.sendCons("Disabled");
	}
	
	private void registerListeners() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new IBCommands(), this);
		pm.registerEvents(new IBInventoryListener(), this);
		pm.registerEvents(new IBConfig(), this);
		pm.registerEvents(new IBPlayerBucketEmpty(), this);
		pm.registerEvents(new EconomyHook(), this);
	}
	
	private void registerCommands() { 
		getCommand("infibucket").setExecutor(new IBCommands());
	}
}