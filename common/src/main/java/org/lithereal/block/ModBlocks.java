package org.lithereal.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.lithereal.Lithereal;
import org.lithereal.item.ModItems;
import org.lithereal.item.obscured.MysteriousBlockItem;
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

    public static final RegistrySupplier<Block> PAILITE = registerBlock("pailite",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final RegistrySupplier<Block> LITHERITE_ORE = registerBlock("litherite_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 6), BlockBehaviour.Properties.of()
                                .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_LITHERITE_ORE = registerBlock("deepslate_litherite_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 6), BlockBehaviour.Properties.of()
                                .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ODYSIUM_BLOCK = registerBlock("odysium_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(8f).requiresCorrectToolForDrops().explosionResistance(50).sound(SoundType.METAL)));

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

    public static final RegistrySupplier<Block> INFINITY_GLASS = registerBlock("infinity_glass",
            () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

    public static final RegistrySupplier<Block> LITHER_LANTERN = registerBlock("lither_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));

    public static final RegistrySupplier<Block> LITHEREAL_VAULT = registerBlock("lithereal_vault",
            () -> new LitherealVaultBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VAULT)));

    public static final RegistrySupplier<Block> NERITH_ORE = registerBlock("nerith_ore",
            () -> new DropExperienceBlock(UniformInt.of(4,7), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_NERITH_ORE = registerBlock("deepslate_nerith_ore",
            () -> new DropExperienceBlock(UniformInt.of(4,7), BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_NERITH_ORE = registerBlock("etherstone_nerith_ore",
            () -> new DropExperienceBlock(UniformInt.of(4,7), BlockBehaviour.Properties.ofFullCopy(ModBlocks.DEEPSLATE_NERITH_ORE.get())));

    public static final RegistrySupplier<Block> LUMINIUM_ORE = registerBlock("luminium_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 6), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_LUMINIUM_ORE = registerBlock("deepslate_luminium_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 6), BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_LUMINIUM_ORE = registerBlock("etherstone_luminium_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 6), BlockBehaviour.Properties.ofFullCopy(ModBlocks.DEEPSLATE_LUMINIUM_ORE.get())));

    public static final RegistrySupplier<Block> CYRUM_ORE = registerBlock("cyrum_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 3), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_CYRUM_ORE = registerBlock("deepslate_cyrum_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 3), BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_CYRUM_ORE = registerBlock("etherstone_cyrum_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 3), BlockBehaviour.Properties.ofFullCopy(ModBlocks.DEEPSLATE_CYRUM_ORE.get())));

    public static final RegistrySupplier<Block> COPALITE_ORE = registerBlock("copalite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 9), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_COPALITE_ORE = registerBlock("deepslate_copalite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 9), BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_COPALITE_ORE = registerBlock("etherstone_copalite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 9), BlockBehaviour.Properties.ofFullCopy(ModBlocks.DEEPSLATE_COPALITE_ORE.get())));

    public static final RegistrySupplier<Block> AURELITE_ORE = registerBlock("aurelite_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_AURELITE_ORE = registerBlock("deepslate_aurelite_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_AURELITE_ORE = registerBlock("etherstone_aurelite_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(ModBlocks.DEEPSLATE_AURELITE_ORE.get())));

    public static final RegistrySupplier<Block> SATURNITE_ORE = registerBlock("saturnite_ore",
            () -> new DropExperienceBlock(UniformInt.of(5, 10), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> HELLIONITE_ORE = registerBlock("hellionite_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 3), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> ELUNITE_ORE = registerBlock("elunite_ore",
            () -> new DropExperienceBlock(UniformInt.of(4,7), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> CHRYON_ORE = registerBlock("chryon_ore",
            () -> new DropExperienceBlock(UniformInt.of(4,7), BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> ALLIAN_ORE = registerBlock("allian_ore",
            () -> new DropExperienceBlock(UniformInt.of(4, 8), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> PHANTOM_DIAMOND_ORE = registerBlock("phantom_diamond_ore", () ->
            new DropExperienceBlock(UniformInt.of(3, 9), BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_DIAMOND_BLOCK = registerBlock("phantom_diamond_block", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_ROSE = registerBlock("phantom_rose", () ->
            new FlowerBlock(MobEffects.DAMAGE_BOOST, 3, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_ICE_FLOWER = registerBlock("phantom_ice_flower", () ->
            new FlowerBlock(MobEffects.WEAVING, 5, BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    public static final RegistrySupplier<Block> PHANTOM_ROSE_ETHEREAL_CORE = registerBlock("phantom_rose_ethereal_core", () ->
            new FlowerBlock(MobEffects.DIG_SPEED, 3.5F, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)), block -> new MysteriousBlockItem(block, new Item.Properties()));

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, Item.Properties::new);
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block, Supplier<Item.Properties> properties) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, properties);
        return toReturn;
    }
    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block, Function<Block, BlockItem> blockItem) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> blockItem.apply(toReturn.get()));
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
