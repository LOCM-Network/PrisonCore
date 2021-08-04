package com.paradox.core.ces.enchants;

import com.paradox.core.ces.obj.CustomEnchant;
import com.paradox.core.ces.obj.EnchantType;
import com.paradox.core.utils.StringUtils;

public class Efficiency extends CustomEnchant {

	private int level;

	public Efficiency(int level) {
		super("Efficiency", level, 100, StringUtils.translateColors("&7Efficiency"), "Make your pickaxe mine faster!",
				2000, EnchantType.VANILLA);
		this.level = level;
	}

	public Efficiency() {
		super("Efficiency", 1, 100, StringUtils.translateColors("&7Efficiency"), "Make your pickaxe mine faster!",
				2000, EnchantType.VANILLA);	}

	public int getLevel() {
		return level;
	}
}
