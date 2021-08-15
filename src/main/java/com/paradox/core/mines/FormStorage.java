package com.paradox.core.mines;

import com.paradox.core.mines.obj.Mine;
import com.paradox.core.utils.MineUtils;
import com.paradox.core.utils.RankUtils;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;

public class FormStorage {

	public static FormWindowSimple MinerMenu(Player p) {
		String prank = RankUtils.getRankByPlayer().getName(); 
		String minestring = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&b&lLOCM &d&lPrisons"),
				StringUtils.translateColors("&b&l&nChoose a Mine to teleport to!"));
		for (Mine mine : MineUtils.getAllMinesFromConfig()) {
			if(minestring.indexOf(prank) >= minestring.indexOf(mine.getMineName())) {
				fs.addButton(new ElementButton(StringUtils.color("&a" + mine.getMineName() + "\n &0ＯＰＥＮ")));
			} else {
				fs.addButton(new ElementButton(StringUtils.color("&c" + mine.getMineName() + "\n &0ＬＯＣＫ")));
			}
		}
		return fs;
	}

}
