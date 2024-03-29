package com.locm.core;

import java.io.File;
import java.util.*;

//import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import com.locm.core.ah.listeners.AuctionListener;
import com.locm.core.ces.listeners.EnchantListener;
import com.locm.core.general.listeners.EventsListener;
import com.locm.core.general.listeners.NPCRotation;
import com.locm.core.score.ScoreObj;
import com.locm.core.general.cmd.BombCommand;
import com.locm.core.general.cmd.BoosterCmd;
import com.locm.core.general.cmd.EnchanterCmd;
import com.locm.core.general.cmd.InvseeCommand;
import com.locm.core.general.cmd.RepairCommand;
import com.locm.core.general.cmd.SellCommand;
import com.locm.core.general.cmd.TPSCommand;
import com.locm.core.general.cmd.HubCommand;
import com.locm.core.general.cmd.KickAllCommand;
import com.locm.core.general.cmd.AdminCommand;
import com.locm.core.kits.KitHandler;
import com.locm.core.kits.cmd.CreateKitCommand;
import com.locm.core.kits.cmd.DeleteKitCommand;
import com.locm.core.mines.cmd.MineCommand;
import com.locm.core.mines.listeners.MinesListener;
import com.locm.core.mines.obj.Mine;
import com.locm.core.orbs.cmd.OrbsCmd;
import com.locm.core.orbs.listeners.OrbsListener;
import com.locm.core.ranks.cmd.PrestigeTopCmd;
import com.locm.core.ranks.cmd.RankupCommand;
import com.locm.core.format.ChatFormat;
import com.locm.core.utils.GeneralUtils;
import com.locm.core.utils.MineUtils;
import com.locm.core.utils.OrbEconomyUtils;
import com.locm.core.utils.RankUtils;
import com.locm.core.general.feautures.Announ;
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
	private Config chatCfg;
	//public static PlaceholderAPI api = PlaceholderAPI.getInstance();
	public static HashMap<Mine, Integer> mineReset = new HashMap<>();

	@Override
	public void onEnable() {
		saveDefaultConfig();
		saveResource("chatformat.yml");
		getDataFolder().mkdirs();
		registerfiles();
		GeneralUtils.setupWorthFile();
/*
		api.builder("playerorbs", Integer.class)
				.visitorLoader(entry -> OrbEconomyUtils.getPlayersTokenBalance(entry.getPlayer())).build();
		api.builder("prestige", Integer.class)
				.visitorLoader(entry -> RankUtils.getPrestigeLevelForPlayer(entry.getPlayer()));
		api.builder("prisonrank", String.class)
				.visitorLoader(entry -> Objects.requireNonNull(RankUtils.getRankByPlayer(entry.getPlayer())).getName());*/
		startFeautures();
	}

	public void startFeautures() {
		//startMineResetTask();
		registerCommands();
		registerEvents();
		loadWorlds();
		startClearLagTask();
		startScoreTask();
		startMineResetThread();
		new Announ().runAnnoun();
	}

	public static void startMineResetThread(){

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
		File formatFile = new File(getDataFolder(), "chatformat.yml");
		chatCfg = new Config(formatFile);
	}

	public void startMineResetTask() {
		if (!MineUtils.getAllMinesFromConfig().isEmpty()) {
			new NukkitRunnable() {
				int i = 12000;
				@Override
				public void run() {
					if (i <= 0) {
						for (Mine m : MineUtils.getAllMinesFromConfig()) {
							m.resetMine();
						}						
						i = 12000;
					}
					i--;
				}
			}.runTaskTimer(this, 0, 20);
		}
	}

	public void startClearLagTask() {
		new NukkitRunnable() {
			int time = 20*60*5;
			@Override
			public void run() {
				if(time <= 0) {
					GeneralUtils.clearlag();
					Server.getInstance().broadcastMessage(TextFormat.colorize("&l&aĐang tối ưu máy chủ!!!"));
					time = 20*60*5;
				}
				List<Integer> intList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 30, 60));
				if(intList.contains(time)) {
					Server.getInstance().broadcastMessage(TextFormat.colorize("&l&fTối ưu máy chủ sau &e" + time + "&f giây nữa"));
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
		//getServer().getCommandMap().register("ah", new AuctionHouseComamnd());
		getServer().getCommandMap().register("repairhand", new RepairCommand());
		getServer().getCommandMap().register("createkit", new CreateKitCommand());
		getServer().getCommandMap().register("deletekit", new DeleteKitCommand());
		//getServer().getCommandMap().register("kit", new KitCommand());
		getServer().getCommandMap().register("enchanter", new EnchanterCmd());
		getServer().getCommandMap().register("booster", new BoosterCmd());
		getServer().getCommandMap().register("ptop", new PrestigeTopCmd());
		getServer().getCommandMap().register("bomb", new BombCommand());
		getServer().getCommandMap().register("invsee", new InvseeCommand());
		getServer().getCommandMap().register("hub", new HubCommand());
		getServer().getCommandMap().register("kickall", new KickAllCommand());
		getServer().getCommandMap().register("admin", new AdminCommand());
	}

	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new EventsListener(), this);
		getServer().getPluginManager().registerEvents(new OrbsListener(), this);
		getServer().getPluginManager().registerEvents(new EnchantListener(), this);
		getServer().getPluginManager().registerEvents(new MinesListener(), this);
		getServer().getPluginManager().registerEvents(new AuctionListener(), this);
		//getServer().getPluginManager().registerEvents(new KitsListener(), this);
		getServer().getPluginManager().registerEvents(new ScoreObj(), this);
		getServer().getPluginManager().registerEvents(new ChatFormat(), this);
		getServer().getPluginManager().registerEvents(new NPCRotation(), this);
	}

	public void loadWorlds() {
		int count = 0;
		try {
			for (File fs : Objects.requireNonNull(new File(new File("").getCanonicalPath() + "/worlds/").listFiles())) {
				if ((fs.isDirectory() && !getServer().isLevelLoaded(fs.getName()))) {
					getServer().loadLevel(fs.getName());
					count++;
				}
			}
			getLogger().info("Loaded " + count + " worlds");
		} catch (Exception e) {
			getLogger().error("Unable to load worlds", e);
		}
	}

	public static Loader getLoader() {
		return loader;
	}

	public static Loader getInstance() {
		return loader;
	}

	public Config getPlayerCfg() {
		return playerCfg;
	}

	public Config getChatCfg() {
		return chatCfg;
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
