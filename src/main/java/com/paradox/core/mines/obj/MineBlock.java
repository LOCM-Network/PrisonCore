package com.paradox.core.mines.obj;

import cn.nukkit.block.Block;

public class MineBlock {

	private Block block;
	private int chance;

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
