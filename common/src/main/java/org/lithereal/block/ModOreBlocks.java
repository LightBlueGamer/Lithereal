package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static org.lithereal.block.ModBlocks.registerBlock;

public class ModOreBlocks {
    public static final RegistrySupplier<Block> LITHERITE_ORE = registerBlock("litherite_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 6), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_LITHERITE_ORE = registerBlock("deepslate_litherite_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 6), BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> NERITH_ORE = registerBlock("nerith_ore",
            () -> new DropExperienceBlock(UniformInt.of(4,7), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_NERITH_ORE = registerBlock("deepslate_nerith_ore",
            () -> new DropExperienceBlock(UniformInt.of(4,7), BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_NERITH_ORE = registerBlock("etherstone_nerith_ore",
            () -> new DropExperienceBlock(UniformInt.of(4,7), BlockBehaviour.Properties.ofFullCopy(ModOreBlocks.DEEPSLATE_NERITH_ORE.get())));

    public static final RegistrySupplier<Block> LUMINIUM_ORE = registerBlock("luminium_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 6), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_LUMINIUM_ORE = registerBlock("deepslate_luminium_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 6), BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_LUMINIUM_ORE = registerBlock("etherstone_luminium_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 6), BlockBehaviour.Properties.ofFullCopy(ModOreBlocks.DEEPSLATE_LUMINIUM_ORE.get())));

    public static final RegistrySupplier<Block> CYRUM_ORE = registerBlock("cyrum_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 3), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_CYRUM_ORE = registerBlock("deepslate_cyrum_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 3), BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_CYRUM_ORE = registerBlock("etherstone_cyrum_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 3), BlockBehaviour.Properties.ofFullCopy(ModOreBlocks.DEEPSLATE_CYRUM_ORE.get())));

    public static final RegistrySupplier<Block> COPALITE_ORE = registerBlock("copalite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 9), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_COPALITE_ORE = registerBlock("deepslate_copalite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 9), BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_COPALITE_ORE = registerBlock("etherstone_copalite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 9), BlockBehaviour.Properties.ofFullCopy(ModOreBlocks.DEEPSLATE_COPALITE_ORE.get())));

    public static final RegistrySupplier<Block> AURELITE_ORE = registerBlock("aurelite_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(25)));

    public static final RegistrySupplier<Block> DEEPSLATE_AURELITE_ORE = registerBlock("deepslate_aurelite_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().explosionResistance(25).sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> ETHERSTONE_AURELITE_ORE = registerBlock("etherstone_aurelite_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(ModOreBlocks.DEEPSLATE_AURELITE_ORE.get())));

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

    public static void register() {

    }
}
