package com.locm.core.ces.enchants;

import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.StringUtils;

public class JackHammer extends CustomEnchant{
	private int level;

	public JackHammer(int level) {
		super("JackHammer", level, 10, StringUtils.translateColors("&eJackHammer"),
				"Đào diện rộng (Ngẫu nhiên)!", 10000, EnchantType.CUSTOM);
		this.level = level;
	}

	public JackHammer() {
		super("JackHammer", 1, 10, StringUtils.translateColors("&eJackHammer"),
				"Đào diện rộng (Ngẫu nhiên)!", 10000, EnchantType.CUSTOM);
	}

	public int getLevel() {
		return level;
	}
}
