package me.NinetyNine.ib.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IBUtils {

	public static String format(String message) {
		return ChatColor.translateAlternateColorCodes('&', "&7[&5InfiBuckets&7] &r" + message);
	}

	public static String f(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static void sendCons(String message) {
		Bukkit.getServer().getLogger().info("[InfiBuckets] " + message);
	}

	public static boolean isInt(String arg0) {
		try {
			Integer.parseInt(arg0);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static ItemStack createItem(boolean isWater, String displayName, List<String> lore,
			Enchantment enchantment, int enchAmplifier) {
		
		ItemStack itemStack;

		if (isWater)
			itemStack = new ItemStack(Material.WATER_BUCKET);
		else
			itemStack = new ItemStack(Material.LAVA_BUCKET);

		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(f(displayName));
		meta.setLore(lore);
		meta.addEnchant(enchantment, enchAmplifier, true);
		itemStack.setItemMeta(meta);
		
		return itemStack;
	}
}