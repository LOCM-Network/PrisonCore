package com.locm.core.mines.obj;

public class LuckyReward {

	private final int chance;
	private final String name;
	private final String cmds;

	public LuckyReward(int chance, String name, String cmds) {
		this.chance = chance;
		this.name = name;
		this.cmds = cmds;
	}

	public int getChance() {
		return chance;
	}

	public String getName() {
		return name;
	}

	public String getCmds() {
		return cmds;
	}

}
