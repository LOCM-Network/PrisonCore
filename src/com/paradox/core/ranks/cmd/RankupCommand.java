package com.paradox.core.ranks.cmd;

import com.paradox.core.Loader;
import com.paradox.core.ranks.obj.Rank;
import com.paradox.core.ranks.storage.RankStorage;
import com.paradox.core.utils.RankUtils;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import me.onebone.economyapi.EconomyAPI;

public class RankupCommand extends Command {

	public RankupCommand() {
		super("rankup");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				Rank currentRank = RankUtils.getRankByPlayer(p);
				if (!currentRank.isLastRank()) {
					Rank nextRank = RankUtils.getNextRankByPlayer(p);
					if (EconomyAPI.getInstance().myMoney(p) >= currentRank.getCost()) {
						EconomyAPI.getInstance().reduceMoney(p, currentRank.getCost());
						RankUtils.setRankByPlayer(p, nextRank);
						p.sendMessage(StringUtils.getPrefix() + "Nâng cấp thành công (" + nextRank.getName() + ")");
						Server.getInstance().broadcastMessage("Người chơi " + sender.getName() + " vừa nâng cấp ("+ nextRank.getName() +")");
						return false;
					} else {
						p.sendMessage(StringUtils.getPrefix() + "Không đủ tiền để nâng cấp.");
						return false;
					}
				} else {
					if (EconomyAPI.getInstance().myMoney(p) >= currentRank.getCost()) {
						EconomyAPI.getInstance().reduceMoney(p, currentRank.getCost());
						RankUtils.setRankByPlayer(p, RankStorage.A);
						RankUtils.setPrestigeLevelForPlayer(p, 1);
						p.sendMessage(StringUtils.getPrefix() + "Bạn vừa nhận được "
								+ RankUtils.getPrestigeLevelForPlayer(p) + " điểm uy tính");
						Server.getInstance().broadcastMessage(StringUtils.getPrefix() + p.getName() + " vừa nhận được "
									+ RankUtils.getPrestigeLevelForPlayer(p) + " điểm uy tính");
					}
				}
			}
		}
		return false;
	}

}
