package com.locm.core.general.cmd;

import com.nukkitx.fakeinventories.inventory.FakeInventories;
import com.nukkitx.fakeinventories.inventory.DoubleChestFakeInventory;
import com.locm.core.Loader;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class InvseeCommand extends Command {
	public InvseeCommand() {
		super("invsee");
	}

	public boolean execute(final CommandSender sender, final String label, final String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				sender.sendMessage("Usage: /invsee <player>");
				return false;
			}
			if (!sender.hasPermission("locm.invsee")){
				sender.sendMessage("No permission.");
				return false;
			}
			final Player target = Loader.getLoader().getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(StringUtils.getPrefix() + "Player is offline");
				return true;
			}
			if (target.equals(sender)) {
				sender.sendMessage(StringUtils.getPrefix() + "Cannot use this command for own inventory.");
				return true;
			}
			DoubleChestFakeInventory inv = new FakeInventories().createDoubleChestInventory();
			inv.setContents(target.getInventory().getContents());
			inv.setName(target.getName() + "'s inventory");
			((Player) sender).addWindow(inv);
			return true;
		} else {
			sender.sendMessage("You can run this command only as a player");
		}
		return true;
	}

}
