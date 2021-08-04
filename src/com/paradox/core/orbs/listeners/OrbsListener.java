package com.paradox.core.orbs.listeners;

import com.paradox.core.general.listeners.EventsListener;
import com.paradox.core.utils.CEUtils;
import com.paradox.core.utils.GeneralUtils;
import com.paradox.core.utils.ItemStorage;
import com.paradox.core.utils.MineUtils;
import com.paradox.core.utils.OrbEconomyUtils;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;

public class OrbsListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Item i = p.getInventory().getItemInHand();
		if (i.getCustomName().equals(ItemStorage.orbPouchTierOne().getCustomName())) {
			e.setCancelled();
			GeneralUtils.pop(i, p, 1);
			int amount = GeneralUtils.getRandomNumberInRange(1000, 10000);
			OrbEconomyUtils.addPlayerBalance(p, amount);
			p.sendMessage(StringUtils.getPrefix() + "You were given " + amount + " orbs!");
		} else if (i.getCustomName().equals(ItemStorage.orbPouchTierTwo().getCustomName())) {
			e.setCancelled();
			GeneralUtils.pop(i, p, 1);
			int amount = GeneralUtils.getRandomNumberInRange(10000, 100000);
			OrbEconomyUtils.addPlayerBalance(p, amount);
			p.sendMessage(StringUtils.getPrefix() + "You were given " + amount + " orbs!");
		} else if (i.getCustomName().equals(ItemStorage.orbPouchTierThree().getCustomName())) {
			e.setCancelled();
			GeneralUtils.pop(i, p, 1);
			int amount = GeneralUtils.getRandomNumberInRange(100000, 1000000);
			OrbEconomyUtils.addPlayerBalance(p, amount);
			p.sendMessage(StringUtils.getPrefix() + "You were given " + amount + " orbs!");
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Item i = p.getInventory().getItemInHand();
		if (i.getId() == 278) {
			if (CEUtils.containsEnchantment(i, CEUtils.getCEByDisplayName(StringUtils.translateColors("&aGreed")))) {
				if (MineUtils.isLocInMine(e.getBlock().getLocation())) {
					if (!EventsListener.playersOrbsBooster.containsKey(p)) {
						int lvl = CEUtils.getLevelOfEnchantByDisplayName(StringUtils.translateColors("&aGreed"), i);
						double incr = lvl / 10.0D;
						int actualAmount = (int) Math.floor(incr);
						OrbEconomyUtils.addPlayerBalance(p, 1 + actualAmount);
					} else {
						int lvl = CEUtils.getLevelOfEnchantByDisplayName(StringUtils.translateColors("&aGreed"), i);
						double incr = lvl / 10.0D;
						int actualAmount = (int) Math.floor(incr) * EventsListener.playersOrbsBooster.get(p);
						OrbEconomyUtils.addPlayerBalance(p, 1 + actualAmount);
					}
				}
			} else {
				if (!EventsListener.playersOrbsBooster.containsKey(p)) {
					OrbEconomyUtils.addPlayerBalance(p, 1);
				} else {
					OrbEconomyUtils.addPlayerBalance(p, 1 * EventsListener.playersOrbsBooster.get(p));
				}
			}
		}
	}
}
