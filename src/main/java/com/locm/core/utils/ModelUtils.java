package com.locm.core.utils;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.level.Position;
import cn.nukkit.nbt.tag.CompoundTag;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import com.locm.core.general.entity.CustomModel;

public class ModelUtils{

    public static Skin createSkin(String name, Path skinPath, Path geometryPath) throws IOException {
        Skin skin = new Skin();
        skin.setSkinData(ImageIO.read(skinPath.toFile()));
        skin.setGeometryName("geometry." + name);
        skin.setGeometryData(new String(Files.readAllBytes(geometryPath)));
        return skin;
    }

    public static CustomModel createModel(Position position, Skin skin) {
        CompoundTag nbt = Entity.getDefaultNBT(position);
        CompoundTag skinTag = new CompoundTag()
                .putByteArray("Data", skin.getSkinData().data)
                .putInt("SkinImageWidth", skin.getSkinData().width)
                .putInt("SkinImageHeight", skin.getSkinData().height)
                .putString("ModelId", skin.getSkinId())
                .putByteArray("SkinResourcePatch", skin.getSkinResourcePatch().getBytes(StandardCharsets.UTF_8))
                .putByteArray("GeometryData", skin.getGeometryData().getBytes(StandardCharsets.UTF_8))
                .putBoolean("IsTrustedSkin", true);
        nbt.putCompound("Skin", skinTag);
        return new CustomModel(position.getChunk(), nbt);
    }
}