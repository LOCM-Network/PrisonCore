package com.locm.core;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import com.locm.core.ah.cmd.AuctionHouseComamnd;
import com.locm.core.ah.listeners.AuctionListener;
import com.locm.core.ces.listeners.EnchantListener;
import com.locm.core.general.listeners.EventsListener;
import com.locm.core.score.ScoreObj;
import com.locm.core.general.cmd.BombCommand;
import com.locm.core.general.cmd.BoosterCmd;
import com.locm.core.general.cmd.EchestCommand;
import com.locm.core.general.cmd.EnchanterCmd;
import com.locm.core.general.cmd.InvseeCommand;
import com.locm.core.general.cmd.RTagCmd;
import com.locm.core.general.cmd.RepairCommand;
import com.locm.core.general.cmd.SellCommand;
import com.locm.core.general.cmd.TPSCommand;
import com.locm.core.general.cmd.HubCommand;
import com.locm.core.kits.KitHandler;
import com.locm.core.kits.cmd.CreateKitCommand;
import com.locm.core.kits.cmd.DeleteKitCommand;
import com.locm.core.kits.cmd.KitCommand;
import com.locm.core.kits.listeners.KitsListener;
import com.locm.core.mines.cmd.MineCommand;
import com.locm.core.mines.listeners.MinesListener;
import com.locm.core.mines.obj.Mine;
import com.locm.core.orbs.cmd.OrbsCmd;
import com.locm.core.orbs.listeners.OrbsListener;
import com.locm.core.ranks.cmd.PrestigeTopCmd;
import com.locm.core.ranks.cmd.RankupCommand;
import com.locm.core.utils.GeneralUtils;
import com.locm.core.utils.MineUtils;
import com.locm.core.utils.OrbEconomyUtils;
import com.locm.core.utils.RankUtils;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.Server;
import cn.nukkit.Player;

public class Loader extends PluginBase {

	private static Loader loader;
	private Config playerCfg;
	private File playersFile;
	private Config minesCfg;
	private File minesFile;
	private Config worthCfg;
	private File worthFile;
	private Config dataCfg;
	private File dataFile;
	private Config kitsCfg;
	private File kitsFile;
	PlaceholderAPI api = PlaceholderAPI.getInstance();
	public static HashMap<Mine, Integer> mineReset = new HashMap<>();

	@Override
	public void onEnable() {
		getDataFolder().mkdirs();
		registerfiles();
		GeneralUtils.setupWorthFile();
		api.visitorSensitivePlaceholder("playerorbs", (p, T) -> OrbEconomyUtils.getPlayersTokenBalance(p));
		api.visitorSensitivePlaceholder("prisonrank", (p, T) -> RankUtils.getRankByPlayer(p).getName());
		api.visitorSensitivePlaceholder("prestige", (p, T) -> RankUtils.getPrestigeLevelForPlayer(p));
		startMineResetTask();
		registerCommands();
		registerEvents();
		//runCooldowns();
		startClearLagTask();
		startScoreTask();
	}

	public static void runCooldowns() {
		new NukkitRunnable() {
			@Override
			public void run() {
				KitHandler.processCooldowns();
			}
		}.runTaskTimer(Loader.getLoader(), 0, 20);

	}

	public void registerfiles() {
		playersFile = new File(getDataFolder(), "players.yml");
		playerCfg = new Config(playersFile);
		minesFile = new File(getDataFolder(), "mines.yml");
		minesCfg = new Config(minesFile);
		worthFile = new File(getDataFolder(), "worth.yml");
		worthCfg = new Config(worthFile);
		dataFile = new File(getDataFolder(), "data.yml");
		dataCfg = new Config(dataFile);
		kitsFile = new File(getDataFolder(), "kits.yml");
		kitsCfg = new Config(kitsFile);
	}

	public void startMineResetTask() {
		if (!MineUtils.getAllMinesFromConfig().isEmpty()) {
			for (Mine m : MineUtils.getAllMinesFromConfig()) {
				new NukkitRunnable() {
					int i = 300;

					@Override
					public void run() {
						if (i <= 0) {
							m.resetMine();
							i = 300;
						}
						for (Mine m : MineUtils.getAllMinesFromConfig()) {
							api.staticPlaceholder(m.getMineName() + "_resetMineDelay", T -> i, new String[0]);
						}
						i--;
					}
				}.runTaskTimer(this, 0, 20);
			}
		}
	}

	public void startClearLagTask() {
		new NukkitRunnable() {
			int time = 20*60*5;
			@Override
			public void run() {
				if(time <= 0) {
					int count = GeneralUtils.clearlag();
					Server.getInstance().broadcastMessage(TextFormat.colorize("&l&fĐã xóa&e " + count + " &fvật phẩm trên mặt đất"));
					time = 20*60*5;
				}
				List<Integer> intList = new ArrayList<>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 30, 60}));
				if(intList.contains(time)) {
					Server.getInstance().broadcastMessage(TextFormat.colorize("&l&fDọn rác sau &e" + time + "&f giây nữa"));
				}
				time--;
			}
		}.runTaskTimer(this, 0, 20);
	}

	public void startScoreTask() {
		new NukkitRunnable() {

			@Override
			public void run() {
				Map<UUID, Player> players = Server.getInstance().getOnlinePlayers();
				if(!players.isEmpty()) {
					for (Player p : players.values()) {

						ScoreObj.show(p);
					}					
				}
			}
		}.runTaskTimer(this, 0, 60);
	}

	@Override
	public void onDisable() {

	}

	@Override
	public void onLoad() {
		loader = this;
	}

	public void registerCommands() {
		getServer().getCommandMap().register("orbs", new OrbsCmd());
		getServer().getCommandMap().register("mines", new MineCommand());
		getServer().getCommandMap().register("rankup", new RankupCommand());
		getServer().getCommandMap().register("sell", new SellCommand());
		getServer().getCommandMap().register("tps", new TPSCommand());
		getServer().getCommandMap().register("ah", new AuctionHouseComamnd());
		getServer().getCommandMap().register("repairhand", new RepairCommand());
		getServer().getCommandMap().register("createkit", new CreateKitCommand());
		getServer().getCommandMap().register("deletekit", new DeleteKitCommand());
		//getServer().getCommandMap().register("kit", new KitCommand());
		getServer().getCommandMap().register("enchanter", new EnchanterCmd());
		getServer().getCommandMap().register("booster", new BoosterCmd());
		getServer().getCommandMap().register("ptop", new PrestigeTopCmd());
		getServer().getCommandMap().register("bomb", new BombCommand());
		getServer().getCommandMap().register("invsee", new InvseeCommand());
		getServer().getCommandMap().register("echest", new EchestCommand());
		getServer().getCommandMap().register("hub", new HubCommand());
		//getServer().getCommandMap().register("rtag", new RTagCmd());
	}

	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new EventsListener(), this);
		getServer().getPluginManager().registerEvents(new OrbsListener(), this);
		getServer().getPluginManager().registerEvents(new EnchantListener(), this);
		getServer().getPluginManager().registerEvents(new MinesListener(), this);
		getServer().getPluginManager().registerEvents(new AuctionListener(), this);
		//getServer().getPluginManager().registerEvents(new KitsListener(), this);
		getServer().getPluginManager().registerEvents(new ScoreObj(), this);
	}

	public static Loader getLoader() {
		return loader;
	}

	public Config getPlayerCfg() {
		return playerCfg;
	}

	public File getPlayersFile() {
		return playersFile;
	}

	public Config getMinesCfg() {
		return minesCfg;
	}

	public File getMinesFile() {
		return minesFile;
	}

	public Config getWorthCfg() {
		return worthCfg;
	}

	public File getWorthFile() {
		return worthFile;
	}

	public Config getDataCfg() {
		return dataCfg;
	}

	public File getDataFile() {
		return dataFile;
	}

	public Config getKitsCfg() {
		return kitsCfg;
	}

	public File getKitsFile() {
		return kitsFile;
	}

}
