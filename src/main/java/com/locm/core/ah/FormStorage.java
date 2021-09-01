package com.locm.core.ah;

import java.util.ArrayList;
import java.util.List;

import com.locm.core.Loader;
import com.locm.core.ah.obj.Listing;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;

public class FormStorage {

	public static FormWindowSimple AHMenu() {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&9&lChợ đen"),
				StringUtils.translateColors("&b&l&nHello"));
		fs.addButton(new ElementButton(StringUtils.translateColors("&bBán vật phẩm")));
		fs.addButton(new ElementButton(StringUtils.translateColors("&bXem chợ")));
		fs.addButton(new ElementButton(StringUtils.translateColors("&bThông tin kệ của bạn")));
		return fs;
	}
	
	public static FormWindowSimple getPersonalListings(Player p) {
		FormWindowSimple fc = new FormWindowSimple(StringUtils.translateColors("&9&lKệ của bạn"),
				(StringUtils.translateColors("&cChọn vật phẩm muốn tháo ra khỏi kệ!")));
		for (Listing l : ListingHandler.getListingsByPlayer(p)){
			fc.addButton(new ElementButton(StringUtils.translateColors("&8#" + l.getId()+" &8" + l.getItem().getCount() + "x &c&l"
					+ l.getItem().getName())));
		}
		return fc;
	}

	public static FormWindowSimple NoItemInHnad() {
		FormWindowSimple fc = new FormWindowSimple(StringUtils.translateColors("&9&lChợ đen"),
				(StringUtils.translateColors("&cBạn cần cầm vật phẩm trên tay!")));
		fc.addButton(new ElementButton(StringUtils.translateColors("&aTrang chính")));
		return fc;
	}

	public static FormWindowCustom sellItem(Item item) {
		FormWindowCustom fc = new FormWindowCustom(StringUtils.translateColors("&9&lBán vật phẩm"));
		fc.addElement(new ElementLabel(StringUtils.translateColors(
				"&fVật phẩm x" + item.getCount() + " &e" + item.getName())));
		fc.addElement(new ElementInput(StringUtils.translateColors("&fGía"), StringUtils.translateColors("$")));
		fc.addElement(new ElementInput(StringUtils.translateColors("&fGhi chú:"),
				StringUtils.translateColors("Mua ngay!")));
		return fc;
	}

	public static FormWindowSimple AHList() {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&9&lCác vật phẩm đang rao bán"),
				StringUtils.translateColors("Vật phẩm được xếp theo thứ tự củ -> mới"));
		for (Listing listing : ListingHandler.getAllListingsFromConfig()) {
			fs.addButton(new ElementButton(StringUtils.translateColors("&8#" + listing.getId()+" &8" + listing.getItem().getCount() + "x &c&l"
					+ listing.getItem().getName())));
		}
		return fs;
	}

	public static FormWindowCustom buyItem(Listing listing) {
		FormWindowCustom fc = new FormWindowCustom(StringUtils.translateColors("&9&lThông tin vật phẩm &r&8#" + listing.getId()));
		fc.addElement(new ElementLabel(StringUtils.translateColors("&7Bạn có chắc muốn mua?, nhập &etrue&7 để mua")));
		fc.addElement(
				new ElementInput(StringUtils.translateColors("&fInput:"), StringUtils.translateColors("type: 'true'")));
		fc.addElement(new ElementLabel(StringUtils.translateColors(
				"&b&lMua: &r&7" + listing.getItem().getCount() + "x &e" + listing.getItem().getName())));
		StringBuilder str = new StringBuilder();
		StringBuilder str2 = new StringBuilder();
		if (listing.getItem().hasEnchantments()) {
			for (Enchantment e : listing.getItem().getEnchantments()) {
				str.append(e.getName() + " " + e.getLevel() + " ");
			}
			fc.addElement(new ElementLabel(StringUtils.translateColors("&b&lVanillaEnchants: &r&7" + str)));
		}
		List<String> lor = new ArrayList<>();
		for (String s : listing.getItem().getLore()) {
			lor.add(s);
		}
		for (String s : lor) {
			str2.append(s + " ");
		}
		fc.addElement(new ElementLabel(StringUtils.translateColors("&b&lChi tiết: &r&7" + str2)));
		fc.addElement(new ElementLabel(StringUtils.translateColors("&b&lGía: &r&7$" + listing.getPricing())));
		fc.addElement(new ElementLabel(StringUtils.translateColors("&b&lNgười bán: &r&7"
				+ Loader.getLoader().getServer().getOfflinePlayer(listing.getSellerUUID()).getName())));
		fc.addElement(new ElementLabel(
				StringUtils.translateColors("&b&lGhi chú từ người bán: &r&7" + listing.getDescription())));
		return fc;
	}

}
