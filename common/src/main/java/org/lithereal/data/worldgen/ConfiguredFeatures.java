package org.lithereal.data.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.tags.ModTags;

import java.util.List;

public class ConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> LITHERITE_ORE_KEY = registerKey("litherite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NERITH_ORE_KEY = registerKey("nerith_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMINIUM_ORE_KEY = registerKey("luminium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CYRUM_ORE_KEY = registerKey("cyrum_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COPALITE_ORE_KEY = registerKey("copalite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURELITE_ORE_KEY = registerKey("aurelite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ALLIAN_ORE_KEY = registerKey("allian_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ELUNITE_ORE_KEY = registerKey("elunite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHRYON_ORE_KEY = registerKey("chryon_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SATURNITE_ORE_KEY = registerKey("saturnite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HELLIONITE_ORE_KEY = registerKey("hellionite_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new TagMatchTest(BlockTags.NETHER_CARVER_REPLACEABLES);
        RuleTest endstoneReplaceables = new BlockMatchTest(Blocks.END_STONE);
        RuleTest etherstoneReplaceables = new TagMatchTest(ModTags.ETHERSTONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> cyrumOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.CYRUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_CYRUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(etherstoneReplaceables, ModBlocks.ETHERSTONE_CYRUM_ORE.get().defaultBlockState())
        );
        register(context, CYRUM_ORE_KEY, Feature.ORE, new OreConfiguration(cyrumOres, 12));

        List<OreConfiguration.TargetBlockState> aureliteOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.AURELITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_AURELITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(etherstoneReplaceables, ModBlocks.ETHERSTONE_AURELITE_ORE.get().defaultBlockState())
        );
        register(context, AURELITE_ORE_KEY, Feature.ORE, new OreConfiguration(aureliteOres, 8)); // Vein size 8

        List<OreConfiguration.TargetBlockState> copaliteOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.COPALITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_COPALITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(etherstoneReplaceables, ModBlocks.ETHERSTONE_COPALITE_ORE.get().defaultBlockState())
        );
        register(context, COPALITE_ORE_KEY, Feature.ORE, new OreConfiguration(copaliteOres, 8)); // Vein size 8

        List<OreConfiguration.TargetBlockState> netherHellioniteOres = List.of(
                OreConfiguration.target(netherrackReplaceables, ModBlocks.HELLIONITE_ORE.get().defaultBlockState())
        );
        register(context, HELLIONITE_ORE_KEY, Feature.ORE, new OreConfiguration(netherHellioniteOres, 5)); // Vein size 5

        List<OreConfiguration.TargetBlockState> litheriteOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.LITHERITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_LITHERITE_ORE.get().defaultBlockState())
        );
        register(context, LITHERITE_ORE_KEY, Feature.ORE, new OreConfiguration(litheriteOres, 4)); // Vein size 4

        List<OreConfiguration.TargetBlockState> netherSaturniteOres = List.of(
                OreConfiguration.target(netherrackReplaceables, ModBlocks.SATURNITE_ORE.get().defaultBlockState())
        );
        register(context, SATURNITE_ORE_KEY, Feature.ORE, new OreConfiguration(netherSaturniteOres, 3)); // Vein size 3

        List<OreConfiguration.TargetBlockState> endChryonOres = List.of(
                OreConfiguration.target(endstoneReplaceables, ModBlocks.CHRYON_ORE.get().defaultBlockState())
        );
        register(context, CHRYON_ORE_KEY, Feature.ORE, new OreConfiguration(endChryonOres, 3)); // Vein size 3

        List<OreConfiguration.TargetBlockState> endEluniteOres = List.of(
                OreConfiguration.target(endstoneReplaceables, ModBlocks.ELUNITE_ORE.get().defaultBlockState())
        );
        register(context, ELUNITE_ORE_KEY, Feature.ORE, new OreConfiguration(endEluniteOres, 2)); // Vein size 2

        List<OreConfiguration.TargetBlockState> endAllianOres = List.of(
                OreConfiguration.target(endstoneReplaceables, ModBlocks.ALLIAN_ORE.get().defaultBlockState())
        );
        register(context, ALLIAN_ORE_KEY, Feature.ORE, new OreConfiguration(endAllianOres, 2)); // Vein size 2

        List<OreConfiguration.TargetBlockState> luminiumOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.LUMINIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_LUMINIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(etherstoneReplaceables, ModBlocks.ETHERSTONE_LUMINIUM_ORE.get().defaultBlockState())
        );
        register(context, LUMINIUM_ORE_KEY, Feature.ORE, new OreConfiguration(luminiumOres, 4)); // Vein size 2

        List<OreConfiguration.TargetBlockState> nerithOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.NERITH_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_NERITH_ORE.get().defaultBlockState()),
                OreConfiguration.target(etherstoneReplaceables, ModBlocks.ETHERSTONE_NERITH_ORE.get().defaultBlockState())
        );
        register(context, NERITH_ORE_KEY, Feature.ORE, new OreConfiguration(nerithOres, 2)); // Vein size 1
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}