package com.locm.core.ces.enchants;

import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.StringUtils;

public class Donator extends CustomEnchant {
	private int level;

	public Donator(int level) {
		super("Donator", level, 10, StringUtils.translateColors("&6Donator"),
				"Có cơ hội nhận X orbs toàn máy chủ!", 50, EnchantType.CUSTOM);
		this.level = level;
	}

	public Donator() {
		super("Donator", 1, 10, StringUtils.translateColors("&6Donator"), "Có cơ hội nhận X orbs toàn máy chủ!",
		50, EnchantType.CUSTOM);
	}

	public int getLevel() {
		return level;
	}
}
