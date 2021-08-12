package com.paradox.core.ces.forms;

import com.paradox.core.ces.enchants.EnchantHandler;
import com.paradox.core.ces.obj.CustomEnchant;
import com.paradox.core.utils.CEUtils;
import com.paradox.core.utils.NumberUtils;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;

public class FormStorage {
	public static FormWindowSimple enchanterMenu() {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&b&lLocm &d&lPrisons"),
				StringUtils.translateColors("&b&l&nChoose a CE to purchase!"));
		for (CustomEnchant ce : EnchantHandler.getAllEnchants()) {
			fs.addButton(new ElementButton(ce.getDisplayNameOfEnchantment()));
		}
		return fs;
	}

	public static FormWindowCustom ceMenu(Item item, CustomEnchant ce) {
		if (CEUtils.containsEnchantment(item, ce)) {
			int level = CEUtils.getLevelOfEnchantByDisplayName(ce.getDisplayNameOfEnchantment(), item);
			FormWindowCustom fs = new FormWindowCustom(
					StringUtils.translateColors(ce.getDisplayNameOfEnchantment() + " &r&b&nEnchantment&d&n Purchase"));
			fs.addElement(new ElementLabel(StringUtils.translateColors("&7Description: " + ce.getDescription())));
			fs.addElement(new ElementSlider(StringUtils.translateColors("&bLevel"), level, ce.getMaxLevelOfEnchantment(), 1));
			return fs;
		}
		FormWindowCustom fs = new FormWindowCustom(
				StringUtils.translateColors(ce.getDisplayNameOfEnchantment() + " &r&b&nEnchantment&d&n Purchase"));
		fs.addElement(new ElementLabel(StringUtils.translateColors("&7Description: " + ce.getDescription())));
		fs.addElement(new ElementSlider(StringUtils.translateColors("&bLevel"), 0F, ce.getMaxLevelOfEnchantment(), 1));
		return fs;
	}

	public static FormWindowSimple confirmMenu(int lvl, CustomEnchant ce) {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&b&lConfirm Purchase?"), StringUtils.translateColors("&7Cost in orbs: "+NumberUtils.getCostOfEnchantmentByLevel(lvl, ce.getCostMultiplier())));
		fs.addButton(new ElementButton(StringUtils.translateColors("&8Accept")));
		fs.addButton(new ElementButton(StringUtils.translateColors("&8Deny")));
		return fs;
	}
}
