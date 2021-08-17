package com.locm.core.general.cmd;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class HubCommand extends Command {

	public HubCommand() {
		super("hub", "back to hub");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			player.teleport(Server.getInstance().getDefaultLevel().getSafeSpawn());
			player.sendActionBar(TextFormat.colorize("&l&fTrở về sảnh...."));
		}
		return false;
	}

}
