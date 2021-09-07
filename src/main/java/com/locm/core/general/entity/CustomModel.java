package com.locm.core.general.entity;

import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class CustomModel extends EntityHuman {

    public CustomModel(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}