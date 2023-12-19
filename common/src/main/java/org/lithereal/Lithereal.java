package org.lithereal;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModItems;

import java.util.function.Supplier;

public class Lithereal {
    public static final String MOD_ID = "lithereal";

    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));
    
    public static void init() {

        ModCreativeTabs.register();
        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();
        
        System.out.println(LitherealExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
