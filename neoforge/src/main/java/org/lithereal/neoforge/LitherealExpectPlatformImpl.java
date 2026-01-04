package org.lithereal.neoforge;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.fml.loading.FMLPaths;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.*;
import org.lithereal.block.entity.EtherCollectorBlockEntity;
import org.lithereal.client.gui.screens.inventory.*;
import org.lithereal.item.WarHammerItem;
import org.lithereal.neoforge.world.block.ForgeBlocks;
import org.lithereal.neoforge.world.block.ForgeInfusementChamberBlock;
import org.lithereal.neoforge.world.block.ForgeStrippableLogBlock;
import org.lithereal.neoforge.world.block.entity.*;
import org.lithereal.neoforge.world.item.ForgeItems;
import org.lithereal.neoforge.world.item.ForgeWarHammerItem;
import org.lithereal.neoforge.client.gui.screens.inventory.ForgeMenuTypes;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.function.Supplier;

public class LitherealExpectPlatformImpl {
    public static WoodType registerWoodType(WoodType woodType) {
        return WoodType.register(woodType);
    }
    public static RotatedPillarBlock strippableLog(Supplier<Block> stripped, BlockBehaviour.Properties properties) {
        return new ForgeStrippableLogBlock(stripped.get(), properties);
    }
    /**
     * This is our actual method to {@link LitherealExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static BlockEntityType<ForgeElectricCrucibleBlockEntity> getElectricCrucibleBlockEntity() {
        return ForgeBlockEntities.ELECTRIC_CRUCIBLE.get();
    }

    public static ElectricCrucibleBlock getElectricCrucibleBlock() {
        return ForgeBlocks.ELECTRIC_CRUCIBLE.get();
    }

    public static MenuType<ElectricCrucibleMenu> getElectricCrucibleMenu() {
        return ForgeMenuTypes.ELECTRIC_CRUCIBLE_MENU.get();
    }

    public static BlockEntityType<ForgeFireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
        return ForgeBlockEntities.FIRE_CRUCIBLE.get();
    }

    public static FireCrucibleBlock getFireCrucibleBlock() {
        return ForgeBlocks.FIRE_CRUCIBLE.get();
    }

    public static MenuType<FireCrucibleMenu> getFireCrucibleMenu() {
        return ForgeMenuTypes.FIRE_CRUCIBLE_MENU.get();
    }

    public static BlockEntityType<ForgeFreezingStationBlockEntity> getFreezingStationBlockEntity() {
        return ForgeBlockEntities.FREEZING_STATION.get();
    }

    public static MenuType<FreezingStationMenu> getFreezingStationMenu() {
        return ForgeMenuTypes.FREEZING_STATION_MENU.get();
    }

    public static FreezingStationBlock getFreezingStationBlock() {
        return ForgeBlocks.FREEZING_STATION.get();
    }

    public static BlockEntityType<ForgeInfusementChamberBlockEntity> getInfusementChamberBlockEntity() {
        return ForgeBlockEntities.INFUSEMENT_CHAMBER.get();
    }

    public static InfusementChamberBlock getInfusementChamberBlock() {
        return ForgeBlocks.INFUSEMENT_CHAMBER.get();
    }

    public static Function<BlockBehaviour.Properties, InfusementChamberBlock> getInfusementChamberBlockFactory() {
        return ForgeInfusementChamberBlock::new;
    }

    public static MenuType<InfusementChamberMenu> getInfusementChamberMenu() {
        return ForgeMenuTypes.INFUSEMENT_CHAMBER_MENU.get();
    }

    public static Item getLitheriteItem() {
        return ForgeItems.LITHERITE_CRYSTAL.get();
    }

    public static BlockEntityType<EtherCollectorBlockEntity> getEtherCollectorBlockEntity() {
        return null;
    }

    public static BlockEntityTicker<EtherCollectorBlockEntity> getEtherCollectorBlockEntityTicker() {
        return null;
    }

    public static EtherCollectorBlock getEtherCollectorBlock() {
        return null;
    }

    public static MenuType<EtherCollectorMenu> getEtherCollectorMenu() {
        return null;
    }

    public static WarHammerItem createWarHammer(Tier tier, int damage, float speed, Item.Properties properties) {
        return new ForgeWarHammerItem(tier, damage, speed, properties);
    }
}
