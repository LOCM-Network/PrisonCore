package com.paradox.core.kits;

import com.paradox.core.kits.obj.Kit;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;

public class FormStorage {
	public static FormWindowSimple kitMenu() {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&9&lLocm Kits"),
				StringUtils.translateColors("&b&l&nChoose a kit below!"));
		for (Kit kit : KitHandler.getAllKitsFromConfig()) {
			fs.addButton(new ElementButton(StringUtils.translateColors("&b") + kit.getName()));
		}
		return fs;
	}

	public static FormWindowSimple kitGet(Kit kit) {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&9&l" + kit.getName()),
				StringUtils.translateColors("&b&l&nRedeem kit " + kit.getName() + "?"));
		fs.addButton(new ElementButton(StringUtils.translateColors("&aAccept!")));
		fs.addButton(new ElementButton(StringUtils.translateColors("&cDeny!")));
		return fs;
	}
}
