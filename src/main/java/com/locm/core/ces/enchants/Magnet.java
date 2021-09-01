package com.locm.core.ces.enchants;

import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.StringUtils;

public class Magnet extends CustomEnchant {

	private int level;

	public Magnet(int level) {
		super("Magnet", level, 1, StringUtils.translateColors("&fMagnet"),
				"Automatically places broken blocks into inventory!", 20000, EnchantType.CUSTOM);
		this.level = level;
	}

	public Magnet() {
		super("Magnet", 1, 1, StringUtils.translateColors("&fMagnet"),
				"Automatically places broken blocks into inventory!", 20000, EnchantType.CUSTOM);
	}

	public int getLevel() {
		return level;
	}

}
