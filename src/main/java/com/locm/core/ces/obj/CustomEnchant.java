package com.locm.core.ces.obj;

public class CustomEnchant {

	private String nameOfEnchantment;
	private int levelOfEnchantment;
	private int maxLevelOfEnchantment;
	private String displayNameOfEnchantment;
	private String description;
	private int costMultiplier;
	private EnchantType type;

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

	public EnchantType getType() {
		return type;
	}

	public int getCostMultiplier() {
		return costMultiplier;
	}

	public String getNameOfEnchantment() {
		return nameOfEnchantment;
	}

	public void setNameOfEnchantment(String nameOfEnchantment) {
		this.nameOfEnchantment = nameOfEnchantment;
	}

	public int getLevelOfEnchantment() {
		return levelOfEnchantment;
	}

	public void setLevelOfEnchantment(int levelOfEnchantment) {
		this.levelOfEnchantment = levelOfEnchantment;
	}

	public int getMaxLevelOfEnchantment() {
		return maxLevelOfEnchantment;
	}

	public void setMaxLevelOfEnchantment(int maxLevelOfEnchantment) {
		this.maxLevelOfEnchantment = maxLevelOfEnchantment;
	}

	public String getDisplayNameOfEnchantment() {
		return displayNameOfEnchantment;
	}

	public void setDisplayNameOfEnchantment(String displayNameOfEnchantment) {
		this.displayNameOfEnchantment = displayNameOfEnchantment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
