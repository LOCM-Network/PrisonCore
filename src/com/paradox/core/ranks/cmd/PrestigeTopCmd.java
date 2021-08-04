package com.paradox.core.ranks.cmd;

import java.util.HashMap;

import com.paradox.core.Loader;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class PrestigeTopCmd extends Command {

	public PrestigeTopCmd() {
		super("ptop");
	}

	HashMap<String, Integer> list = new HashMap<String, Integer>();

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		for (String playerName : Loader.getLoader().getPlayerCfg().getSection("Players").getKeys(false)) {
			list.put(playerName, Loader.getLoader().getPlayerCfg().getInt("Players." + playerName + ".prestigeLevel"));
		}
		sender.sendMessage("§7===========§b[§b§lTop§d§lPrestige§r§b]§7===========");

		String nextTop = "";
		Integer nextTopKills = 0;

		for(int i = 1; i < 11; i++){
			for (String playerName : list.keySet()) {
				if (list.get(playerName) > nextTopKills) {
					nextTop = playerName;
					nextTopKills = list.get(playerName);
				}
			}
			sender.sendMessage("§b#" + i + " §e" + nextTop + " §8: §b" + nextTopKills);
			list.remove(nextTop);
			nextTop = "";
			nextTopKills = 0;
		}
		return false;

	}
}
