package com.locm.core.general.cmd;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.Command;
public class KickAllCommand extends Command{

	public KickAllCommand() {
		super("kickall", "Kick all player");
	}

	public boolean execute(CommandSender sender, String label, String[] args) {
		if(!sender.hasPermission("locm.invsee")) return false;
		String reason = "&l&cMáy chủ bảo trì";
		if(args.length != 0) {
			reason = String.join(" ", args);
		}
		for (Player player : Server.getInstance().getOnlinePlayers().values()) {
			if (player != sender) {
				player.kick(TextFormat.colorize(reason));
			}
		}
		return true;
	}
}