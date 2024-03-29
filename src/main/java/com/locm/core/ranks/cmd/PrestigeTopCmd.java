package com.locm.core.ranks.cmd;

import java.util.HashMap;

import com.locm.core.Loader;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;

public class PrestigeTopCmd extends Command {

	public PrestigeTopCmd() {
		super("topprestige");
	}

	HashMap<String, Integer> list = new HashMap<String, Integer>();

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		for (String playerName : Loader.getLoader().getPlayerCfg().getSection("Players").getKeys(false)) {
			list.put(playerName, Loader.getLoader().getPlayerCfg().getInt("Players." + playerName + ".prestigeLevel"));
		}
		FormWindowCustom fc = new FormWindowCustom(StringUtils.translateColors("&9&lBXH Prestige"));
		String nextTop = "";
		Integer nextTopKills = 0;

		for(int i = 1; i < 11; i++){
			for (String playerName : list.keySet()) {
				if (list.get(playerName) > nextTopKills) {
					nextTop = playerName;
					nextTopKills = list.get(playerName);
				}
			}
			String content = StringUtils.translateColors("&l&eＴｏｐ " + i + ". &a" + nextTop + " &fcấp&a " + nextTopKills);
			fc.addElement(new ElementLabel(content));
			list.remove(nextTop);
			nextTop = "";
			nextTopKills = 0;
		}
		((Player) sender).showFormWindow(fc);
		return false;
	}
}
