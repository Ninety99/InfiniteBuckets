package me.NinetyNine.ib.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import lombok.Getter;
import me.NinetyNine.ib.utils.IBConfig;
import me.NinetyNine.ib.utils.IBUtils;

public class IBCommands implements Listener, CommandExecutor {

	private static Inventory inventory = Bukkit.createInventory(null, 9, ChatColor.RED + "Get infibuckets");

	@Getter
	public static List<String> lore = Arrays.asList(" ", "A special bucket!");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (cmd.getName().equalsIgnoreCase("infibucket")) {
				if (args.length == 0) {
					sender.sendMessage(IBUtils.format("&cYou need to be a player to execute this command!"));
					return true;
				}

				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("save")) {
						IBConfig.save();
						sender.sendMessage(IBUtils.format("&aSaved & Reloaded!"));
						return true;
					}
				}

				if (args[0].equalsIgnoreCase("setPrice")) {
					if (args.length == 1) {
						sender.sendMessage(IBUtils.format("&cUsage: /infibucket setPrice <number>"));
						return true;
					}

					if (args.length == 2) {
						if (IBUtils.isInt(args[1])) {
							int number = Integer.parseInt(args[1]);
							IBConfig.set("price", number);
							sender.sendMessage(IBUtils.format("&aSuccessfully set price to: " + args[1] + "!"));
							return true;
						} else {
							sender.sendMessage(IBUtils.format("&c" + args[1] + " is not a valid number!"));
							return true;
						}
					}

					if (args.length > 2) {
						sender.sendMessage(IBUtils.format("&cInvalid command!"));
						return true;
					}
				}

				if (args[0].equalsIgnoreCase("setPricePerUse")) {
					if (args.length == 1) {
						sender.sendMessage(IBUtils.format("&cUsage: /infibucket setPrice <number>"));
						return true;
					}

					if (args.length == 2) {
						if (IBUtils.isInt(args[1])) {
							int number = Integer.parseInt(args[1]);
							IBConfig.set("pricePerUse", number);
							sender.sendMessage(IBUtils.format("&aSuccessfully set price per use to: " + args[1] + "!"));
							return true;
						} else {
							sender.sendMessage(IBUtils.format("&c" + args[1] + " is not a valid number!"));
							return true;
						}
					}

					if (args.length > 2) {
						sender.sendMessage(IBUtils.format("&cInvalid command!"));
						return true;
					}
				}
			}
			return true;
		} else {
			Player player = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("infibucket")) {
				if (args.length == 0) {
					if (inventory.getSize() == 9) {
						player.sendMessage(IBUtils.format("&8Opening..."));

						inventory.setItem(3,
								IBUtils.createItem(true, "&bWater Bucket", lore, Enchantment.ARROW_INFINITE, 1));

						inventory.setItem(5,
								IBUtils.createItem(false, "&bLava Bucket", lore, Enchantment.ARROW_INFINITE, 1));

						player.openInventory(inventory);
						return true;
					} else
						return false;
				}

				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("save")) {
						if (player.hasPermission("infibucket.save")) {
							IBConfig.save();
							player.sendMessage(IBUtils.format("&aSaved & Reloaded!"));

							if (args.length > 2) {
								player.sendMessage(IBUtils.format("&cInvalid command!"));
								return true;
							}
							return true;
						} else {
							player.sendMessage(IBUtils.format("&cYou do not have permissions to use this command!"));
						}
					}
				}

				if (args[0].equalsIgnoreCase("setPrice")) {
					if (player.hasPermission("infibucket.set.price")) {
						if (args.length == 1) {
							player.sendMessage(IBUtils.format("&cUsage: /infibucket setPrice <number>"));
							return true;
						}

						if (args.length == 2) {
							if (IBUtils.isInt(args[1])) {
								int number = Integer.parseInt(args[1]);
								IBConfig.set("price", number);
								player.sendMessage(IBUtils.format("&aSuccessfully set price to: " + args[1] + "!"));
								return true;
							} else {
								player.sendMessage(IBUtils.format("&c" + args[1] + " is not a valid number!"));
								return true;
							}
						}

						if (args.length > 2) {
							player.sendMessage(IBUtils.format("&cInvalid command!"));
							return true;
						}
					} else {
						player.sendMessage(IBUtils.format("&cYou do not have permissions to use this command!"));
					}
				}

				if (args[0].equalsIgnoreCase("setPricePerUse")) {
					if (player.hasPermission("infibucket.set.priceperuse")) {
						if (args.length == 1) {
							player.sendMessage(IBUtils.format("&cUsage: /infibucket setPrice <number>"));
							return true;
						}

						if (args.length == 2) {
							if (IBUtils.isInt(args[1])) {
								int number = Integer.parseInt(args[1]);
								IBConfig.set("pricePerUse", number);
								player.sendMessage(
										IBUtils.format("&aSuccessfully set price per use to: " + args[1] + "!"));
								return true;
							} else {
								player.sendMessage(IBUtils.format("&c" + args[1] + " is not a valid number!"));
								return true;
							}
						}

						if (args.length > 2) {
							player.sendMessage(IBUtils.format("&cInvalid command!"));
							return true;
						}
					} else {
						player.sendMessage(IBUtils.format("&cYou do not have permissions to use this command!"));
					}
				}
			}
		}
		return false;
	}

	public static Inventory getInventory() {
		return inventory;
	}
}