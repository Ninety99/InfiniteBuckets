package me.NinetyNine.ib.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

import me.NinetyNine.ib.utils.EconomyHook;
import me.NinetyNine.ib.utils.IBConfig;
import me.NinetyNine.ib.utils.IBUtils;
import net.milkbowl.vault.economy.EconomyResponse;

public class IBPlayerBucketEmpty implements Listener {

	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlockClicked();
		ItemStack bucket = player.getItemInHand();

		if (bucket.getItemMeta().hasEnchants()) {
			if (!player.isOp()) {
				String price = IBConfig.getConfig().getString("pricePerUse");
				if (IBUtils.isInt(price)) {
					if (EconomyHook.economy.has(player, IBConfig.getConfig().getInt("pricePerUse"))) {
						e.setCancelled(true);
						getMoney(player, IBConfig.getConfig().getInt("pricePerUse"));
						if (bucket.toString().contains("LAVA"))
							spawn(block, false, e.getBlockFace());
						else
							spawn(block, true, e.getBlockFace());
						return;
					} else {
						e.setCancelled(true);
						player.sendMessage(
								IBUtils.format("&cYou do not have " + "%price%" + "!").replace("%price%", price));
						return;
					}
				} else {
					IBUtils.sendCons(price + " is not a number!");
					return;
				}
			} else {
				e.setCancelled(true);
				if (bucket.toString().contains("LAVA"))
					spawn(block, false, e.getBlockFace());
				else
					spawn(block, true, e.getBlockFace());
			}
		} else
			return;
	}

	public void getMoney(Player player, int cost) {
		EconomyResponse r = EconomyHook.economy.withdrawPlayer(player, cost);
		if (r.transactionSuccess())
			return;
		else {
			player.sendMessage(IBUtils.format("&cTransaction failed."));
			return;
		}
	}

	public void spawn(Block block, boolean isWater, BlockFace face) {
		block.getRelative(face).setType(isWater ? Material.WATER : Material.LAVA);
	}
}