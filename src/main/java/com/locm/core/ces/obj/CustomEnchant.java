package com.locm.core.ces.obj;

public class CustomEnchant {

	private String nameOfEnchantment;
	private int levelOfEnchantment;
	private int maxLevelOfEnchantment;
	private String displayNameOfEnchantment;
	private String description;
	private final int costMultiplier;
	private final EnchantType type;
	private int id;

	public CustomEnchant(String nameOfEnchantment, int levelOfEnchantment, int maxLevelOfEnchantment,
			String displayNameOfEnchantment, String description, int costMultiplier, EnchantType enchantType) {
		this.nameOfEnchantment = nameOfEnchantment;
		this.levelOfEnchantment = levelOfEnchantment;
		this.maxLevelOfEnchantment = maxLevelOfEnchantment;
		this.displayNameOfEnchantment = displayNameOfEnchantment;
		this.description = description;
		this.costMultiplier = costMultiplier;
		this.type = enchantType;
	}

	public int getId(){
		return this.id;
	}

	public EnchantType getType() {
		return type;
	}

	public int getLevelOfEnchantment() {
		return levelOfEnchantment;
	}

	public int getCostMultiplier() {
		return costMultiplier;
	}

	public int getMaxLevelOfEnchantment() {
		return maxLevelOfEnchantment;
	}

	public String getDescription() {
		return description;
	}

	public String getDisplayNameOfEnchantment() {
		return displayNameOfEnchantment;
	}

	public String getNameOfEnchantment() {
		return nameOfEnchantment;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDisplayNameOfEnchantment(String displayNameOfEnchantment) {
		this.displayNameOfEnchantment = displayNameOfEnchantment;
	}

	public void setLevelOfEnchantment(int levelOfEnchantment) {
		this.levelOfEnchantment = levelOfEnchantment;
	}

	public void setMaxLevelOfEnchantment(int maxLevelOfEnchantment) {
		this.maxLevelOfEnchantment = maxLevelOfEnchantment;
	}

	public void setNameOfEnchantment(String nameOfEnchantment) {
		this.nameOfEnchantment = nameOfEnchantment;
	}
}
