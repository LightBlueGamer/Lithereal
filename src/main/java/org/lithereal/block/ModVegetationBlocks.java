package org.lithereal.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.lithereal.block.datagen.BlockDataTemplates;
import org.lithereal.world.feature.ModFeatures;

import java.util.Optional;

import static org.lithereal.block.ModBlocks.registerBlock;
import static org.lithereal.block.ModTreeBlocks.createPottedPlant;

public class ModVegetationBlocks {
    public static final TreeGrower FORTSHROOM_GROWER = new TreeGrower("fortshroom", 0.0F, Optional.of(ModFeatures.MEGA_FORTSHROOM), Optional.empty(), Optional.of(ModFeatures.HUGE_FORTSHROOM), Optional.empty(), Optional.empty(), Optional.empty());
    public static final TreeGrower MALISHROOM_GROWER = new TreeGrower("malishroom", 0.0F, Optional.of(ModFeatures.MEGA_MALISHROOM), Optional.empty(), Optional.of(ModFeatures.HUGE_MALISHROOM), Optional.empty(), Optional.empty(), Optional.empty());

    public static final RegistrySupplier<Block> MALISHROOM = createSaplingLike("malishroom", MALISHROOM_GROWER, MapColor.COLOR_PURPLE);

    public static final RegistrySupplier<Block> POTTED_MALISHROOM = createPottedPlant("potted_malishroom", MALISHROOM);

    public static final RegistrySupplier<Block> FORTSHROOM = createSaplingLike("fortshroom", FORTSHROOM_GROWER, MapColor.COLOR_GREEN);

    public static final RegistrySupplier<Block> POTTED_FORTSHROOM = createPottedPlant("potted_fortshroom", FORTSHROOM);

    public static final RegistrySupplier<Block> PHANTOM_ROSE = createFlower("phantom_rose", MobEffects.STRENGTH, 3);

    public static final RegistrySupplier<Block> POTTED_PHANTOM_ROSE = createPottedPlant("potted_phantom_rose", PHANTOM_ROSE);

    public static final RegistrySupplier<Block> PHANTOM_ICE_FLOWER = createFlower("phantom_ice_flower", MobEffects.WEAVING, 5);

    public static final RegistrySupplier<Block> POTTED_PHANTOM_ICE_FLOWER = createPottedPlant("potted_phantom_ice_flower", PHANTOM_ICE_FLOWER);

    public static final RegistrySupplier<Block> PHANTOM_ROSE_ETHEREAL_CORE = createFlower("phantom_rose_ethereal_core", MobEffects.HASTE, 3.5F);

    public static final RegistrySupplier<Block> POTTED_PHANTOM_ROSE_ETHEREAL_CORE = createPottedPlant("potted_phantom_rose_ethereal_core", PHANTOM_ROSE_ETHEREAL_CORE);

    public static RegistrySupplier<Block> createFlower(String name, Holder<MobEffect> effect, float effectSeconds) {
        return BlockDataTemplates.EMPTY.addTag(BlockTags.SMALL_FLOWERS)
                .consume(registerBlock(name, resourceKey ->
                        new FlowerBlock(effect, effectSeconds, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).setId(resourceKey))));
    }

    public static RegistrySupplier<Block> createSaplingLike(String name, TreeGrower treeGrower, MapColor mapColor) {
        return BlockDataTemplates.EMPTY
                .consume(registerBlock(name, resourceKey ->
                        new SaplingLikeMushroomBlock(treeGrower, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).setId(resourceKey))));
    }

    public static void register() {

    }
}
