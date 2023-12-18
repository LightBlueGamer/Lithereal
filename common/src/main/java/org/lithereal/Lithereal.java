package org.lithereal;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;
import org.lithereal.block.Blocks;
import org.lithereal.item.CreativeTabs;
import org.lithereal.item.Items;

import java.util.function.Supplier;

public class Lithereal {
    public static final String MOD_ID = "lithereal";

    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));
    
    public static void init() {

        CreativeTabs.register();
        Items.register();
        Blocks.register();
        
        System.out.println(LitherealExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
