package org.lithereal.fabric;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.lithereal.LitherealExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;
import org.lithereal.block.custom.FireCrucibleBlock;
import org.lithereal.block.custom.FreezingStationBlock;
import org.lithereal.block.custom.InfusedLitheriteBlock;
import org.lithereal.block.custom.InfusementChamberBlock;
import org.lithereal.block.entity.FireCrucibleBlockEntity;
import org.lithereal.block.entity.FreezingStationBlockEntity;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.block.entity.InfusementChamberBlockEntity;
import org.lithereal.fabric.block.FabricBlocks;
import org.lithereal.fabric.block.entities.FabricBlockEntities;
import org.lithereal.screen.FireCrucibleMenu;
import org.lithereal.screen.FreezingStationMenu;
import org.lithereal.screen.InfusementChamberMenu;

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

    public static BlockEntityTicker<FireCrucibleBlockEntity> getFireCrucibleBlockEntityTicker() {
    }

    public static FireCrucibleBlock getFireCrucibleBlock() {
    }

    public static MenuType<FireCrucibleMenu> getFireCrucibleMenu() {
    }

    public static BlockEntityType<FreezingStationBlockEntity> getFreezingStationBlockEntity() {
    }

    public static BlockEntityTicker<FreezingStationBlockEntity> getFreezingStationBlockEntityTicker() {
    }

    public static MenuType<FreezingStationMenu> getFreezingStationMenu() {
    }

    public static FreezingStationBlock getFreezingStationBlock() {
    }

    public static BlockEntityType<InfusementChamberBlockEntity> getInfusementChamberBlockEntity() {
    }

    public static BlockEntityTicker<InfusementChamberBlockEntity> getInfusementChamberBlockEntityTicker() {
    }

    public static InfusementChamberBlock getInfusementChamberBlock() {
    }

    public static MenuType<InfusementChamberMenu> getInfusementChamberMenu() {
    }
}
