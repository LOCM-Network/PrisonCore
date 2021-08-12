package com.paradox.core.ces.enchants;

import com.paradox.core.ces.obj.CustomEnchant;
import com.paradox.core.ces.obj.EnchantType;
import com.paradox.core.utils.StringUtils;

public class Donator extends CustomEnchant {
	private int level;

	public Donator(int level) {
		super("Donator", level, 10, StringUtils.translateColors("&6Donator"),
				"Có cơ hội nhận X orbs toàn máy chủ!", 15000, EnchantType.CUSTOM);
		this.level = level;
	}

	public Donator() {
		super("Donator", 1, 10, StringUtils.translateColors("&6Donator"), "Có cơ hội nhận X orbs toàn máy chủ!",
				15000, EnchantType.CUSTOM);
	}

	public int getLevel() {
		return level;
	}
}
