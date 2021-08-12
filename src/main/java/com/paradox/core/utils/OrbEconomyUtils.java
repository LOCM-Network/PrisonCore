package com.paradox.core.utils;

import java.io.File;

import com.paradox.core.Loader;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;

public class OrbEconomyUtils {

	public static Config players = Loader.getLoader().getPlayerCfg();
	public static File playersFile = Loader.getLoader().getPlayersFile();

	public static int getPlayersTokenBalance(Player p) {
		if (!players.exists("Players." + p.getName() + ".orbsAmount")) {
			players.set("Players." + p.getName() + ".orbsAmount", 0);
			players.save(playersFile);
		}
		return players.getInt("Players." + p.getName() + ".orbsAmount");
	}

	public static void setPlayerBalance(Player p, int amount) {
		players.set("Players." + p.getName() + ".orbsAmount", amount);
		players.save(playersFile);
	}

	public static void addPlayerBalance(Player p, int amount) {
		setPlayerBalance(p, getPlayersTokenBalance(p) + amount);
	}

	public static void removePlayerBalance(Player p, int amount) {
		addPlayerBalance(p, -amount);
	}

	public static boolean hasPlayerBalance(Player p, int amount) {
		return getPlayersTokenBalance(p) >= amount;
	}
}
