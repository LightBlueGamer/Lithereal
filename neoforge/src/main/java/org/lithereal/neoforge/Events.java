package org.lithereal.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.lithereal.neoforge.data.worldgen.LitherealOverworldRegion;
import terrablender.api.Regions;

public class Events {
    public static class ModBusEvents {
        public static void register(IEventBus bus) {
            bus.register(ModBusEvents.class);
        }
        public static void commonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                Regions.register(new LitherealOverworldRegion(2));
            });
        }
    }
}
