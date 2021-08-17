package com.locm.core.utils;

import java.util.Random;

public class NumberUtils {

	public static int getCostOfEnchantmentByLevel(int level, int multiplier) {
		return level * multiplier;
	}

	public static int getMins(int input) {
		return ((input % 86400) % 3600) / 60;
	}

	public static int random(int min, int max) {
		Random rand = new Random();
		int ran = rand.nextInt(max - min + 1) + min;
		return ran;
	}

}
