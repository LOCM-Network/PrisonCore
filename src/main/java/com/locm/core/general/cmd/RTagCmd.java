package com.locm.core.general.cmd;

import com.locm.core.Loader;
import com.locm.core.utils.ItemStorage;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;

public class RTagCmd extends Command {

	public RTagCmd() {
		super("rtag");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length != 3) {
			sender.sendMessage(StringUtils.translateColors("&b&l(!)&r&7 Proper usage: /rtag give <name> <amount>"));
			return false;
		}
		if (sender.hasPermission("locm.owner")) {
			if (args[0].equals("give")) {
				Player t = Loader.getLoader().getServer().getPlayer(args[1]);
				if (t != null) {
					try {
						Item rtag = ItemStorage.randomTag();
						int amount = Integer.parseInt(args[2]);
						rtag.setCount(amount);
						sender.sendMessage(StringUtils.getPrefix() + "Gave " + t.getName() + " a random tag voucher");
						t.sendMessage(StringUtils.getPrefix() + "You were given a Random Tag Voucher.");
						t.getInventory().addItem(rtag);
					} catch (NumberFormatException e) {
						sender.sendMessage(StringUtils.getPrefix() + "Incorrect usage, must be integer for amount");
					}
				}
			}
		} else {
			sender.sendMessage(StringUtils.getPrefix() + "No permission to give random tags.");
		}
		return false;
	}

}
