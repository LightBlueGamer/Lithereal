package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.lithereal.block.datagen.BlockDataTemplates;

import java.util.function.Supplier;

import static org.lithereal.block.ModBlocks.registerBlock;

public class ModOreBlocks {
    public static final RegistrySupplier<Block> LITHERITE_ORE = createOre("litherite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(2, 6), 3f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> DEEPSLATE_LITHERITE_ORE = createOre("deepslate_litherite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(2, 6), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> ETHERSTONE_LITHERITE_ORE = createOre("etherstone_litherite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(2, 6), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> NERITH_ORE = createOre("nerith_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(4, 7), 5f, 25, SoundType.STONE);

    public static final RegistrySupplier<Block> DEEPSLATE_NERITH_ORE = createOre("deepslate_nerith_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(4, 7), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> ETHERSTONE_NERITH_ORE = createOre("etherstone_nerith_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(4, 7), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> LUMINIUM_ORE = createOre("luminium_ore", BlockTags.NEEDS_IRON_TOOL,
            UniformInt.of(1, 6), 3f, 25, SoundType.STONE);

    public static final RegistrySupplier<Block> DEEPSLATE_LUMINIUM_ORE = createOre("deepslate_luminium_ore", BlockTags.NEEDS_IRON_TOOL,
            UniformInt.of(1, 6), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> ETHERSTONE_LUMINIUM_ORE = createOre("etherstone_luminium_ore", BlockTags.NEEDS_IRON_TOOL,
            UniformInt.of(1, 6), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> CYRUM_ORE = createOre("cyrum_ore", BlockTags.NEEDS_IRON_TOOL,
            UniformInt.of(0, 3), 3f, 25, SoundType.STONE);

    public static final RegistrySupplier<Block> DEEPSLATE_CYRUM_ORE = createOre("deepslate_cyrum_ore", BlockTags.NEEDS_IRON_TOOL,
            UniformInt.of(0, 3), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> ETHERSTONE_CYRUM_ORE = createOre("etherstone_cyrum_ore", BlockTags.NEEDS_IRON_TOOL,
            UniformInt.of(0, 3), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> COPALITE_ORE = createOre("copalite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(3, 9), 3f, 25, SoundType.STONE);

    public static final RegistrySupplier<Block> DEEPSLATE_COPALITE_ORE = createOre("deepslate_copalite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(3, 9), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> ETHERSTONE_COPALITE_ORE = createOre("etherstone_copalite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(3, 9), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> AURELITE_ORE = createOre("aurelite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(0, 2), 3f, 25, SoundType.STONE);

    public static final RegistrySupplier<Block> DEEPSLATE_AURELITE_ORE = createOre("deepslate_aurelite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(0, 2), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> ETHERSTONE_AURELITE_ORE = createOre("etherstone_aurelite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(0, 2), 6f, 25, SoundType.DEEPSLATE);

    public static final RegistrySupplier<Block> SATURNITE_ORE = createOre("saturnite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(0, 3), 4f, 15, SoundType.NETHER_ORE);

    public static final RegistrySupplier<Block> PAILITE_SATURNITE_ORE = createOre("pailite_saturnite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(0, 3), 5f, 25, SoundType.STONE);

    public static final RegistrySupplier<Block> HELLIONITE_ORE = createOre("hellionite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(0, 3), 4f, 25, SoundType.NETHER_ORE);

    public static final RegistrySupplier<Block> PAILITE_HELLIONITE_ORE = createOre("pailite_hellionite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(0, 3), 5f, 25, SoundType.STONE);

    public static final RegistrySupplier<Block> ELUNITE_ORE = createOre("elunite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(4, 7), 3f, 45, SoundType.STONE);

    public static final RegistrySupplier<Block> CHRYON_ORE = createOre("chryon_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(4, 7), 3f, 45, SoundType.STONE);

    public static final RegistrySupplier<Block> ALLIAN_ORE = createOre("allian_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(4, 8), 5f, 45, SoundType.STONE);

    public static final RegistrySupplier<Block> PAILITE_NETHERITE_ORE = createOre("pailite_netherite_ore", BlockTags.NEEDS_DIAMOND_TOOL,
            UniformInt.of(4, 6), 6f, 45, SoundType.STONE,
            () -> new Item.Properties().fireResistant());

    public static final RegistrySupplier<Block> PHANTOM_DIAMOND_ORE = createOre("phantom_diamond_ore", BlockTags.NEEDS_IRON_TOOL,
            UniformInt.of(3, 9), 5f, 25, SoundType.STONE);

    public static final RegistrySupplier<Block> PHANTOM_QUARTZ_ORE = createOre("phantom_quartz_ore",
            UniformInt.of(2, 5), 5f, 25, SoundType.STONE);

    public static RegistrySupplier<Block> createOre(String name, UniformInt exp, float strength, int explosionResistance, SoundType soundType) {
        return BlockDataTemplates.STANDARD.addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .consume(registerBlock(name, resourceKey -> new DropExperienceBlock(exp, BlockBehaviour.Properties.of().setId(resourceKey)
                        .strength(strength).requiresCorrectToolForDrops().explosionResistance(explosionResistance).sound(soundType))));
    }

    public static RegistrySupplier<Block> createOre(String name, TagKey<Block> needsTag, UniformInt exp, float strength, int explosionResistance, SoundType soundType) {
        return BlockDataTemplates.STANDARD.addTags(BlockTags.MINEABLE_WITH_PICKAXE, needsTag)
                .consume(registerBlock(name, resourceKey -> new DropExperienceBlock(exp, BlockBehaviour.Properties.of().setId(resourceKey)
                        .strength(strength).requiresCorrectToolForDrops().explosionResistance(explosionResistance).sound(soundType))));
    }

    public static RegistrySupplier<Block> createOre(String name, TagKey<Block> needsTag, UniformInt exp, float strength, int explosionResistance, SoundType soundType, Supplier<Item.Properties> itemProperties) {
        return BlockDataTemplates.STANDARD.addTags(BlockTags.MINEABLE_WITH_PICKAXE, needsTag)
                .consume(registerBlock(name, resourceKey -> new DropExperienceBlock(exp, BlockBehaviour.Properties.of().setId(resourceKey)
                        .strength(strength).requiresCorrectToolForDrops().explosionResistance(explosionResistance).sound(soundType)), itemProperties));
    }

    public static void register() {

    }
}
