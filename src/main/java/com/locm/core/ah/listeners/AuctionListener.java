package com.locm.core.ah.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.locm.core.Loader;
import com.locm.core.ah.FormStorage;
import com.locm.core.ah.ListingHandler;
import com.locm.core.ah.obj.Listing;
import com.locm.core.utils.StringUtils;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.Config;
import me.onebone.economyapi.EconomyAPI;

public class AuctionListener implements Listener {
	public static Config dataCfg = Loader.getLoader().getDataCfg();
	public static File dataFile = Loader.getLoader().getDataFile();

	@EventHandler
	public void onResponse(PlayerFormRespondedEvent e) {
		Player p = e.getPlayer();
		Item i = e.getPlayer().getInventory().getItemInHand();
		if (e.getWindow() != null) {
			if (e.getWindow() instanceof FormWindowSimple) {
				FormWindowSimple gui = (FormWindowSimple) e.getWindow();
				if (gui != null) {
					if (gui.getTitle().equals(StringUtils.translateColors("&9&lChợ đen"))) {
						if (gui.getResponse().getClickedButton().getText() != null) {
							String responseName = gui.getResponse().getClickedButton().getText();
							if (responseName != null) {
								if (responseName.equals(StringUtils.translateColors("&bBán vật phẩm"))) {
									p.removeAllWindows();
									if (i == null || i.getId() == 0) {
										p.showFormWindow(FormStorage.NoItemInHnad());
									} else {
										p.showFormWindow(FormStorage.sellItem(i));
									}
								} else if (responseName.equals(StringUtils.translateColors("&aTrang chính"))) {
									p.removeAllWindows();
									p.showFormWindow(FormStorage.AHMenu());
								} else if (responseName.equals(StringUtils.translateColors("&b&bXem chợ"))) {
									p.removeAllWindows();
									p.showFormWindow(FormStorage.AHList());
								} else if (responseName
										.equals(StringUtils.translateColors("&bThông tin kệ của bạn"))) {
									p.removeAllWindows();
									p.showFormWindow(FormStorage.getPersonalListings(p));
								}
							}
						}
					} else if (gui.getTitle().equals(StringUtils.translateColors("&9&lKệ của bạn"))) {
						if (gui.getResponse().getClickedButton().getText() != null) {
							String responseName = gui.getResponse().getClickedButton().getText();
							if (responseName != null) {
								for (Listing l : ListingHandler.getAllListingsFromConfig()) {
									if (responseName.contains(StringUtils.translateColors("&8#" + l.getId()))) {
										ListingHandler.removeListingFromConfig(l.getId() + "");
										p.sendMessage(StringUtils.getPrefix()
												+ "Vật phẩm đã bị xóa khỏi chợ");
										p.getInventory().addItem(l.getItem());
									}
								}
							}
						}
					}
				}
			} else if (e.getWindow() instanceof FormWindowCustom) {
				FormWindowCustom gui = (FormWindowCustom) e.getWindow();
				if (gui.getTitle().equals(StringUtils.translateColors("&9&lBán vật phẩm"))) {
					if (e.getResponse() != null) {
						try {
							Item item = p.getInventory().getItemInHand();
							if (i == null || i.getId() == 0) {
								p.removeAllWindows();
								p.showFormWindow(FormStorage.NoItemInHnad());
								return;
							}
							if (getTotalListingsByPlayer(p) > 1) {
								p.removeAllWindows();
								p.sendMessage(
										StringUtils.getPrefix() + "Bạn đang treo 1 vật phẩm rồi.");
								return;
							}
							int price = Integer.parseInt(gui.getResponse().getInputResponse(1));
							if (price >= 100) {
								int id = getUniqueNumber();
								String desc = gui.getResponse().getInputResponse(2);
								dataCfg.set("Listings." + id + ".sellerUUID", p.getUniqueId().toString());
								dataCfg.set("Listings." + id + ".price", price);
								dataCfg.set("Listings." + id + ".description", desc);
								dataCfg.set("Listings." + id + ".item.id", item.getId());
								dataCfg.set("Listings." + id + ".item.amount", item.getCount());
								dataCfg.set("Listings." + id + ".item.damage", item.getDamage());
								if (item.hasCustomName()) {
									dataCfg.set("Listings." + id + ".item.customName", item.getCustomName());
								} else {
									dataCfg.set("Listings." + id + ".item.customName", item.getName());
								}
								List<String> lore = new ArrayList<String>();
								for (int it = 0; it < item.getLore().length; it++) {
									lore.add(item.getLore()[it]);
								}
								dataCfg.set("Listings." + id + ".item.lore", lore);
								if (item.hasEnchantments()) {
									for (int i1 = 0; i1 < item.getEnchantments().length; i1++) {
										Enchantment e1 = item.getEnchantments()[i1];
										dataCfg.set("Listings." + id + ".item.enchants." + e1.getId(),
												Integer.valueOf(e1.getLevel()));
									}
								}
								dataCfg.save(dataFile);
								p.sendMessage(StringUtils.getPrefix() + StringUtils.translateColors(
										"&7Treo thành công " + i.getCount() + "x " + i.getName() + "."));
								p.getInventory().remove(item);

							} else {
								p.removeAllWindows();
								p.sendMessage((StringUtils.getPrefix() + "Giá của vật phẩm phải trên 100 xu"));
							}
						} catch (NumberFormatException err) {
							p.removeAllWindows();
							p.sendMessage(StringUtils.getPrefix() + "Vui lòng nhập giá.");
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onResponse2(PlayerFormRespondedEvent e) {
		Player p = e.getPlayer();
		if (e.getWindow() != null) {
			if (e.getWindow() instanceof FormWindowSimple) {
				FormWindowSimple gui = (FormWindowSimple) e.getWindow();
				if (gui != null) {
					if (gui.getTitle().equals(StringUtils.translateColors("&9&lCác vật phẩm đang rao bán"))) {
						if (gui.getResponse().getClickedButton().getText() != null) {
							String responseName = gui.getResponse().getClickedButton().getText();
							if (responseName != null) {
								for (Listing l : ListingHandler.getAllListingsFromConfig()) {
									if (l != null) {
										if (responseName.contains(StringUtils.translateColors("&8#" + l.getId()))) {
											p.removeAllWindows();
											p.showFormWindow(FormStorage.buyItem(l));
											break;
										}
									}
								}
							}
						}
					}
				}
			} else if (e.getWindow() instanceof FormWindowCustom) {
				FormWindowCustom gui = (FormWindowCustom) e.getWindow();
				for (Listing l : ListingHandler.getAllListingsFromConfig()) {
					if (gui != null) {
						if (gui.getTitle().equals(StringUtils.translateColors("&9&lThông tin vật phẩm &r&8#" + l.getId()))) {
							String response = gui.getResponse().getInputResponse(1);
							if (response.equals("true")) {
								Player target = Loader.getLoader().getServer().getPlayer(l.getSellerUUID()).get();
								if (target.getName().equals(p.getName())) {
									p.removeAllWindows();
									p.sendMessage(StringUtils.getPrefix() + "Bạn không thể mua vật phẩm của chính mình.");
									return;
								}
								if (EconomyAPI.getInstance().myMoney(p) >= l.getPricing()) {
									p.getInventory().addItem(l.getItem());
									p.sendMessage(StringUtils.getPrefix() + "Bạn đã mua " + target.getName()
											+ " với giá " + l.getPricing() + " xu");
									IPlayer t = Loader.getLoader().getServer().getOfflinePlayer(l.getSellerUUID());
									EconomyAPI.getInstance().reduceMoney(p, l.getPricing());
									EconomyAPI.getInstance().addMoney(t, l.getPricing());
									ListingHandler.removeListingFromConfig(l.getId() + "");
									return;
								} else {
									p.sendMessage(StringUtils.getPrefix()
											+ "Bạn không đủ tiền để mua.");
								}
							}
						}
					}
				}
			}
		}
	}

	public int getUniqueNumber() {
		Random r = new Random();
		int id = r.nextInt(10000000);
		if (!dataCfg.exists("Listings." + id)) {
			return id;
		}
		return getUniqueNumber();
	}

	public int getTotalListingsByPlayer(Player p) {
		int total = 0;
		for (String key : dataCfg.getSections("Listings").getKeys(false)) {
			if (dataCfg.getString("Listings." + key + ".sellerUUID").equals(p.getUniqueId().toString())) {
				total++;
			}
		}
		return total;
	}

}
