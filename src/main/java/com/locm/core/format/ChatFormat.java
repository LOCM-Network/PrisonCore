package com.locm.core.format;

import cn.nukkit.Player;
import cn.nukkit.Server;
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
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;

public class ChatFormat{

	private LuckPerms api;
	private static Config config;

	public ChatFormat() {
		config = Loader.getInstance().getChatCfg();
	}

	public static void fecthGroup() {
		for (Group g : api.getGroupManager().getLoadedGroups()) {
			String group = g.getName();
			config.set("Chat." + group, "[%name%] >> %msg%");
			config.set("NameTag." + group, "%name%");
			Server.getInstance().getLogger().info(pf + TextFormat.LIGHT_PURPLE + " Fetching group: " + group);
		}
		config.save();
	}

	@EventHandler(ignoreCancelled = true)
	public void onChat(PlayerChatEvent event) {
		String message = event.getMessage();
		Player player = event.getPlayer();
		String perm = getGroup(player);
		String format = (config.getString("Chat."+perm)
				.replace("%%n", "\n")
				.replace("%%r", "\r")
				.replace("%%t", "\t")
				.replace("%name%", player.getName())
				.replace("%rank%", getPrisonRank(player))
				.replace("%disname%", player.getDisplayName())
				.replace("%group%", perm)
				.replace("%msg%", message));
		format = Loader.api.translateString(msg, player);
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
				.replace("%group%", perm)
				.replace("%msg%", message));
		format = Loader.api.translateString(msg, player);
		player.setNameTag(TextFormat.colorize('&', format));
	}

	public String getNameTag(Player player, String group) {
		String format = (config.getString("NameTag."+perm)
				.replace("%%n", "\n")
				.replace("%%r", "\r")
				.replace("%%t", "\t")
				.replace("%name%", player.getName())
				.replace("%rank%", getPrisonRank(player))
				.replace("%disname%", player.getDisplayName())
				.replace("%group%", perm));
		return format;
	}

	public static String getGroup(Player player) {
		return  api.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
	}

	public String getPrisonRank(Player player) {
		return RankUtils.getRankByPlayer(player).getName();
	}
}