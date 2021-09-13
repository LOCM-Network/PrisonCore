package com.locm.core.ces.enchants;

import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.StringUtils;

import cn.nukkit.item.enchantment.Enchantment;

public class Fortune extends CustomEnchant {
	private int level;

	public Fortune(int level) {
		super("Fortune", level, 10, StringUtils.translateColors("&7Fortune"), "Có cơ hội nhận thêm vật phẩm rớt ra khi đào!",
			25000, EnchantType.VANILLA);
		this.level = level;
	}

	public Fortune() {
		super("Fortune", 1, 10, StringUtils.translateColors("&7Fortune"), "Có cơ hội nhận thêm vật phẩm rớt ra khi đào!",
			25000, EnchantType.VANILLA);	}

	public int getLevel() {
		return level;
	}

	public int getId(){
		return Enchantment.ID_FORTUNE_DIGGING;
	}
}
