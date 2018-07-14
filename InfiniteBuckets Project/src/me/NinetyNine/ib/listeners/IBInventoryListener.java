package me.NinetyNine.ib.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.NinetyNine.ib.commands.IBCommands;
import me.NinetyNine.ib.utils.EconomyHook;
import me.NinetyNine.ib.utils.IBConfig;
import me.NinetyNine.ib.utils.IBUtils;
import net.milkbowl.vault.economy.EconomyResponse;

public class IBInventoryListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;
		else {
			if (e.getInventory().getName().equals(ChatColor.RED + "Get infibuckets")) {
				Player player = (Player) e.getWhoClicked();
				e.setCancelled(true);
				if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
					return;

				String price = IBConfig.getConfig().getString("price");

				ItemStack waterBucket = IBUtils.createItem(true, "&bWater Bucket", IBCommands.getLore(),
						Enchantment.ARROW_INFINITE, 1);

				if (e.getCurrentItem().getType() == Material.WATER_BUCKET && e.getCurrentItem().hasItemMeta()) {
					if (!player.isOp()) {
						if (IBUtils.isInt(price)) {
							if (EconomyHook.economy.has(player, IBConfig.getConfig().getInt("price"))) {
								player.closeInventory();
								getMoney(player, IBConfig.getConfig().getInt("price"));
								player.getInventory().addItem(waterBucket);
								return;
							} else {
								if (IBUtils.isInt(price)) {
									e.setCancelled(true);
									player.sendMessage(
											IBUtils.format("&cYou do not have " + "%price%" + " to buy this bucket!")
													.replace("%price%", price));
									return;
								} else {
									IBUtils.sendCons(price + "is not a number!");
									return;
								}
							}
						} else {
							IBUtils.sendCons(price + " is not a valid number!");
							return;
						}
					} else {
						player.getInventory().addItem(waterBucket);
						player.closeInventory();
						return;
					}
				}

				ItemStack lavaBucket = IBUtils.createItem(false, "&bLava Bucket", IBCommands.getLore(),
						Enchantment.ARROW_INFINITE, 1);

				if (e.getCurrentItem().getType() == Material.LAVA_BUCKET && e.getCurrentItem().hasItemMeta()) {
					if (!player.isOp()) {
						if (EconomyHook.economy.has(player, IBConfig.getConfig().getInt("price"))) {
							player.closeInventory();
							getMoney(player, IBConfig.getConfig().getInt("price"));
							player.getInventory().addItem(lavaBucket);
							return;
						} else {
							if (IBUtils.isInt(price)) {
								e.setCancelled(true);
								player.sendMessage(
										IBUtils.format("&cYou do not have " + "%price%" + " to buy this bucket!")
												.replace("%price%", price));
								return;
							} else {
								IBUtils.sendCons(price + "is not a valid number!");
								return;
							}
						}
					} else {
						player.getInventory().addItem(lavaBucket);
						return;
					}
				}
			} else
				return;
		}
	}

	public void getMoney(Player player, int cost) {
		EconomyResponse r = EconomyHook.economy.withdrawPlayer(player, cost);
		if (r.transactionSuccess())
			return;
		else {
			System.out.println("notSuccess");
			player.sendMessage(IBUtils.format("&cTransaction failed."));
			return;
		}
	}
}