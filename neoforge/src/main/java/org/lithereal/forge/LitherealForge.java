package org.lithereal.forge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.lithereal.Lithereal;
import org.lithereal.forge.block.ForgeBlocks;
import org.lithereal.forge.block.entity.ForgeBlockEntities;
import org.lithereal.forge.event.ClientEvents;
import org.lithereal.forge.item.ForgeItems;
import org.lithereal.forge.screen.ForgeMenuTypes;

@Mod(Lithereal.MOD_ID)
public class LitherealForge {
    public LitherealForge(IEventBus modEventBus) {
        ForgeBlocks.register(modEventBus);
        ForgeBlockEntities.register(modEventBus);
        ForgeItems.register(modEventBus);
        ForgeMenuTypes.register(modEventBus);

        ClientEvents.ClientModBusEvents.register(modEventBus);

        Lithereal.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }
}
