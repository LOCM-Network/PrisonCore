package com.locm.core.ah.cmd;

import com.locm.core.ah.FormStorage;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class AuctionHouseComamnd extends Command {

	public AuctionHouseComamnd() {
		super("ah", "buy or sell items in /ah", "/ah", new String[] {"ca","auctions"});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			p.showFormWindow(FormStorage.AHMenu());
		}
		return false;
	}

}
