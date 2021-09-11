package com.locm.core.ces.enchants;

import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.StringUtils;

public class Efficiency extends CustomEnchant {

	private int level;

	public Efficiency(int level) {
		super("Efficiency", level, 100, StringUtils.translateColors("&7Efficiency"), "Make your pickaxe mine faster!",
				4000, EnchantType.VANILLA);
		this.level = level;
	}

	public Efficiency() {
		super("Efficiency", 1, 100, StringUtils.translateColors("&7Efficiency"), "Make your pickaxe mine faster!",
				4000, EnchantType.VANILLA);	}

	public int getLevel() {
		return level;
	}
}
