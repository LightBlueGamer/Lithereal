package org.lithereal.quilt;

import org.lithereal.Lithereal;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class LitherealQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        Lithereal.init();
    }
}
