package com.locm.core.mines.obj;

import lombok.Getter;

public class MineDelay {

	@Getter
	private final Mine mine;
	@Getter
	private final int delayCount;

	public MineDelay(Mine mine, int delayCount) {
		this.mine = mine;
		this.delayCount = delayCount;
	}

}
