package com.locm.core.mines.obj;

import com.locm.core.Loader;
import com.locm.core.utils.RandomCollection;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Location;

public class Mine {
	private MineRegion region;
	private String mineName;
	private MineComposition mineComposition;
	private Location tpLocation;

	public Mine(MineRegion region, String mineName, MineComposition mineComposition, Location tpLocation) {
		this.region = region;
		this.mineName = mineName;
		this.mineComposition = mineComposition;
		this.tpLocation = tpLocation;
	}

	public void resetMine() {
		for (Player o : Loader.getLoader().getServer().getOnlinePlayers().values()) {
			if (region.isInRegion(o.getLocation())) {
				o.teleport(tpLocation);
			}
		}

		double maxX = Math.max(region.getLocMin().getX(), region.getLocMax().getX());
		double maxY = Math.max(region.getLocMin().getY(), region.getLocMax().getY());
		double maxZ = Math.max(region.getLocMin().getZ(), region.getLocMax().getZ());
		double minX = Math.min(region.getLocMin().getX(), region.getLocMax().getX());
		double minY = Math.min(region.getLocMin().getY(), region.getLocMax().getY());
		double minZ = Math.min(region.getLocMin().getZ(), region.getLocMax().getZ());
		for (int x = (int) minX; x <= maxX; x++) {
			for (int y = (int) minY; y <= maxY; y++) {
				for (int z = (int) minZ; z <= maxZ; z++) {
					Block b = getWinningBlock();
					Location loc = new Location(x, y, z);
					if(region.getLvl() == null) return;
					region.getLvl().setBlock(loc, b);
				}
			}
		}
	}

	public Integer getCountBlock(boolean rest){
		double maxX = Math.max(region.getLocMin().getX(), region.getLocMax().getX());
		double maxY = Math.max(region.getLocMin().getY(), region.getLocMax().getY());
		double maxZ = Math.max(region.getLocMin().getZ(), region.getLocMax().getZ());
		double minX = Math.min(region.getLocMin().getX(), region.getLocMax().getX());
		double minY = Math.min(region.getLocMin().getY(), region.getLocMax().getY());
		double minZ = Math.min(region.getLocMin().getZ(), region.getLocMax().getZ());
		int blocks = 0;
		for (int x = (int) minX; x <= maxX; x++) {
			for (int y = (int) minY; y <= maxY; y++) {
				for (int z = (int) minZ; z <= maxZ; z++) {
					if(rest){
						Location loc = new Location(x, y, z);
						if(region.getLvl().getBlock(loc).getId() != 0){
							blocks += 1;
						}
					}else{
						blocks += 1;
					}
				}
			}
		}
		return blocks;
	}
	
	public boolean isSmaller(int percen){
		int allblocks = this.getCountBlock(false);
		int restblocks = this.getCountBlock(true);
		int perblocks = percen * allblocks / 100;
		return ((allblocks - restblocks) <= perblocks);
	}

	public Block getWinningBlock() {
		RandomCollection<Block> random = new RandomCollection<>();
		for (MineBlock mb : getMineComposition().getMb()) {
			random.add(mb.getChance(), mb.getBlock());
		}
		return random.next();
	}

	public MineRegion getRegion() {
		return region;
	}

	public void setRegion(MineRegion region) {
		this.region = region;
	}

	public String getMineName() {
		return mineName;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public MineComposition getMineComposition() {
		return mineComposition;
	}

	public void setMineComposition(MineComposition mineComposition) {
		this.mineComposition = mineComposition;
	}

	public Location getTpLocation() {
		return tpLocation;
	}

	public void setTpLocation(Location tpLocation) {
		this.tpLocation = tpLocation;
	}
}
