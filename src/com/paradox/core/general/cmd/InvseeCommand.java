package com.paradox.core.general.cmd;

import com.nukkitx.fakeinventories.inventory.DoubleChestFakeInventory;
import com.paradox.core.Loader;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.inventory.Inventory;

public class InvseeCommand extends Command {
	public InvseeCommand() {
		super("invsee");
	}

	public boolean execute(final CommandSender sender, final String label, final String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				sender.sendMessage("§cUsage: /invsee <player>");
				return false;
			}
			if (!sender.hasPermission("paradox.invsee")){
				sender.sendMessage("§cNo permission.");
				return false;
			}
			final Player target = Loader.getLoader().getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(StringUtils.getPrefix() + "Player is offline");
				return true;
			}
			if (target.equals((Object) sender)) {
				sender.sendMessage(StringUtils.getPrefix() + "Cannot use this command for own inventory.");
				return true;
			}
			final DoubleChestFakeInventory inv = new DoubleChestFakeInventory();
			inv.setContents(target.getInventory().getContents());
			inv.setName(target.getName() + "'s inventory");
			((Player) sender).addWindow((Inventory) inv);
			return true;
		} else {
			sender.sendMessage("§cYou can run this command only as a player");
		}
		return true;
	}

}
