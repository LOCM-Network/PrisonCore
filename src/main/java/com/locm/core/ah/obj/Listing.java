package com.locm.core.ah.obj;

import java.util.UUID;

import cn.nukkit.item.Item;

public class Listing {

	private final Item item;
	private final int pricing;
	private final UUID sellerUUID;
	private final String description;
	private final int id;
	
	public Listing(Item item, int pricing, UUID sellerUUID, String desc, int id) {
		super();
		this.item = item;
		this.pricing = pricing;
		this.sellerUUID = sellerUUID;
		this.description = desc;
		this.id=id;
	}

	public String getDescription() {
		return description;
	}

	public Item getItem() {
		return item;
	}

	public int getPricing() {
		return pricing;
	}

	public UUID getSellerUUID() {
		return sellerUUID;
	}

	public int getId() {
		return id;
	}

}
