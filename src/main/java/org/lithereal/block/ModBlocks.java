package org.lithereal.block;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.color.item.Constant;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.lithereal.Lithereal;
import org.lithereal.LitherealPlatform;
import org.lithereal.block.datagen.BlockDataProvider;
import org.lithereal.block.datagen.BlockDataTemplate;
import org.lithereal.block.datagen.BlockDataTemplates;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.item.ModItems;
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.tags.ModTags;
import org.lithereal.util.CommonUtils;
import org.lithereal.util.HoeTillable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.lithereal.block.datagen.BlockDataTemplate.*;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Lithereal.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> ETHEREAL_RIFT = BlockDataTemplates.RIFT.consume(registerBlock("ethereal_rift",
            resourceKey -> new EtherealRiftBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.END_PORTAL).setId(resourceKey))));

    public static final RegistrySupplier<Block> ETHEREAL_CORE_PORTAL = BlockDataTemplates.RIFT
            .copyWithModelOverride(BlockDataTemplate.RIFT_LIKE_PORTAL_BLOCK).consumeWithModelRelatives(registerBlock("ethereal_core_portal",
                    resourceKey -> new EtherealCorePortalBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.END_PORTAL).setId(resourceKey))), ETHEREAL_RIFT);

    public static final RegistrySupplier<Block> IMPURE_ETHEREAL_CRYSTAL = BlockDataTemplates.EMPTY.copyWithModelOverride(BlockDataTemplate.EXISTING_CREATE_STATES_WITH_FACING)
            .addTag(BlockTags.MINEABLE_WITH_PICKAXE).consume(registerBlock("impure_ethereal_crystal",
                    resourceKey -> new ImpureEtherealCrystalBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_BLUE).sound(SoundType.AMETHYST_CLUSTER).strength(5, 8))));

    public static final RegistrySupplier<Block> ETHEREAL_CRYSTAL_BLOCK = BlockDataTemplates.STORAGE
            .addTags(BlockTags.MINEABLE_WITH_PICKAXE, ModTags.NEEDS_ODYSIUM_TOOL).consume(registerBlock("ethereal_crystal_block",
            resourceKey -> new HalfTransparentBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(6f)
                    .requiresCorrectToolForDrops()
                    .explosionResistance(25)
                    .mapColor(MapColor.COLOR_BLUE)
                    .sound(SoundType.AMETHYST_CLUSTER)
                    .lightLevel(state -> 9)
                    .noOcclusion()
                    .isValidSpawn(CommonUtils::never)
                    .isRedstoneConductor(CommonUtils::never)
                    .isSuffocating(CommonUtils::never)
                    .isViewBlocking(CommonUtils::never))), ModRawMaterialItems.IMPURE_ETHEREAL_CRYSTAL_SHARD);

    public static final RegistrySupplier<Block> PURE_ETHER_SOURCE = BlockDataTemplates.STANDARD.addTags(BlockTags.MINEABLE_WITH_PICKAXE, ModTags.NEEDS_APEX_TOOL).consume(registerBlock("pure_ether_source",
            resourceKey -> new Block(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(180f)
                    .requiresCorrectToolForDrops()
                    .explosionResistance(1000)
                    .mapColor(MapColor.COLOR_BLUE)
                    .sound(SoundType.AMETHYST_CLUSTER)
                    .lightLevel(state -> 15)
                    .noOcclusion()
                    .isValidSpawn(CommonUtils::never)
                    .isRedstoneConductor(CommonUtils::never))));

    public static final RegistrySupplier<Block> LITHERITE_CRYSTAL_BLOCK = BlockDataTemplates.STANDARD
            .addTags(BlockTags.MINEABLE_WITH_PICKAXE, ModTags.NEEDS_ODYSIUM_TOOL).consume(registerBlock("litherite_crystal_block",
            resourceKey -> new HalfTransparentBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(6f)
                    .requiresCorrectToolForDrops()
                    .explosionResistance(25)
                    .mapColor(MapColor.COLOR_GREEN)
                    .sound(SoundType.AMETHYST_CLUSTER)
                    .lightLevel(state -> 9)
                    .noOcclusion()
                    .isValidSpawn(CommonUtils::never)
                    .isRedstoneConductor(CommonUtils::never)
                    .isSuffocating(CommonUtils::never)
                    .isViewBlocking(CommonUtils::never))));

    public static final RegistrySupplier<Block> PURE_ETHEREAL_CRYSTAL_BLOCK = BlockDataTemplates.STANDARD.
            copyWithModelOverride(CORNERED_BLOCK).consume(registerBlock("pure_ethereal_crystal_block",
                    resourceKey -> new PureEtherealCrystalBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.END_PORTAL_FRAME).setId(resourceKey), ETHEREAL_CORE_PORTAL.get())));

    public static final RegistrySupplier<Block> CREATIVE_ETHER_SOURCE = BlockDataTemplates.STANDARD.addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL).consume(registerBlock("creative_ether_source",
            resourceKey -> new InfiniteEtherSourceBlock(10000, (TagKey<Biome>) null, BlockBehaviour.Properties.ofLegacyCopy(Blocks.IRON_BLOCK).setId(resourceKey))));

    public static final RegistrySupplier<Block> PASSIVE_ETHER_ABSORBER = BlockDataTemplates.STANDARD.addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL).consume(registerBlock("passive_ether_absorber",
            resourceKey -> new InfiniteEtherSourceBlock(10, ModTags.PROVIDES_ETHER, BlockBehaviour.Properties.ofLegacyCopy(Blocks.IRON_BLOCK).setId(resourceKey))));

    public static final RegistrySupplier<Block> COARSE_ETHEREAL_DIRT = BlockDataTemplates.STANDARD
            .and(BlockDataProvider.TagType.DIRTLIKE).addTags(BlockTags.DIRT, ModTags.BLUE_FIRE_BASE_BLOCKS).consume(registerBlock("coarse_ethereal_dirt",
                    resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.COARSE_DIRT).setId(resourceKey))));

    public static final RegistrySupplier<Block> ETHEREAL_DIRT = BlockDataTemplates.STANDARD
            .and(BlockDataProvider.TagType.DIRTLIKE).addTags(BlockTags.DIRT, ModTags.ETHEREAL_CORE_CARVER_REPLACEABLES, ModTags.BLUE_FIRE_BASE_BLOCKS).consume(registerBlock("ethereal_dirt",
                    resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.DIRT).setId(resourceKey))));

    public static final RegistrySupplier<Block> ETHEREAL_FARMLAND = BlockDataTemplates.STANDARD
            .copyWithModelOverride(FARMLAND_BLOCK)
            .and(BlockDataProvider.TagType.DIRTLIKE).and(BlockDataProvider.TagType.FARMLAND).consumeWithModelRelatives(registerBlock("ethereal_farmland",
            resourceKey -> new ModFarmlandBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.FARMLAND).setId(resourceKey), ETHEREAL_DIRT.getKey())), ETHEREAL_DIRT);

    public static final RegistrySupplier<Block> ETHEREAL_GRASS_BLOCK = BlockDataTemplates.STANDARD
            .copyWithModelOverride(GRASS_LIKE_BLOCK.apply(new Constant(8573157)))
            .and(BlockDataProvider.TagType.DIRTLIKE).addTags(BlockTags.DIRT, ModTags.ETHEREAL_CORE_CARVER_REPLACEABLES, ModTags.BLUE_FIRE_BASE_BLOCKS).consumeWithModelRelatives(registerBlock("ethereal_grass_block",
                    resourceKey -> new ExtendedGrassBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.GRASS_BLOCK).setId(resourceKey), ETHEREAL_DIRT.getKey())), ETHEREAL_DIRT);

    public static final RegistrySupplier<Block> PHANTOM_GRAVEL = BlockDataTemplates.STANDARD.addTags(BlockTags.MINEABLE_WITH_SHOVEL).consume(registerBlock("phantom_gravel",
            resourceKey -> new ColoredFallingBlock(new ColorRGBA(0x6d8aa6ff), BlockBehaviour.Properties.ofLegacyCopy(Blocks.GRAVEL).setId(resourceKey))));

    public static final RegistrySupplier<Block> BLUE_FIRE = BlockDataTemplates.STANDARD
            .copyWithModelOverride(FIRE_BLOCK)
            .and(BlockDataProvider.TagType.FIRE).consume(registerBlockOnly("blue_fire",
                    resourceKey -> new BlueFireBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.FIRE).setId(resourceKey))));

    public static final RegistrySupplier<Block> LITHER_TORCH = BlockDataTemplates.EMPTY.addTag(BlockTags.WALL_POST_OVERRIDE).consume(registerBlockOnly("lither_torch",
            resourceKey -> new WaterloggableTorchBlock(ModParticles.LITHER_FIRE_FLAME, BlockBehaviour.Properties.ofLegacyCopy(Blocks.TORCH).setId(resourceKey).lightLevel(_ -> 15))));

    public static final RegistrySupplier<Block> LITHER_WALL_TORCH = BlockDataTemplates.EMPTY.consume(registerBlockOnly("lither_wall_torch",
            resourceKey -> new WaterloggableWallTorchBlock(ModParticles.LITHER_FIRE_FLAME, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WALL_TORCH).setId(resourceKey).lightLevel(_ -> 15).overrideLootTable(LITHER_TORCH.get().getLootTable()).overrideDescription(LITHER_TORCH.get().getDescriptionId()))));

    public static final RegistrySupplier<Block> LITHER_LANTERN = BlockDataTemplates.STANDARD
            .copyWithModelOverride(LANTERN_BLOCK).addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL).consume(registerBlock("lither_lantern",
                    resourceKey -> new LanternBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.LANTERN).setId(resourceKey))));

    public static final RegistrySupplier<Block> LITHEREAL_VAULT = BlockDataTemplates.STANDARD
            .copyWithModelOverride(VAULT_BLOCK.apply(false)).addTag(BlockTags.FEATURES_CANNOT_REPLACE).consume(registerBlock("lithereal_vault",
                    resourceKey -> new LitherealVaultBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.VAULT).setId(resourceKey))));

    public static final RegistrySupplier<Block> SCORCHED_NETHERRACK = BlockDataTemplates.STANDARD
            .addTags(BlockTags.INFINIBURN_OVERWORLD, ModTags.BLUE_FIRE_BASE_BLOCKS, BlockTags.MINEABLE_WITH_PICKAXE).consume(registerBlock("scorched_netherrack",
                    resourceKey -> new ScorchedBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHERRACK).setId(resourceKey))));

    public static final RegistrySupplier<Block> SCORCHED_CRIMSON_NYLIUM = BlockDataTemplates.NYLIUM
            .addTags(BlockTags.INFINIBURN_OVERWORLD, ModTags.BLUE_FIRE_BASE_BLOCKS, BlockTags.MINEABLE_WITH_PICKAXE).consumeWithModelRelatives(registerBlock("scorched_crimson_nylium",
                    resourceKey -> new ScorchedBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_NYLIUM).setId(resourceKey))), () -> Blocks.CRIMSON_NYLIUM, SCORCHED_NETHERRACK);

    public static final RegistrySupplier<Block> SCORCHED_WARPED_NYLIUM = BlockDataTemplates.NYLIUM
            .addTags(BlockTags.INFINIBURN_OVERWORLD, ModTags.BLUE_FIRE_BASE_BLOCKS, BlockTags.MINEABLE_WITH_PICKAXE).consumeWithModelRelatives(registerBlock("scorched_warped_nylium",
                    resourceKey -> new ScorchedBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_NYLIUM).setId(resourceKey))), () -> Blocks.WARPED_NYLIUM, SCORCHED_NETHERRACK);

    public static final RegistrySupplier<Block> INFINITY_GLASS = BlockDataTemplates.STANDARD.addTag(BlockTags.IMPERMEABLE).consume(registerBlock("infinity_glass",
            resourceKey -> new TransparentBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.GLASS).setId(resourceKey))));

    public static final RegistrySupplier<ElectricCrucibleBlock> ELECTRIC_CRUCIBLE = BlockDataTemplates.STANDARD
            .copyWithModelOverride(ELECTRIC_CRUCIBLE_BLOCK).addTag(BlockTags.MINEABLE_WITH_PICKAXE).consume(registerBlock("electric_crucible",
            resourceKey -> new ElectricCrucibleBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion())));

    public static final RegistrySupplier<FireCrucibleBlock> FIRE_CRUCIBLE = BlockDataTemplates.STANDARD
            .copyWithModelOverride(FIRE_CRUCIBLE_BLOCK).addTag(BlockTags.MINEABLE_WITH_PICKAXE).consume(registerBlock("fire_crucible",
            resourceKey -> new FireCrucibleBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion())));

    public static final RegistrySupplier<FreezingStationBlock> FREEZING_STATION = BlockDataTemplates.STANDARD
            .copyWithModelOverride(EXISTING_CREATE_STATES).addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL).consume(registerBlock("freezing_station",
            resourceKey -> new FreezingStationBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion())));

    public static final RegistrySupplier<InfusementChamberBlock> INFUSEMENT_CHAMBER = BlockDataTemplates.STANDARD
            .copyWithModelOverride(INFUSEMENT_CHAMBER_BLOCK).addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL).consume(registerBlock("infusement_chamber",
            resourceKey -> new InfusementChamberBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion())));

    static <T extends Block> RegistrySupplier<T> registerBlock(String name, Function<ResourceKey<Block>, T> block) {
        return registerBlock(name, block, Item.Properties::new);
    }

    static <T extends Block> RegistrySupplier<T> registerBlock(String name, Function<ResourceKey<Block>, T> block, Supplier<Item.Properties> properties) {
        ResourceKey<Block> resourceKey = Lithereal.key(Registries.BLOCK, name);
        RegistrySupplier<T> toReturn = BLOCKS.register(name, () -> block.apply(resourceKey));
        registerBlockItem(name, toReturn, properties);
        return toReturn;
    }
    static <T extends Block> RegistrySupplier<T> registerBlock(String name, Function<ResourceKey<Block>, T> block, BiFunction<Block, Item.Properties, BlockItem> blockItem) {
        ResourceKey<Block> resourceKey = Lithereal.key(Registries.BLOCK, name);
        ResourceKey<Item> itemResourceKey = Lithereal.key(Registries.ITEM, name);
        RegistrySupplier<T> toReturn = BLOCKS.register(name, () -> block.apply(resourceKey));
        ModItems.ITEMS.register(name, () -> blockItem.apply(toReturn.get(), new Item.Properties().setId(itemResourceKey).useBlockDescriptionPrefix()));
        return toReturn;
    }

    static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block, Supplier<Item.Properties> propertiesSupplier) {
        ResourceKey<Item> resourceKey = Lithereal.key(Registries.ITEM, name);
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                propertiesSupplier.get().setId(resourceKey).useBlockDescriptionPrefix()));
    }

    static <T extends Block> RegistrySupplier<T> registerBlockOnly(String name, Function<ResourceKey<Block>, T> block) {
        ResourceKey<Block> resourceKey = Lithereal.key(Registries.BLOCK, name);
        return BLOCKS.register(name, () -> block.apply(resourceKey));
    }

    public static Block strippedLog(MapColor mapColor, ResourceKey<Block> resourceKey) {
        return new RotatedPillarBlock(
                BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                        .mapColor(mapColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)
                        .ignitedByLava()
        );
    }

    public static Block strippedLog(MapColor mapColor, SoundType soundType, ResourceKey<Block> resourceKey) {
        return new RotatedPillarBlock(
                BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                        .mapColor(mapColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(soundType)
                        .ignitedByLava()
        );
    }

    public static Block log(Supplier<Block> stripped, MapColor mapColor, ResourceKey<Block> resourceKey) {
        return LitherealPlatform.INSTANCE.strippableLog(
                stripped,
                BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                        .mapColor(mapColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)
                        .ignitedByLava()
        );
    }

    public static Block log(Supplier<Block> stripped, MapColor mapColor, SoundType soundType, ResourceKey<Block> resourceKey) {
        return LitherealPlatform.INSTANCE.strippableLog(
                stripped,
                BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                        .mapColor(mapColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(soundType)
                        .ignitedByLava()
        );
    }

    public static Block log(MapColor upColor, MapColor sideColor, ResourceKey<Block> resourceKey) {
        return new RotatedPillarBlock(
                BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                        .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? upColor : sideColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)
                        .ignitedByLava()
        );
    }

    public static Block log(MapColor upColor, MapColor sideColor, SoundType soundType, ResourceKey<Block> resourceKey) {
        return new RotatedPillarBlock(
                BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                        .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? upColor : sideColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(soundType)
                        .ignitedByLava()
        );
    }

    public static Block log(Supplier<Block> stripped, MapColor upColor, MapColor sideColor, ResourceKey<Block> resourceKey) {
        return LitherealPlatform.INSTANCE.strippableLog(
                stripped,
                BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                        .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? upColor : sideColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)
                        .ignitedByLava()
        );
    }

    public static Block log(Supplier<Block> stripped, MapColor upColor, MapColor sideColor, SoundType soundType, ResourceKey<Block> resourceKey) {
        return LitherealPlatform.INSTANCE.strippableLog(
                stripped,
                BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                        .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? upColor : sideColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(soundType)
                        .ignitedByLava()
        );
    }

    public static final List<HoeTillable> HOE_TILLABLES = new ArrayList<>();

    public static void register() {
        HOE_TILLABLES.add(new HoeTillable(ETHEREAL_DIRT, () -> ETHEREAL_FARMLAND.get().defaultBlockState(), HoeTillable::onlyIfAirAbove, HoeTillable.changeIntoState(Suppliers.memoize(() -> ETHEREAL_FARMLAND.get().defaultBlockState()))));
        HOE_TILLABLES.add(new HoeTillable(ETHEREAL_GRASS_BLOCK, () -> ETHEREAL_FARMLAND.get().defaultBlockState(), HoeTillable::onlyIfAirAbove, HoeTillable.changeIntoState(Suppliers.memoize(() -> ETHEREAL_FARMLAND.get().defaultBlockState()))));
        HOE_TILLABLES.add(new HoeTillable(COARSE_ETHEREAL_DIRT, () -> ETHEREAL_DIRT.get().defaultBlockState(), HoeTillable::onlyIfAirAbove, HoeTillable.changeIntoState(Suppliers.memoize(() -> ETHEREAL_DIRT.get().defaultBlockState()))));
        ModTreeBlocks.register();
        ModVegetationBlocks.register();
        ModStoneBlocks.register();
        ModStorageBlocks.register();
        ModOreBlocks.register();
        BLOCKS.register();
    }
}
