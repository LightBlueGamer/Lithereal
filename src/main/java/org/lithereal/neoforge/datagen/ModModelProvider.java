package org.lithereal.neoforge.datagen;

//? neoforge {
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Holder;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import org.apache.commons.lang3.stream.Streams;
import org.jspecify.annotations.NonNull;
import org.lithereal.Lithereal;
import org.lithereal.block.*;
import org.lithereal.block.datagen.BlockDataProvider;
import org.lithereal.data.datagen.ItemLikeDataProvider;
import org.lithereal.item.*;
import org.lithereal.item.datagen.ItemDataProvider;
import org.lithereal.util.ModBlockFamilies;

import java.util.stream.Stream;

import static net.minecraft.client.data.models.BlockModelGenerators.*;
import static org.lithereal.data.datagen.CommonModelCreators.*;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, Lithereal.MOD_ID);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        registerBlockModels(blockModels);
        registerItemModels(itemModels);
    }

    public void registerBlockModels(BlockModelGenerators blockModels) {
        ModBlockFamilies.MOD_BLOCK_FAMILIES.stream().filter(BlockFamily::shouldGenerateModel).forEach(blockFamily -> blockModels.family(blockFamily.getBaseBlock()).generateFor(blockFamily));
        ItemLikeDataProvider.ALL_DATA_PROVIDERS.stream().filter(dataProvider -> dataProvider instanceof BlockDataProvider).map(BlockDataProvider.class::cast).forEach(blockDataProvider -> blockDataProvider.modelCreator().ifPresent(creator -> creator.accept(blockModels)));
        createGlassEnclosedTorch(ModBlocks.LITHER_TORCH.get(), ModBlocks.LITHER_WALL_TORCH.get(), blockModels);

        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_PLANKS.get(), blockModels);
        blockModels.createPlantWithDefaultItem(ModTreeBlocks.PHANTOM_OAK_SAPLING.get(), ModTreeBlocks.POTTED_PHANTOM_OAK_SAPLING.get(), PlantType.NOT_TINTED);
        logBlock(ModTreeBlocks.PHANTOM_OAK_LOG.get(),  ModTreeBlocks.PHANTOM_OAK_WOOD.get(), blockModels);
        logBlock(ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get(), blockModels);
        blockModels.createHangingSign(ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get(), ModTreeBlocks.PHANTOM_OAK_HANGING_SIGN.get(), ModTreeBlocks.PHANTOM_OAK_WALL_HANGING_SIGN.get());
        blockModels.createShelf(ModTreeBlocks.PHANTOM_OAK_SHELF.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_FENCE_GATE.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_PRESSURE_PLATE.get(), blockModels);

        itemForBlockModel(ModTreeBlocks.MALISHROOM_PLANKS.get(), blockModels);
        logBlock(ModTreeBlocks.MALISHROOM_STEM.get(), ModTreeBlocks.MALISHROOM_HYPHAE.get(), blockModels);
        logBlock(ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get(), ModTreeBlocks.STRIPPED_MALISHROOM_HYPHAE.get(), blockModels);
        blockModels.createHangingSign(ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get(), ModTreeBlocks.MALISHROOM_HANGING_SIGN.get(), ModTreeBlocks.MALISHROOM_WALL_HANGING_SIGN.get());
        blockModels.createShelf(ModTreeBlocks.MALISHROOM_SHELF.get(), ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get());
        itemForBlockModel(ModTreeBlocks.MALISHROOM_FENCE_GATE.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.MALISHROOM_PRESSURE_PLATE.get(), blockModels);

        itemForBlockModel(ModTreeBlocks.FORTSHROOM_PLANKS.get(), blockModels);
        logBlock(ModTreeBlocks.FORTSHROOM_STEM.get(), ModTreeBlocks.FORTSHROOM_HYPHAE.get(), blockModels);
        logBlock(ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_HYPHAE.get(), blockModels);
        blockModels.createHangingSign(ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get(), ModTreeBlocks.FORTSHROOM_HANGING_SIGN.get(), ModTreeBlocks.FORTSHROOM_WALL_HANGING_SIGN.get());
        blockModels.createShelf(ModTreeBlocks.FORTSHROOM_SHELF.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get());
        itemForBlockModel(ModTreeBlocks.FORTSHROOM_FENCE_GATE.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.FORTSHROOM_PRESSURE_PLATE.get(), blockModels);

        blockModels.createPlantWithDefaultItem(ModVegetationBlocks.MALISHROOM.get(), ModVegetationBlocks.POTTED_MALISHROOM.get(), PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModVegetationBlocks.FORTSHROOM.get(), ModVegetationBlocks.POTTED_FORTSHROOM.get(), PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModVegetationBlocks.PHANTOM_ROSE.get(), ModVegetationBlocks.POTTED_PHANTOM_ROSE.get(), PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModVegetationBlocks.PHANTOM_ICE_FLOWER.get(), ModVegetationBlocks.POTTED_PHANTOM_ICE_FLOWER.get(), PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModVegetationBlocks.PHANTOM_ROSE_ETHEREAL_CORE.get(), ModVegetationBlocks.POTTED_PHANTOM_ROSE_ETHEREAL_CORE.get(), PlantType.NOT_TINTED);

        itemForBlockModel(ModStoneBlocks.ETHERSTONE.get(), blockModels);
        blockModels.family(ModStoneBlocks.ETHERSTONE.get()).generateFor(new BlockFamily.Builder(ModStoneBlocks.ETHERSTONE.get())
                .wall(ModStoneBlocks.ETHERSTONE_WALL.get())
                .stairs(ModStoneBlocks.ETHERSTONE_STAIRS.get())
                .slab(ModStoneBlocks.ETHERSTONE_SLAB.get())
                .button(ModStoneBlocks.ETHERSTONE_BUTTON.get())
                .pressurePlate(ModStoneBlocks.ETHERSTONE_PRESSURE_PLATE.get())
                .polished(ModStoneBlocks.POLISHED_ETHERSTONE.get())
                .getFamily());
        itemForBlockModel(ModStoneBlocks.ETHERSTONE_PRESSURE_PLATE.get(), blockModels);
        blockModels.createAxisAlignedPillarBlock(ModStoneBlocks.CHISELED_ETHERSTONE.get(), TexturedModel.COLUMN);
        itemForBlockModel(ModStoneBlocks.CHISELED_ETHERSTONE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_ETHERSTONE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_ETHERSTONE_PRESSURE_PLATE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.ETHERSTONE_BRICKS.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.ETHERSTONE_BRICK_PRESSURE_PLATE.get(), blockModels);

        itemForBlockModel(ModStoneBlocks.PAILITE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.PAILITE_PRESSURE_PLATE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_PRESSURE_PLATE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_BRICKS.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_BRICK_PRESSURE_PLATE.get(), blockModels);
        blockModels.family(ModStoneBlocks.POLISHED_PAILITE_BRICKS.get()).generateFor(new BlockFamily.Builder(ModStoneBlocks.POLISHED_PAILITE_BRICKS.get())
                .wall(ModStoneBlocks.POLISHED_PAILITE_BRICK_WALL.get())
                .stairs(ModStoneBlocks.POLISHED_PAILITE_BRICK_STAIRS.get())
                .slab(ModStoneBlocks.POLISHED_PAILITE_BRICK_SLAB.get())
                .button(ModStoneBlocks.POLISHED_PAILITE_BRICK_BUTTON.get())
                .pressurePlate(ModStoneBlocks.POLISHED_PAILITE_BRICK_PRESSURE_PLATE.get())
                .getFamily());
        blockModels.createAxisAlignedPillarBlock(ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get(), TexturedModel.COLUMN);
        itemForBlockModel(ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get(), blockModels);

        itemForBlockModel(ModStoneBlocks.VERDONE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.VERDONE_PRESSURE_PLATE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_VERDONE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_VERDONE_PRESSURE_PLATE.get(), blockModels);

        itemForBlockModel(ModStoneBlocks.LUMINITE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.LUMINITE_PRESSURE_PLATE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_LUMINITE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_LUMINITE_PRESSURE_PLATE.get(), blockModels);
    }

    public void registerItemModels(ItemModelGenerators itemModels) {
        ItemDataProvider.ALL_DATA_PROVIDERS.stream().filter(dataProvider -> dataProvider instanceof ItemDataProvider).map(ItemDataProvider.class::cast).forEach(itemDataProvider -> itemDataProvider.modelCreator().ifPresent(creator -> creator.accept(itemModels)));
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Streams.of(ModBlocks.BLOCKS);
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Streams.of(ModItems.ITEMS);
    }
}
//?}