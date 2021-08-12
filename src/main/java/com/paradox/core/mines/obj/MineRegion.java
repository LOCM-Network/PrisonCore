package com.paradox.core.mines.obj;

import cn.nukkit.level.Level;
import cn.nukkit.level.Location;

public class MineRegion {

	private Location locMin;
	private Location locMax;
	private Level lvl;

	public MineRegion(Location locMax, Location locMin, Level lvl) {
		this.locMin = locMin;
		this.locMax = locMax;
		this.lvl = lvl;
	}

	public boolean isInRegion(Location pLoc) {
		double maxX = Math.max(getLocMin().getX(),getLocMax().getX());
		double maxY = Math.max(getLocMin().getY(), getLocMax().getY());
		double maxZ = Math.max(getLocMin().getZ(), getLocMax().getZ());
		double minX = Math.min(getLocMin().getX(), getLocMax().getX());
		double minY = Math.min(getLocMin().getY(),  getLocMax().getY());
		double minZ = Math.min(getLocMin().getZ(), getLocMax().getZ());
		return pLoc.getLevel().equals(locMax.getLevel())
				&& (pLoc.getX() >= minX && pLoc.getX() <= maxX && pLoc.getY() >= minY)
				&& pLoc.getY() <= maxY&& pLoc.getZ() >= minZ && pLoc.getZ() <= maxZ;
	}

	public Level getLvl() {
		return lvl;
	}

	public Location getLocMin() {
		return locMin;
	}

	public Location getLocMax() {
		return locMax;
	}

}
