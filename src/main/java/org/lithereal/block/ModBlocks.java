package org.lithereal.block;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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
import org.lithereal.client.particle.ModParticles;
import org.lithereal.item.ModItems;
import org.lithereal.tags.ModTags;
import org.lithereal.util.CommonUtils;
import org.lithereal.util.HoeTillable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Lithereal.MOD_ID, Registries.BLOCK);

    public static final Identifier ETHEREAL_CORE_PORTAL_LOC = Lithereal.id("ethereal_core_portal");

    public static final RegistrySupplier<Block> ETHEREAL_CORE_PORTAL = registerBlock("ethereal_core_portal",
            resourceKey -> new EtherealCorePortalBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.END_PORTAL).setId(resourceKey)));

    public static final RegistrySupplier<Block> ETHEREAL_RIFT = registerBlock("ethereal_rift",
            resourceKey -> new EtherealRiftBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.END_PORTAL).setId(resourceKey)));

    public static final RegistrySupplier<Block> IMPURE_ETHEREAL_CRYSTAL = registerBlock("impure_ethereal_crystal",
            resourceKey -> new ImpureEtherealCrystalBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_BLUE).sound(SoundType.AMETHYST_CLUSTER).strength(5, 8)));

    public static final RegistrySupplier<Block> ETHEREAL_CRYSTAL_BLOCK = registerBlock("ethereal_crystal_block",
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
                    .isViewBlocking(CommonUtils::never)));

    public static final RegistrySupplier<Block> PURE_ETHER_SOURCE = registerBlock("pure_ether_source",
            resourceKey -> new Block(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(180f)
                    .requiresCorrectToolForDrops()
                    .explosionResistance(1000)
                    .mapColor(MapColor.COLOR_BLUE)
                    .sound(SoundType.AMETHYST_CLUSTER)
                    .lightLevel(state -> 15)
                    .noOcclusion()
                    .isValidSpawn(CommonUtils::never)
                    .isRedstoneConductor(CommonUtils::never)));

    public static final RegistrySupplier<Block> LITHERITE_CRYSTAL_BLOCK = registerBlock("litherite_crystal_block",
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
                    .isViewBlocking(CommonUtils::never)));

    public static final RegistrySupplier<Block> PURE_ETHEREAL_CRYSTAL_BLOCK = registerBlock("pure_ethereal_crystal_block",
            resourceKey -> new PureEtherealCrystalBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.END_PORTAL_FRAME).setId(resourceKey), ETHEREAL_CORE_PORTAL.get()));

    public static final RegistrySupplier<Block> CREATIVE_ETHER_SOURCE = registerBlock("creative_ether_source",
            resourceKey -> new InfiniteEtherSourceBlock(10000, (TagKey<Biome>) null, BlockBehaviour.Properties.ofLegacyCopy(Blocks.IRON_BLOCK).setId(resourceKey)));

    public static final RegistrySupplier<Block> PASSIVE_ETHER_ABSORBER = registerBlock("passive_ether_absorber",
            resourceKey -> new InfiniteEtherSourceBlock(10, ModTags.PROVIDES_ETHER, BlockBehaviour.Properties.ofLegacyCopy(Blocks.IRON_BLOCK).setId(resourceKey)));

    public static final RegistrySupplier<Block> COARSE_ETHEREAL_DIRT = registerBlock("coarse_ethereal_dirt",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.COARSE_DIRT).setId(resourceKey)));

    public static final RegistrySupplier<Block> ETHEREAL_DIRT = registerBlock("ethereal_dirt",
            resourceKey -> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.DIRT).setId(resourceKey)));

    public static final RegistrySupplier<Block> ETHEREAL_FARMLAND = registerBlock("ethereal_farmland",
            resourceKey -> new ModFarmlandBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.FARMLAND).setId(resourceKey), ETHEREAL_DIRT.getKey()));

    public static final RegistrySupplier<Block> ETHEREAL_GRASS_BLOCK = registerBlock("ethereal_grass_block",
            resourceKey -> new ExtendedGrassBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.GRASS_BLOCK).setId(resourceKey), ETHEREAL_DIRT.getKey()));

    public static final RegistrySupplier<Block> PHANTOM_GRAVEL = registerBlock("phantom_gravel",
            resourceKey -> new ColoredFallingBlock(new ColorRGBA(0x6d8aa6ff), BlockBehaviour.Properties.ofLegacyCopy(Blocks.GRAVEL).setId(resourceKey)));

    public static final RegistrySupplier<Block> BLUE_FIRE = registerBlockOnly("blue_fire",
            resourceKey -> new BlueFireBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.FIRE).setId(resourceKey)));

    public static final RegistrySupplier<Block> LITHER_TORCH = registerBlockOnly("lither_torch",
            resourceKey -> new WaterloggableTorchBlock(ModParticles.LITHER_FIRE_FLAME, BlockBehaviour.Properties.ofLegacyCopy(Blocks.TORCH).setId(resourceKey).lightLevel(_ -> 15)));

    public static final RegistrySupplier<Block> LITHER_WALL_TORCH = registerBlockOnly("lither_wall_torch",
            resourceKey -> new WaterloggableWallTorchBlock(ModParticles.LITHER_FIRE_FLAME, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WALL_TORCH).setId(resourceKey).lightLevel(_ -> 15).overrideLootTable(LITHER_TORCH.get().getLootTable()).overrideDescription(LITHER_TORCH.get().getDescriptionId())));

    public static final RegistrySupplier<Block> LITHER_LANTERN = registerBlock("lither_lantern",
            resourceKey -> new LanternBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.LANTERN).setId(resourceKey)));

    public static final RegistrySupplier<Block> LITHEREAL_VAULT = registerBlock("lithereal_vault",
            resourceKey -> new LitherealVaultBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.VAULT).setId(resourceKey)));

    public static final RegistrySupplier<Block> SCORCHED_NETHERRACK = registerBlock("scorched_netherrack",
            resourceKey -> new ScorchedBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHERRACK).setId(resourceKey)));

    public static final RegistrySupplier<Block> SCORCHED_CRIMSON_NYLIUM = registerBlock("scorched_crimson_nylium",
            resourceKey -> new ScorchedBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_NYLIUM).setId(resourceKey)));

    public static final RegistrySupplier<Block> SCORCHED_WARPED_NYLIUM = registerBlock("scorched_warped_nylium",
            resourceKey -> new ScorchedBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_NYLIUM).setId(resourceKey)));

    public static final RegistrySupplier<Block> INFINITY_GLASS = registerBlock("infinity_glass",
            resourceKey -> new TransparentBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.GLASS).setId(resourceKey)));

    public static final RegistrySupplier<ElectricCrucibleBlock> ELECTRIC_CRUCIBLE = registerBlock("electric_crucible",
            resourceKey -> new ElectricCrucibleBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistrySupplier<FireCrucibleBlock> FIRE_CRUCIBLE = registerBlock("fire_crucible",
            resourceKey -> new FireCrucibleBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistrySupplier<FreezingStationBlock> FREEZING_STATION = registerBlock("freezing_station",
            resourceKey -> new FreezingStationBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistrySupplier<InfusementChamberBlock> INFUSEMENT_CHAMBER = registerBlock("infusement_chamber",
            resourceKey -> new InfusementChamberBlock(BlockBehaviour.Properties.of().setId(resourceKey).setId(resourceKey)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion()));

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
        ModPhantomBlocks.register();
        BLOCKS.register();
    }
}
