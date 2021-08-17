package com.locm.core.utils;

import java.io.File;
import java.util.Random;

import com.locm.core.Loader;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.level.Level;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;

public class GeneralUtils {

	public static Config worth = Loader.getLoader().getWorthCfg();
	public static File worthFile = Loader.getLoader().getWorthFile();

	public static void setupWorthFile() {
		if (!worth.exists("worth")) {
			worth.set("worth.1", 8.0);
			worth.set("worth.4", 4.0);
			worth.set("worth.5", 12.0);
			worth.set("worth.263", 10.0);
			worth.set("worth.16", 9.0);
			worth.set("worth.14", 20.0);
			worth.set("worth.15", 28.0);
			worth.set("worth.331", 24.0);
			worth.set("worth.73", 24.0);
			worth.set("worth.266", 40.0);
			worth.set("worth.265", 48.0);
			worth.set("worth.264", 50.0);
			worth.set("worth.388", 100.0);
			worth.set("worth.49", 200.0);
			worth.set("worth.42", 225.0);
			worth.set("worth.41", 275.0);
			worth.set("worth.57", 450.0);
			worth.set("worth.133", 9000.0);
			worth.save(worthFile);
		}
	}

	public static void pop(Item item, Player p, int amount) {
		p.getInventory().remove(item);
		item.setCount(item.getCount() - amount);
		p.getInventory().addItem(item);
	}

	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public static boolean canPlayerBuild(Player p) {
/*		SRegionProtectorMain api = SRegionProtectorMain.getInstance();
		if (!p.getBoundingBox().intersectsWith(api.getRegionManager().getRegion("spawn").getBoundingBox())) {
			return true;
		}*/
		return true;
	}

	public static Integer clearlag() {
		Integer i = 0;
		for (Level level : Server.getInstance().getLevels().values()) {
			for (Entity entity : level.getEntities()) {
				if (!(entity instanceof EntityHuman) && (entity.namedTag == null || (!entity.namedTag.contains("npc") && !entity.namedTag.contains("hologramId")))) {
					entity.close();
					i++;
				}
			}
		}
		return i;
	}

}
