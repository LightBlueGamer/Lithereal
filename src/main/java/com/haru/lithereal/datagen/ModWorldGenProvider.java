//package com.haru.lithereal.datagen;
//
//import com.haru.lithereal.Lithereal;
//import com.haru.lithereal.worldgen.ModConfiguredFeatures;
//import com.haru.lithereal.worldgen.ModPlacedFeatures;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.server.level.WorldGenRegion;
//
//import java.util.Set;
//
//
//public class ModWorldGenProvider extends WorldGenRegion {
//    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
//            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
//            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
//
//    public ModWorldGenProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> registries) {
//        super(generator, registries, BUILDER, Set.of(lithereal.MOD_ID));
//    }
//}
