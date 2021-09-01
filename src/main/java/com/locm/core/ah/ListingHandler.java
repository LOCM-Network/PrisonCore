package com.locm.core.ah;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.locm.core.Loader;
import com.locm.core.ah.obj.Listing;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.Config;

public class ListingHandler {
	public static Config dataCfg = Loader.getLoader().getDataCfg();
	public static File dataFile = Loader.getLoader().getDataFile();

	public static void removeListingFromConfig(String id) {
		dataCfg.set("Listings." + id, null);
		dataCfg.save(dataFile);
	}

	public static List<Listing> getListingsByPlayer(Player p) {
		List<Listing> listings = new ArrayList<>();
		for (Listing l : getAllListingsFromConfig()) {
			if (p.getUniqueId().toString().equals(l.getSellerUUID().toString())) {
				listings.add(l);
			}
		}
		return listings;
	}

	public static List<Listing> getAllListingsFromConfig() {
		List<Listing> listings = new ArrayList<>();
		for (String key : dataCfg.getSections("Listings").getKeys(false)) {
			UUID uuid = UUID.fromString(dataCfg.getString("Listings." + key + ".sellerUUID"));
			int price = dataCfg.getInt("Listings." + key + ".price");
			String desc = dataCfg.getString("Listings." + key + ".description");
			Item item = new Item(dataCfg.getInt("Listings." + key + ".item.id"));
			item.setCount(dataCfg.getInt(("Listings." + key + ".item.amount")));
			item.setDamage(dataCfg.getInt(("Listings." + key + ".item.damage")));
			if (dataCfg.exists("Listings." + key + ".item.customName")) {
				item.setCustomName(dataCfg.getString("Listings." + key + ".item.customName"));
			}
			List<String> lore = dataCfg.getStringList("Listings." + key + ".item.lore");
			List<String> newLore = new ArrayList<String>();
			for (String s : lore) {
				newLore.add(s);
			}
			String[] itemLore = newLore.toArray(new String[0]);
			if (!isEmptyStringArray(itemLore)) {
				item.setLore(itemLore);
			}
			for (String enchId : dataCfg.getSection("Listings." + key + ".item.enchants").getKeys(false)) {
				int id = Integer.parseInt(enchId);
				int lvl = dataCfg.getInt("Listings." + key + ".item.enchants." + enchId);
				item.addEnchantment(Enchantment.getEnchantment(id).setLevel(lvl, false));
			}
			Listing listing = new Listing(item, price, uuid, desc, Integer.parseInt(key));
			listings.add(listing);
		}
		return listings;
	}

	public static boolean isEmptyStringArray(String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				return false;
			}
		}
		return true;
	}
}
