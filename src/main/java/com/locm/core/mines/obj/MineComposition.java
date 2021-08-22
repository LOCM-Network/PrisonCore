package com.locm.core.mines.obj;

import lombok.Getter;

import java.util.List;

public class MineComposition {

	private final List<MineBlock> mb;

	public MineComposition(List<MineBlock> mb) {
		this.mb = mb;
	}

	public List<MineBlock> getMb() {
		return mb;
	}
}
