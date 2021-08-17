package com.locm.core.ces.enchants;

import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.StringUtils;

public class Explosive extends CustomEnchant{
	private int level;

	public Explosive(int level) {
		super("Explosive", level, 15, StringUtils.translateColors("&cExplosive"), "Cơ hội gây nổ!", 20000,
				EnchantType.CUSTOM);
		this.level = level;
	}

	public Explosive() {
		super("Explosive", 1, 15, StringUtils.translateColors("&cExplosive"), "Cơ hội gây nổ!", 20000,
				EnchantType.CUSTOM);
	}

	public int getLevel() {
		return level;
	}
}
