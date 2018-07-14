package me.NinetyNine.ib.listeners;

import org.bukkit.Location;
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
							spawn(player, block, false);
						else
							spawn(player, block, true);
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
					spawn(player, block, false);
				else
					spawn(player, block, true);
			}
		} else
			return;
	}

	public void getMoney(Player player, int cost) {
		EconomyResponse r = EconomyHook.economy.withdrawPlayer(player, cost);
		System.out.println("Economy#withdrawPlayer()");
		if (r.transactionSuccess()) {
			System.out.println("success getMoney()");
			return;
		} else {
			player.sendMessage(IBUtils.format("&cTransaction failed."));
			return;
		}
	}

	public void spawn(Player player, Block block, boolean isWater) {
		// east or west
		int x;

		int y;

		// north or south
		int z;

		Block up = block.getRelative(BlockFace.UP);

		if (up.getType() == Material.AIR)
			y = block.getLocation().clone().getBlockY() + 1;
		else
			y = block.getLocation().clone().getBlockY();

		Block east = block.getRelative(BlockFace.EAST);
		Block west = block.getRelative(BlockFace.WEST);

		if (east.getType() == Material.AIR || west.getType() == Material.AIR)
			x = block.getLocation().clone().getBlockX() - 1;
		else
			x = block.getLocation().clone().getBlockX();

		Block down = block.getRelative(BlockFace.DOWN);

		if (down.getType() == Material.AIR)
			z = block.getLocation().clone().getBlockZ() + 1;
		else
			z = block.getLocation().clone().getBlockZ();

		//I originally had
		/* 
		 * int x = block.getLocation().clone().getBlockX(); 
		 * int y = block.getLocation().clone().getBlockY() + 1; 
		 * int z = block.getLocation().clone().getBlockZ();
		 * 
		 * Location bLoc = new Location(x, y, z);
		 * 
		 * I tried that when I just right clicked on the ground, not on a blocks' side.
		 * Then, I was wondering what would happen if I do that, I tried it and it
		 * spawned water above the block that I right clicked
		 */

		Location bLoc = new Location(block.getWorld(), x, y, z);
		if (isWater)
			bLoc.getBlock().setType(Material.WATER);
		else
			bLoc.getBlock().setType(Material.LAVA);
	}
}