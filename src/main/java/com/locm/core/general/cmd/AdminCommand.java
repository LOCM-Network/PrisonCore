package com.locm.core.general.cmd;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import com.locm.core.format.ChatFormat;

public class AdminCommand extends Command {

	public AdminCommand() {
		super("admin", "admin command");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (sender.hasPermission("locm.admin")) {
			if(args[0].equals("fecthgroup")) {
				ChatFormat.fecthGroup();
				sender.sendMessage("Fecth succesfully");
			}
		}
		return false;
	}
}
