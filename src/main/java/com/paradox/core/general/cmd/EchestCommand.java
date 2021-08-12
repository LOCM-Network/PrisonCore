package com.paradox.core.general.cmd;

import com.nukkitx.fakeinventories.inventory.ChestFakeInventory;
import com.nukkitx.fakeinventories.inventory.FakeSlotChangeEvent;
import com.paradox.core.Loader;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.inventory.Inventory;

public class EchestCommand extends Command {

	public EchestCommand() {
		super("echest");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (sender instanceof Player) {
			final ChestFakeInventory inv = new ChestFakeInventory();
			if (args.length == 1) {
				final Player target = Loader.getLoader().getServer().getPlayer(args[0]);
				inv.addListener(this::onSlotChange);
				inv.setContents(target.getEnderChestInventory().getContents());
				inv.setName(target.getName() + "'s ender chest inventory");
				((Player) sender).addWindow((Inventory) inv);
				return false;
			} else {
				Player p = (Player) sender;
				inv.addListener(this::onSlotChange);
				inv.setContents(p.getEnderChestInventory().getContents());
				inv.setName(p.getName() + "'s ender chest inventory");
				p.addWindow(inv);
			}
		}
		return false;
	}

	private void onSlotChange(final FakeSlotChangeEvent e) {
		if (e.getInventory() instanceof ChestFakeInventory
				&& (e.getInventory().getName().contains("'s ender chest inventory")
						|| e.getInventory().getName().contains("'s inventory"))) {
			e.setCancelled(true);
		}
	}
}
