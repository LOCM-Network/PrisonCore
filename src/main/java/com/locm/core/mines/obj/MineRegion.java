package com.locm.core.mines.obj;

import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import lombok.Getter;

public class MineRegion {

	@Getter
	private final Location locMin;
	@Getter
	private final Location locMax;
	@Getter
	private final Level lvl;

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
		if(pLoc.getLevel() == null) return false;
		if(locMax.getLevel() == null) return false;
		return pLoc.getLevel().getName().equals(locMax.getLevel().getName())
				&& (pLoc.getX() >= minX && pLoc.getX() <= maxX && pLoc.getY() >= minY)
				&& pLoc.getY() <= maxY && pLoc.getZ() >= minZ && pLoc.getZ() <= maxZ;
	}
}
