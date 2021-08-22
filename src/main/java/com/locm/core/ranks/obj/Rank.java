package com.locm.core.ranks.obj;

public class Rank {

	private final String name;
	private final int order;
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

	public String getName() {
		return name;
	}

	public int getOrder() {
		return order;
	}

	public int getCost() {
		return cost;
	}

	public boolean isFirstRank() {
		return isFirstRank;
	}

	public boolean isLastRank() {
		return isLastRank;
	}

}
