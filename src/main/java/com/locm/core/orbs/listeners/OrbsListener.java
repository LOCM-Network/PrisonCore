package com.locm.core.orbs.listeners;

import com.locm.core.general.listeners.EventsListener;
import com.locm.core.utils.CEUtils;
import com.locm.core.utils.GeneralUtils;
import com.locm.core.utils.ItemStorage;
import com.locm.core.utils.MineUtils;
import com.locm.core.utils.OrbEconomyUtils;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;

import java.util.Random;

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
			p.sendMessage(StringUtils.getPrefix() + "Bạn vừa nhận được " + amount + " orb!");
		} else if (i.getCustomName().equals(ItemStorage.orbPouchTierTwo().getCustomName())) {
			e.setCancelled();
			GeneralUtils.pop(i, p, 1);
			int amount = GeneralUtils.getRandomNumberInRange(10000, 100000);
			OrbEconomyUtils.addPlayerBalance(p, amount);
			p.sendMessage(StringUtils.getPrefix() + "Bạn vừa nhận được " + amount + " orbs!");
		} else if (i.getCustomName().equals(ItemStorage.orbPouchTierThree().getCustomName())) {
			e.setCancelled();
			GeneralUtils.pop(i, p, 1);
			int amount = GeneralUtils.getRandomNumberInRange(100000, 1000000);
			OrbEconomyUtils.addPlayerBalance(p, amount);
			p.sendMessage(StringUtils.getPrefix() + "Bạn vừa nhận được " + amount + " orbs!");
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Item i = p.getInventory().getItemInHand();
		if(!MineUtils.isLocInMine(p)) return;
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
				Random rd = new Random();
				if(rd.nextInt(3) == 1){
					if (!EventsListener.playersOrbsBooster.containsKey(p)) {
						OrbEconomyUtils.addPlayerBalance(p, 1);
					} else {
						OrbEconomyUtils.addPlayerBalance(p, 1 * EventsListener.playersOrbsBooster.get(p));
					}
				}
			}
		}
	}
}
