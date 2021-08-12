package com.paradox.core.mines;

import com.paradox.core.mines.obj.Mine;
import com.paradox.core.utils.MineUtils;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;

public class FormStorage {

	public static FormWindowSimple MinerMenu() {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&b&lParadox &d&lPrisons"),
				StringUtils.translateColors("&b&l&nChoose a Mine to teleport to!"));
		for (Mine mine : MineUtils.getAllMinesFromConfig()) {
			fs.addButton(new ElementButton(StringUtils.translateColors("&a") + mine.getMineName()));
		}
		return fs;
	}

}
