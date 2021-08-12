package com.paradox.core.ces.enchants;

import com.paradox.core.ces.obj.CustomEnchant;
import com.paradox.core.ces.obj.EnchantType;
import com.paradox.core.utils.StringUtils;

public class Fortune extends CustomEnchant {
	private int level;

	public Fortune(int level) {
		super("Fortune", level, 10, StringUtils.translateColors("&7Fortune"), "Chance to give more drops for mining!",
			15000, EnchantType.VANILLA);
		this.level = level;
	}

	public Fortune() {
		super("Fortune", 1, 10, StringUtils.translateColors("&7Fortune"), "Chance to give more drops for mining!",
				15000, EnchantType.VANILLA);	}

	public int getLevel() {
		return level;
	}
}
