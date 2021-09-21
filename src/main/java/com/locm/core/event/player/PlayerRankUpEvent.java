package com.locm.core.event.player;

import com.locm.core.ranks.obj.Rank;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import lombok.Getter;

public class PlayerRankUpEvent extends PlayerEvent{

    public static HandlerList handlerList = new HandlerList();

    @Getter
    private Rank rank;
    
    public PlayerRankUpEvent(Player player, Rank rank){
        this.player = player;
        this.rank = rank;
    }

    public static HandlerList getHandlers(){
		return handlerList;
	}
}
