package com.locm.core.general.feautures;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

import cn.nukkit.Server;
import cn.nukkit.scheduler.NukkitRunnable;

import com.locm.core.Loader;

public class Announ {

	private List<String> mess;

	public Announ() {
		mess = new LinkedList<>(Loader.getInstance().getList("broadcast"));
	}

	public void runAnnoun() {
		new NukkitRunnable(){
			@Override
			public void onRun() {
				Random rand = new Random();
				String announ = mess.get(rand.nextInt(mess.size()));
			}
		}.runTaskTimer(this, 0, 2000);
	}

}
