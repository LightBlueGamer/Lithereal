package org.lithereal.neoforge;

//? neoforge {
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.fml.loading.FMLPaths;
import org.lithereal.LitherealPlatform;
import org.lithereal.block.*;
import org.lithereal.client.gui.screens.inventory.*;
import org.lithereal.neoforge.util.ModAttachmentTypes;
import org.lithereal.neoforge.world.block.ForgeStrippableLogBlock;
import org.lithereal.util.ChillData;

import java.nio.file.Path;
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

    public ChillData getChillData(LivingEntity entity) {
        return entity.getData(ModAttachmentTypes.CHILL_DATA_ATTACHMENT_TYPE.get());
    }
}
//?}