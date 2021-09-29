package com.locm.core.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

public class PlayerBuyVipEvent extends PlayerEvent{

    private final String vip;

    public static HandlerList handlerList = new HandlerList();
    
    public PlayerBuyVipEvent(Player player, String vip){
        this.vip = vip;
        this.player = player;
    }

    public String getVip() { return vip; }

    public static HandlerList getHandlers(){
		return handlerList;
	}
}
