package org.lithereal;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.lithereal.block.custom.*;
import org.lithereal.block.entity.*;
import org.lithereal.screen.FireCrucibleMenu;
import org.lithereal.screen.FreezingStationMenu;
import org.lithereal.screen.InfusementChamberMenu;
import org.lithereal.screen.LitherCollectorMenu;

import java.nio.file.Path;

public class LitherealExpectPlatform {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
    @ExpectPlatform
    public static InfusedLitheriteBlock getInfusedLitheriteBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<InfusedLitheriteBlockEntity> getInfusedLitheriteBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<FireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityTicker<FireCrucibleBlockEntity> getFireCrucibleBlockEntityTicker() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static FireCrucibleBlock getFireCrucibleBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<FireCrucibleMenu> getFireCrucibleMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<FreezingStationBlockEntity> getFreezingStationBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityTicker<FreezingStationBlockEntity> getFreezingStationBlockEntityTicker() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static LitherCollectorBlock getFreezingStationBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<FreezingStationMenu> getFreezingStationMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<InfusementChamberBlockEntity> getInfusementChamberBlockEntity() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityTicker<InfusementChamberBlockEntity> getInfusementChamberBlockEntityTicker() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static InfusementChamberBlock getInfusementChamberBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<InfusementChamberMenu> getInfusementChamberMenu() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item getLitheriteItem() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ResourceLocation getResourceLocation(ItemStack stack) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<LitherCollectorBlockEntity> getLitherCollectorBlockEntity() {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static BlockEntityTicker<LitherCollectorBlockEntity> getLitherCollectorBlockEntityTicker() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static LitherCollectorBlock getLitherCollectorBlock() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MenuType<LitherCollectorMenu> getLitherCollectorMenu() {
        throw new AssertionError();
    }
}
