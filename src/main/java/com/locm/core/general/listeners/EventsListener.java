package com.locm.core.general.listeners;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

import cn.nukkit.event.player.*;
import cn.nukkit.item.ItemHoeGold;
import cn.nukkit.item.ItemTool;
import com.locm.core.Loader;
import com.locm.core.event.player.PlayerPushButtonEvent;
import com.locm.core.event.player.PlayerRankUpEvent;
import com.locm.core.format.ChatFormat;
import com.locm.core.kits.KitHandler;
import com.locm.core.kits.obj.Kit;
import com.locm.core.mines.LuckyRewardStorage;
import com.locm.core.mines.obj.LuckyReward;
import com.locm.core.mines.obj.Mine;
import com.locm.core.ranks.obj.Rank;
import com.locm.core.ranks.storage.RankStorage;
import com.locm.core.utils.GeneralUtils;
import com.locm.core.utils.ItemStorage;
import com.locm.core.utils.MineUtils;
import com.locm.core.utils.OrbEconomyUtils;
import com.locm.core.utils.RandomCollection;
import com.locm.core.utils.RankUtils;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.block.BlockAir;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.LeavesDecayEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.ItemSpawnEvent;
import cn.nukkit.entity.Entity;
import cn.nukkit.network.protocol.LoginPacket;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

public class EventsListener implements Listener {

	public static HashMap<UUID, Long> cooldown = new HashMap<>();
	public static HashMap<Player, Integer> playersSellBooster = new HashMap<>();
	public static HashMap<Player, Integer> playersOrbsBooster = new HashMap<>();
	public static Config players = Loader.getLoader().getPlayerCfg();
	public static File playersFile = Loader.getLoader().getPlayersFile();

	@EventHandler(ignoreCancelled = true)
	public void DataPacketReceiveEvent(DataPacketReceiveEvent e) {
		if (e.getPacket() instanceof LoginPacket) {
			((LoginPacket) e.getPacket()).username = ((LoginPacket) e.getPacket()).username.replace(" ", "_");
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		if(!player.getLevel().getName().contains("skyblock")){
			if(player.isOp()) return;
			if(MineUtils.isLocInMine(event.getBlock().getLocation())){
				if(event.getBlock().getId() == BlockID.CRAFTING_TABLE) return;
			}
			event.setCancelled();
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		event.setKeepInventory(true);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event){
		if(event instanceof EntityDamageByEntityEvent){
			Entity entity = event.getEntity();
			if(!entity.getLevel().getName().contains("skyblock")){
				event.setCancelled();
			}
		}
	}

	@EventHandler
	public void onClickEnchantTable(PlayerInteractEvent event){
		Block block = event.getBlock();
		if(block.getId() == BlockID.ENCHANTING_TABLE || block.getId() == BlockID.ENCHANTMENT_TABLE || block.getId() == BlockID.ENCHANT_TABLE){
			event.setCancelled();
			event.getPlayer().sendActionBar(TextFormat.colorize("&l&cKhông thể dùng"));
		}
	}

	@EventHandler
	public void onPush(PlayerPushButtonEvent event){
		Player player = event.getPlayer();
		Rank rank = RankUtils.getRankByPlayer(player);
		Mine mine = MineUtils.getMineByName(rank.getName());
		for(Player mine_player : Server.getInstance().getOnlinePlayers().values()){
			Rank mine_rank = RankUtils.getRankByPlayer(mine_player);
			if(rank.getName().equals(mine_rank.getName())){
				if(mine_player.getLevel().getName().contains("ps")){
					Server.getInstance().dispatchCommand(mine_player, "hub");
					mine_player.sendMessage(TextFormat.colorize("&e[&fTHÔNG BÁO&e] &fKhu mỏ của bạn đang được làm mới, vui lòng quay lại sau vài giây"));
				}
			}
		}
		if(mine != null){
			mine.resetMine();
		}
	}

	@EventHandler
	public void onBucket(PlayerBucketFillEvent event){
		Player player = event.getPlayer();
		if(!player.getLevel().getName().contains("skyblock")){
			if(player.isOp()) return;
			event.setCancelled();
		}
	}

	@EventHandler
	public void onBucketEmpty(PlayerBucketEmptyEvent event){
		Player player = event.getPlayer();
		if(!player.getLevel().getName().contains("skyblock")){
			if(player.isOp()) return;
			event.setCancelled();
		}
	}

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) throws NoSuchFieldException, IllegalAccessException{
		Entity i = event.getEntity();
		int itemLifetime = 6000 - 2 * 20 * 60;
		i.namedTag.putShort("Age", itemLifetime);
		Class<?> c = i.getClass().getSuperclass();
		Field f = c.getDeclaredField("age");
		f.setAccessible(true);
		f.set(i, itemLifetime);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		event.setDropExp(0);
	}

	@EventHandler
	public void leaveDecay(LeavesDecayEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if(!event.isCancelled()) {
			String player = event.getPlayer().getName();
			Loader.getInstance().getLogger().info(player + ": " + event.getMessage());
			if(event.getMessage().contains("/me")) event.setCancelled();
		}
	}

	@EventHandler
	public void onField(PlayerInteractEvent event) {
		if (event.getAction() == PlayerInteractEvent.Action.PHYSICAL) {
			if (event.getBlock().getId() == BlockID.FARMLAND) {
				event.setCancelled();
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!player.hasPlayedBefore()) {
			for (Kit kit : KitHandler.getAllKitsFromConfig()) {
				if (kit.getName().equals("Daily")) {
					kit.giveKitToPlayer(e.getPlayer());
				}
			}
		}
		if (!players.exists("Players." + player.getName() + ".rank")) {
			players.set("Players." + player.getName() + ".rank", RankStorage.A.getName());
			players.set("Players." + player.getName() + ".prestigeLevel", 1);
			players.save(playersFile);
			Item axe = Item.get(ItemID.WOODEN_AXE);
			axe.setCustomName(TextFormat.colorize("&l&eRìu khởi đầu"));
			Item pickaxe = Item.get(ItemID.WOODEN_PICKAXE);
			pickaxe.setCustomName(TextFormat.colorize("&l&eCúp khởi đầu"));
			player.getInventory().addItem(axe);
			player.getInventory().addItem(pickaxe);
			GeneralUtils.playSound(player, Sound.NOTE_HARP);
			for(String mess : Loader.getInstance().getConfig().getStringList("first-join")){
				player.sendMessage(TextFormat.colorize(mess));
			}
		}else{
			for(String mess : Loader.getInstance().getConfig().getStringList("join")){
				player.sendMessage(TextFormat.colorize(mess));
			}
			GeneralUtils.playSound(player, Sound.NOTE_BASS);	
		}
		Position pos = Server.getInstance().getDefaultLevel().getSafeSpawn();
		Random randpos = new Random();
		int facing = randpos.nextInt(2);
		if(facing == 0) {
			pos.add(2.0D, 0.0D, 0.0D);
		}
		if(facing == 1) {
			pos.add(-2.0D, 0.0D, 0.0D);
		}
		if(facing == 2) {
			pos.add(0D, 0.0D, 2.0D);
		}
		if(facing == 3) {
			pos.add(0D, 0.0D, -2.0D);
		}
		player.teleport(pos);
		if(!player.hasPlayedBefore()){
			e.setJoinMessage("§l§eNEW§f " + player.getName());
		}else{
			e.setJoinMessage("§l§a•§f " + player.getName());
		}
	}

	@EventHandler
	public void onChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		if(cooldown.containsKey(player.getUniqueId())){
			long left = ((cooldown.get(player.getUniqueId()) / 1000) + 2) - (System.currentTimeMillis() / 1000);
			if(left > 0){
				player.sendMessage(TextFormat.colorize("&cVui lòng chat sau &e" + left + " &cgiây nữa!"));
				event.setCancelled();
				return;
			}
		}
		cooldown.put(player.getUniqueId(), System.currentTimeMillis());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage("§l§c•§f " + e.getPlayer().getName());
	}

	@EventHandler
	public void onRankUp(PlayerRankUpEvent event){
		Player player = event.getPlayer();
		String nametag = ChatFormat.getNameTag(player);
		player.setNameTag(nametag);
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
							StringUtils.color("&l&fBạn có thể dùng boom sau &e" + secondsLeft + "&f giây."));
					return;
				}
			}
			GeneralUtils.pop(i, p, 1);
			Item bomb = new Item(385);
			Location loc = p.getLocation().add(new Vector3(0, 2, 0));
			Vector3 v3 = p.getDirectionVector();
			CompoundTag itemTag = NBTIO.putItemHelper(bomb);
			EntityItem itemEntity = createItemEntity(loc, itemTag, v3, p);
			itemEntity.setNameTag("&l&cBOOMMMM!!!!");
			itemEntity.isNameTagAlwaysVisible();
			itemEntity.spawnToAll();
			cooldown.put(p.getUniqueId(), System.currentTimeMillis());
			int lvl = 2;
			new NukkitRunnable() {
				private int min = 5;

				@Override
				public void run() {
					if(this.min == 0){
						detonatebomb(itemEntity.getLocation(), lvl, e, p);
						itemEntity.despawnFromAll();
						itemEntity.close();
						itemEntity.kill();						
					}else{
						itemEntity.setNameTag("&l&cBOOMMMM!!!!\n&l&fPhát nổ sau&e " + this.min + " &fgiây");
						this.min -= 1;
					}
				}
			}.runTaskLater(Loader.getLoader(), 20);

		} else if (i.getCustomName().equals(ItemStorage.mediumBomb().getCustomName())) {
			e.setCancelled();
			if (cooldown.containsKey(p.getUniqueId())) {
				long secondsLeft = ((cooldown.get(p.getUniqueId()) / 1000) + 5) - (System.currentTimeMillis() / 1000);
				if (secondsLeft > 0) {
					p.sendActionBar(
							StringUtils.color("&l&fBạn có thể dùng boom sau &e" + secondsLeft + "&f giây."));
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
							StringUtils.color("&l&fBạn có thể dùng boom sau &e" + secondsLeft + "&f giây."));
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

	@EventHandler
	public void onUseTool(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Item hand = p.getInventory().getItemInHand();

		String world = p.getLevel().getName();

		if(!world.startsWith("ps") && !world.startsWith("skyblock")) {
			if(!p.isOp()) {
				event.setCancelled();
				p.sendPopup(StringUtils.color("&l&cBạn không thể dùng item này ở đây!"));
			}
		}
	}

	public EntityItem createItemEntity(Location loc, CompoundTag itemTag, Vector3 v3, Player p) {
		return new EntityItem(
				p.getLevel().getChunk((int) loc.getX() >> 4, (int) loc.getZ() >> 4, true),
				new CompoundTag()
						.putList(new ListTag<DoubleTag>("Pos").add(new DoubleTag("", loc.getX()))
								.add(new DoubleTag("", loc.getY())).add(new DoubleTag("", loc.getZ())))

						.putList(new ListTag<DoubleTag>("Motion").add(new DoubleTag("", v3.x))
								.add(new DoubleTag("", v3.y)).add(new DoubleTag("", v3.z)))

						.putList(new ListTag<FloatTag>("Rotation")
								.add(new FloatTag("", new Random().nextFloat() * 360))
								.add(new FloatTag("", 0)))

						.putShort("Health", 5).putCompound("Item", itemTag).putShort("PickupDelay", Integer.MAX_VALUE));
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
								e.getPlayer().sendTitle(StringUtils.translateColors("&b&lLucky Block"),
										StringUtils.translateColors("&bBạn vừa nhận được quà!"));
								e.getPlayer().sendActionBar(StringUtils.color("&l&eBạn vừa nhận được&f "
										+ realReward.getName() + " &etừ luckyblock!"));
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
										EventsListener.playersOrbsBooster.get(e.getPlayer()));
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
					p.sendActionBar(StringUtils.color("&l&eSử dụng:&f SELL BOOSTER"));
					return;
				}
				GeneralUtils.pop(i, p, 1);
				p.sendMessage(TextFormat.colorize("&l&fBạn đang được &fx" + it
						+ " &fxu khi bán! &e/sell&f để bán!"));
				playersSellBooster.put(p, it);
				new NukkitRunnable() {
					int i1 = 600;

					@Override
					public void run() {
						if (i1 <= 0) {
							playersSellBooster.remove(p);
							p.sendMessage(StringUtils.color("&l&eSELL BOOSTER&f của bạn đã hết hạn!"));
							cancel();
						}
						i1--;
					}
				}.runTaskTimer(Loader.getLoader(), 0, 20);
			} else if (i.getCustomName().equals(ItemStorage.orbsBoosterTenMinutes(it).getCustomName())) {
				if (playersOrbsBooster.containsKey(p)) {
					p.sendActionBar(StringUtils.color("&l&eSử dụng:&f ORBS BOOSTER"));
					return;
				}
				GeneralUtils.pop(i, p, 1);
				p.sendMessage(StringUtils.color("&l&fBạn đang được &ex" + it + "&f orbs!"));
				playersOrbsBooster.put(p, it);
				new NukkitRunnable() {
					int i1 = 600;

					@Override
					public void run() {
						if (i1 <= 0) {
							playersOrbsBooster.remove(p);
							p.sendMessage(StringUtils.color("&l&eORB BOOSTER&f của bạn đã hết hạn!"));
							cancel();
						}
						i1--;
					}
				}.runTaskTimer(Loader.getLoader(), 0, 20);
			}
		}
	}

}
