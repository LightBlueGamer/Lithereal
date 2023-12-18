package org.lithereal.fabric;

import org.lithereal.Lithereal;
import net.fabricmc.api.ModInitializer;

public class LitherealFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Lithereal.init();
    }
}
