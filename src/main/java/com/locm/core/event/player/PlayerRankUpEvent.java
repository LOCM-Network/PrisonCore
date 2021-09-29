package com.locm.core.event.player;

import com.locm.core.ranks.obj.Rank;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

public class PlayerRankUpEvent extends PlayerEvent{

    public static HandlerList handlerList = new HandlerList();

    private final Rank rank;
    
    public PlayerRankUpEvent(Player player, Rank rank){
        this.player = player;
        this.rank = rank;
    }

    public Rank getRank() { return rank; }

    public static HandlerList getHandlers(){
		return handlerList;
	}
}
