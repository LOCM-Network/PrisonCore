package com.locm.core.score;

import de.lucgameshd.scoreboard.api.ScoreboardAPI;
import de.lucgameshd.scoreboard.network.DisplaySlot;
import de.lucgameshd.scoreboard.network.Scoreboard;
import de.lucgameshd.scoreboard.network.ScoreboardDisplay;
import me.onebone.economyapi.EconomyAPI;
import com.locm.core.utils.RankUtils;
import com.locm.core.utils.OrbEconomyUtils;
import com.locm.core.ranks.obj.Rank;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerLocallyInitializedEvent;
import cn.nukkit.event.EventHandler;
import cn.nukkit.utils.TextFormat;
import java.util.HashMap;
import java.util.Map;

public class ScoreObj implements Listener{

	static final Map<Player, Scoreboard> scoreboards = new HashMap<>();
	private static me.locm.economyapi.EconomyAPI lcoin;

	@EventHandler
	public void onJoin(PlayerLocallyInitializedEvent event) {
		show(event.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		scoreboards.remove(event.getPlayer());
	}

	public static void show(Player p) {
		try {
			Scoreboard old = scoreboards.get(p);
			ScoreboardAPI.removeScorebaord(p, old);	
		}catch(Exception ignored) {}

		String tile = TextFormat.colorize("&l&eＬＯＣＭ&b ＰＲＩＳＯＮ");
		Scoreboard scoreboard = ScoreboardAPI.createScoreboard();
		ScoreboardDisplay scoreboardDisplay = scoreboard.addDisplay(DisplaySlot.SIDEBAR, "dumy", tile);
		scoreboardDisplay.addLine(TextFormat.colorize("&l&6٭ &b " +p.getName()), 0);

		Integer orbs = OrbEconomyUtils.getPlayersTokenBalance(p);
		scoreboardDisplay.addLine(TextFormat.colorize("&l&6٭ &fOrb&b "+ orbs), 1);

		Double money = EconomyAPI.getInstance().myMoney(p);
		scoreboardDisplay.addLine(TextFormat.colorize("&l&6٭ &fXu &b"+ money), 2);
		Double coin = me.locm.economyapi.EconomyAPI.getInstance().myCoin(p);
		scoreboardDisplay.addLine(TextFormat.colorize("&l&6٭ &fLCoin &b"+ coin), 3);
		Integer prestige = RankUtils.getPrestigeLevelForPlayer(p);
		scoreboardDisplay.addLine(TextFormat.colorize("&l&6٭ &fPrestige&b "+prestige), 4);
		Integer onlines = Server.getInstance().getOnlinePlayers().values().toArray().length;

		scoreboardDisplay.addLine(TextFormat.colorize("&l&6٭ &fTrực tuyến&b "+ onlines), 5);
		String rank = RankUtils.getRankByPlayer(p).getName();
		if(rank == null) rank = "A";

		scoreboardDisplay.addLine(TextFormat.colorize("&l&6٭ &fKhu mỏ&b " + rank), 6);
		Rank nextrank = RankUtils.getNextRankByPlayer(p);
		
		if (RankUtils.getRankByPlayer(p).isLastRank()) {
			scoreboardDisplay.addLine(TextFormat.colorize("&l&6٭ &eMày là trùm rồi nâng là cày lại"), 7);
		} else {
			scoreboardDisplay.addLine(TextFormat.colorize("&l&6٭ &fKhu kế&b "+ nextrank.getCost() +" &fxu"), 7);
		}
		ScoreboardAPI.setScoreboard(p, scoreboard);
		scoreboards.put(p, scoreboard);
	}
}