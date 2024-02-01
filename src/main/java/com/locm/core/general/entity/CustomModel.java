package com.locm.core.general.entity;

import com.locm.core.event.player.PlayerPushButtonEvent;
import com.locm.core.mines.obj.Mine;
import com.locm.core.ranks.obj.Rank;
import com.locm.core.utils.MineUtils;
import com.locm.core.utils.RankUtils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;

public class CustomModel extends EntityHuman {

    public CustomModel(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }


    @Override
    public void saveNBT() {
        super.saveNBT();
    }

    @Override
    public void initEntity() {
        super.initEntity();
        this.setNameTag(TextFormat.colorize("&l&cLàm mới khu mỏ"));
        this.setNameTagAlwaysVisible(true);
        this.setNameTagVisible(true);
        this.setScale(1.5f);
        this.setImmobile(true);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if (source instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) source;
            if(event.getDamager() instanceof Player){
                Player player = (Player) event.getDamager();
                if(player.isOp() && player.isSneaking()){
                    this.close();
                    return false;
                }
                Rank rank = RankUtils.getRankByPlayer(player);
                Mine mine = MineUtils.getMineByName(rank.getName());

                if(mine == null) {
                    player.sendActionBar(TextFormat.colorize("&l&cBạn không có quyền làm mới khu mỏ"));
                    return false;
                }

                if(mine.isSmaller(20)){
                    Server.getInstance().getPluginManager().callEvent(new PlayerPushButtonEvent(player));
                    player.sendActionBar(TextFormat.colorize("&l&aĐang làm mới khu mỏ"));
                    return false;
                }
                player.sendActionBar(TextFormat.colorize("&l&cKhông thể làm mới khi khu mỏ còn nhiều hơn &e20%"));
            }
        }
        source.setCancelled();
        return super.attack(source);
    }
}