package com.locm.core.ranks.obj;

import lombok.Getter;

public class Rank {

	@Getter
	private final String name;
	@Getter
	private final int order;
	@Getter
	private final int cost;

	private final boolean isFirstRank;
	private final boolean isLastRank;

	public Rank(String name, int order, int cost, boolean isFirstRank, boolean isLastRank) {
		this.name = name;
		this.order = order;
		this.cost = cost;
		this.isFirstRank = isFirstRank;
		this.isLastRank = isLastRank;
	}

	public boolean isFirstRank() {
		return isFirstRank;
	}

	public boolean isLastRank() {
		return isLastRank;
	}

}
