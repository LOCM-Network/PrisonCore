package com.locm.core.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import lombok.Getter;

public class PlayerBuyVipEvent extends PlayerEvent{

    @Getter
    private String vip;

    public static HandlerList handlerList = new HandlerList();
    
    public PlayerBuyVipEvent(Player player, String vip){
        this.vip = vip;
        this.player = player;
    }

    public static HandlerList getHandlers(){
		return handlerList;
	}
}
