package com.haru.lithereal.worldgen;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.haru.lithereal.Lithereal;
import com.haru.lithereal.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Lithereal.MOD_ID);
    public static final Supplier<List<OreConfiguration.TargetBlockState>>
            OVERWORLD_LITHERITE_ORES = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
            ModBlocks.LITHERITE_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
            ModBlocks.DEEPSLATE_LITHERITE_ORE.get().defaultBlockState())));
    public static final RegistryObject<ConfiguredFeature<?, ?>> LITHERITE_ORE = CONFIGURED_FEATURES.register("litherite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_LITHERITE_ORES.get(),3)));
    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }
}