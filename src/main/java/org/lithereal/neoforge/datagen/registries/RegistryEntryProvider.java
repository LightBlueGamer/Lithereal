package org.lithereal.neoforge.datagen.registries;

//? neoforge {
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.lithereal.Lithereal;

public interface RegistryEntryProvider<T> extends RegistrySetBuilder.RegistryBootstrap<T> {
    default ResourceKey<T> key(String name) {
        return Lithereal.key(key(), name);
    }

    default ResourceKey<T> key(Identifier id) {
        return ResourceKey.create(key(), id);
    }
    ResourceKey<Registry<T>> key();
}
//?}