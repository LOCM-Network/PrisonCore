package com.locm.core.general.feautures;

import java.util.List;
import java.util.Random;

import cn.nukkit.Server;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.TextFormat;

import com.locm.core.Loader;

public class Announ {

	private final List<String> mess;

	public Announ() {
		mess = Loader.getInstance().getConfig().getStringList("broadcast");
	}

	public void runAnnoun() {
		new NukkitRunnable(){
			@Override
			public void run() {
				Random rand = new Random();
				String announ = mess.get(rand.nextInt(mess.size()));
				Server.getInstance().broadcastMessage(TextFormat.colorize(announ));
			}
		}.runTaskTimer(Loader.getInstance(), 0, 2000);
	}
}
