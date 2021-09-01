package com.locm.core.ces.listeners;

import java.util.HashMap;
import java.util.Random;

import com.locm.core.Loader;
import com.locm.core.ces.enchants.EnchantHandler;
import com.locm.core.ces.forms.FormStorage;
import com.locm.core.ces.obj.CustomEnchant;
import com.locm.core.ces.obj.EnchantType;
import com.locm.core.ces.obj.TempCE;
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
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Location;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Position;
import cn.nukkit.potion.Effect;

public class EnchantListener implements Listener {

	public static HashMap<Player, TempCE> costOfEnchantByPlayer = new HashMap<>();
	public static HashMap<Player, CustomEnchant> ceByPlayer = new HashMap<>();

	@EventHandler
	public void onResponse1(PlayerFormRespondedEvent e) {
		Player p = e.getPlayer();
		if (e.getWindow() != null) {
			if (e.getWindow() instanceof FormWindowSimple) {
				FormWindowSimple gui = (FormWindowSimple) e.getWindow();
				if (gui != null && gui.getResponse() != null) {
					if (gui.getResponse().getClickedButton().getText() != null) {
						String responseName = gui.getResponse().getClickedButton().getText();
						if (responseName != null) {
							if (CEUtils.getCEByDisplayName(responseName) != null) {
								CustomEnchant ce = CEUtils.getCEByDisplayName(responseName);
								if (ce != null) {
									p.removeAllWindows();
									p.showFormWindow(FormStorage.ceMenu(p.getInventory().getItemInHand(), ce));
									ceByPlayer.put(p, ce);
								}
							}
						}
					}
				}
			} else if (e.getWindow() instanceof FormWindowCustom) {
				FormWindowCustom gui = (FormWindowCustom) e.getWindow();
				if (gui != null) {
					if (ceByPlayer.containsKey(p)) {
						if (gui.getTitle().equals(StringUtils.translateColors(
								ceByPlayer.get(p).getDisplayNameOfEnchantment() + " &r&b&nEnchantment&d&n Purchase"))) {
							if (e.getResponse() != null) {
								if (!costOfEnchantByPlayer.containsKey(p)) {
									if (gui.getResponse().getSliderResponse(1) != 0) {
										float lvl = gui.getResponse().getSliderResponse(1);
										p.removeAllWindows();
										p.showFormWindow(FormStorage.confirmMenu((int) lvl, ceByPlayer.get(p)));
										costOfEnchantByPlayer.put(p,
												new TempCE(ceByPlayer.get(p), (int) lvl,
														NumberUtils.getCostOfEnchantmentByLevel((int) lvl,
																ceByPlayer.get(p).getCostMultiplier())));
										ceByPlayer.remove(p);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onResponse2(PlayerFormRespondedEvent e) {
		Player p = e.getPlayer();
		if (e.getWindow() != null) {
			if (p.getInventory().getItemInHand().getId() == 278) {
				if (e.getWindow() instanceof FormWindowSimple) {
					if (costOfEnchantByPlayer.containsKey(p)) {
						FormWindowSimple gui = (FormWindowSimple) e.getWindow();
						CustomEnchant ce = costOfEnchantByPlayer.get(p).getCe();
						if (gui != null) {
							String responseName = gui.getResponse().getClickedButton().getText();
							if (responseName.contains("Phù phép")) {
								if (ce.getType().equals(EnchantType.CUSTOM)) {
									if (OrbEconomyUtils.hasPlayerBalance(p, costOfEnchantByPlayer.get(p).getCost())) {
										if (CEUtils.getLevelOfEnchantByDisplayName(ce.getDisplayNameOfEnchantment(),
												p.getInventory().getItemInHand()) < costOfEnchantByPlayer.get(p)
														.getLevel()) {
											OrbEconomyUtils.removePlayerBalance(p,
													costOfEnchantByPlayer.get(p).getCost());
											p.sendMessage(StringUtils.translateColors(StringUtils.getPrefix()
													+ "Phù phép thành công!"));
											EnchantHandler.applyEnchantment(p, p.getInventory().getItemInHand(), ce,
													costOfEnchantByPlayer.get(p).getLevel());
											costOfEnchantByPlayer.remove(p);
											if(costOfEnchantByPlayer.get(p) != null){
												OrbEconomyUtils.removePlayerBalance(p,
														costOfEnchantByPlayer.get(p).getCost());												
											}
										} else {
											p.sendMessage(StringUtils.getPrefix()
													+ "Cúp của bạn đã có phù phép xịn hơn!.");
											costOfEnchantByPlayer.remove(p);
										}
									} else {
										p.sendMessage(StringUtils.getPrefix()
												+ "Không đủ orbs để phù phép.");
										costOfEnchantByPlayer.remove(p);
									}
								} else {
									if (ce.getDisplayNameOfEnchantment().contains("Unbreaking")) {
										if (OrbEconomyUtils.hasPlayerBalance(p,
												costOfEnchantByPlayer.get(p).getCost())) {
											Enchantment e1 = Enchantment.getEnchantment(Enchantment.ID_DURABILITY);
											e1.setLevel(costOfEnchantByPlayer.get(p).getLevel(), false);
											if (CEUtils.containsEnchantment(p.getInventory().getItemInHand(), e1)) {
												if (CEUtils.isHigherEnchantLevel(p.getInventory().getItemInHand(),
														e1)) {
													EnchantHandler.applyEnchant(p, p.getInventory().getItemInHand(), e1,
															costOfEnchantByPlayer.get(p).getLevel());
													OrbEconomyUtils.removePlayerBalance(p,
															costOfEnchantByPlayer.get(p).getCost());
													costOfEnchantByPlayer.remove(p);
												} else {
													p.sendMessage(StringUtils.getPrefix()
															+ "Cúp của bạn đã có phù phép xịn hơn.");
													costOfEnchantByPlayer.remove(p);
												}
											} else {
												EnchantHandler.applyEnchant(p, p.getInventory().getItemInHand(), e1,
														costOfEnchantByPlayer.get(p).getLevel());
												OrbEconomyUtils.removePlayerBalance(p,
														costOfEnchantByPlayer.get(p).getCost());
												costOfEnchantByPlayer.remove(p);
											}
										} else {
											p.sendMessage(StringUtils.getPrefix()
													+ "Bạn không đủ orbs để phù phép.");
											costOfEnchantByPlayer.remove(p);
										}
									} else if (ce.getDisplayNameOfEnchantment().contains("Efficiency")) {
										if (OrbEconomyUtils.hasPlayerBalance(p,
												costOfEnchantByPlayer.get(p).getCost())) {
											Enchantment e1 = Enchantment.getEnchantment(Enchantment.ID_EFFICIENCY);
											e1.setLevel(costOfEnchantByPlayer.get(p).getLevel(), false);
											if (CEUtils.containsEnchantment(p.getInventory().getItemInHand(), e1)) {
												if (CEUtils.isHigherEnchantLevel(p.getInventory().getItemInHand(),
														e1)) {
													EnchantHandler.applyEnchant(p, p.getInventory().getItemInHand(), e1,
															costOfEnchantByPlayer.get(p).getLevel());
													OrbEconomyUtils.removePlayerBalance(p,
															costOfEnchantByPlayer.get(p).getCost());
													costOfEnchantByPlayer.remove(p);
												} else {
													p.sendMessage(StringUtils.getPrefix()
															+ "Cúp của bạn đã có phù phép xịn hơn.");
													costOfEnchantByPlayer.remove(p);
												}
											} else {
												EnchantHandler.applyEnchant(p, p.getInventory().getItemInHand(), e1,
														costOfEnchantByPlayer.get(p).getLevel());
												OrbEconomyUtils.removePlayerBalance(p,
														costOfEnchantByPlayer.get(p).getCost());
												costOfEnchantByPlayer.remove(p);
											}
										} else {
											p.sendMessage(StringUtils.getPrefix()
													+ "Bạn không đủ orbs để phù phép.");
											costOfEnchantByPlayer.remove(p);
										}
									} else if (ce.getDisplayNameOfEnchantment().contains("Fortune")) {
										if (OrbEconomyUtils.hasPlayerBalance(p,
												costOfEnchantByPlayer.get(p).getCost())) {
											Enchantment e1 = Enchantment.getEnchantment(Enchantment.ID_FORTUNE_DIGGING);
											e1.setLevel(costOfEnchantByPlayer.get(p).getLevel(), false);
											if (CEUtils.containsEnchantment(p.getInventory().getItemInHand(), e1)) {
												if (CEUtils.isHigherEnchantLevel(p.getInventory().getItemInHand(),
														e1)) {
													EnchantHandler.applyEnchant(p, p.getInventory().getItemInHand(), e1,
															costOfEnchantByPlayer.get(p).getLevel());
													OrbEconomyUtils.removePlayerBalance(p,
															costOfEnchantByPlayer.get(p).getCost());
													costOfEnchantByPlayer.remove(p);
												} else {
													p.sendMessage(StringUtils.getPrefix()
															+ "Cúp của bạn đã có phù phép xịn hơn.");
													costOfEnchantByPlayer.remove(p);
												}
											} else {
												EnchantHandler.applyEnchant(p, p.getInventory().getItemInHand(), e1,
														costOfEnchantByPlayer.get(p).getLevel());
												OrbEconomyUtils.removePlayerBalance(p,
														costOfEnchantByPlayer.get(p).getCost());
												costOfEnchantByPlayer.remove(p);
											}
										} else {
											p.sendMessage(StringUtils.getPrefix()
													+ "Bạn không đủ orbs để phù phép.");
											costOfEnchantByPlayer.remove(p);
										}
									}
								}
							} else if (responseName.contains("Từ chối")) {
/*								p.sendMessage(StringUtils.translateColors(StringUtils.getPrefix()
										+ "Denied purchase of " + " " + ce.getDisplayNameOfEnchantment() + "&7!"));*/
								costOfEnchantByPlayer.remove(p);
							}
						}
						costOfEnchantByPlayer.remove(p);
					}
				}
			}
		}
	}

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
		if (true) {
			if ((MineUtils.isLocInMine(e.getBlock().getLocation())) || e.getPlayer().isOp()) {
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

	public void runJackHammer(Player player, Block block, BlockBreakEvent e) {
		Location location = block.getLocation();
		Item tool = player.getInventory().getItemInHand();
		int lvl = CEUtils.getLevelOfEnchantByDisplayName(StringUtils.translateColors("&eJackHammer"), tool);
		int rand = NumberUtils.random(1, 100);
		if (rand > lvl) {
			return;
		}
		Position v1 = MineUtils.getCorners(block.getLocation()).get(0);
		Position v2 = MineUtils.getCorners(block.getLocation()).get(1);
		int x1 = 0;
		int x2 = 0;

		int z1 = 0;
		int z2 = 0;

		int y = location.getFloorY();
		if (v1.getFloorX() > v2.getFloorX()) {
			x1 = v2.getFloorX();
			x2 = v1.getFloorX();
		} else {
			x1 = v1.getFloorX();
			x2 = v2.getFloorX();
		}
		if (v1.getFloorZ() > v2.getFloorZ()) {
			z1 = v2.getFloorZ();
			z2 = v1.getFloorZ();
		} else {
			z1 = v1.getFloorZ();
			z2 = v2.getFloorZ();
		}
		for (int x = x1; x <= x2; x++) {
			for (int z = z1; z <= z2; z++) {
				Location loc = new Location(x, y, z, e.getPlayer().getLevel());
				Block b = loc.getLevel().getBlock(loc);
				if (MineUtils.isLocInMine(loc)) {
					if (b.getId() == 19) {
						RandomCollection<LuckyReward> randomrewards = new RandomCollection<>();
						for (LuckyReward lr : LuckyRewardStorage.rews()) {
							randomrewards.add(lr.getChance(), lr);
						}
						e.setCancelled();
						LuckyReward realReward = randomrewards.next();
						e.getPlayer().sendTitle(StringUtils.translateColors("&b&lLucky Block"),
								StringUtils.translateColors("&bVừa mới được khai thác!"));
						e.getPlayer().sendActionBar(StringUtils.getPrefix() + "Bạn vừa nhận được "
								+ realReward.getName() + " từ Lucky Block!");
						Loader.getLoader().getServer().dispatchCommand(new ConsoleCommandSender(),
								realReward.getCmds().replace("{name}", e.getPlayer().getName()));
					}
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
					Item[] itemToAdd = e.getDrops();
					if (e.getBlock().getId() == 15) {
						itemToAdd = (new Item[] { new Item(265) });
					} else if (e.getBlock().getId() == 14) {
						itemToAdd = (new Item[] { new Item(266) });
					}
					e.getPlayer().getLevel().dropItem(loc, itemToAdd[0]);
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

	public boolean runExplosive(Player player, Block block, BlockBreakEvent e) {
		Location location = block.getLocation();
		Item tool = player.getInventory().getItemInHand();
		int lvl = CEUtils.getLevelOfEnchantByDisplayName(StringUtils.translateColors("&cExplosive"), tool);
		int rand = NumberUtils.random(1, 100);
		if (rand > lvl) {
			return false;
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
		return true;
	}
}
