package com.locm.core.utils;


import com.locm.core.ces.enchants.EnchantHandler;
import com.locm.core.ces.obj.CustomEnchant;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.TextFormat;

public class CEUtils {
	
	public static boolean isHigherEnchantLevel(Item item, Enchantment e) {
		for (Enchantment t : item.getEnchantments()) {
			if (t.getId()==e.getId()) {
				if (t.getLevel()<e.getLevel()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean containsEnchantment(Item item, Enchantment e) {
		for (Enchantment t : item.getEnchantments()) {
			if (t.getId()==e.getId()) {
				return true;
			}
		}
		return false;
	}

	public static CustomEnchant getCEByDisplayName(String displayName) throws NullPointerException {
		for (CustomEnchant ce : EnchantHandler.getAllEnchants()) {
			if (ce.getDisplayNameOfEnchantment().equals(displayName)) {
				return ce;
			}
		}
		return null;
	}

	public static int getLevelOfEnchantByDisplayName(String name, Item item) {
		if (item.getLore().length > 0) {
			for (int i = 0; i < item.getLore().length; i++) {
				if (item.getLore()[i].contains(name)) {
					return Integer.parseInt(TextFormat.clean(item.getLore()[i]).replaceAll("[^\\d.]", ""));
				}
			}
		}
		return 0;
	}

	public static boolean containsEnchantment(Item item, CustomEnchant ce) {
        return StringUtils.stringContainsItemFromList(StringUtils.translateColors(ce.getDisplayNameOfEnchantment()), item.getLore());
    }

}
