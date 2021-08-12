package com.paradox.core.ces.enchants;

import com.paradox.core.ces.obj.CustomEnchant;
import com.paradox.core.ces.obj.EnchantType;
import com.paradox.core.utils.StringUtils;

public class AutoSell extends CustomEnchant {
	private int level;

	public AutoSell(int level) {
		super("AutoSell", level, 1, StringUtils.translateColors("&dAutoSell"),
				"Tự động bán!", 100000, EnchantType.CUSTOM);
		this.level = level;
	}

	public AutoSell() {
		super("AutoSell", 1, 1, StringUtils.translateColors("&dAutoSell"), "Tự động bán!",
				100000, EnchantType.CUSTOM);
	}

	public int getLevel() {
		return level;
	}
}
