package org.lithereal.world.structure;

import com.mojang.serialization.MapCodec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import org.lithereal.Lithereal;

public class ModStructureProcessorTypes {
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSOR_TYPES = DeferredRegister.create(Lithereal.MOD_ID, Registries.STRUCTURE_PROCESSOR);

    public static final RegistrySupplier<StructureProcessorType<WaterReplaceProcessor>> WATER_REPLACE = registerStructureProcessorType("water_replace", WaterReplaceProcessor.CODEC);
    private static <T extends StructureProcessor> RegistrySupplier<StructureProcessorType<T>> registerStructureProcessorType(String name, MapCodec<T> codec) {
        return PROCESSOR_TYPES.register(name, () -> () -> codec);
    }

    public static void register() {
        PROCESSOR_TYPES.register();
    }
}
