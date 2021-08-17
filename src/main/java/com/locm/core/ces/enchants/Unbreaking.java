package com.locm.core.ces.enchants;

import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.StringUtils;

public class Unbreaking extends CustomEnchant {

	private int level;

	public Unbreaking(int level) {
		super("Unbreaking", level, 100, StringUtils.translateColors("&7Unbreaking"), "Make your pickaxe more durable",
				1750, EnchantType.VANILLA);
		this.level = level;
	}

	public Unbreaking() {
		super("Unbreaking", 1, 100, StringUtils.translateColors("&7Unbreaking"), "Make your pickaxe more durable", 1750,
				EnchantType.VANILLA);
	}

	public int getLevel() {
		return level;
	}

}
