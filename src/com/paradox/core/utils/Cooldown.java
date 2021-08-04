package com.paradox.core.utils;

import java.util.HashMap;
import java.util.UUID;

import cn.nukkit.Player;

public class Cooldown {

    public Cooldown(int seconds) {
        this.seconds = seconds;
    }

    private int seconds = 0;

    private HashMap<String, Long> hashmap = new HashMap<>();

    public void putInCooldown(Player player) {
        hashmap.put(player.getUniqueId().toString(), System.currentTimeMillis() + (seconds * 1000L));
    }

    public boolean isOnCooldown(Player player) {
        UUID uuid = player.getUniqueId();
        return (hashmap.get(uuid.toString()) != null && hashmap.get(uuid.toString()) > System.currentTimeMillis());
    }

    public int getCooldownSeconds(Player player) {
        UUID uuid = player.getUniqueId();
        return (int)  ((hashmap.get(uuid.toString()) - System.currentTimeMillis()) / 1000L);
    }
}