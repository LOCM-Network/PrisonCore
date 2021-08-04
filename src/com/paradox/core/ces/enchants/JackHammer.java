package com.paradox.core.ces.enchants;

import com.paradox.core.ces.obj.CustomEnchant;
import com.paradox.core.ces.obj.EnchantType;
import com.paradox.core.utils.StringUtils;

public class JackHammer extends CustomEnchant{
	private int level;

	public JackHammer(int level) {
		super("JackHammer", level, 10, StringUtils.translateColors("&eJackHammer"),
				"Chance to eliminate an entire layer of a mine!", 10000, EnchantType.CUSTOM);
		this.level = level;
	}

	public JackHammer() {
		super("JackHammer", 1, 10, StringUtils.translateColors("&eJackHammer"),
				"Chance to eliminate an entire layer of a mine!", 10000, EnchantType.CUSTOM);
	}

	public int getLevel() {
		return level;
	}
}
