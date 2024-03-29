package com.locm.core.general.cmd;

import com.locm.core.Loader;
import com.locm.core.general.listeners.EventsListener;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import me.onebone.economyapi.EconomyAPI;

public class SellCommand extends Command {

	public static Config worth = Loader.getLoader().getWorthCfg();
	//public static File worthFile = Loader.getLoader().getWorthFile();

	public SellCommand() {
		super("sell", "Sell all mine item");
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
					String id = String.valueOf(i.getId());
					if(i.getDamage() != 0){
						id += ":" + i.getDamage();
					}
					total += (worth.getDouble("worth." + id) * getNumberOfItemInv(i, p));
					p.getInventory().remove(i);
				}
			}
			double sell = Loader.getInstance().getConfig().getDouble("sell") * total;
			EconomyAPI.getInstance().addMoney(p, sell);
			p.sendMessage(TextFormat.colorize("&l&fBán các tài nguyên nhận được &e" + sell + "&f xu."));
		} else {
			int multiplier = EventsListener.playersSellBooster.get(p);
			for (Item i : p.getInventory().getContents().values()) {
				if (canSell(i)) {
					String id = String.valueOf(i.getId());
					if(i.getDamage() != 0){
						id += ":" + i.getDamage();
					}
					total += (worth.getDouble("worth." + id) * getNumberOfItemInv(i, p));
					p.getInventory().remove(i);
				}
			}
			double sell = (int)Loader.getInstance().getConfig().getDouble("sell")  * multiplier * total;
			EconomyAPI.getInstance().addMoney(p, sell);
			//double total2 = (multiplier*total);
			p.sendMessage(TextFormat.colorize("&l&fBán các tài nguyên nhận được &e" + sell + "&f xu &7(&fBạn đang có &ex"+multiplier+"&f)"));
		}
	}

	public static void sellItem(Player p, Item item) {
		if (canSell(item)) {
			if (EventsListener.playersSellBooster.containsKey(p)) {
				EconomyAPI.getInstance().addMoney(p,
						worth.getDouble("worth." + item.getId()) * EventsListener.playersSellBooster.get(p));
				p.sendActionBar(StringUtils.translateColors("&b&l(!)&r&d AutoSell Enchant và Booster "
						+ EventsListener.playersSellBooster.get(p) + "x kích hoạt!"));
			} else {
				EconomyAPI.getInstance().addMoney(p, worth.getDouble("worth." + item.getId()));
				p.sendActionBar(StringUtils.translateColors("&b&l(!)&r&d AutoSell Enchant Đã Kích Hoạt!"));
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
			String[] parts = key.split(":");
			if (parts.length == 2) {
				int id = Integer.parseInt(parts[0]);
				int meta =  Integer.parseInt(parts[1]);
				if (item.getId() == id && item.getDamage() == meta) {
				return true;
				}
			}else if (item.getId() == Integer.parseInt(key)) {
				return true;
			}
		}
		return false;
	}

}
