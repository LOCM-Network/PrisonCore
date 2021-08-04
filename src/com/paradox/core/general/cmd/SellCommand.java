package com.paradox.core.general.cmd;

import java.io.File;

import com.paradox.core.Loader;
import com.paradox.core.general.listeners.EventsListener;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import me.onebone.economyapi.EconomyAPI;

public class SellCommand extends Command {
	public static Config worth = Loader.getLoader().getWorthCfg();
	public static File worthFile = Loader.getLoader().getWorthFile();

	public SellCommand() {
		super("sell");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(StringUtils.getPrefix() + "Only players can use this command.");
			return false;
		}
		if (args.length == 0) {
			Player p = (Player) sender;
			sellInv(p);
		}
		return false;
	}

	public static void sellInv(Player p) {
		double total = 0;
		if (!EventsListener.playersSellBooster.containsKey(p)) {
			for (Item i : p.getInventory().getContents().values()) {
				if (canSell(i)) {
					total += (worth.getDouble("worth." + i.getId()) * getNumberOfItemInv(i, p));
					p.getInventory().remove(i);
				}
			}
			EconomyAPI.getInstance().addMoney(p, total);
			p.sendMessage(StringUtils.getPrefix() + "Sold blocks mined for $" + total + ".");
		} else {
			int multiplier = EventsListener.playersSellBooster.get(p);
			for (Item i : p.getInventory().getContents().values()) {
				if (canSell(i)) {
					total += (worth.getDouble("worth." + i.getId()) * getNumberOfItemInv(i, p));
					p.getInventory().remove(i);
				}
			}
			EconomyAPI.getInstance().addMoney(p, total*multiplier);
			double total2 = (multiplier*total);
			p.sendMessage(StringUtils.getPrefix() + "Sold blocks mined for a "+multiplier+"x boosted $" + total2 + ".");
		}
	}

	public static void sellItem(Player p, Item item) {
		if (canSell(item)) {
			if (EventsListener.playersSellBooster.containsKey(p)) {
				EconomyAPI.getInstance().addMoney(p,
						worth.getDouble("worth." + item.getId()) * EventsListener.playersSellBooster.get(p));
				p.sendActionBar(StringUtils.translateColors("&b&l(!)&r&d AutoSell Enchant and Booster "
						+ EventsListener.playersSellBooster.get(p) + "x activated!"));
			} else {
				EconomyAPI.getInstance().addMoney(p, worth.getDouble("worth." + item.getId()));
				p.sendActionBar(StringUtils.translateColors("&b&l(!)&r&d AutoSell Enchant Activated!"));
			}
		}
	}

	public static int getNumberOfItemInv(Item item, Player p) {
		int it = 0;
		for (Item i : p.getInventory().getContents().values()) {
			if (item.getId() == i.getId()) {
				it += i.getCount();
			}
		}
		return it;
	}

	public static boolean canSell(Item item) {
		for (String key : worth.getSection("worth").getKeys(false)) {
			if (item.getId() == Integer.parseInt(key)) {
				return true;
			}
		}
		return false;
	}

}
