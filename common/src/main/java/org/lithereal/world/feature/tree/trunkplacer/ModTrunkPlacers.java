package org.lithereal.world.feature.tree.trunkplacer;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.lithereal.Lithereal;

import java.util.function.Supplier;

public class ModTrunkPlacers {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Lithereal.MOD_ID, Registries.TRUNK_PLACER_TYPE);

    public static final RegistrySupplier<TrunkPlacerType<MegaOakTrunkPlacer>> MEGA_OAK = registerTrunkPlacerType("mega_oak_trunk_placer", () -> new TrunkPlacerType<>(MegaOakTrunkPlacer.CODEC));
    private static <T extends TrunkPlacer> RegistrySupplier<TrunkPlacerType<T>> registerTrunkPlacerType(String name, Supplier<TrunkPlacerType<T>> trunkPlacerType) {
        return TRUNK_PLACERS.register(name, trunkPlacerType);
    }

    public static void register() {
        TRUNK_PLACERS.register();
    }
}
