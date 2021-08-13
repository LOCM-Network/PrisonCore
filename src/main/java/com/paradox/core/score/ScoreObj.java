package com.paradox.core.score;

import de.theamychan.scoreboard.network.Scoreboard;
import me.onebone.economyapi.EconomyAPI;
import com.paradox.core.utils.RankUtils;
import com.paradox.core.utils.OrbEconomyUtils;
import com.paradox.core.ranks.obj.Rank;
import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreObj implements Listener{

	static final Map<Player, Scoreboard> scoreboards = new HashMap<>();

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        scoreboards.removeScorebaord(event.getPlayer());
    }

    public static void show(Player p) {
    	String tile = "Prison";
		Scoreboard scoreboard = ScoreboardAPI.createScoreboard();
		ScoreboardDisplay scoreboardDisplay = scoreboard.addDisplay(DisplaySlot.SIDEBAR, "dumy", tile);
		scoreboardDisplay.addLine("Name "+p.getName(), 0);
		Integer orbs = OrbEconomyUtils.getPlayersTokenBalance(p);
		scoreboardDisplay.addLine("Orb ("+orbs+")", 1);
		Integer money = EconomyAPI.getInstance().myMoney(p);
		scoreboardDisplay.addLine("Money ("+money+")", 2);
		Integer prestige = RankUtils.getPrestigeLevelForPlayer(p);
		scoreboardDisplay.addLine("Money ("+prestige+")", 3);
		scoreboardDisplay.addLine("Prison rank", 4);
		String rank = RankUtils.getRankByPlayer(p).getName();
		scoreboardDisplay.addLine("Current " + rank);
		Rank nextrank = RankUtils.getNextRankByPlayer(p);
		if (nextrank.isLastRank()) {
			scoreboardDisplay.addLine("Mày là trùm");
		} else {
			scoreboardDisplay.addLine("Next " + nextrank.getName() + "("+ rank.getCost() +")");
		}
		scoreboard.setScoreboard(p, scoreboard);
		Scoreboard.scoreboards.put(p, scoreboard);
    } 
}