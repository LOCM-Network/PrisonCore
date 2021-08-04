package com.paradox.core.kits.obj;

import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.item.Item;

public class Kit {

	private String name;
	private int coolDownInSeconds;
	private List<Item> items;

	public Kit(String name, int coolDownInSeconds, List<Item> items) {
		super();
		this.name = name;
		this.coolDownInSeconds = coolDownInSeconds;
		this.items = items;
	}

	public void giveKitToPlayer(Player p) {
		for (Item i : items) {
			p.getInventory().addItem(i);
		}
	}

	public String getName() {
		return name;
	}

	public int getCoolDownInSeconds() {
		return coolDownInSeconds;
	}

	public List<Item> getItems() {
		return items;
	}

}
