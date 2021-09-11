package com.locm.core.ces.enchants;

import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.StringUtils;

public class Gears extends CustomEnchant {
	private int level;

	public Gears(int level) {
		super("Gears", level, 3, StringUtils.translateColors("&3Gears"), "Nhận được hiệu ứng nhanh khi đào.", 40,
				EnchantType.CUSTOM);
		this.level = level;
	}

	public Gears() {
		super("Gears", 1, 3, StringUtils.translateColors("&3Gears"), "Nhận được hiệu ứng nhanh khi đào.", 40,
				EnchantType.CUSTOM);
	}

	public int getLevel() {
		return level;
	}
}
