package com.locm.core.general.entity;

import com.locm.core.mines.obj.Mine;
import com.locm.core.ranks.obj.Rank;
import com.locm.core.utils.MineUtils;
import com.locm.core.utils.RankUtils;

import cn.nukkit.Player;
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
    public boolean attack(EntityDamageEvent source) {
        if (source instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) source;
            if(event.getDamager() instanceof Player){
                Player player = (Player) event.getDamager();
                if(player.isOp() && player.isSneaking()){
                    this.close();
                    return false;
                }
                try{
                    Rank rank =  RankUtils.getRankByPlayer(player);
                    Mine mine =  MineUtils.getMineByName(rank.getName());
                    if(mine.isSmaller(20)){
                        mine.resetMine();
                        player.sendActionBar(TextFormat.colorize("&l&aĐã làm mới khu mỏ"));
                        return false;
                    }
                    player.sendActionBar(TextFormat.colorize("&l&cKhông thể làm mới khi khu mỏ còn nhiều hơn &e20%"));
                }catch(NullPointerException exception){
                    player.sendActionBar(TextFormat.colorize("&l&cLỗi: không tìm thấy khu mỏ!"));
                }
            }
        }
        source.setCancelled();
        return super.attack(source);
    }
}