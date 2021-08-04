package com.paradox.core.mines.obj;

public class LuckyReward {

	private int chance;
	private String name;
	private String cmds;

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
