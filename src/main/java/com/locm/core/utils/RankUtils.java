package com.locm.core.utils;

import java.io.File;

import com.locm.core.Loader;
import com.locm.core.ranks.obj.Rank;
import com.locm.core.ranks.storage.RankStorage;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;

public class RankUtils {

	public static Config players = Loader.getLoader().getPlayerCfg();
	public static File playersFile = Loader.getLoader().getPlayersFile();

	public static int getPrestigeLevelForPlayer(Player p) {
		return players.getInt("Players." + p.getName() + ".prestigeLevel");
	}

	public static void setPrestigeLevelForPlayer(Player p, int amount) {
		players.set("Players." + p.getName() + ".prestigeLevel", getPrestigeLevelForPlayer(p) + amount);
		players.save(playersFile);
	}

	public static Rank getRankByPlayer(Player p) throws NullPointerException {
		for (Rank r : RankStorage.getAllRanks()) {
			if (r.getName().equals(players.getString("Players." + p.getName() + ".rank"))) {
				return r;
			}
		}
		return null;
	}
	public static Rank getRankByName(String name) {
		for (Rank r : RankStorage.getAllRanks()) {
			if (r.getName().equals(name)) {
				return r;
			}
		}
		return null;
	}
	public static Rank getRankByOrder(int order) {
		for (Rank r : RankStorage.getAllRanks()) {
			if (r.getOrder() == order) {
				return r;
			}
		}
		return null;
	}

	public static Rank getNextRankByPlayer(Player p) {
		for (Rank r : RankStorage.getAllRanks()) {
			if (r.getName().equals(players.getString("Players." + p.getName() + ".rank"))) {
				return getRankByOrder(r.getOrder() + 1);
			}
		}
		return null;
	}

	public static void setRankByPlayer(Player p, Rank rank) {
		players.set("Players." + p.getName() + ".rank", rank.getName());
		players.save(playersFile);
	}

}
