package com.paradox.core.general.cmd;

import com.paradox.core.ces.forms.FormStorage;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class EnchanterCmd extends Command {

	public EnchanterCmd() {
		super("enchanter","Open enchant menu","/enchanter",new String[]{"ce"});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.getInventory().getItemInHand().isPickaxe() || p.getInventory().getItemInHand().isAxe()) {
				p.showFormWindow(FormStorage.enchanterMenu());
			} else {
				p.sendMessage(TextFormat.colorize("&cChỉ có thể phù phép cho cúp hoặc rìu."));
			}
		}
		return false;
	}
}
