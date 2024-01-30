package com.locm.core.ces.forms;

import java.util.Arrays;
import java.util.List;

import com.locm.core.Loader;
import com.locm.core.ces.enchants.EnchantHandler;
import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.utils.CEUtils;
import com.locm.core.utils.NumberUtils;
import com.locm.core.utils.OrbEconomyUtils;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.TextFormat;
import me.locm.economyapi.EconomyAPI;
import ru.contentforge.formconstructor.form.CustomForm;
import ru.contentforge.formconstructor.form.ModalForm;
import ru.contentforge.formconstructor.form.SimpleForm;
import ru.contentforge.formconstructor.form.element.SelectableElement;
import ru.contentforge.formconstructor.form.element.StepSlider;

public class FormStorage {

	public void sendEnchantForm(Player player){
		SimpleForm form = new SimpleForm(StringUtils.translateColors("&l&eENCHANT SHOP"));
		form.addButton(StringUtils.translateColors("&l&f●&0 Orbs &l&f●&0"), (p, button) -> this.sendEnchantXu(player));

		form.addButton(StringUtils.translateColors("&l&f●&0 LCoin &l&f●&0"), (p, button) -> this.sendEnchantLCoin(player));
		form.addButton(StringUtils.color("&l&f●&c Lưu ý Trước khi phù phép &l&f●&0"), (p, button) -> noticeForm(p));
		form.send(player);
	}

	public void noticeForm(Player player){
		CustomForm form = new CustomForm(TextFormat.colorize("&l&4Lưu ý"));
		List<String> notice = Loader.getInstance().getConfig().getStringList("notice-enchantment");
		for(String note : notice){
			form.addElement(StringUtils.color(note));
		}
		form.setHandler((p, respones) -> this.sendEnchantForm(player));
		form.send(player);
	}

	public void sendEnchantXu(Player player){
		SimpleForm form = new SimpleForm(StringUtils.translateColors("&l&eEnchant Orbs"));
		for(CustomEnchant enchant : EnchantHandler.getAllEnchants()){
			if(enchant.getType() == EnchantType.VANILLA){
				form.addButton(StringUtils.color("&l&f●&0 "+enchant.getNameOfEnchantment() + " &l&f●&0"), (p, button) -> this.enchantForm(player, enchant));
			}
		}
		form.send(player);
	}

	public void sendEnchantLCoin(Player player){
		SimpleForm form = new SimpleForm(StringUtils.translateColors("&l&eEnchant LCoin"));
		for(CustomEnchant enchant : EnchantHandler.getAllEnchants()){
			if(enchant.getType() == EnchantType.CUSTOM){
				form.addButton(StringUtils.color("&l&f●&0 "+enchant.getNameOfEnchantment() +" &l&f●&0"), (p, button) -> this.enchantForm(player, enchant));
			}
		}
		form.send(player);
	}

	public void enchantForm(Player player, CustomEnchant enchant){
		CustomForm form = new CustomForm(StringUtils.color("&l&ENCHANT " + enchant.getDisplayNameOfEnchantment()));
		//String payment = (enchant.getType() == EnchantType.CUSTOM) ? "LCoin" : "Xu";
		//form.addElement(StringUtils.color("&l&e;Gi: " + enchant.getCostMultiplier() + " "+ payment));
		form.addElement(StringUtils.color("&l&c⇾ &l&eMô tả:&f " + enchant.getDescription()));
		List<SelectableElement> elements = Arrays.asList(
			new SelectableElement("0", 0),
			new SelectableElement("1", 1),
			new SelectableElement("2", 2),
			new SelectableElement("3", 3),
			new SelectableElement("4", 4),
			new SelectableElement("5", 5)
		);
		form.addElement("slider", new StepSlider(StringUtils.color("&l&c⇾ &l&eCấp độ:&f "), elements));
		form.setHandler((p, response) -> {
			int level = Integer.parseInt(response.getStepSlider("slider").getValue().getText());
			if(level == 0){
				this.sendEnchantForm(player);
				return;
			}
			this.confirmForm(player, enchant, level);
		});
		form.send(player);
	}

	public void confirmForm(Player player, CustomEnchant enchant, int level){
		int price = NumberUtils.getCostOfEnchantmentByLevel(level, enchant.getCostMultiplier());
		ModalForm form = new ModalForm(StringUtils.color("&l&eENCHANT "+ enchant.getDisplayNameOfEnchantment()));
		String payment = (enchant.getType() == EnchantType.CUSTOM) ? "LCoin" : "Orbs";
		form.setContent(StringUtils.color("&l&c⇾ &f"+payment+":&e " + price));
		form.setPositiveButton(StringUtils.color("&l&f●&0 Phù phép &l&f●&0"));
		form.setNegativeButton(StringUtils.color("&l&f●&0 Không &l&f●&0"));
		form.setHandler((p, response) -> {
			if(response){
				if(enchant.getType() == EnchantType.CUSTOM){
					if(EconomyAPI.getInstance().myCoin(player) >= price){
						EconomyAPI.getInstance().reduceCoin(player, price);
						EnchantHandler.applyEnchantment(p, p.getInventory().getItemInHand(), enchant,
													level);
						p.sendMessage(StringUtils.color("&l&f● &l&aPhù phép thành công &f(&e" + enchant.getDisplayNameOfEnchantment() + "&f)"));
					}else{
						p.sendMessage(StringUtils.color("&l&cKhông đủ LCoin để phù phép"));
					}
				}else{
					if(OrbEconomyUtils.hasPlayerBalance(p, price)){
						OrbEconomyUtils.removePlayerBalance(p, price);
						Enchantment ec = Enchantment.getEnchantment(enchant.getId());
						ec.setLevel(level);
						EnchantHandler.applyEnchant(p, p.getInventory().getItemInHand(), ec,
													level);
						p.sendMessage(StringUtils.color("&l&f● &l&aPhù phép thành công &f(&e" + enchant.getDisplayNameOfEnchantment() + "&f)"));
					}else{
						p.sendMessage(StringUtils.color("&l&cKhông đủ orb để phù phép"));
					}
				}
			}
		});
		form.send(player);
	}

	public static FormWindowSimple enchanterMenu() {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&b&lLocm &d&lPrisons"),
				StringUtils.translateColors("&b&l&nChọn phù phép muốn mua!"));
		for (CustomEnchant ce : EnchantHandler.getAllEnchants()) {
			fs.addButton(new ElementButton(ce.getDisplayNameOfEnchantment()));
		}
		return fs;
	}

	public static FormWindowCustom ceMenu(Item item, CustomEnchant ce) {
		if (CEUtils.containsEnchantment(item, ce)) {
			int level = CEUtils.getLevelOfEnchantByDisplayName(ce.getDisplayNameOfEnchantment(), item);
			FormWindowCustom fs = new FormWindowCustom(
					StringUtils.translateColors(ce.getDisplayNameOfEnchantment() + " &r&b&nEnchantment&d&n Purchase"));
			fs.addElement(new ElementLabel(StringUtils.translateColors("&7Mô tả: " + ce.getDescription())));
			fs.addElement(new ElementSlider(StringUtils.translateColors("&bCấp độ:"), level, ce.getMaxLevelOfEnchantment(), 1));
			return fs;
		}
		FormWindowCustom fs = new FormWindowCustom(
				StringUtils.translateColors(ce.getDisplayNameOfEnchantment() + " &r&b&nEnchantment&d&n Purchase"));
		fs.addElement(new ElementLabel(StringUtils.translateColors("&7Mô tả: " + ce.getDescription())));
		fs.addElement(new ElementSlider(StringUtils.translateColors("&bCấp độ: "), 0F, ce.getMaxLevelOfEnchantment(), 1));
		return fs;
	}

	public static FormWindowSimple confirmMenu(int lvl, CustomEnchant ce) {
		FormWindowSimple fs = new FormWindowSimple(StringUtils.translateColors("&b&lXác nhận phù phép!"), StringUtils.translateColors("&7Cost in orbs: "+NumberUtils.getCostOfEnchantmentByLevel(lvl, ce.getCostMultiplier())));
		fs.addButton(new ElementButton(StringUtils.translateColors("&8Phù phép")));
		fs.addButton(new ElementButton(StringUtils.translateColors("&8Từ chối")));
		return fs;
	}
}
