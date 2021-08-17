package com.paradox.core.mines.obj;

import cn.nukkit.block.Block;
import lombok.Getter;

public class MineBlock {

	@Getter
	private final Block block;
	@Getter
	private final int chance;

	public MineBlock(Block block, int chance) {
		this.block = block;
		this.chance = chance;
	}
}
