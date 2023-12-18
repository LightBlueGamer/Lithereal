package org.lithereal.forge;

import dev.architectury.platform.forge.EventBuses;
import org.lithereal.Lithereal;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Lithereal.MOD_ID)
public class LitherealForge {
    public LitherealForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Lithereal.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Lithereal.init();
    }
}
