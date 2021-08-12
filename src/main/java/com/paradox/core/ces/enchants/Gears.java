package com.paradox.core.ces.enchants;

import com.paradox.core.ces.obj.CustomEnchant;
import com.paradox.core.ces.obj.EnchantType;
import com.paradox.core.utils.StringUtils;

public class Gears extends CustomEnchant {
	private int level;

	public Gears(int level) {
		super("Gears", level, 3, StringUtils.translateColors("&3Gears"), "Nhận được hiệu ứng nhanh khi đào.", 5000,
				EnchantType.CUSTOM);
		this.level = level;
	}

	public Gears() {
		super("Gears", 1, 3, StringUtils.translateColors("&3Gears"), "Nhận được hiệu ứng nhanh khi đào.", 5000,
				EnchantType.CUSTOM);
	}

	public int getLevel() {
		return level;
	}
}
