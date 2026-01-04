package org.lithereal.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.lithereal.Lithereal;
import org.lithereal.neoforge.client.event.ClientEvents;

@Mod(value = Lithereal.MOD_ID, dist = Dist.CLIENT)
public class LitherealForgeClient {
    public LitherealForgeClient(IEventBus modEventBus) {
        ClientEvents.ClientModBusEvents.register(modEventBus);
        ClientEvents.ClientForgeBusEvents.register();
    }
}
