package org.lithereal.fabric;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.lithereal.LitherealExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;
import org.lithereal.block.custom.FireCrucibleBlock;
import org.lithereal.block.custom.InfusedLitheriteBlock;
import org.lithereal.block.entity.FireCrucibleBlockEntity;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.fabric.block.FabricBlocks;
import org.lithereal.fabric.block.entities.FabricBlockEntities;
import org.lithereal.screen.FireCrucibleMenu;

import java.nio.file.Path;

public class LitherealExpectPlatformImpl {
    /**
     * This is our actual method to {@link LitherealExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static InfusedLitheriteBlock getInfusedLitheriteBlock() {
        return (InfusedLitheriteBlock) FabricBlocks.INFUSED_LITHERITE_BLOCK;
    }

    public static BlockEntityType<InfusedLitheriteBlockEntity> getInfusedLitheriteBlockEntity() {
        return FabricBlockEntities.INFUSED_LITHERITE_BLOCK_ENTITY;
    }

    public static BlockEntityType<FireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
    }

    public static FireCrucibleBlockEntity getFireCrucibleBlockEntityClass() {
    }

    public static BlockEntityTicker<FireCrucibleBlockEntity> getFireCrucibleBlockEntityTicker() {
    }

    public static FireCrucibleBlock getFireCrucibleBlock() {
    }

    public static FireCrucibleMenu getFireCrucibleMenu() {
    }

    public static BlockItem getFireCrucibleBlockItem() {
    }
}
