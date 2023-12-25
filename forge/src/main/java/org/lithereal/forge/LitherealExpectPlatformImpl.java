package org.lithereal.forge;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;
import org.lithereal.LitherealExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;
import org.lithereal.block.custom.FireCrucibleBlock;
import org.lithereal.block.custom.InfusedLitheriteBlock;
import org.lithereal.block.entity.FireCrucibleBlockEntity;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.forge.block.ForgeBlocks;
import org.lithereal.forge.block.entities.ForgeBlockEntities;
import org.lithereal.forge.block.entities.ForgeFireCrucibleBlockEntity;
import org.lithereal.forge.screen.ForgeFireCrucibleMenu;
import org.lithereal.forge.screen.ForgeMenuTypes;

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

    public static BlockEntityType<FireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
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
}
