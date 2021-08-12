package com.paradox.core.ces.enchants;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ObjectArrays;
import com.paradox.core.ces.obj.CustomEnchant;
import com.paradox.core.utils.CEUtils;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;

public class EnchantHandler {

	public static List<CustomEnchant> getAllEnchants() {
		List<CustomEnchant> enchants = new ArrayList<CustomEnchant>();
		enchants.add(new Unbreaking());
		enchants.add(new Efficiency());
		enchants.add(new Fortune());
		enchants.add(new Magnet());
		enchants.add(new Greed());
		enchants.add(new AutoSell());
		enchants.add(new Donator());
		enchants.add(new Explosive());
		enchants.add(new JackHammer());
		enchants.add(new Gears());
		return enchants;
	}

	public static List<CustomEnchant> getAllVanillaEnchants() {
		List<CustomEnchant> enchants = new ArrayList<CustomEnchant>();
		enchants.add(new Unbreaking());
		enchants.add(new Efficiency());
		enchants.add(new Fortune());
		return enchants;
	}

	public static void applyEnchant(Player p, Item item, Enchantment e, int lvl) {
		if (item.getId() == 278) {
			p.getInventory().removeItem(item);
			item.addEnchantment(e.setLevel(lvl, false));
			p.getInventory().addItem(item);
		}
	}

	public static void applyEnchantment(Player p, Item item, CustomEnchant ce, int lvl) {
		if (item.getId() == 278) {
			if (!CEUtils.containsEnchantment(item, ce)) {
				p.getInventory().remove(item);
				String[] lore = item.getLore();
				String[] lore2 = new String[] { ce.getDisplayNameOfEnchantment() + " " + lvl };
				item.setLore(ObjectArrays.concat(lore, lore2, String.class));
				p.getInventory().addItem(item);
			} else {
				for (int i = 0; i < item.getLore().length; i++) {
					if (item.getLore()[i].contains(ce.getDisplayNameOfEnchantment())) {
						p.getInventory().remove(item);
						String lore2 = ce.getDisplayNameOfEnchantment() + " " + lvl;
						String[] lore = item.getLore();
						lore[i] = lore2;
						item.setLore(lore);
						p.getInventory().addItem(item);
					}
				}
			}
		}
	}
}