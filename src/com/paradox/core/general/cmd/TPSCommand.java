package com.paradox.core.general.cmd;

import com.paradox.core.Loader;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class TPSCommand extends Command {

	public TPSCommand() {
		super("tps");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		sender.sendMessage(StringUtils.getPrefix()+"TPS: "+Loader.getLoader().getServer().getTicksPerSecond());
		return false;
	}

}
