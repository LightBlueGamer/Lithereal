package org.lithereal;

import com.google.common.base.Suppliers;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.client.Minecraft;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModItems;
import org.lithereal.util.KeyBinding;

import java.util.function.Supplier;

public class Lithereal {
    public static final String MOD_ID = "lithereal";

    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));
    
    public static void init() {

        ModCreativeTabs.register();
        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();

        ClientLifecycleEvent.CLIENT_SETUP.register(client -> {
            registerKeyBindings(client);
        });
        
        System.out.println(LitherealExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }

    private static void registerKeyBindings(Minecraft client) {
        KeyMappingRegistry.register(KeyBinding.FREEZE_KEY);
        KeyMappingRegistry.register(KeyBinding.SCORCH_KEY);
    }
}
