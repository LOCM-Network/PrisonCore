package com.locm.core.mines;

import com.locm.core.mines.obj.Mine;
import com.locm.core.utils.MineUtils;
import com.locm.core.utils.RankUtils;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;

public class FormStorage {

	public static FormWindowSimple MinerMenu(Player p) {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&b&lLOCM &d&lPrisons"),
				StringUtils.translateColors("&b&l&nChoose a Mine to teleport to!"));
		for (Mine mine : MineUtils.getAllMinesFromConfig()) {
			int order = RankUtils.getRankByName(mine.getMineName()).getOrder();
			int porder = RankUtils.getRankByPlayer(p).getOrder();
			if(porder >= order) {
				fs.addButton(new ElementButton(StringUtils.color("&l&0" + mine.getMineName() + "\n &aＯＰＥＮ")));
			} else {
				fs.addButton(new ElementButton(StringUtils.color("&l&0" + mine.getMineName() + "\n &cＬＯＣＫ")));
			}
		}
		return fs;
	}

}
