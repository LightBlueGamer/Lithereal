package org.lithereal.neoforge.datagen;

//? neoforge {
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.color.item.Potion;
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
        fireCrucibleBlock(ModBlocks.FIRE_CRUCIBLE.get(), blockModels);
        electricCrucibleBlock(ModBlocks.ELECTRIC_CRUCIBLE.get(), blockModels);
        existingBlockWithItem(ModBlocks.FREEZING_STATION, blockModels);
        infusementChamberBlock(ModBlocks.INFUSEMENT_CHAMBER.get(), blockModels);
        createFarmland(ModBlocks.ETHEREAL_FARMLAND.get(), ModBlocks.ETHEREAL_DIRT.get(), blockModels);
        blockWithItem(ModBlocks.ETHEREAL_DIRT, blockModels);
        grassLikeBlock(ModBlocks.ETHEREAL_GRASS_BLOCK, ModBlocks.ETHEREAL_DIRT, new Constant(8573157), blockModels);
        blockWithItem(ModBlocks.SCORCHED_NETHERRACK, blockModels);
        nyliumBlock(ModBlocks.SCORCHED_CRIMSON_NYLIUM.get(), Blocks.CRIMSON_NYLIUM, ModBlocks.SCORCHED_NETHERRACK.get(), blockModels);
        nyliumBlock(ModBlocks.SCORCHED_WARPED_NYLIUM.get(), Blocks.WARPED_NYLIUM, ModBlocks.SCORCHED_NETHERRACK.get(), blockModels);
        blockWithItem(ModBlocks.COARSE_ETHEREAL_DIRT, blockModels);
        blockWithItem(ModBlocks.PHANTOM_GRAVEL, blockModels);
        blockWithItem(ModBlocks.CREATIVE_ETHER_SOURCE, blockModels);
        blockWithItem(ModBlocks.PASSIVE_ETHER_ABSORBER, blockModels);
        blockWithItem(ModBlocks.PURE_ETHER_SOURCE, blockModels);
        blockWithItem(ModBlocks.INFINITY_GLASS, blockModels);
        blockWithItem(ModBlocks.LITHERITE_CRYSTAL_BLOCK, blockModels);
        blockWithItem(ModBlocks.ETHEREAL_CRYSTAL_BLOCK, blockModels);
        corneredBlock(ModBlocks.PURE_ETHEREAL_CRYSTAL_BLOCK.get(), blockModels);
        createGlassEnclosedTorch(ModBlocks.LITHER_TORCH.get(), ModBlocks.LITHER_WALL_TORCH.get(), blockModels);
        blockModels.createLantern(ModBlocks.LITHER_LANTERN.get());
        createFire(ModBlocks.BLUE_FIRE.get(), blockModels);
        existingBlockWithFacing(ModBlocks.IMPURE_ETHEREAL_CRYSTAL.get(), blockModels);
        riftBlock(ModBlocks.ETHEREAL_RIFT.get(), blockModels);
        riftLikePortalBlock(ModBlocks.ETHEREAL_CORE_PORTAL.get(), ModBlocks.ETHEREAL_RIFT.get(), blockModels);
        createVault(ModBlocks.LITHEREAL_VAULT.get(), blockModels, false);

        blockWithItem(ModStorageBlocks.LITHERITE_BLOCK, blockModels);
        blockWithItem(ModStorageBlocks.BURNING_LITHERITE_BLOCK, blockModels);
        blockWithItem(ModStorageBlocks.FROZEN_LITHERITE_BLOCK, blockModels);
        blockWithItem(ModStorageBlocks.WITHERING_LITHERITE_BLOCK, blockModels);
        blockWithTintedItem(ModStorageBlocks.INFUSED_LITHERITE_BLOCK, new Potion(), blockModels);
        blockWithItem(ModStorageBlocks.CHARGED_LITHERITE_BLOCK, blockModels);
        blockWithItem(ModStorageBlocks.ODYSIUM_BLOCK, blockModels);
        blockWithItem(ModPhantomBlocks.PHANTOM_DIAMOND_BLOCK, blockModels);

        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_PLANKS.get(), blockModels);
        blockModels.createPlantWithDefaultItem(ModTreeBlocks.PHANTOM_OAK_SAPLING.get(), ModTreeBlocks.POTTED_PHANTOM_OAK_SAPLING.get(), PlantType.NOT_TINTED);
        logBlock(ModTreeBlocks.PHANTOM_OAK_LOG.get(),  ModTreeBlocks.PHANTOM_OAK_WOOD.get(), blockModels);
        logBlock(ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get(), blockModels);
        blockModels.createHangingSign(ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get(), ModTreeBlocks.PHANTOM_OAK_HANGING_SIGN.get(), ModTreeBlocks.PHANTOM_OAK_WALL_HANGING_SIGN.get());
        blockModels.createShelf(ModTreeBlocks.PHANTOM_OAK_SHELF.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
        blockModels.createTrivialBlock(ModTreeBlocks.PHANTOM_OAK_LEAVES.get(), TexturedModel.LEAVES);
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_LEAVES.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_FENCE_GATE.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_PRESSURE_PLATE.get(), blockModels);

        blockWithItem(ModTreeBlocks.MALISHROOM_BLOCK, blockModels);
        blockWithItem(ModTreeBlocks.RED_MALISHROOM_BLOCK, blockModels);
        itemForBlockModel(ModTreeBlocks.MALISHROOM_PLANKS.get(), blockModels);
        logBlock(ModTreeBlocks.MALISHROOM_STEM.get(), ModTreeBlocks.MALISHROOM_HYPHAE.get(), blockModels);
        logBlock(ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get(), ModTreeBlocks.STRIPPED_MALISHROOM_HYPHAE.get(), blockModels);
        blockModels.createHangingSign(ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get(), ModTreeBlocks.MALISHROOM_HANGING_SIGN.get(), ModTreeBlocks.MALISHROOM_WALL_HANGING_SIGN.get());
        blockModels.createShelf(ModTreeBlocks.MALISHROOM_SHELF.get(), ModTreeBlocks.STRIPPED_MALISHROOM_STEM.get());
        itemForBlockModel(ModTreeBlocks.MALISHROOM_FENCE_GATE.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.MALISHROOM_PRESSURE_PLATE.get(), blockModels);

        blockWithItem(ModTreeBlocks.FORTSHROOM_BLOCK, blockModels);
        itemForBlockModel(ModTreeBlocks.FORTSHROOM_PLANKS.get(), blockModels);
        logBlock(ModTreeBlocks.FORTSHROOM_STEM.get(), ModTreeBlocks.FORTSHROOM_HYPHAE.get(), blockModels);
        logBlock(ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_HYPHAE.get(), blockModels);
        blockModels.createHangingSign(ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get(), ModTreeBlocks.FORTSHROOM_HANGING_SIGN.get(), ModTreeBlocks.FORTSHROOM_WALL_HANGING_SIGN.get());
        blockModels.createShelf(ModTreeBlocks.FORTSHROOM_SHELF.get(), ModTreeBlocks.STRIPPED_FORTSHROOM_STEM.get());
        itemForBlockModel(ModTreeBlocks.FORTSHROOM_FENCE_GATE.get(), blockModels);
        itemForBlockModel(ModTreeBlocks.FORTSHROOM_PRESSURE_PLATE.get(), blockModels);

        blockModels.createPlantWithDefaultItem(ModVegetationBlocks.MALISHROOM.get(), ModVegetationBlocks.POTTED_MALISHROOM.get(), PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModVegetationBlocks.FORTSHROOM.get(), ModVegetationBlocks.POTTED_FORTSHROOM.get(), PlantType.NOT_TINTED);

        itemForBlockModel(ModStoneBlocks.ETHERSTONE.get(), blockModels);
        blockModels.family(ModStoneBlocks.ETHERSTONE.get()).generateFor(new BlockFamily.Builder(ModStoneBlocks.ETHERSTONE.get())
                .wall(ModStoneBlocks.ETHERSTONE_WALL.get())
                .stairs(ModStoneBlocks.ETHERSTONE_STAIRS.get())
                .slab(ModStoneBlocks.ETHERSTONE_SLAB.get())
                .polished(ModStoneBlocks.POLISHED_ETHERSTONE.get())
                .getFamily());
        blockModels.createAxisAlignedPillarBlock(ModStoneBlocks.CHISELED_ETHERSTONE.get(), TexturedModel.COLUMN);
        itemForBlockModel(ModStoneBlocks.CHISELED_ETHERSTONE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_ETHERSTONE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.ETHERSTONE_BRICKS.get(), blockModels);

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
        itemForBlockModel(ModStoneBlocks.POLISHED_VERDONE.get(), blockModels);

        itemForBlockModel(ModStoneBlocks.LUMINITE.get(), blockModels);
        itemForBlockModel(ModStoneBlocks.POLISHED_LUMINITE.get(), blockModels);

        blockWithItem(ModOreBlocks.LITHERITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_LITHERITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_LITHERITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.NERITH_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_NERITH_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_NERITH_ORE, blockModels);
        blockWithItem(ModOreBlocks.LUMINIUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_LUMINIUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_LUMINIUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.CYRUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_CYRUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_CYRUM_ORE, blockModels);
        blockWithItem(ModOreBlocks.COPALITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_COPALITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_COPALITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.AURELITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.DEEPSLATE_AURELITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.ETHERSTONE_AURELITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.SATURNITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.PAILITE_SATURNITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.HELLIONITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.PAILITE_HELLIONITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.ELUNITE_ORE, blockModels);
        blockWithItem(ModOreBlocks.CHRYON_ORE, blockModels);
        blockWithItem(ModOreBlocks.ALLIAN_ORE, blockModels);
        blockWithItem(ModOreBlocks.PAILITE_NETHERITE_ORE, blockModels);

        blockWithItem(ModPhantomBlocks.PHANTOM_DIAMOND_ORE, blockModels);
        blockWithItem(ModPhantomBlocks.PHANTOM_QUARTZ_ORE, blockModels);
        blockModels.createPlantWithDefaultItem(ModPhantomBlocks.PHANTOM_ROSE.get(), ModPhantomBlocks.POTTED_PHANTOM_ROSE.get(), PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModPhantomBlocks.PHANTOM_ICE_FLOWER.get(), ModPhantomBlocks.POTTED_PHANTOM_ICE_FLOWER.get(), PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModPhantomBlocks.PHANTOM_ROSE_ETHEREAL_CORE.get(), ModPhantomBlocks.POTTED_PHANTOM_ROSE_ETHEREAL_CORE.get(), PlantType.NOT_TINTED);
    }

    public void registerItemModels(ItemModelGenerators itemModels) {
        ItemDataProvider.ALL_ITEM_DATA_PROVIDERS.forEach(itemDataProvider -> itemDataProvider.modelCreator().ifPresent(creator -> creator.accept(itemModels)));
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