package com.locm.core.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.locm.core.Loader;
import com.locm.core.mines.obj.Mine;
import com.locm.core.mines.obj.MineBlock;
import com.locm.core.mines.obj.MineComposition;
import com.locm.core.mines.obj.MineRegion;

import cn.nukkit.block.Block;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;

public class MineUtils {
	public static Config minescfg = Loader.getLoader().getMinesCfg();
	public static File minesFile = Loader.getLoader().getMinesFile();

	public static List<Mine> getAllMinesFromConfig() {
		List<Mine> mines = new ArrayList<Mine>();
		for (String key : minescfg.getSection("Mines").getKeys(false)) {
			Location tpLoc = new Location(minescfg.getDouble("Mines." + key + ".tpLocX"),
					(minescfg.getDouble("Mines." + key + ".tpLocY")), (minescfg.getDouble("Mines." + key + ".tpLocZ")),
					minescfg.getDouble("Mines." + key + ".tpYaw"), minescfg.getDouble("Mines." + key + ".tpPitch"),
					Loader.getLoader().getServer()
							.getLevelByName(minescfg.getString("Mines." + key + ".tpLocLevelName")));

			String name = minescfg.getString("Mines." + key + ".name");
			Location minLoc = new Location(minescfg.getDouble("Mines." + key + ".minX"),
					minescfg.getDouble("Mines." + key + ".minY"), minescfg.getDouble("Mines." + key + ".minZ"),
					Loader.getLoader().getServer()
							.getLevelByName(minescfg.getString("Mines." + key + ".tpLocLevelName")));
			Location maxLoc = new Location(minescfg.getDouble("Mines." + key + ".maxX"),
					minescfg.getDouble("Mines." + key + ".maxY"), minescfg.getDouble("Mines." + key + ".maxZ"),
					Loader.getLoader().getServer()
							.getLevelByName(minescfg.getString("Mines." + key + ".tpLocLevelName")));
			List<MineBlock> mbs = new ArrayList<>();
			for (String comp : minescfg.getSection("Mines." + key + ".composition").getKeys(false)) {
				String[] parts = comp.split(":");
				MineBlock mb = new MineBlock(Block.get(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])),
						minescfg.getInt("Mines." + key + ".composition." + comp));
				mbs.add(mb);
			}
			MineRegion mr = new MineRegion(maxLoc, minLoc, Loader.getLoader().getServer()
					.getLevelByName(minescfg.getString("Mines." + key + ".tpLocLevelName")));
			MineComposition cmp = new MineComposition(mbs);
			Mine mine = new Mine(mr, name, cmp, tpLoc);
			mines.add(mine);
		}
		return mines;
	}

	public static Mine mineByLoc(Location loc) {
		for (Mine m : getAllMinesFromConfig()) {
			if (m.getRegion().isInRegion(loc)) {
				return m;
			}
		}
		return null;
	}

	public static boolean isLocInMine(Location loc) {
		for (Mine m : getAllMinesFromConfig()) {
			if (m.getRegion().isInRegion(loc)) {
				return true;
			}
		}
		return false;
	}

	public static Mine getMineByName(String name) throws NullPointerException {
		for (Mine m : getAllMinesFromConfig()) {
			if (name.equals(m.getMineName())) {
				return m;
			}
		}
		return null;
	}

	public static List<Position> getCorners(Location loc) {
		List<Position> locs = new ArrayList<>();
		if (mineByLoc(loc) != null) {
			Mine m = mineByLoc(loc);
			Position cornerOne = m.getRegion().getLocMax().setComponents(m.getRegion().getLocMax().getX(), loc.getY(), m.getRegion().getLocMax().getZ());
			Position cornerTwo = m.getRegion().getLocMin().setComponents(m.getRegion().getLocMin().getX(), loc.getY(), m.getRegion().getLocMin().getZ());
			locs.add(cornerOne);
			locs.add(cornerTwo);
		}
		return locs;
	}

}
