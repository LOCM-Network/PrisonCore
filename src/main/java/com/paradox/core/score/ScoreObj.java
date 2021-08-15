package com.paradox.core.score;

import de.lucgameshd.scoreboard.api.ScoreboardAPI;
import de.lucgameshd.scoreboard.network.DisplaySlot;
import de.lucgameshd.scoreboard.network.Scoreboard;
import de.lucgameshd.scoreboard.network.ScoreboardDisplay;
import me.onebone.economyapi.EconomyAPI;
import com.paradox.core.utils.RankUtils;
import com.paradox.core.utils.OrbEconomyUtils;
import com.paradox.core.ranks.obj.Rank;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerLocallyInitializedEvent;
import cn.nukkit.event.EventHandler;
import cn.nukkit.utils.TextFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreObj implements Listener{

	static final Map<Player, Scoreboard> scoreboards = new HashMap<>();

	@EventHandler
	public void onJoin(PlayerLocallyInitializedEvent event) {
		show(event.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		scoreboards.remove(event.getPlayer());
	}

    public static void show(Player p) {
    	Scoreboard old = scoreboards.get(p);
    	ScoreboardAPI.removeScorebaord(p, old);

    	String tile = TextFormat.colorize("&l&eＬＯＣＭ&b ＰＲＩＳＯＮ");
		Scoreboard scoreboard = ScoreboardAPI.createScoreboard();
		ScoreboardDisplay scoreboardDisplay = scoreboard.addDisplay(DisplaySlot.SIDEBAR, "dumy", tile);
		scoreboardDisplay.addLine(TextFormat.colorize("&l&b" +p.getName()), 0);

		Integer orbs = OrbEconomyUtils.getPlayersTokenBalance(p);
		scoreboardDisplay.addLine(TextFormat.colorize("&l&b"+ orbs +"&f Orb"), 1);

		Double money = EconomyAPI.getInstance().myMoney(p);
		scoreboardDisplay.addLine(TextFormat.colorize("&b&l "+ money+"&f Xu"), 2);

		Integer prestige = RankUtils.getPrestigeLevelForPlayer(p);
		scoreboardDisplay.addLine(TextFormat.colorize("&b"+prestige+" &f Prestige&b"), 3);
		Integer onlines = Server.getInstance().getOnlinePlayers().values().toArray().length;

		scoreboardDisplay.addLine(TextFormat.colorize("&l&b"+ onlines +"&f Trực tuyến"), 4);
		String rank = RankUtils.getRankByPlayer(p).getName();

		scoreboardDisplay.addLine(TextFormat.colorize("&l&fKhu mỏ:&b " + rank), 6);
		Rank nextrank = RankUtils.getNextRankByPlayer(p);
		
		if (nextrank.isLastRank()) {
			scoreboardDisplay.addLine(TextFormat.colorize("&l&eMày là trùm"), 7);
		} else {
			scoreboardDisplay.addLine(TextFormat.colorize("&l&fKhu kế&b "+ nextrank.getCost() +" &fxu"), 7);
		}
		ScoreboardAPI.setScoreboard(p, scoreboard);
		scoreboards.put(p, scoreboard);
    }
}