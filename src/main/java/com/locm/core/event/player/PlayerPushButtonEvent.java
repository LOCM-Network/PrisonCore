package com.locm.core.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

public class PlayerPushButtonEvent extends PlayerEvent{


    public static HandlerList handlerList = new HandlerList();
    
    public PlayerPushButtonEvent(Player player){
        this.player = player;
    }

    public static HandlerList getHandlers(){
		return handlerList;
	}
}
