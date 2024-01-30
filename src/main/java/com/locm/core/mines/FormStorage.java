package com.locm.core.mines;

import com.locm.core.mines.obj.Mine;
import com.locm.core.utils.MineUtils;
import com.locm.core.utils.RankUtils;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ru.contentforge.formconstructor.form.SimpleForm;

import java.util.Objects;

public class FormStorage {

	public static void sendForm(Player player, String content){
		SimpleForm form = new SimpleForm(StringUtils.color("&l&eLOCM PRISON"));	
		form.setContent(StringUtils.color(content));
		form.addButton(StringUtils.color("&l&2Làm mới khu mỏ của bạn"), (p, button) -> {
			Mine mine = MineUtils.getMineByName(RankUtils.getRankByPlayer(p).getName());
			if(mine == null) {
				p.sendMessage(StringUtils.color("&l&cLỗi: &fKhông tìm thấy khu mỏ của bạn!"));
				return;
			}
			if(mine.isSmaller(25)){
				mine.resetMine();
				sendForm(player, "&l&fĐã làm mới khu mỏ!");
			}else{
				sendForm(player, "&l&fKhu mỏ phải còn dưới &e25%%");
			}
		});
		form.addButton(StringUtils.color("&l&2Tất cả khu mỏ"), (p, button) -> {
			p.showFormWindow(MinerMenu(p));
		});
		form.addButton(StringUtils.color("&l&2Dịch chuyển tới khu mỏ"), (p, button) -> {
			Mine mine = MineUtils.getMineByName(RankUtils.getRankByPlayer(p).getName());
			if(mine == null) {
				p.sendMessage(StringUtils.color("&l&cLỗi: &fKhông tìm thấy khu mỏ của bạn!"));
				return;
			}
			p.teleport(mine.getTpLocation());
			p.sendActionBar(StringUtils.color("&l&eĐang dịch chuyển !!!"));
		});
		form.send(player);
	}

	public static FormWindowSimple MinerMenu(Player p) {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&b&lLOCM &d&lPrisons"),
				StringUtils.translateColors("&b&l&nChoose a Mine to teleport to!"));
		for (Mine mine : MineUtils.getAllMinesFromConfig()) {
			if(mine == null) {
				p.sendMessage(StringUtils.color("&l&cLỗi: &fKhông tìm thấy khu mỏ của bạn!"));
				return null;
			}
			int order = Objects.requireNonNull(RankUtils.getRankByName(mine.getMineName())).getOrder();
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
