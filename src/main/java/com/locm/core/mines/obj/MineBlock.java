package com.locm.core.mines.obj;

import cn.nukkit.block.Block;

public class MineBlock {

	private final Block block;
	private final int chance;

	public MineBlock(Block block, int chance) {
		super();
		this.block = block;
		this.chance = chance;
	}

	public Block getBlock() {
		return block;
	}

	public int getChance() {
		return chance;
	}

}
