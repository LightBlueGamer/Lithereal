package org.lithereal.forge;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.custom.FireCrucibleBlock;
import org.lithereal.block.custom.LitherCollectorBlock;
import org.lithereal.block.custom.InfusedLitheriteBlock;
import org.lithereal.block.custom.InfusementChamberBlock;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.block.entity.LitherCollectorBlockEntity;
import org.lithereal.forge.block.ForgeBlocks;
import org.lithereal.forge.block.entity.ForgeBlockEntities;
import org.lithereal.forge.block.entity.ForgeFireCrucibleBlockEntity;
import org.lithereal.forge.block.entity.ForgeFreezingStationBlockEntity;
import org.lithereal.forge.block.entity.ForgeInfusementChamberBlockEntity;
import org.lithereal.forge.item.ForgeItems;
import org.lithereal.forge.screen.ForgeFireCrucibleMenu;
import org.lithereal.forge.screen.ForgeFreezingStationMenu;
import org.lithereal.forge.screen.ForgeInfusementChamberMenu;
import org.lithereal.forge.screen.ForgeMenuTypes;
import org.lithereal.screen.LitherCollectorMenu;

import java.nio.file.Path;

public class LitherealExpectPlatformImpl {
    /**
     * This is our actual method to {@link LitherealExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static InfusedLitheriteBlock getInfusedLitheriteBlock() {
        return (InfusedLitheriteBlock) ForgeBlocks.INFUSED_LITHERITE_BLOCK.get();
    }

    public static BlockEntityType<InfusedLitheriteBlockEntity> getInfusedLitheriteBlockEntity() {
        return ForgeBlockEntities.INFUSED_LITHERITE_BLOCK.get();
    }

    public static BlockEntityType<ForgeFireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
        return ForgeBlockEntities.FIRE_CRUCIBLE.get();
    }

    public static BlockEntityTicker<ForgeFireCrucibleBlockEntity> getFireCrucibleBlockEntityTicker() {
        return ForgeFireCrucibleBlockEntity::tick;
    }

    public static FireCrucibleBlock getFireCrucibleBlock() {
        return (FireCrucibleBlock) ForgeBlocks.FIRE_CRUCIBLE.get();
    }

    public static MenuType<ForgeFireCrucibleMenu> getFireCrucibleMenu() {
        return ForgeMenuTypes.FIRE_CRUCIBLE_MENU.get();
    }

    public static BlockEntityType<ForgeFreezingStationBlockEntity> getFreezingStationBlockEntity() {
        return ForgeBlockEntities.FREEZING_STATION.get();
    }

    public static BlockEntityTicker<ForgeFreezingStationBlockEntity> getFreezingStationBlockEntityTicker() {
        return ForgeFreezingStationBlockEntity::tick;
    }

    public static MenuType<ForgeFreezingStationMenu> getFreezingStationMenu() {
        return ForgeMenuTypes.FREEZING_STATION_MENU.get();
    }

    public static LitherCollectorBlock getFreezingStationBlock() {
        return (LitherCollectorBlock) ForgeBlocks.FREEZING_STATION.get();
    }

    public static BlockEntityType<ForgeInfusementChamberBlockEntity> getInfusementChamberBlockEntity() {
        return ForgeBlockEntities.INFUSEMENT_CHAMBER.get();
    }

    public static BlockEntityTicker<ForgeInfusementChamberBlockEntity> getInfusementChamberBlockEntityTicker() {
        return ForgeInfusementChamberBlockEntity::tick;
    }

    public static InfusementChamberBlock getInfusementChamberBlock() {
        return (InfusementChamberBlock) ForgeBlocks.INFUSEMENT_CHAMBER.get();
    }

    public static MenuType<ForgeInfusementChamberMenu> getInfusementChamberMenu() {
        return ForgeMenuTypes.INFUSEMENT_CHAMBER_MENU.get();
    }

    public static Item getLitheriteItem() {
        return ForgeItems.LITHERITE_CRYSTAL.get();
    }

    public static ResourceLocation getResourceLocation(ItemStack stack) {
        return ForgeRegistries.ITEMS.getKey(stack.getItem());
    }

    public static BlockEntityType<LitherCollectorBlockEntity> getLitherCollectorBlockEntity() {
        return null;
    }

    public static BlockEntityTicker<LitherCollectorBlockEntity> getLitherCollectorBlockEntityTicker() {
        return null;
    }

    public static LitherCollectorBlock getLitherCollectorBlock() {
        return null;
    }

    public static MenuType<LitherCollectorMenu> getLitherCollectorMenu() {
        return null;
    }

    public static Iterable<Potion> getRegisteredPotions() {
        return ForgeRegistries.POTIONS;
    }
}