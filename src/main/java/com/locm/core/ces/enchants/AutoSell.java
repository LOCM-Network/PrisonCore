package com.locm.core.ces.enchants;

import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.StringUtils;

public class AutoSell extends CustomEnchant {
	private int level;

	public AutoSell(int level) {
		super("AutoSell", level, 1, StringUtils.translateColors("&dAutoSell"),
				"Tự động bán! &f(Không phù phép chung với Magnet)", 30, EnchantType.CUSTOM);
		this.level = level;
	}

	public AutoSell() {
		super("AutoSell", 1, 1, StringUtils.translateColors("&dAutoSell"), "Tự động bán! &f(Không phù phép chung với Magnet)",
				30, EnchantType.CUSTOM);
	}

	public int getLevel() {
		return level;
	}
}
