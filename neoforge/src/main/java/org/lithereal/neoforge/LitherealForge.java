package org.lithereal.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.lithereal.Lithereal;
import org.lithereal.neoforge.world.block.ForgeBlocks;
import org.lithereal.neoforge.world.block.entity.ForgeBlockEntities;
import org.lithereal.neoforge.client.event.ClientEvents;
import org.lithereal.neoforge.world.item.ForgeItems;
import org.lithereal.neoforge.client.gui.screens.inventory.ForgeMenuTypes;

@Mod(Lithereal.MOD_ID)
public class LitherealForge {
    public LitherealForge(IEventBus modEventBus) {
        ForgeBlocks.register(modEventBus);
        ForgeBlockEntities.register(modEventBus);
        ForgeItems.register(modEventBus);
        ForgeMenuTypes.register(modEventBus);

        ClientEvents.ClientModBusEvents.register(modEventBus);
        ClientEvents.ClientForgeBusEvents.register();

        Lithereal.init();
    }
}
