package com.locm.core.ces.enchants;

import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.StringUtils;

public class Greed extends CustomEnchant {

	private int level;

	public Greed(int level) {
		super("Greed", level, 100, StringUtils.translateColors("&aGreed"), "Nhận thêm orbs khi đào!", 60,
				EnchantType.CUSTOM);
		this.level = level;
	}

	public Greed() {
		super("Greed", 1, 100, StringUtils.translateColors("&aGreed"), "Nhận thêm orbs khi đào!", 60,
				EnchantType.CUSTOM);
	}

	public int getLevel() {
		return level;
	}

}
