package com.paradox.core.ah.obj;

import java.util.UUID;

import cn.nukkit.item.Item;

public class Listing {

	private Item item;
	private int pricing;
	private UUID sellerUUID;
	private String description;
	private int id;
	
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
