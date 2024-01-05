package org.lithereal.fabric;

import net.minecraft.world.level.block.entity.BlockEntityType;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.custom.InfusedLitheriteBlock;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.fabric.block.QuiltBlocks;
import org.lithereal.fabric.block.entity.QuiltBlockEntities;
import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

public class LitherealExpectPlatformImpl {
    /**
     * This is our actual method to {@link LitherealExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return QuiltLoader.getConfigDir();
    }

    public static InfusedLitheriteBlock getInfusedLitheriteBlock() {
        return (InfusedLitheriteBlock) QuiltBlocks.INFUSED_LITHERITE_BLOCK;
    }

    public static BlockEntityType<InfusedLitheriteBlockEntity> getInfusedLitheriteBlockEntity() {
        return QuiltBlockEntities.INFUSED_LITHERITE_BLOCK_ENTITY;
    }
}
