package org.lithereal.core.component;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import org.lithereal.Lithereal;

import java.util.function.UnaryOperator;

public class ModComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Lithereal.MOD_ID, Registries.DATA_COMPONENT_TYPE);
    private static <T> RegistrySupplier<DataComponentType<T>> register(String string, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return COMPONENTS.register(string, () -> builder.apply(DataComponentType.builder()).build());
    }
    public static void register() {
        COMPONENTS.register();
    }
}
