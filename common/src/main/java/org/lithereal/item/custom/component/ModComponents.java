package org.lithereal.item.custom.component;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import org.lithereal.Lithereal;

import java.util.function.UnaryOperator;

public class ModComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Lithereal.MOD_ID, Registries.DATA_COMPONENT_TYPE);
    public static final RegistrySupplier<DataComponentType<Enhanced>> ENHANCED = register("enhanced", (builder) -> builder.persistent(Enhanced.CODEC).networkSynchronized(Enhanced.STREAM_CODEC).cacheEncoding());
    public static final RegistrySupplier<DataComponentType<Boolean>> REVEALED = register("revealed", (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    private static <T> RegistrySupplier<DataComponentType<T>> register(String string, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return COMPONENTS.register(string, () -> builder.apply(DataComponentType.builder()).build());
    }
    public static void register() {
        COMPONENTS.register();
    }
}
