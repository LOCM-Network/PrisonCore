package com.paradox.core.mines.obj;

import com.paradox.core.Loader;
import com.paradox.core.utils.RandomCollection;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Location;
import lombok.Getter;
import lombok.Setter;

public class Mine {

	@Getter
	@Setter
	private final MineRegion region;
	@Getter
	@Setter
	private final String mineName;
	@Getter
	@Setter
	private final MineComposition mineComposition;
	@Getter
	@Setter
	private final Location tpLocation;

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
					if (Loader.getLoader().getServer().getDefaultLevel().getBlock(x, y, z).getId() == 0) {
						Loader.getLoader().getServer().getDefaultLevel().setBlock(loc, b);
					}
				}
			}
		}
	}

	public Block getWinningBlock() {
		RandomCollection<Block> random = new RandomCollection<>();
		for (MineBlock mb : getMineComposition().getMb()) {
			random.add(mb.getChance(), mb.getBlock());
		}
		return random.next();
	}
}
