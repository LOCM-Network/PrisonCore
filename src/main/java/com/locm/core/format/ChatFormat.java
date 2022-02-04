package com.locm.core.format;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import com.locm.core.Loader;
import com.locm.core.utils.RankUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;

public class ChatFormat implements Listener{

	private static LuckPerms api;
	private static Config config;

	public ChatFormat() {
		config = Loader.getInstance().getChatCfg();
		api = LuckPermsProvider.get();
		fecthGroup();
	}

	public static void fecthGroup() {
		for (Group g : api.getGroupManager().getLoadedGroups()) {
			String group = g.getName();
			if(config.getString("Chat." + group) == null) {
				config.set("Chat." + group, "[%name%] >> %msg%");
				config.set("NameTag." + group, "%name%");
				Loader.getInstance().getLogger().info(TextFormat.LIGHT_PURPLE + " Fetching group: " + group);					
			}
		}
		config.save();
	}

	@EventHandler(ignoreCancelled = true)
	public void onChat(PlayerChatEvent event) {
		String message = event.getMessage();
		Player player = event.getPlayer();
		String perm = getGroup(player);

		String format = config.getString("Chat."+perm)
				.replace("%%n", "\n")
				.replace("%%r", "\r")
				.replace("%%t", "\t")
				.replace("%name%", player.getName())
				.replace("%rank%", getPrisonRank(player))
				.replace("%disname%", player.getDisplayName())
				.replace("%group%", perm)
				.replace("%msg%", message);

		if(player.hasPermission("police.perm")){
			format = format.replace("%staff_rank%", config.getString("police.chat"));
		}else if(player.hasPermission("helper.perm")){
			format = format.replace("%staff_rank%", config.getString("helper.chat"));
		}else{
			format = format.replace("%staff_rank%", "");
		}
		//format = Loader.api.translateString(format, player);
		event.setFormat(TextFormat.colorize('&', format));
	}

	@EventHandler(ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String perm = getGroup(player);
		String format = (config.getString("NameTag."+perm)
				.replace("%%n", "\n")
				.replace("%%r", "\r")
				.replace("%%t", "\t")
				.replace("%name%", player.getName())
				.replace("%rank%", getPrisonRank(player))
				.replace("%disname%", player.getDisplayName())
				.replace("%group%", perm));
		if(player.hasPermission("police.perm")){
			format = format.replace("%staff_rank%", TextFormat.colorize(config.getString("police.nametag")));
		}else if(player.hasPermission("helper.perm")){
			format = format.replace("%staff_rank%", TextFormat.colorize(config.getString("helper.nametag")));
		}else{
			format = format.replace("%staff_rank%", "");
		}
		//format = Loader.api.translateString(format, player);
		player.setNameTag(TextFormat.colorize('&', format));
	}

	public static String getNameTag(Player player) {
		String perm = getGroup(player);
		String format = (config.getString("NameTag."+perm)
				.replace("%%n", "\n")
				.replace("%%r", "\r")
				.replace("%%t", "\t")
				.replace("%name%", player.getName())
				.replace("%rank%", getPrisonRank(player))
				.replace("%disname%", player.getDisplayName())
				.replace("%group%", perm));
		//format = Loader.api.translateString(format, player);
		return format;
	}

	public static String getGroup(Player player) {
		return api.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
	}

	public static String getPrisonRank(Player player) {
		return RankUtils.getRankByPlayer(player).getName();
	}
}
