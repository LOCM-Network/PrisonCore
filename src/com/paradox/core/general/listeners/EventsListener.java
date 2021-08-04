package com.paradox.core.general.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.paradox.core.Loader;
import com.paradox.core.kits.KitHandler;
import com.paradox.core.kits.obj.Kit;
import com.paradox.core.mines.LuckyRewardStorage;
import com.paradox.core.mines.obj.LuckyReward;
import com.paradox.core.ranks.storage.RankStorage;
import com.paradox.core.utils.GeneralUtils;
import com.paradox.core.utils.ItemStorage;
import com.paradox.core.utils.MineUtils;
import com.paradox.core.utils.OrbEconomyUtils;
import com.paradox.core.utils.RandomCollection;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.Config;
import me.onebone.economyapi.EconomyAPI;

public class EventsListener implements Listener {

	public static HashMap<UUID, Long> cooldown = new HashMap<>();
	public static HashMap<Player, Integer> playersSellBooster = new HashMap<>();
	public static HashMap<Player, Integer> playersOrbsBooster = new HashMap<>();
	public static Config players = Loader.getLoader().getPlayerCfg();
	public static File playersFile = Loader.getLoader().getPlayersFile();

	public String tagByPerm(String perm) {
		switch (perm) {
		case "tags.paradox":
			return StringUtils.translateColors("&6&l+&eParadox&6+");
		case "tags.noob":
			return StringUtils.translateColors("&bNoob &3:(");
		case "tags.hacker":
			return StringUtils.translateColors("&c&lHacking&cScum");
		case "tags.pvper":
			return StringUtils.translateColors("&aPvper");
		case "tags.god":
			return StringUtils.translateColors("&f&lGod");
		}
		return perm;
	}

	public List<String> listOfPermsTags() {
		List<String> list = new ArrayList<String>();
		list.add("tags.paradox");
		list.add("tags.noob");
		list.add("tags.hacker");
		list.add("tags.pvper");
		list.add("tags.god");
		return list;
	}

	public boolean hasAllTags(Player p) {
		return (p.hasPermission("tags.paradox") && p.hasPermission("tags.noob") && p.hasPermission("tags.hacker")
				&& p.hasPermission("tags.pvper") && p.hasPermission("tags.god"));
	}

	public String getRandomTagPerm(Player p) {
		Random r = new Random();
		String perm = listOfPermsTags().get(r.nextInt(listOfPermsTags().size()));
		if (p.hasPermission(perm)) {
			getRandomTagPerm(p);
		}
		return perm;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (!e.getPlayer().hasPlayedBefore()) {
			for (Kit kit : KitHandler.getAllKitsFromConfig()) {
				if (kit.getName().equals("Daily")) {
					kit.giveKitToPlayer(e.getPlayer());
				}
			}
		}
		if (!players.exists("Players." + e.getPlayer().getName() + ".rank")) {
			players.set("Players." + e.getPlayer().getName() + ".rank", RankStorage.A.getName());
			players.set("Players." + e.getPlayer().getName() + ".prestigeLevel", 1);
			players.save(playersFile);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (p.getLevel().getName().equals("plotsworld")) {
				e.setCancelled();
			}
		}
	}

	@EventHandler
	public void onInteractBomb(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Item i = p.getInventory().getItemInHand();
		if (i.getCustomName().equals(ItemStorage.smallBomb().getCustomName())) {
			e.setCancelled();
			if (cooldown.containsKey(p.getUniqueId())) {
				long secondsLeft = ((cooldown.get(p.getUniqueId()) / 1000) + 5) - (System.currentTimeMillis() / 1000);
				if (secondsLeft > 0) {
					p.sendActionBar(
							StringUtils.getPrefix() + "You can throw another bomb in " + secondsLeft + " seconds.");
					return;
				}
			}
			GeneralUtils.pop(i, p, 1);
			Item bomb = new Item(385);
			Location loc = p.getLocation().add(new Vector3(0, 2, 0));
			Vector3 v3 = p.getDirectionVector();
			CompoundTag itemTag = NBTIO.putItemHelper(bomb);
			EntityItem itemEntity = createItemEntity(loc, itemTag, v3, p);
			itemEntity.spawnToAll();
			cooldown.put(p.getUniqueId(), System.currentTimeMillis());
			int lvl = 2;
			new NukkitRunnable() {

				@Override
				public void run() {
					detonatebomb(itemEntity.getLocation(), lvl, e, p);
					itemEntity.despawnFromAll();
					itemEntity.close();
					itemEntity.kill();
				}
			}.runTaskLater(Loader.getLoader(), 5 * 20);

		} else if (i.getCustomName().equals(ItemStorage.mediumBomb().getCustomName())) {
			e.setCancelled();
			if (cooldown.containsKey(p.getUniqueId())) {
				long secondsLeft = ((cooldown.get(p.getUniqueId()) / 1000) + 5) - (System.currentTimeMillis() / 1000);
				if (secondsLeft > 0) {
					p.sendActionBar(
							StringUtils.getPrefix() + "You can throw another bomb in " + secondsLeft + " seconds.");
					return;
				}
			}
			GeneralUtils.pop(i, p, 1);
			Item bomb = new Item(385);
			Location loc = p.getLocation().add(new Vector3(0, 2, 0));
			Vector3 v3 = p.getDirectionVector();
			CompoundTag itemTag = NBTIO.putItemHelper(bomb);
			EntityItem itemEntity = createItemEntity(loc, itemTag, v3, p);
			itemEntity.spawnToAll();
			cooldown.put(p.getUniqueId(), System.currentTimeMillis());
			int lvl = 4;
			new NukkitRunnable() {

				@Override
				public void run() {
					detonatebomb(itemEntity.getLocation(), lvl, e, p);
					itemEntity.despawnFromAll();
					itemEntity.close();
					itemEntity.kill();
				}
			}.runTaskLater(Loader.getLoader(), 5 * 20);

		} else if (i.getCustomName().equals(ItemStorage.largeBomb().getCustomName())) {
			e.setCancelled();
			if (cooldown.containsKey(p.getUniqueId())) {
				long secondsLeft = ((cooldown.get(p.getUniqueId()) / 1000) + 5) - (System.currentTimeMillis() / 1000);
				if (secondsLeft > 0) {
					p.sendActionBar(
							StringUtils.getPrefix() + "You can throw another bomb in " + secondsLeft + " seconds.");
					return;
				}
			}
			GeneralUtils.pop(i, p, 1);
			Item bomb = new Item(385);
			Location loc = p.getLocation().add(new Vector3(0, 2, 0));
			Vector3 v3 = p.getDirectionVector();
			CompoundTag itemTag = NBTIO.putItemHelper(bomb);
			EntityItem itemEntity = createItemEntity(loc, itemTag, v3, p);
			itemEntity.spawnToAll();
			cooldown.put(p.getUniqueId(), System.currentTimeMillis());
			int lvl = 8;
			new NukkitRunnable() {

				@Override
				public void run() {
					detonatebomb(itemEntity.getLocation(), lvl, e, p);
					itemEntity.despawnFromAll();
					itemEntity.close();
					itemEntity.kill();
				}
			}.runTaskLater(Loader.getLoader(), 5 * 20);

		}
	}

	public EntityItem createItemEntity(Location loc, CompoundTag itemTag, Vector3 v3, Player p) {
		EntityItem itemEntity = new EntityItem(
				p.getLevel().getChunk((int) loc.getX() >> 4, (int) loc.getZ() >> 4, true),
				new CompoundTag()
						.putList(new ListTag<DoubleTag>("Pos").add(new DoubleTag("", loc.getX()))
								.add(new DoubleTag("", loc.getY())).add(new DoubleTag("", loc.getZ())))

						.putList(new ListTag<DoubleTag>("Motion").add(new DoubleTag("", v3.x))
								.add(new DoubleTag("", v3.y)).add(new DoubleTag("", v3.z)))

						.putList(new ListTag<FloatTag>("Rotation")
								.add(new FloatTag("", new java.util.Random().nextFloat() * 360))
								.add(new FloatTag("", 0)))

						.putShort("Health", 5).putCompound("Item", itemTag).putShort("PickupDelay", Integer.MAX_VALUE));
		return itemEntity;
	}

	public void detonatebomb(Vector3 v3, int lvl, PlayerInteractEvent e, Player p) {
		int ox = v3.getFloorX();
		int oy = v3.getFloorY();
		int oz = v3.getFloorZ();
		int r = (int) (2.0D + Math.floor(lvl / 4));
		for (int x = ox - r; x <= ox + r; x++) {
			for (int y = oy - r; y <= oy + r; y++) {
				for (int z = oz - r; z <= oz + r; z++) {
					double dist = Math.sqrt((x - ox) * (x - ox) + (y - oy) * (y - oy) + (z - oz) * (z - oz));
					if (dist <= r) {
						Location loc = new Location(x, y, z, p.getLevel());
						Block b = p.getLevel().getBlock(loc);
						if (MineUtils.isLocInMine(loc)) {
							if (b.getId() == 19) {
								RandomCollection<LuckyReward> randomrewards = new RandomCollection<>();
								for (LuckyReward lr : LuckyRewardStorage.rews()) {
									randomrewards.add(lr.getChance(), lr);
								}
								LuckyReward realReward = randomrewards.next();
								e.getPlayer().sendTitle(StringUtils.translateColors("&b&lA Lucky Block"),
										StringUtils.translateColors("&bWas just mined!"));
								e.getPlayer().sendActionBar(StringUtils.getPrefix() + "You have been given the reward "
										+ realReward.getName() + " from the luckyblock!");
								Loader.getLoader().getServer().dispatchCommand(new ConsoleCommandSender(),
										realReward.getCmds().replace("{name}", e.getPlayer().getName()));
							}
							e.getPlayer().getLevel().setBlock(b.getLocation(), new BlockAir());
							if (b.getId() != 19) {
								e.getPlayer().getInventory().addItem(b.toItem());
							}
							if (!EventsListener.playersOrbsBooster.containsKey(e.getPlayer())) {
								OrbEconomyUtils.addPlayerBalance(e.getPlayer(), 1);
							} else {
								OrbEconomyUtils.addPlayerBalance(e.getPlayer(),
										1 * EventsListener.playersOrbsBooster.get(e.getPlayer()));
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Item i = p.getInventory().getItemInHand();
		for (int it = 0; it <= 20; it++) {
			if (i.getCustomName().equals(ItemStorage.sellBoosterTenMinutes(it).getCustomName())) {
				if (playersSellBooster.containsKey(p)) {
					p.sendActionBar(StringUtils.getPrefix() + "Booster already active.");
					return;
				}
				GeneralUtils.pop(i, p, 1);
				p.sendMessage(StringUtils.getPrefix() + "Booster activated! Now getting x" + it
						+ " money! /sell or autosell now!");
				playersSellBooster.put(p, it);
				new NukkitRunnable() {
					int i1 = 600;

					@Override
					public void run() {
						if (i1 <= 0) {
							playersSellBooster.remove(p);
							p.sendMessage(StringUtils.getPrefix() + "Booster completed!");
							cancel();
						}
						i1--;
					}
				}.runTaskTimer(Loader.getLoader(), 0, 20);
			} else if (i.getCustomName().equals(ItemStorage.orbsBoosterTenMinutes(it).getCustomName())) {
				if (playersOrbsBooster.containsKey(p)) {
					p.sendActionBar(StringUtils.getPrefix() + "Booster already active.");
					return;
				}
				GeneralUtils.pop(i, p, 1);
				p.sendMessage(StringUtils.getPrefix() + "Booster activated! Now getting x" + it + " orbs! mine now!");
				playersOrbsBooster.put(p, it);
				new NukkitRunnable() {
					int i1 = 600;

					@Override
					public void run() {
						if (i1 <= 0) {
							playersOrbsBooster.remove(p);
							p.sendMessage(StringUtils.getPrefix() + "Booster completed!");
							cancel();
						}
						i1--;
					}
				}.runTaskTimer(Loader.getLoader(), 0, 20);
			}
		}
		if (i.getCustomName().equals(ItemStorage.randomTag().getCustomName())) {
			if (hasAllTags(p)) {
				GeneralUtils.pop(i, p, 1);
				EconomyAPI.getInstance().addMoney(p, 250000D);
				p.sendMessage(StringUtils.getPrefix() + StringUtils
						.translateColors("You already have all /tags! Given $250,000. &f(Not including custom)"));
				return;
			} else {
				String perm = getRandomTagPerm(p);
				p.addAttachment(Loader.getLoader(), perm, true);
				p.sendMessage(StringUtils.getPrefix() + "You have been granted to the tag " + tagByPerm(perm)
						+ " use /tags now!");
			}
		}
	}

}
