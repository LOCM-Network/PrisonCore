package com.locm.core.mines;

import java.util.ArrayList;
import java.util.List;

import com.locm.core.mines.obj.LuckyReward;

public class LuckyRewardStorage {

	public static LuckyReward prizeOne = new LuckyReward(30, "100 Orbs", "orbs give {name} 100");
	public static LuckyReward prizeTwo = new LuckyReward(20, "1x Basic Key", "crate give {name} Basic 1");
	public static LuckyReward prizeThree = new LuckyReward(10, "Orbs Pouch Tier I", "orbs givepouch {name} 1 1");
	public static LuckyReward prizeFour = new LuckyReward(5, "1,000 Orbs", "orbs give {name} 1000");
	public static LuckyReward prizeFive = new LuckyReward(35, "$500", "givemoney {name} 500");
	public static LuckyReward prizeSix = new LuckyReward(35, "4x Small bombs", "bomb give {name} 4 small");

	public static List<LuckyReward> rews() {
		List<LuckyReward> rews = new ArrayList<LuckyReward>();
		rews.add(prizeOne);
		rews.add(prizeTwo);
		rews.add(prizeThree);
		rews.add(prizeFour);
		rews.add(prizeFive);
		return rews;
	}

}
