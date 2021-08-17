package com.locm.core.kits;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.locm.core.Loader;
import com.locm.core.ah.ListingHandler;
import com.locm.core.kits.obj.Kit;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.Config;

public class KitHandler {
	public static Config kits1 = Loader.getLoader().getKitsCfg();
	public static File kitsFile = Loader.getLoader().getKitsFile();
	public static Config playerscfg = Loader.getLoader().getPlayerCfg();
	public static File playersFile = Loader.getLoader().getPlayersFile();

	public static void processCooldowns() {
		for (String s : getPlayersInCooldown()) {
			for (String key : playerscfg.getSection("Players." + s + ".cooldown").getKeys(false)) {
				if (playerscfg.get(("Players." + s + ".cooldown." + key)) != null) {
					if (playerscfg.getInt(("Players." + s + ".cooldown." + key)) <= 0) {
						playerscfg.set("Players." + s + ".cooldown." + key, null);
						playerscfg.save(playersFile);
						return;
					}
					playerscfg.set(("Players." + s + ".cooldown." + key),
							playerscfg.getInt(("Players." + s + ".cooldown." + key)) - 1);
					playerscfg.save(playersFile);
				}
			}
		}
	}

	public static List<String> getPlayersInCooldown() {
		List<String> players = new ArrayList<>();
		for (String key : playerscfg.getSection("Players").getKeys(false)) {
			for (String key2 : playerscfg.getSection("Players." + key + ".cooldown").getKeys(false)) {
				if (playerscfg.exists("Players." + key + ".cooldown")) {
					if (playerscfg.get(("Players." + key + ".cooldown." + key2)) != null) {
						players.add(key);
					}
				}
			}
		}
		return players;
	}

	public static List<Kit> getAllKitsFromConfig() {
		List<Kit> kits = new ArrayList<Kit>();
		for (String key : kits1.getSection("Kits").getKeys(false)) {
			String name = key;
			int cooldown = kits1.getInt("Kits." + key + ".cooldown");
			List<Item> items = new ArrayList<>();
			for (String key2 : kits1.getSection("Kits." + key + ".items").getKeys(false)) {
				Item i = new Item(kits1.getInt("Kits." + key + ".items." + key2 + ".id"));
				i.setCount(kits1.getInt("Kits." + key + ".items." + key2 + ".amount"));
				i.setDamage(kits1.getInt("Kits." + key + ".items." + key2 + ".damage"));
				i.setCustomName(kits1.getString("Kits." + key + ".items." + key2 + ".customName"));
				List<String> lore = kits1.getStringList("Kits." + key + ".items." + key2 + ".lore");
				List<String> newLore = new ArrayList<String>();
				for (String s : lore) {
					newLore.add(s);
				}
				String[] itemLore = (String[]) newLore.toArray(new String[0]);
				if (!ListingHandler.isEmptyStringArray(itemLore)) {
					i.setLore(itemLore);
				}
				for (String enchId : kits1.getSection("Kits." + key + ".items." + key2 + ".enchants").getKeys(false)) {
					int id = Integer.parseInt(enchId);
					int lvl = kits1.getInt("Kits." + key + ".items." + key2 + ".enchants." + enchId);
					i.addEnchantment(new Enchantment[] { Enchantment.getEnchantment(id).setLevel(lvl, false) });
				}
				items.add(i);
			}
			Kit kit = new Kit(name, cooldown, items);
			kits.add(kit);
		}
		return kits;
	}

}
