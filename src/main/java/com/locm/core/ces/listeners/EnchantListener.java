package com.locm.core.ces.listeners;

import java.util.Objects;
import java.util.Random;

import cn.nukkit.utils.TextFormat;
import com.locm.core.Loader;
import com.locm.core.general.cmd.SellCommand;
import com.locm.core.general.listeners.EventsListener;
import com.locm.core.mines.LuckyRewardStorage;
import com.locm.core.mines.obj.LuckyReward;
import com.locm.core.utils.CEUtils;
import com.locm.core.utils.MineUtils;
import com.locm.core.utils.NumberUtils;
import com.locm.core.utils.OrbEconomyUtils;
import com.locm.core.utils.RandomCollection;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Location;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Position;
import cn.nukkit.potion.Effect;

public class EnchantListener implements Listener {

	@EventHandler
	public void onSwitchItem(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Item tool = p.getInventory().getItemInHand();
		if (CEUtils.containsEnchantment(tool, CEUtils.getCEByDisplayName(StringUtils.translateColors("&3Gears")))) {
			int lvl = CEUtils.getLevelOfEnchantByDisplayName(StringUtils.translateColors("&3Gears"), tool);
			p.addEffect(Effect.getEffect(Effect.SPEED).setDuration(Integer.MAX_VALUE).setAmplifier(lvl));
		} else {
			p.removeEffect(Effect.SPEED);
		}
	}

	@EventHandler
	public void onMine(BlockBreakEvent e) {
			if ((MineUtils.isLocInMine(e.getBlock().getLocation())) || e.getPlayer().isOp()) {
				if(MineUtils.testMine(e.getPlayer(), e.getBlock().getLocation())){
					if(e.getPlayer().isOp()) return;
					e.setCancelled();
					return;
				}
				Item tool = e.getPlayer().getInventory().getItemInHand();
				if (e.getBlock().getId() == 19) {
					RandomCollection<LuckyReward> randomrewards = new RandomCollection<>();
					for (LuckyReward lr : LuckyRewardStorage.rews()) {
						randomrewards.add(lr.getChance(), lr);
					}
					e.setCancelled();
					e.getPlayer().getLevel().setBlock(e.getBlock().getLocation(), new BlockAir());
					LuckyReward realReward = randomrewards.next();
					e.getPlayer().sendActionBar(StringUtils.getPrefix() + "Bạn vừa nhận được "
							+ realReward.getName() + " từ luckyblock!");
					Loader.getLoader().getServer().dispatchCommand(new ConsoleCommandSender(),
						realReward.getCmds().replace("{name}", e.getPlayer().getName()));
					e.getPlayer().sendTitle(StringUtils.translateColors("&b&lLucky Block"),
						StringUtils.translateColors("&bVừa mới được khai thác!"));
					return;
				}
				if (CEUtils.containsEnchantment(tool,
						CEUtils.getCEByDisplayName(StringUtils.translateColors("&fMagnet")))) {
					if (!CEUtils.containsEnchantment(tool,
							CEUtils.getCEByDisplayName(StringUtils.translateColors("&dAutoSell")))) {
						PlayerInventory inventoryAutoAdd = e.getPlayer().getInventory();
						Item[] itemsToAdd = e.getDrops();
						if (!e.isCancelled()) {
							if (CEUtils.containsEnchantment(tool, Enchantment.getEnchantment(18))) {
								runFortuneMagnet(e.getPlayer(), e.getBlock(), e);
								Item[] dropsNull = { new Item(0) };
								e.setDrops(dropsNull);
								return;
							}
							if (e.getBlock().getId() == 15) {
								itemsToAdd = (new Item[] { new Item(265) });
							} else if (e.getBlock().getId() == 14) {
								itemsToAdd = (new Item[] { new Item(266) });
							}
							inventoryAutoAdd.addItem(itemsToAdd);
						}
						Item[] dropsNull = { new Item(0) };
						e.setDrops(dropsNull);
						return;
					}
				}
				if (CEUtils.containsEnchantment(tool,
						CEUtils.getCEByDisplayName(StringUtils.translateColors("&dAutoSell")))) {
					if (SellCommand.canSell(e.getBlock().toItem())) {
						e.setCancelled();
						e.getPlayer().getLevel().setBlock(e.getBlock().getLocation(), new BlockAir());
						SellCommand.sellItem(e.getPlayer(), e.getBlock().toItem());
						return;
					}
				}
				if (CEUtils.containsEnchantment(tool,
						CEUtils.getCEByDisplayName(StringUtils.translateColors("&6Donator")))) {
					int lvl = CEUtils.getLevelOfEnchantByDisplayName(StringUtils.translateColors("&6Donator"),
							tool);
					if (new Random().nextInt(101) <= 1) {
						for (Player o : Loader.getLoader().getServer().getOnlinePlayers().values()) {
							if (!EventsListener.playersOrbsBooster.containsKey(e.getPlayer())) {
								OrbEconomyUtils.addPlayerBalance(o, lvl * 10);
								o.sendPopup(StringUtils.getPrefix() + e.getPlayer().getName() + " Vừa tặng cho toàn máy chủ "
										+ lvl * 10 + " orbs (Phù phép Donator)!");
							} else {
								OrbEconomyUtils.addPlayerBalance(o,
										(lvl * 10) * EventsListener.playersOrbsBooster.get(e.getPlayer()));
								o.sendPopup(StringUtils.getPrefix() + e.getPlayer().getName() + " Vừa tặng cho toàn máy chủ"
										+ (lvl * 10) * EventsListener.playersOrbsBooster.get(e.getPlayer())
										+ " orbs (Phù phép Donator)");
							}
						}
					}
				}
				if (CEUtils.containsEnchantment(tool,
						CEUtils.getCEByDisplayName(StringUtils.translateColors("&cExplosive")))) {
					runExplosive(e.getPlayer(), e.getBlock(), e);
					return;
				}
				if (CEUtils.containsEnchantment(tool, Enchantment.getEnchantment(18))) {
					runFortune(e.getPlayer(), e.getBlock(), e);
					return;
				}
				if (CEUtils.containsEnchantment(tool,
					CEUtils.getCEByDisplayName(StringUtils.translateColors("&eJackHammer")))) {
					runJackHammer(e.getPlayer(), e.getBlock(), e);
					return;
				}
				if (e.getBlock().getId() == 15) {
					e.setDrops(new Item[] { new Item(265) });
				} else if (e.getBlock().getId() == 14) {
					e.setDrops(new Item[] { new Item(266) });
				}
			} else {
				if(!e.getPlayer().getLevel().getName().contains("skyblock")){
					e.setCancelled();
				}
			}
	}

	public void runFortuneMagnet(Player player, Block b, BlockBreakEvent e) {
		Item tool = player.getInventory().getItemInHand();
		int lvl = tool.getEnchantment(18).getLevel();
		int rand = NumberUtils.random(1, 100);
		if (rand > lvl) {
			return;
		}
		if (e.getBlock().getId() == 15) {
			e.setDrops(new Item[] { new Item(265) });
		} else if (e.getBlock().getId() == 14) {
			e.setDrops(new Item[] { new Item(266) });
		}
		for (Item i : e.getDrops()) {
			for (int o = 0; o < lvl + 1; o++) {
				if (CEUtils.containsEnchantment(tool,
						CEUtils.getCEByDisplayName(StringUtils.translateColors("&fMagnet")))) {
					if (!CEUtils.containsEnchantment(tool,
							CEUtils.getCEByDisplayName(StringUtils.translateColors("&dAutoSell")))) {
						PlayerInventory inventoryAutoAdd = player.getInventory();
						inventoryAutoAdd.addItem(i);
					}
				}
			}
		}
	}

	public void runFortune(Player player, Block b, BlockBreakEvent e) {
		Item tool = player.getInventory().getItemInHand();
		int lvl = tool.getEnchantment(18).getLevel();
		for (Item i : e.getDrops()) {
			int rand = NumberUtils.random(1, 100);
			if (rand > lvl) {
				return;
			}
			for (int o = 0; o < lvl + 1; o++) {
				if (CEUtils.containsEnchantment(tool,
						CEUtils.getCEByDisplayName(StringUtils.translateColors("&fMagnet")))) {
					if (!CEUtils.containsEnchantment(tool,
							CEUtils.getCEByDisplayName(StringUtils.translateColors("&dAutoSell")))) {
						PlayerInventory inventoryAutoAdd = player.getInventory();
						if (e.getBlock().getId() == 15) {
							i = new Item(265);
						} else if (e.getBlock().getId() == 14) {
							i = new Item(266);
						}
						i.setCount(lvl);
						inventoryAutoAdd.addItem(i);
					}
				}
				if (CEUtils.containsEnchantment(tool,
						CEUtils.getCEByDisplayName(StringUtils.translateColors("&dAutoSell")))) {
					if (e.getBlock().getId() == 15) {
						i = new Item(265);
					} else if (e.getBlock().getId() == 14) {
						i = new Item(266);
					}
					if (SellCommand.canSell(i)) {
						e.setCancelled();
						SellCommand.sellItem(e.getPlayer(), i);
					}
				} else {
					if (e.getBlock().getId() == 15) {
						i = new Item(265);
					} else if (e.getBlock().getId() == 14) {
						i = new Item(266);
					}
					player.getLevel().dropItem(b.getLocation(), i);
				}
			}
		}
	}

	public void runJackHammer(Player player, Block block, BlockBreakEvent event){
		Position pos = block.getLocation();
		Item tool = player.getInventory().getItemInHand();
		int lvl = CEUtils.getLevelOfEnchantByDisplayName(StringUtils.translateColors("&eJackHammer"), tool);
		int chance = new Random().nextInt(6 - lvl);
		if(chance != 0) return;
		int xFrom = (int) (pos.getX() - lvl);
		int yFrom = (int) (pos.getY() - new Random().nextInt(lvl - 1));
		int zFrom = (int) (pos.getZ() - new Random().nextInt(lvl));
		if(lvl >= 4){
			yFrom = (int) (pos.getY() - new Random().nextInt(lvl - 1));
		}
		int xTo = (int) (pos.getX() + lvl - new Random().nextInt(2));
		int yTo = (int) (pos.getY());
		if(lvl >= 3){
			yTo = (int) (pos.getY() + new Random().nextInt(lvl - 1));
		}
		int zTo = (int) (pos.getZ() + lvl - 1);
		Block temp;
		for (int y = yFrom; y <= yTo; y++) {
			for (int x = xFrom; x <= xTo; x++) {
				for (int z = zFrom; z <= zTo; z++) {
					if (x == block.getX() && y == block.getY() && z == block.getZ()) {
						continue; // ignore this block so the durability code executes
					}
					Location loc = new Location(x, y, z, block.getLevel());
					temp = block.getLevel().getBlock(loc);
					if(MineUtils.isLocInMine(loc)){
						if(temp.getId() == Block.AIR) continue;
						if (CEUtils.containsEnchantment(tool,
								Objects.requireNonNull(CEUtils.getCEByDisplayName(StringUtils.translateColors("&fMagnet"))))) {
							player.getInventory().addItem(temp.toItem());
						}else{
							loc.getLevel().dropItem(loc, temp.toItem());
						}
						loc.getLevel().setBlock(loc, Block.get(Block.AIR));
					}
				}
			}
		}
		//tool.setDamage(tool.getDamage() - new Random().nextInt(30) + lvl);
		player.getInventory().setItemInHand(tool);
		player.sendActionBar(TextFormat.colorize("&l&cJACKHAMMER!!!"));
	}


	public void runExplosive(Player player, Block block, BlockBreakEvent e) {
		Location location = block.getLocation();
		Item tool = player.getInventory().getItemInHand();
		int lvl = CEUtils.getLevelOfEnchantByDisplayName(StringUtils.translateColors("&cExplosive"), tool);
		int rand = NumberUtils.random(1, 100);
		if (rand > lvl) {
			return;
		}
		int particles = 10;
		for (int rep = 0; rep <= particles; rep++) {
			block.getLevel().addParticleEffect(location, ParticleEffect.LAVA_PARTICLE);
		}
		int ox = location.getFloorX();
		int oy = location.getFloorY();
		int oz = location.getFloorZ();

		int r = (int) (2.0D + Math.floor(lvl / 4));
		for (int x = ox - r; x <= ox + r; x++) {
			for (int y = oy - r; y <= oy + r; y++) {
				for (int z = oz - r; z <= oz + r; z++) {
					double dist = Math.sqrt((x - ox) * (x - ox) + (y - oy) * (y - oy) + (z - oz) * (z - oz));
					if (dist <= r) {
						Location loc = new Location(x, y, z, block.getLevel());
						Block b = location.getLevel().getBlock(loc);
						if (b.getId() == 19) {
							RandomCollection<LuckyReward> randomrewards = new RandomCollection<>();
							for (LuckyReward lr : LuckyRewardStorage.rews()) {
								randomrewards.add(lr.getChance(), lr);
							}
							e.setCancelled();
							LuckyReward realReward = randomrewards.next();
							e.getPlayer().sendTitle(StringUtils.translateColors("&b&lLucky Block"),
									StringUtils.translateColors("&bVừa mới được khai thác!"));
							e.getPlayer().sendActionBar(StringUtils.getPrefix() + "Bạn vừa nhận được phần thường "
									+ realReward.getName() + " từ Lucky Block!");
							Loader.getLoader().getServer().dispatchCommand(new ConsoleCommandSender(),
									realReward.getCmds().replace("{name}", e.getPlayer().getName()));
						}
						if (MineUtils.isLocInMine(loc)) {
							if (CEUtils.containsEnchantment(tool,
									CEUtils.getCEByDisplayName(StringUtils.translateColors("&fMagnet")))) {
								if (!CEUtils.containsEnchantment(tool,
										CEUtils.getCEByDisplayName(StringUtils.translateColors("&dAutoSell")))) {
									if (b.getId() != 19) {
										PlayerInventory inventoryAutoAdd = e.getPlayer().getInventory();
										Item[] itemToAdd = e.getDrops();
										if (e.getBlock().getId() == 15) {
											itemToAdd = (new Item[] { new Item(265) });
										} else if (e.getBlock().getId() == 14) {
											itemToAdd = (new Item[] { new Item(266) });
										}
										inventoryAutoAdd.addItem(itemToAdd);
									}
								}
							} else if (CEUtils.containsEnchantment(tool,
									CEUtils.getCEByDisplayName(StringUtils.translateColors("&dAutoSell")))) {
								if (SellCommand.canSell(e.getBlock().toItem())) {
									e.setCancelled();
									SellCommand.sellItem(e.getPlayer(), b.toItem());
								}
							}
							e.getPlayer().getLevel().setBlock(b.getLocation(), new BlockAir());
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
}
