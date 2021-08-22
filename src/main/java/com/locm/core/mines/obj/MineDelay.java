package com.locm.core.mines.obj;

import lombok.Getter;

public class MineDelay {

	private final Mine mine;
	private final int delayCount;

	public MineDelay(Mine mine, int delayCount) {
		this.mine = mine;
		this.delayCount = delayCount;
	}

	public Mine getMine() {
		return mine;
	}

	public int getDelayCount() {
		return delayCount;
	}
}
