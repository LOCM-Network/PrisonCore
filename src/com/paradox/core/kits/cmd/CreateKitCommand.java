package com.paradox.core.kits.cmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.paradox.core.Loader;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.Config;

public class CreateKitCommand extends Command {

	public static Config kits = Loader.getLoader().getKitsCfg();
	public static File kitsFile = Loader.getLoader().getKitsFile();

	public CreateKitCommand() {
		super("createkit");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("paradox.owner")) {
				if (args.length != 2) {
					p.sendMessage(StringUtils.translateColors("Proper usage: /createkit <name> <cooldown>"));
					return false;
				}
				if (!kits.exists("Kits." + args[0])) {
					try {
						kits.set("Kits." + args[0] + ".cooldown", Integer.parseInt(args[1]));
						int totalItems = 0;
						for (Item i : p.getInventory().getContents().values()) {
							kits.set("Kits." + args[0] + ".items." + totalItems + ".id", i.getId());
							kits.set("Kits." + args[0] + ".items." + totalItems + ".amount", i.getCount());
							kits.set("Kits." + args[0] + ".items." + totalItems + ".damage", i.getDamage());
							if (i.hasCustomName()) {
								kits.set("Kits." + args[0] + ".items." + totalItems + ".customName", i.getCustomName());
							} else {
								kits.set("Kits." + args[0] + ".items." + totalItems + ".customName", i.getName());
							}
							List<String> lore = new ArrayList<String>();
							for (int it = 0; it < i.getLore().length; it++) {
								lore.add(i.getLore()[it]);
							}
							kits.set("Kits." + args[0] + ".items." + totalItems + ".lore", lore);
							if (i.hasEnchantments()) {
								for (int i1 = 0; i1 < i.getEnchantments().length; i1++) {
									Enchantment e1 = i.getEnchantments()[i1];
									kits.set("Kits." + args[0] + ".items." + totalItems + ".enchants." + e1.getId(),
											Integer.valueOf(e1.getLevel()));
								}
							}
							totalItems++;
							kits.save(kitsFile);
						} 
						p.sendMessage(StringUtils.getPrefix() + "Created the kit " + args[0] + ".");
					} catch (NumberFormatException e) {
						p.sendMessage(StringUtils.getPrefix() + "Invalid cooldown input, must be integer.");
					}
				} else {
					p.sendMessage(StringUtils.getPrefix() + "The kit " + args[0] + " already exists.");
				}
			}
		}
		return false;
	}

}
