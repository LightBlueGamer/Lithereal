package org.lithereal.fabric;

//? fabric {
/*import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.lithereal.LitherealPlatform;
import org.lithereal.block.*;
import org.lithereal.fabric.data.mixin.accessor.WoodTypeAccessor;

import java.nio.file.Path;
import java.util.function.Supplier;

public class FabricLitherealPlatformImpl implements LitherealPlatform {
    public WoodType registerWoodType(WoodType woodType) {
        return WoodTypeAccessor.callRegister(woodType);
    }
    public RotatedPillarBlock strippableLog(Supplier<Block> stripped, BlockBehaviour.Properties properties) {
        RotatedPillarBlock rotatedPillarBlock = new RotatedPillarBlock(properties);
        StrippableBlockRegistry.register(rotatedPillarBlock, stripped.get());
        return rotatedPillarBlock;
    }
    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
*///?}