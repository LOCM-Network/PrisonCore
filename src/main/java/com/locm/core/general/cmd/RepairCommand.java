package com.locm.core.general.cmd;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import me.onebone.economyapi.EconomyAPI;

public class RepairCommand extends Command {

	public RepairCommand() {
		super("repairhand", "Sửa chữa vật phẩm (theo dộ hỏng)", "/repairhand", new String[]{"fix"});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		EconomyAPI api = EconomyAPI.getInstance();
		Player p = (Player) sender;
		Item i = p.getInventory().getItemInHand();
		Integer price = i.getDamage() * 45;
		if (i.isArmor() || i.isPickaxe() || i.isSword() || i.isAxe() || i.isShears() || i.isPickaxe() || i.isShovel()) {
			if (api.myMoney(p) >= price){
				api.reduceMoney(p, price);
				p.sendMessage(TextFormat.colorize("&l&fVật phẩm đã như mới &7(&f" + price + "&e xu&7)."));
				p.getInventory().remove(i);
				i.setDamage(0);
				p.getInventory().addItem(i);
			} else {
				p.sendMessage(TextFormat.colorize("&l&cKhông đủ tiền để sửa vật phẩm cần &e" + price + " &c xu"));	
			}
		} else {
			p.sendMessage(TextFormat.colorize("&cVật phẩm không hợp lệ &l&f(&eHợp lệ:&f Kiếm, giáp, công cụ)"));
		}
		return false;
	}

}
