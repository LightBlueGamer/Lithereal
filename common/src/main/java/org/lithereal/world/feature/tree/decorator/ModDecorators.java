package org.lithereal.world.feature.tree.decorator;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.lithereal.Lithereal;

import java.util.function.Supplier;

public class ModDecorators {
    public static final DeferredRegister<TreeDecoratorType<?>> DECORATORS = DeferredRegister.create(Lithereal.MOD_ID, Registries.TREE_DECORATOR_TYPE);

    public static final RegistrySupplier<TreeDecoratorType<AttachedToTrunkDecorator>> ATTACHED_TO_TRUNK = registerTreeDecoratorType("attached_to_trunk", () -> new TreeDecoratorType<>(AttachedToTrunkDecorator.CODEC));
    private static <T extends TreeDecorator> RegistrySupplier<TreeDecoratorType<T>> registerTreeDecoratorType(String name, Supplier<TreeDecoratorType<T>> treeDecoratorType) {
        return DECORATORS.register(name, treeDecoratorType);
    }

    public static void register() {
        DECORATORS.register();
    }
}
