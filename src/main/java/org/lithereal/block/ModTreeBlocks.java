package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.lithereal.LitherealPlatform;
import org.lithereal.block.datagen.BlockDataProvider;
import org.lithereal.block.datagen.BlockDataTemplates;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.data.mixin.accessor.BlockSetTypeAccessor;
import org.lithereal.tags.ModTags;
import org.lithereal.world.feature.ModFeatures;

import java.util.Optional;
import java.util.function.Supplier;

import static org.lithereal.block.ModBlocks.*;

public class ModTreeBlocks {
    public static final TreeGrower PHANTOM_OAK = new TreeGrower("phantom_oak", 0.1F, Optional.of(ModFeatures.MEGA_PHANTOM_OAK), Optional.empty(), Optional.of(ModFeatures.PHANTOM_OAK), Optional.of(ModFeatures.FANCY_PHANTOM_OAK), Optional.empty(), Optional.empty());
    public static final BlockSetType PHANTOM_OAK_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("phantom_oak"));
    public static final WoodType PHANTOM_OAK_WOOD_TYPE = LitherealPlatform.INSTANCE.registerWoodType(new WoodType("phantom_oak", PHANTOM_OAK_SET));
    public static final RegistrySupplier<Block> PHANTOM_OAK_PLANKS = createPlanks("phantom_oak_planks", SoundType.WOOD, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_SLAB = createSlab("phantom_oak_slab", SoundType.WOOD, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_STAIRS = createStairs("phantom_oak_stairs", PHANTOM_OAK_PLANKS);

    public static final RegistrySupplier<Block> PHANTOM_OAK_FENCE = createFence("phantom_oak_fence", SoundType.WOOD, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_FENCE_GATE = createFenceGate("phantom_oak_fence_gate", PHANTOM_OAK_WOOD_TYPE, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_PRESSURE_PLATE = createPressurePlate("phantom_oak_pressure_plate", PHANTOM_OAK_SET, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_BUTTON = createButton("phantom_oak_button", PHANTOM_OAK_SET, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_SHELF = createShelf("phantom_oak_shelf", MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_SIGN = createSign("phantom_oak_sign", PHANTOM_OAK_WOOD_TYPE, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_WALL_SIGN = createWallSign("phantom_oak_wall_sign", PHANTOM_OAK_WOOD_TYPE, MapColor.TERRACOTTA_PURPLE, PHANTOM_OAK_SIGN);

    public static final RegistrySupplier<Block> PHANTOM_OAK_HANGING_SIGN = createHangingSign("phantom_oak_hanging_sign", PHANTOM_OAK_WOOD_TYPE, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_WALL_HANGING_SIGN = createWallHangingSign("phantom_oak_wall_hanging_sign", PHANTOM_OAK_WOOD_TYPE, MapColor.TERRACOTTA_PURPLE, PHANTOM_OAK_HANGING_SIGN);

    public static final RegistrySupplier<Block> PHANTOM_OAK_DOOR = createDoor("phantom_oak_door", PHANTOM_OAK_SET, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_TRAPDOOR = createTrapdoor("phantom_oak_trapdoor", PHANTOM_OAK_SET, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_LEAVES = createUntintedParticleLeaves("phantom_oak_leaves", 0.1F, ModParticles.PHANTOM_OAK_LEAVES::get, SoundType.GRASS, MapColor.COLOR_MAGENTA);

    public static final RegistrySupplier<Block> PHANTOM_OAK_SAPLING = createSapling("phantom_oak_sapling", PHANTOM_OAK);

    public static final RegistrySupplier<Block> POTTED_PHANTOM_OAK_SAPLING = createPottedPlant("potted_phantom_oak_sapling", PHANTOM_OAK_SAPLING);

    public static final RegistrySupplier<Block> STRIPPED_PHANTOM_OAK_LOG = createStrippedLog("stripped_phantom_oak_log", ModTags.PHANTOM_OAK_LOGS_B, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_LOG = createLog("phantom_oak_log", ModTags.PHANTOM_OAK_LOGS_B, MapColor.COLOR_PURPLE, MapColor.TERRACOTTA_PURPLE, STRIPPED_PHANTOM_OAK_LOG);

    public static final RegistrySupplier<Block> STRIPPED_PHANTOM_OAK_WOOD = createStrippedWood("stripped_phantom_oak_wood", ModTags.PHANTOM_OAK_LOGS_B, MapColor.TERRACOTTA_PURPLE);

    public static final RegistrySupplier<Block> PHANTOM_OAK_WOOD = createWood("phantom_oak_wood", ModTags.PHANTOM_OAK_LOGS_B, MapColor.COLOR_PURPLE, STRIPPED_PHANTOM_OAK_WOOD);

    public static final BlockSetType FORTSHROOM_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("fortshroom"));
    public static final WoodType FORTSHROOM_WOOD_TYPE = LitherealPlatform.INSTANCE.registerWoodType(new WoodType("fortshroom", FORTSHROOM_SET));
    public static final RegistrySupplier<Block> FORTSHROOM_PLANKS = createPlanks("fortshroom_planks", SoundType.NETHER_WOOD, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_SLAB = createSlab("fortshroom_slab", SoundType.NETHER_WOOD, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_STAIRS = createStairs("fortshroom_stairs", FORTSHROOM_PLANKS);

    public static final RegistrySupplier<Block> FORTSHROOM_FENCE = createFence("fortshroom_fence", SoundType.NETHER_WOOD, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_FENCE_GATE = createFenceGate("fortshroom_fence_gate", FORTSHROOM_WOOD_TYPE, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_PRESSURE_PLATE = createPressurePlate("fortshroom_pressure_plate", FORTSHROOM_SET, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_BUTTON = createButton("fortshroom_button", FORTSHROOM_SET, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_SHELF = createShelf("fortshroom_shelf", MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_SIGN = createSign("fortshroom_sign", FORTSHROOM_WOOD_TYPE, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_WALL_SIGN = createWallSign("fortshroom_wall_sign", FORTSHROOM_WOOD_TYPE, MapColor.COLOR_GREEN, FORTSHROOM_SIGN);

    public static final RegistrySupplier<Block> FORTSHROOM_HANGING_SIGN = createHangingSign("fortshroom_hanging_sign", FORTSHROOM_WOOD_TYPE, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_WALL_HANGING_SIGN = createWallHangingSign("fortshroom_wall_hanging_sign", FORTSHROOM_WOOD_TYPE, MapColor.COLOR_GREEN, FORTSHROOM_HANGING_SIGN);

    public static final RegistrySupplier<Block> FORTSHROOM_DOOR = createDoor("fortshroom_door", FORTSHROOM_SET, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_TRAPDOOR = createTrapdoor("fortshroom_trapdoor", FORTSHROOM_SET, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> STRIPPED_FORTSHROOM_STEM = createStrippedLog("stripped_fortshroom_stem", ModTags.FORTSHROOM_STEMS_B, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_STEM = createLog("fortshroom_stem", ModTags.FORTSHROOM_STEMS_B, MapColor.DEEPSLATE, MapColor.COLOR_GREEN, STRIPPED_FORTSHROOM_STEM);

    public static final RegistrySupplier<Block> STRIPPED_FORTSHROOM_HYPHAE = createStrippedWood("stripped_fortshroom_hyphae", ModTags.FORTSHROOM_STEMS_B, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> FORTSHROOM_HYPHAE = createWood("fortshroom_hyphae", ModTags.FORTSHROOM_STEMS_B, MapColor.DEEPSLATE, STRIPPED_FORTSHROOM_HYPHAE);

    public static final RegistrySupplier<Block> FORTSHROOM_BLOCK = BlockDataTemplates.STORAGE
            .addTags(BlockTags.MINEABLE_WITH_HOE).consume(registerBlock("fortshroom_block", resourceKey ->
                    new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_WART_BLOCK).setId(resourceKey).mapColor(MapColor.COLOR_GREEN))), ModVegetationBlocks.FORTSHROOM);

    public static final BlockSetType MALISHROOM_SET = BlockSetTypeAccessor.callRegister(new BlockSetType("malishroom"));
    public static final WoodType MALISHROOM_WOOD_TYPE = LitherealPlatform.INSTANCE.registerWoodType(new WoodType("malishroom", MALISHROOM_SET));
    public static final RegistrySupplier<Block> MALISHROOM_PLANKS = createPlanks("malishroom_planks", SoundType.NETHER_WOOD, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_SLAB = createSlab("malishroom_slab", SoundType.NETHER_WOOD, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_STAIRS = createStairs("malishroom_stairs", MALISHROOM_PLANKS);

    public static final RegistrySupplier<Block> MALISHROOM_FENCE = createFence("malishroom_fence", SoundType.NETHER_WOOD, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_FENCE_GATE = createFenceGate("malishroom_fence_gate", MALISHROOM_WOOD_TYPE, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_PRESSURE_PLATE = createPressurePlate("malishroom_pressure_plate", MALISHROOM_SET, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_BUTTON = createButton("malishroom_button", MALISHROOM_SET, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_SHELF = createShelf("malishroom_shelf", MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_SIGN = createSign("malishroom_sign", MALISHROOM_WOOD_TYPE, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_WALL_SIGN = createWallSign("malishroom_wall_sign", MALISHROOM_WOOD_TYPE, MapColor.COLOR_PURPLE, MALISHROOM_SIGN);

    public static final RegistrySupplier<Block> MALISHROOM_HANGING_SIGN = createHangingSign("malishroom_hanging_sign", MALISHROOM_WOOD_TYPE, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_WALL_HANGING_SIGN = createWallHangingSign("malishroom_wall_hanging_sign", MALISHROOM_WOOD_TYPE, MapColor.COLOR_PURPLE, MALISHROOM_HANGING_SIGN);

    public static final RegistrySupplier<Block> MALISHROOM_DOOR = createDoor("malishroom_door", MALISHROOM_SET, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_TRAPDOOR = createTrapdoor("malishroom_trapdoor", MALISHROOM_SET, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> STRIPPED_MALISHROOM_STEM = createStrippedLog("stripped_malishroom_stem", ModTags.MALISHROOM_STEMS_B, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_STEM = createLog("malishroom_stem", ModTags.MALISHROOM_STEMS_B, MapColor.DEEPSLATE, MapColor.COLOR_PURPLE, STRIPPED_MALISHROOM_STEM);

    public static final RegistrySupplier<Block> STRIPPED_MALISHROOM_HYPHAE = createStrippedWood("stripped_malishroom_hyphae", ModTags.MALISHROOM_STEMS_B, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> MALISHROOM_HYPHAE = createWood("malishroom_hyphae", ModTags.MALISHROOM_STEMS_B, MapColor.DEEPSLATE, STRIPPED_MALISHROOM_HYPHAE);

    public static final RegistrySupplier<Block> MALISHROOM_BLOCK = BlockDataTemplates.STORAGE
            .addTags(BlockTags.MINEABLE_WITH_HOE).consume(registerBlock("malishroom_block", resourceKey ->
                    new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHER_WART_BLOCK).setId(resourceKey).mapColor(MapColor.COLOR_PURPLE))), ModVegetationBlocks.MALISHROOM);

    public static final RegistrySupplier<Block> RED_MALISHROOM_BLOCK = BlockDataTemplates.STANDARD
            .addTags(BlockTags.MINEABLE_WITH_HOE).consume(registerBlock("red_malishroom_block", resourceKey ->
                    new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHER_WART_BLOCK).setId(resourceKey).mapColor(MapColor.COLOR_RED))));

    public static RegistrySupplier<Block> createPlanks(String name, SoundType soundType, MapColor color) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.PLANKS)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlock(name, resourceKey ->
                        new Block(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(soundType).setId(resourceKey).mapColor(color))));
    }

    public static RegistrySupplier<Block> createSlab(String name, SoundType soundType, MapColor color) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.WOODEN_SLABS)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlock(name, resourceKey ->
                        new SlabBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(soundType).setId(resourceKey).mapColor(color))));
    }

    public static RegistrySupplier<Block> createStairs(String name, RegistrySupplier<Block> parent) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.WOODEN_STAIRS)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlock(name, resourceKey ->
                        new StairBlock(parent.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(parent.get()).setId(resourceKey))));
    }

    public static RegistrySupplier<Block> createFence(String name, SoundType soundType, MapColor color) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.WOODEN_FENCES)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlock(name, resourceKey ->
                        new FenceBlock(BlockBehaviour.Properties.of().setId(resourceKey).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(soundType).mapColor(color))));
    }

    public static RegistrySupplier<Block> createFenceGate(String name, WoodType type, MapColor color) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.FENCE_GATES)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlock(name, resourceKey ->
                        new FenceGateBlock(type, BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).setId(resourceKey).mapColor(color))));
    }

    public static RegistrySupplier<Block> createPressurePlate(String name, BlockSetType type, MapColor color) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.WOODEN_PRESSURE_PLATES)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlock(name, resourceKey ->
                        new PressurePlateBlock(type, BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY).setId(resourceKey).mapColor(color))));
    }

    public static RegistrySupplier<Block> createButton(String name, BlockSetType type, MapColor color) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.WOODEN_BUTTONS)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlock(name, resourceKey ->
                        new ButtonBlock(type, 30, BlockBehaviour.Properties.of().noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY).setId(resourceKey).mapColor(color))));
    }

    public static RegistrySupplier<Block> createShelf(String name, MapColor color) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.WOODEN_SHELVES)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlock(name, resourceKey ->
                                new ShelfBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).sound(SoundType.SHELF).strength(2.0F, 3.0F).setId(resourceKey).mapColor(color)),
                        () -> new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)));
    }

    public static RegistrySupplier<Block> createSign(String name, WoodType type, MapColor color) {
        return BlockDataTemplates.EMPTY.addTag(BlockTags.STANDING_SIGNS)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlockOnly(name, resourceKey ->
                        new CustomStandingSignBlock(type, BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).setId(resourceKey).mapColor(color))));
    }

    public static RegistrySupplier<Block> createWallSign(String name, WoodType type, MapColor color, RegistrySupplier<Block> parent) {
        return BlockDataTemplates.EMPTY.addTag(BlockTags.WALL_SIGNS).consume(registerBlockOnly(name, resourceKey ->
                new CustomWallSignBlock(type, BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).setId(resourceKey).mapColor(color).overrideLootTable(parent.get().getLootTable()).overrideDescription(parent.get().getDescriptionId()))));
    }

    public static RegistrySupplier<Block> createHangingSign(String name, WoodType type, MapColor color) {
        return BlockDataTemplates.EMPTY.addTag(BlockTags.CEILING_HANGING_SIGNS)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlockOnly(name, resourceKey ->
                        new CustomCeilingHangingSignBlock(type, BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).setId(resourceKey).mapColor(color))));
    }

    public static RegistrySupplier<Block> createWallHangingSign(String name, WoodType type, MapColor color, RegistrySupplier<Block> parent) {
        return BlockDataTemplates.EMPTY.addTag(BlockTags.WALL_HANGING_SIGNS).consume(registerBlockOnly(name, resourceKey ->
                new CustomWallHangingSignBlock(type, BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).setId(resourceKey).mapColor(color).overrideLootTable(parent.get().getLootTable()).overrideDescription(parent.get().getDescriptionId()))));
    }

    public static RegistrySupplier<Block> createDoor(String name, BlockSetType type, MapColor color) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.WOODEN_DOORS)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlock(name, resourceKey ->
                        new DoorBlock(type, BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().pushReaction(PushReaction.DESTROY).setId(resourceKey).mapColor(color)), DoubleHighBlockItem::new));
    }

    public static RegistrySupplier<Block> createTrapdoor(String name, BlockSetType type, MapColor color) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.WOODEN_TRAPDOORS)
                .addItemTag(ItemTags.NON_FLAMMABLE_WOOD).consume(registerBlock(name, resourceKey ->
                        new TrapDoorBlock(type, BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(Blocks::never).setId(resourceKey).mapColor(color))));
    }

    public static RegistrySupplier<Block> createUntintedParticleLeaves(String name, float leafParticleChance, Supplier<ParticleOptions> optionsSupplier, SoundType soundType, MapColor mapColor) {
        return BlockDataTemplates.LEAVES.consume(registerBlock(name, resourceKey ->
                new ModUntintedParticleLeavesBlock(leafParticleChance, optionsSupplier, BlockBehaviour.Properties.of().mapColor(mapColor).strength(0.2F).randomTicks().sound(soundType).noOcclusion().isValidSpawn(Blocks::ocelotOrParrot).isSuffocating((_, _, _) -> false).isViewBlocking((_, _, _) -> false).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor((_, _, _) -> false).setId(resourceKey))));
    }

    public static RegistrySupplier<Block> createSapling(String name, TreeGrower treeGrower) {
        return BlockDataTemplates.EMPTY.and(BlockDataProvider.TagType.SAPLINGS)
                .consume(registerBlock(name, resourceKey ->
                        new SaplingBlock(treeGrower, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).setId(resourceKey))));
    }

    public static RegistrySupplier<Block> createPottedPlant(String name, RegistrySupplier<Block> parent) {
        return BlockDataTemplates.EMPTY.addTag(BlockTags.FLOWER_POTS)
                .consume(registerBlockOnly(name, resourceKey ->
                        new FlowerPotBlock(parent.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).setId(resourceKey))));
    }

    public static RegistrySupplier<Block> createStrippedLog(String name, TagKey<Block> logsTag, MapColor color) {
        return BlockDataTemplates.EMPTY.addTags(logsTag, BlockDataProvider.cBlockTag("stripped_logs")).consume(registerBlock(name, resourceKey ->
                strippedLog(color, resourceKey)));
    }

    public static RegistrySupplier<Block> createLog(String name, TagKey<Block> logsTag, MapColor colorSide, MapColor colorTop, RegistrySupplier<Block> stripped) {
        return BlockDataTemplates.EMPTY.addTag(logsTag).consume(registerBlock(name, resourceKey ->
                log(stripped, colorSide, colorTop, resourceKey)));
    }

    public static RegistrySupplier<Block> createStrippedWood(String name, TagKey<Block> logsTag, MapColor color) {
        return BlockDataTemplates.EMPTY.addTags(logsTag, BlockDataProvider.cBlockTag("stripped_wood")).consume(registerBlock(name, resourceKey ->
                strippedLog(color, resourceKey)));
    }

    public static RegistrySupplier<Block> createWood(String name, TagKey<Block> logsTag, MapColor color, RegistrySupplier<Block> stripped) {
        return BlockDataTemplates.EMPTY.addTag(logsTag).consume(registerBlock(name, resourceKey ->
                log(stripped, color, resourceKey)));
    }

    public static void register() {

    }
}
