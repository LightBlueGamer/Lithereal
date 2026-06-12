package org.lithereal.world.feature.tree.foliageplacer;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.lithereal.Lithereal;

import java.util.function.Supplier;

public class ModFoliagePlacers {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(Lithereal.MOD_ID, Registries.FOLIAGE_PLACER_TYPE);

    public static final RegistrySupplier<FoliagePlacerType<CapFoliagePlacer>> CAP = registerFoliagePlacerType("cap_foliage_placer", () -> new FoliagePlacerType<>(CapFoliagePlacer.CODEC));
    private static <T extends FoliagePlacer> RegistrySupplier<FoliagePlacerType<T>> registerFoliagePlacerType(String name, Supplier<FoliagePlacerType<T>> foliagePlacerType) {
        return FOLIAGE_PLACERS.register(name, foliagePlacerType);
    }

    public static void register() {
        FOLIAGE_PLACERS.register();
    }
}
