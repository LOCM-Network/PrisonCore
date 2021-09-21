package com.locm.core.ranks.cmd;

import com.locm.core.ranks.obj.Rank;
import com.locm.core.event.player.PlayerRankUpEvent;
import com.locm.core.ranks.storage.RankStorage;
import com.locm.core.utils.GeneralUtils;
import com.locm.core.utils.RankUtils;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
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
				if (currentRank != null && !currentRank.isLastRank()) {
					Rank nextRank = RankUtils.getNextRankByPlayer(p);
					if (EconomyAPI.getInstance().myMoney(p) >= nextRank.getCost()) {
						EconomyAPI.getInstance().reduceMoney(p, nextRank.getCost());
						RankUtils.setRankByPlayer(p, nextRank);
						p.sendMessage(StringUtils.color("&l&fNâng cấp thành công (&e" + nextRank.getName() + "&f)"));
						Server.getInstance().broadcastMessage(StringUtils.color("&l&fNgười chơi&e " + sender.getName() + "&f đã đạt cấp (&e"+ nextRank.getName() +"&f)"));
						GeneralUtils.playSound(p, Sound.FIREWORK_LAUNCH);
						Server.getInstance().getPluginManager().callEvent(new PlayerRankUpEvent(p, nextRank));
						return false;
					} else {
						p.sendMessage(StringUtils.color("&l&cKhông đủ tiền để nâng cấp."));
						return false;
					}
				} else {
					if (EconomyAPI.getInstance().myMoney(p) >= currentRank.getCost()) {
						EconomyAPI.getInstance().reduceMoney(p, currentRank.getCost());
						RankUtils.setRankByPlayer(p, RankStorage.A);
						RankUtils.setPrestigeLevelForPlayer(p, 1);
						p.sendMessage(StringUtils.color("&l&fBạn vừa nhận được &e"
								+ RankUtils.getPrestigeLevelForPlayer(p) + " &fPrestige"));
						Server.getInstance().broadcastMessage(StringUtils.color("&l&e" + p.getName() + "&f vừa nhận được &e"
									+ RankUtils.getPrestigeLevelForPlayer(p) + " &fPrestige"));
					}
				}
			}
		}
		if(args.length == 3){
			if(!sender.hasPermission("locm.owner")) return false;
			if(args[0].equals("set")){
				Player player = Server.getInstance().getPlayerExact(args[1]);
				if(player == null){
					sender.sendMessage("sai ten");
					return false;
				}
				Rank rank = RankUtils.getRankByName(args[2]);
				RankUtils.setRankByPlayer(player, rank);
				sender.sendMessage("Da set rank cua player thanh: " + rank.getName());
			}
		}
		return false;
	}

}
