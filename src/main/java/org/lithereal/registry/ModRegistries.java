package org.lithereal.registry;

//? fabric {
/*import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
*///?}
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
//? neoforge {
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
//?}
import org.lithereal.Lithereal;
import org.lithereal.core.component.miner_shape.MinerShape;
import org.lithereal.core.component.miner_shape.block_pos_processor.BlockPosProcessor;
import org.lithereal.core.component.miner_shape.condition.ValidityCondition;
import org.lithereal.core.component.miner_shape.value_provider.ValueProvider;

public class ModRegistries {
    public static final ResourceKey<Registry<MinerShape>> MINER_SHAPE_REGISTRY = ResourceKey.createRegistryKey(Lithereal.id("miner_shapes"));
    private static void commonRegister() {
        ValidityCondition.bootstrap();
        ValueProvider.bootstrap();
        BlockPosProcessor.bootstrap();
    }
    //? neoforge {
    public static void register(IEventBus bus) {
        commonRegister();
        bus.addListener(DataPackRegistryEvent.NewRegistry.class, newRegistry -> {
            newRegistry.dataPackRegistry(MINER_SHAPE_REGISTRY, MinerShape.DIRECT_CODEC, MinerShape.DIRECT_CODEC);
        });
    }
    //?}
    //? fabric {
    /*public static void register() {
        commonRegister();
        DynamicRegistries.registerSynced(ModRegistries.MINER_SHAPE_REGISTRY, MinerShape.DIRECT_CODEC);
    }
    *///?}
}
