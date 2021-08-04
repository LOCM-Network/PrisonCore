package com.paradox.core.general.cmd;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import me.onebone.economyapi.EconomyAPI;

public class RepairCommand extends Command {

	public RepairCommand() {
		super("repairhand", "repair item in hand for $5k", "/repairhand");
	}

	EconomyAPI api = EconomyAPI.getInstance();
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		Player p = (Player) sender;
		Item i = p.getInventory().getItemInHand();
		if (i.isArmor() || i.isPickaxe() || i.isSword() || i.isAxe() || i.isShears() || i.isPickaxe() || i.isShovel()) {
			if (api.myMoney(p)>=5000){
				api.reduceMoney(p, 5000);
				p.sendMessage("§b§l(§d!§b)§r§7 Repaired the item you wield.");
				p.getInventory().remove(i);
				i.setDamage(0);
				p.getInventory().addItem(i);
			} else {
				p.sendMessage("§b§l(§d!§b)§r§7 Cannot afford this purchase.");	
			}
		} else {
			p.sendMessage("§b§l(§d!§b)§r§7 Incompatible item.");
		}
		return false;
	}

}
