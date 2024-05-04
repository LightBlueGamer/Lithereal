package org.lithereal.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.lithereal.Lithereal;
import org.lithereal.neoforge.block.ForgeBlocks;
import org.lithereal.neoforge.block.entity.ForgeBlockEntities;
import org.lithereal.neoforge.event.ClientEvents;
import org.lithereal.neoforge.item.ForgeItems;
import org.lithereal.neoforge.screen.ForgeMenuTypes;

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
