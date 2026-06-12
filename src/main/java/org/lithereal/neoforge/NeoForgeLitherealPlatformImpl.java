package org.lithereal.neoforge;

//? neoforge {
/*import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.fml.loading.FMLPaths;
import org.lithereal.LitherealPlatform;
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

public class NeoForgeLitherealPlatformImpl implements LitherealPlatform {
    public WoodType registerWoodType(WoodType woodType) {
        return WoodType.register(woodType);
    }
    public RotatedPillarBlock strippableLog(Supplier<Block> stripped, BlockBehaviour.Properties properties) {
        return new ForgeStrippableLogBlock(stripped.get(), properties);
    }

    public Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
*///?}