package org.lithereal.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.lithereal.Lithereal;
import org.lithereal.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Lithereal.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> LITHER_DIRT = registerBlock("lither_dirt",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));

    public static final RegistrySupplier<Block> LITHER_GRASS_BLOCK = registerBlock("lither_grass_block",
            () -> new ExtendedGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK), LITHER_DIRT));

    public static final RegistrySupplier<Block> LITHERITE_ORE = registerBlock("litherite_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 6), BlockBehaviour.Properties.of()
                                .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_LITHERITE_ORE = registerBlock("deepslate_litherite_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 6), BlockBehaviour.Properties.of()
                                .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> LITHERITE_BLOCK = registerBlock("litherite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)));

    public static final RegistrySupplier<Block> BURNING_LITHERITE_BLOCK = registerBlock("burning_litherite_block",
            () -> new BurningLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)), () -> new Item.Properties().fireResistant());

    public static final RegistrySupplier<Block> FROZEN_LITHERITE_BLOCK = registerBlock("frozen_litherite_block",
            () -> new FrozenLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)), () -> new Item.Properties().fireResistant());

    public static final RegistrySupplier<Block> WITHERING_LITHERITE_BLOCK = registerBlock("withering_litherite_block",
            () -> new WitheringLitheriteBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)), () -> new Item.Properties().fireResistant());

    public static final RegistrySupplier<Block> CHARGED_LITHERITE_BLOCK = registerBlock("charged_litherite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.METAL)), () -> new Item.Properties().fireResistant());

    public static final RegistrySupplier<Block> BLUE_FIRE = registerBlockOnly("blue_fire",
            () -> new BlueFireBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE)));

    public static final RegistrySupplier<Block> SCORCHED_NETHERRACK = registerBlock("scorched_netherrack",
            () -> new ScorchedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK)));

    public static final RegistrySupplier<Block> SCORCHED_CRIMSON_NYLIUM = registerBlock("scorched_crimson_nylium",
            () -> new ScorchedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM)));

    public static final RegistrySupplier<Block> SCORCHED_WARPED_NYLIUM = registerBlock("scorched_warped_nylium",
            () -> new ScorchedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_NYLIUM)));

    public static final RegistrySupplier<Block> ETHERSTONE = registerBlock("etherstone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_STAIRS = registerBlock("etherstone_stairs",
            () -> new StairBlock(ETHERSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));

    public static final RegistrySupplier<Block> ETHERSTONE_SLAB = registerBlock("etherstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));

    public static final RegistrySupplier<Block> ETHERSTONE_WALL = registerBlock("etherstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> CHISELED_ETHERSTONE = registerBlock("chiseled_etherstone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_DEEPSLATE)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE = registerBlock("polished_etherstone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_STAIRS = registerBlock("polished_etherstone_stairs",
            () -> new StairBlock(POLISHED_ETHERSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_STAIRS)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_SLAB = registerBlock("polished_etherstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_SLAB)));

    public static final RegistrySupplier<Block> POLISHED_ETHERSTONE_WALL = registerBlock("polished_etherstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICKS = registerBlock("etherstone_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_STAIRS = registerBlock("etherstone_brick_stairs",
            () -> new StairBlock(ETHERSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_SLAB = registerBlock("etherstone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB)));

    public static final RegistrySupplier<Block> ETHERSTONE_BRICK_WALL = registerBlock("etherstone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL)));

    public static final RegistrySupplier<Block> INFINITY_GLASS = registerBlock("infinity_glass",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

    public static final RegistrySupplier<Block> LITHER_LANTERN = registerBlock("lither_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));

    public static final RegistrySupplier<Block> LITHEREAL_VAULT = registerBlock("lithereal_vault",
            () -> new LitherealVaultBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VAULT)));

    public static final RegistrySupplier<Block> NERITH_ORE = registerBlock("nerith_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_NERITH_ORE = registerBlock("deepslate_nerith_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ELUNITE_ORE = registerBlock("elunite_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> CHRYON_ORE = registerBlock("chryon_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> SATURNITE_ORE = registerBlock("saturnite_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> HELLIONITE_ORE = registerBlock("hellionite_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> LUMINIUM_ORE = registerBlock("luminium_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_LUMINIUM_ORE = registerBlock("deepslate_luminium_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> CYRUM_ORE = registerBlock("cyrum_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_CYRUM_ORE = registerBlock("deepslate_cyrum_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> COPALITE_ORE = registerBlock("copalite_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_COPALITE_ORE = registerBlock("deepslate_copalite_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> AURELITE_ORE = registerBlock("aurelite_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_AURELITE_ORE = registerBlock("deepslate_aurelite_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ALLIAN_ORE = registerBlock("allian_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> ETHERSTONE_AURELITE_ORE = registerBlock("etherstone_aurelite_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.DEEPSLATE_AURELITE_ORE.get())));

    public static final RegistrySupplier<Block> ETHERSTONE_COPALITE_ORE = registerBlock("etherstone_copalite_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.DEEPSLATE_COPALITE_ORE.get())));

    public static final RegistrySupplier<Block> ETHERSTONE_CYRUM_ORE = registerBlock("etherstone_cyrum_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.DEEPSLATE_CYRUM_ORE.get())));

    public static final RegistrySupplier<Block> ETHERSTONE_LITHERITE_ORE = registerBlock("etherstone_litherite_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.DEEPSLATE_LITHERITE_ORE.get())));

    public static final RegistrySupplier<Block> ETHERSTONE_LUMINIUM_ORE = registerBlock("etherstone_luminium_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.DEEPSLATE_LUMINIUM_ORE.get())));

    public static final RegistrySupplier<Block> ETHERSTONE_NERITH_ORE = registerBlock("etherstone_nerith_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.DEEPSLATE_NERITH_ORE.get())));

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, Item.Properties::new);
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block, Supplier<Item.Properties> properties) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, properties);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block, Supplier<Item.Properties> propertiesSupplier) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                propertiesSupplier.get()));
    }

    private static <T extends Block> RegistrySupplier<T> registerBlockOnly(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void register() {
        BLOCKS.register();
    }
}
