package org.lithereal.fabric;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.*;
import org.lithereal.fabric.client.gui.screens.inventory.*;
import org.lithereal.fabric.data.mixin.accessor.WoodTypeAccessor;
import org.lithereal.fabric.world.block.FabricBlocks;
import org.lithereal.fabric.world.block.FabricInfusementChamberBlock;
import org.lithereal.fabric.world.block.entity.*;
import org.lithereal.fabric.world.item.FabricItems;
import org.lithereal.item.WarHammerItem;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.function.Supplier;

public class LitherealExpectPlatformImpl {
    public static WoodType registerWoodType(WoodType woodType) {
        return WoodTypeAccessor.callRegister(woodType);
    }
    public static RotatedPillarBlock strippableLog(Supplier<Block> stripped, BlockBehaviour.Properties properties) {
        RotatedPillarBlock rotatedPillarBlock = new RotatedPillarBlock(properties);
        StrippableBlockRegistry.register(rotatedPillarBlock, stripped.get());
        return rotatedPillarBlock;
    }
    /**
     * This is our actual method to {@link LitherealExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static BlockEntityType<FabricElectricCrucibleBlockEntity> getElectricCrucibleBlockEntity() {
        return FabricBlockEntities.ELECTRIC_CRUCIBLE_BLOCK_ENTITY;
    }

    public static ElectricCrucibleBlock getElectricCrucibleBlock() {
        return (ElectricCrucibleBlock) FabricBlocks.ELECTRIC_CRUCIBLE_BLOCK;
    }

    public static MenuType<FabricElectricCrucibleMenu> getElectricCrucibleMenu() {
        return FabricScreenHandlers.ELECTRIC_CRUCIBLE_SCREEN_HANDLER;
    }

    public static BlockEntityType<FabricFireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
        return FabricBlockEntities.FIRE_CRUCIBLE_BLOCK_ENTITY;
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

    public static MenuType<FabricFreezingStationMenu> getFreezingStationMenu() {
        return FabricScreenHandlers.FREEZING_STATION_SCREEN_HANDLER;
    }

    public static FreezingStationBlock getFreezingStationBlock() {
        return (FreezingStationBlock) FabricBlocks.FREEZING_STATION_BLOCK;
    }

    public static BlockEntityType<FabricInfusementChamberBlockEntity> getInfusementChamberBlockEntity() {
        return FabricBlockEntities.INFUSEMENT_CHAMBER_BLOCK_ENTITY;
    }

    public static InfusementChamberBlock getInfusementChamberBlock() {
        return (InfusementChamberBlock) FabricBlocks.INFUSEMENT_CHAMBER_BLOCK;
    }

    public static Function<BlockBehaviour.Properties, InfusementChamberBlock> getInfusementChamberBlockFactory() {
        return FabricInfusementChamberBlock::new;
    }

    public static MenuType<FabricInfusementChamberMenu> getInfusementChamberMenu() {
        return FabricScreenHandlers.INFUSEMENT_CHAMBER_SCREEN_HANDLER;
    }

    public static Item getLitheriteItem() {
        return FabricItems.LITHERITE_CRYSTAL;
    }

    public static WarHammerItem createWarHammer(Tier tier, int damage, float speed, Item.Properties properties) {
        return new WarHammerItem(tier, damage, speed, properties);
    }
}
