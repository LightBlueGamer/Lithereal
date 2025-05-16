package org.lithereal.world.dimension;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import org.lithereal.Lithereal;

import java.util.OptionalLong;

public class ModDimensions {
    public static ResourceKey<LevelStem> LITHEREAL_KEY = ResourceKey.create(Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "lithereal_key"));
    public static ResourceKey<Level> LITHEREAL_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "lithereal_level_key"));
    public static ResourceKey<DimensionType> LITHEREAL_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "lithereal_dim_type"));

    public static void bootstrapType(BootstrapContext<DimensionType> context) {
        context.register(LITHEREAL_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000),
                true, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                -64, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 0), 0)));
    }
}
