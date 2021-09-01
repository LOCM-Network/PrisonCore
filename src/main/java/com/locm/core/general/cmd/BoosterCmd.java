package com.locm.core.general.cmd;

import com.locm.core.Loader;
import com.locm.core.utils.ItemStorage;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;

public class BoosterCmd extends Command {

	public BoosterCmd() {
		super("booster");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length != 5) {
			sender.sendMessage(StringUtils.translateColors(
					"&b&l(!)&r&7 Proper usage: /booster give <name> <amount> <multiplier> <sell/orbs>"));
			return false;
		}
		if (sender.hasPermission("locm.owner")) {
			if (args[0].equals("give")) {
				Player t = Loader.getLoader().getServer().getPlayer(args[1]);
				if (t != null) {
					if (args[4].equals("sell")) {
						try {
							Item booster = ItemStorage.sellBoosterTenMinutes(Integer.parseInt(args[3]));
							sender.sendMessage(StringUtils.getPrefix() + "Gave " + t.getName() + " a x" + args[3]
									+ " sell booster.");
							t.sendMessage(StringUtils.getPrefix() + "Given a sell booster.");
							booster.setCount(Integer.parseInt(args[2]));
							t.getInventory().addItem(booster);
						} catch (NumberFormatException e) {
							sender.sendMessage(StringUtils.getPrefix()
									+ "Incorrect usage, must be integer for amount and multiplier.");
						}
					} else if (args[4].equals("orbs")) {
						try {
							Item booster = ItemStorage.orbsBoosterTenMinutes(Integer.parseInt(args[3]));
							sender.sendMessage(StringUtils.getPrefix() + "Gave " + t.getName() + " a x" + args[3]
									+ " orbs booster.");
							t.sendMessage(StringUtils.getPrefix() + "Given an orbs booster.");
							booster.setCount(Integer.parseInt(args[2]));
							t.getInventory().addItem(booster);
						} catch (NumberFormatException e) {
							sender.sendMessage(StringUtils.getPrefix()
									+ "Incorrect usage, must be integer for amount and multiplier.");
						}
					}
				}
			}
		} else {
			sender.sendMessage(StringUtils.getPrefix() + "No permission to give boosters.");
		}
		return false;
	}

}
