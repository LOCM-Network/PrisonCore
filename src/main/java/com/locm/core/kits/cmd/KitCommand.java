package com.locm.core.kits.cmd;

import com.locm.core.kits.FormStorage;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class KitCommand extends Command {

	public KitCommand() {
		super("kit");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			p.showFormWindow(FormStorage.kitMenu());
		}
		return false;
	}

}
