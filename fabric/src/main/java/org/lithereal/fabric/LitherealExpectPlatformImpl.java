package org.lithereal.fabric;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.lithereal.LitherealExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;
import org.lithereal.block.custom.*;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.block.entity.LitherCollectorBlockEntity;
import org.lithereal.fabric.block.FabricBlocks;
import org.lithereal.fabric.block.entity.*;
import org.lithereal.fabric.item.FabricItems;
import org.lithereal.fabric.screen.*;
import org.lithereal.screen.LitherCollectorMenu;

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

    public static BlockEntityType<FabricFireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
        return FabricBlockEntities.FIRE_CRUCIBLE_BLOCK_ENTITY;
    }

    public static BlockEntityTicker<FabricFireCrucibleBlockEntity> getFireCrucibleBlockEntityTicker() {
        return FabricFireCrucibleBlockEntity::tick;
    }

    public static FireCrucibleBlock getFireCrucibleBlock() {
        return (FireCrucibleBlock) FabricBlocks.FIRE_CRUCIBLE_BLOCK;
    }

    public static MenuType<FabricFireCrucibleMenu> getFireCrucibleMenu() {
        return FabricScreenHandlers.FIRE_CRUCIBLE_SCREEN_HANDLER;
    }

    public static BlockEntityType<FabricFreezingStationBlockEntity> getFreezingStationBlockEntity() {
        return FabricBlockEntities.FREEZING_STATION_BLOCK_ENTITY;
    }

    public static BlockEntityTicker<FabricFreezingStationBlockEntity> getFreezingStationBlockEntityTicker() {
        return FabricFreezingStationBlockEntity::tick;
    }

    public static MenuType<FabricFreezingStationMenu> getFreezingStationMenu() {
        return FabricScreenHandlers.FREEZING_STATION_SCREEN_HANDLER;
    }

    public static LitherCollectorBlock getFreezingStationBlock() {
        return (LitherCollectorBlock) FabricBlocks.FREEZING_STATION_BLOCK;
    }

    public static BlockEntityType<FabricInfusementChamberBlockEntity> getInfusementChamberBlockEntity() {
        return FabricBlockEntities.INFUSEMENT_CHAMBER_BLOCK_ENTITY;
    }

    public static BlockEntityTicker<FabricInfusementChamberBlockEntity> getInfusementChamberBlockEntityTicker() {
        return FabricInfusementChamberBlockEntity::tick;
    }

    public static InfusementChamberBlock getInfusementChamberBlock() {
        return (InfusementChamberBlock) FabricBlocks.INFUSEMENT_CHAMBER_BLOCK;
    }

    public static MenuType<FabricInfusementChamberMenu> getInfusementChamberMenu() {
        return FabricScreenHandlers.INFUSEMENT_CHAMBER_SCREEN_HANDLER;
    }

    public static Item getLitheriteItem() {
        return FabricItems.LITHERITE_CRYSTAL;
    }

    public static ResourceLocation getResourceLocation(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem());
    }

    public static BlockEntityType<FabricLitherCollectorBlockEntity> getLitherCollectorBlockEntity() {
        return FabricBlockEntities.LITHER_COLLECTOR_BLOCK_ENTITY;
    }

    public static BlockEntityTicker<FabricLitherCollectorBlockEntity> getLitherCollectorBlockEntityTicker() {
        return FabricLitherCollectorBlockEntity::tick;
    }

    public static LitherCollectorBlock getLitherCollectorBlock() {
        return (LitherCollectorBlock) FabricBlocks.LITHER_COLLECTOR_BLOCK;
    }

    public static MenuType<FabricLitherCollectorMenu> getLitherCollectorMenu() {
        return FabricScreenHandlers.LITHER_COLLECTOR_SCREEN_HANDLER;
    }

    public static BlockEntityType<FabricLitherBatteryBlockEntity> getLitherBatteryBlockEntity() {
        return FabricBlockEntities.LITHER_BATTERY_BLOCK_ENTITY;
    }

    public static BlockEntityTicker<FabricLitherBatteryBlockEntity> getLitherBatteryBlockEntityTicker() {
        return FabricLitherBatteryBlockEntity::tick;
    }

    public static MenuType<FabricLitherBatteryMenu> getLitherBatteryMenu() {
        return FabricScreenHandlers.LITHER_BATTERY_SCREEN_HANDLER;
    }

    public static LitherBatteryBlock getLitherBatteryBlock() {
        return (LitherBatteryBlock) FabricBlocks.LITHER_BATTERY_BLOCK;
    }
}
