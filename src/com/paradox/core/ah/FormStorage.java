package com.paradox.core.ah;

import java.util.ArrayList;
import java.util.List;

import com.paradox.core.Loader;
import com.paradox.core.ah.obj.Listing;
import com.paradox.core.utils.StringUtils;

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
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&9&lParadox Auctions"),
				StringUtils.translateColors("&b&l&nChoose an option below!"));
		fs.addButton(new ElementButton(StringUtils.translateColors("&bSell Item")));
		fs.addButton(new ElementButton(StringUtils.translateColors("&bView Listings")));
		fs.addButton(new ElementButton(StringUtils.translateColors("&bCheck Your Auction Info")));
		return fs;
	}
	
	public static FormWindowSimple getPersonalListings(Player p) {
		FormWindowSimple fc = new FormWindowSimple(StringUtils.translateColors("&9&lYour Listings"),
				(StringUtils.translateColors("&cChoose an item to remove from the ah!")));
		for (Listing l : ListingHandler.getListingsByPlayer(p)){
			fc.addButton(new ElementButton(StringUtils.translateColors("&8#" + l.getId()+" &8" + l.getItem().getCount() + "x &c&l"
					+ l.getItem().getName())));
		}
		return fc;
	}

	public static FormWindowSimple NoItemInHnad() {
		FormWindowSimple fc = new FormWindowSimple(StringUtils.translateColors("&9&lParadox Auctions"),
				(StringUtils.translateColors("&cHold an item first!")));
		fc.addButton(new ElementButton(StringUtils.translateColors("&aMain menu")));
		return fc;
	}

	public static FormWindowCustom sellItem(Item item) {
		FormWindowCustom fc = new FormWindowCustom(StringUtils.translateColors("&9&lAh Sell"));
		fc.addElement(new ElementLabel(StringUtils.translateColors(
				"&fSelling your x" + item.getCount() + " &e" + item.getName() + (" &ffor how much? "))));
		fc.addElement(new ElementInput(StringUtils.translateColors("&fPrice"), StringUtils.translateColors("$")));
		fc.addElement(new ElementInput(StringUtils.translateColors("&fSeller Notes (optional):"),
				StringUtils.translateColors("Buy now, great value!")));
		return fc;
	}

	public static FormWindowSimple AHList() {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&9&lAH List"),
				StringUtils.translateColors("&7All auctions listed here oldest->newest."));
		for (Listing listing : ListingHandler.getAllListingsFromConfig()) {
			fs.addButton(new ElementButton(StringUtils.translateColors("&8#" + listing.getId()+" &8" + listing.getItem().getCount() + "x &c&l"
					+ listing.getItem().getName())));
		}
		return fs;
	}

	public static FormWindowCustom buyItem(Listing listing) {
		FormWindowCustom fc = new FormWindowCustom(StringUtils.translateColors("&9&lAh Info &r&8#" + listing.getId()));
		fc.addElement(new ElementLabel(StringUtils.translateColors("&7Type true to confirm purchase!")));
		fc.addElement(
				new ElementInput(StringUtils.translateColors("&fInput:"), StringUtils.translateColors("type: 'true'")));
		fc.addElement(new ElementLabel(StringUtils.translateColors(
				"&b&lBuying: &r&7" + listing.getItem().getCount() + "x &e" + listing.getItem().getName())));
		StringBuilder str = new StringBuilder();
		StringBuilder str2 = new StringBuilder();
		if (listing.getItem().hasEnchantments()) {
			for (Enchantment e : listing.getItem().getEnchantments()) {
				str.append(e.getName() + " " + e.getLevel() + " ");
			}
			fc.addElement(new ElementLabel(StringUtils.translateColors("&b&lVanillaEnchants: &r&7" + str.toString())));
		}
		List<String> lor = new ArrayList<>();
		for (String s : listing.getItem().getLore()) {
			lor.add(s);
		}
		for (String s : lor) {
			str2.append(s + " ");
		}
		fc.addElement(new ElementLabel(StringUtils.translateColors("&b&lLore: &r&7" + str2.toString())));
		fc.addElement(new ElementLabel(StringUtils.translateColors("&b&lPrice: &r&7$" + listing.getPricing())));
		fc.addElement(new ElementLabel(StringUtils.translateColors("&b&lSeller: &r&7"
				+ Loader.getLoader().getServer().getOfflinePlayer(listing.getSellerUUID()).getName())));
		fc.addElement(new ElementLabel(
				StringUtils.translateColors("&b&lSeller Description: &r&7" + listing.getDescription())));
		return fc;
	}

}
