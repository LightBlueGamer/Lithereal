package org.lithereal.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.lithereal.Lithereal;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.item.ModItems;
import org.lithereal.util.CommonUtils;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Lithereal.MOD_ID, Registries.BLOCK);

    public static final ResourceLocation ETHEREAL_CORE_PORTAL_LOC = Lithereal.id("ethereal_core_portal");

    public static final RegistrySupplier<Block> ETHEREAL_CORE_PORTAL = registerBlock("ethereal_core_portal",
            () -> new EtherealCorePortalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_PORTAL)));

    public static final RegistrySupplier<Block> IMPURE_ETHEREAL_CRYSTAL = registerBlock("impure_ethereal_crystal",
            () -> new ImpureEtherealCrystalBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().mapColor(MapColor.COLOR_BLUE).sound(SoundType.AMETHYST_CLUSTER).strength(5, 8)));

    public static final RegistrySupplier<Block> LITHERITE_CRYSTAL_BLOCK = registerBlock("litherite_crystal_block",
            () -> new TransparentBlock(BlockBehaviour.Properties.of()
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
            () -> new PureEtherealCrystalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_PORTAL_FRAME), ETHEREAL_CORE_PORTAL.get()));

    public static final RegistrySupplier<Block> ETHEREAL_DIRT = registerBlock("ethereal_dirt",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));

    public static final RegistrySupplier<Block> ETHEREAL_GRASS_BLOCK = registerBlock("ethereal_grass_block",
            () -> new ExtendedGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK), ETHEREAL_DIRT));

    public static final RegistrySupplier<Block> BLUE_FIRE = registerBlockOnly("blue_fire",
            () -> new BlueFireBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE)));

    public static final RegistrySupplier<Block> LITHER_TORCH = registerBlockOnly("lither_torch",
            () -> new WaterloggableTorchBlock(ModParticles.LITHER_FIRE_FLAME.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).lightLevel(state -> 15)));

    public static final RegistrySupplier<Block> LITHER_WALL_TORCH = registerBlockOnly("lither_wall_torch",
            () -> new WaterloggableWallTorchBlock(ModParticles.LITHER_FIRE_FLAME.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH).lightLevel(state -> 15).dropsLike(LITHER_TORCH.get())));

    public static final RegistrySupplier<Block> LITHER_LANTERN = registerBlock("lither_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));

    public static final RegistrySupplier<Block> LITHEREAL_VAULT = registerBlock("lithereal_vault",
            () -> new LitherealVaultBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VAULT)));

    public static final RegistrySupplier<Block> SCORCHED_NETHERRACK = registerBlock("scorched_netherrack",
            () -> new ScorchedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK)));

    public static final RegistrySupplier<Block> SCORCHED_CRIMSON_NYLIUM = registerBlock("scorched_crimson_nylium",
            () -> new ScorchedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM)));

    public static final RegistrySupplier<Block> SCORCHED_WARPED_NYLIUM = registerBlock("scorched_warped_nylium",
            () -> new ScorchedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_NYLIUM)));

    public static final RegistrySupplier<Block> INFINITY_GLASS = registerBlock("infinity_glass",
            () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

    static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, Item.Properties::new);
    }

    static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block, Supplier<Item.Properties> properties) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, properties);
        return toReturn;
    }
    static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block, Function<Block, BlockItem> blockItem) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> blockItem.apply(toReturn.get()));
        return toReturn;
    }

    static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block, Supplier<Item.Properties> propertiesSupplier) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                propertiesSupplier.get()));
    }

    static <T extends Block> RegistrySupplier<T> registerBlockOnly(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static Block strippedLog(MapColor mapColor) {
        return new RotatedPillarBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)
                        .ignitedByLava()
        );
    }

    public static Block strippedLog(MapColor mapColor, SoundType soundType) {
        return new RotatedPillarBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(soundType)
                        .ignitedByLava()
        );
    }

    public static Block log(Supplier<Block> stripped, MapColor mapColor) {
        return LitherealExpectPlatform.strippableLog(
                stripped,
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)
                        .ignitedByLava()
        );
    }

    public static Block log(Supplier<Block> stripped, MapColor mapColor, SoundType soundType) {
        return LitherealExpectPlatform.strippableLog(
                stripped,
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(soundType)
                        .ignitedByLava()
        );
    }

    public static Block log(MapColor upColor, MapColor sideColor) {
        return new RotatedPillarBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? upColor : sideColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)
                        .ignitedByLava()
        );
    }

    public static Block log(MapColor upColor, MapColor sideColor, SoundType soundType) {
        return new RotatedPillarBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? upColor : sideColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(soundType)
                        .ignitedByLava()
        );
    }

    public static Block log(Supplier<Block> stripped, MapColor upColor, MapColor sideColor) {
        return LitherealExpectPlatform.strippableLog(
                stripped,
                BlockBehaviour.Properties.of()
                        .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? upColor : sideColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)
                        .ignitedByLava()
        );
    }

    public static Block log(Supplier<Block> stripped, MapColor upColor, MapColor sideColor, SoundType soundType) {
        return LitherealExpectPlatform.strippableLog(
                stripped,
                BlockBehaviour.Properties.of()
                        .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? upColor : sideColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(soundType)
                        .ignitedByLava()
        );
    }

    public static void register() {
        ModTreeBlocks.register();
        ModStoneBlocks.register();
        ModStorageBlocks.register();
        ModOreBlocks.register();
        ModPhantomBlocks.register();
        BLOCKS.register();
    }
}
