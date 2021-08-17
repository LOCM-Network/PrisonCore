package com.locm.core.ces.enchants;

import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.StringUtils;

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
