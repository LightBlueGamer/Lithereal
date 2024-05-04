package org.lithereal.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.lithereal.Lithereal;
import org.lithereal.neoforge.block.ForgeBlocks;
import org.lithereal.neoforge.block.entity.ForgeBlockEntities;
import org.lithereal.neoforge.block.entity.ImplementedItemHandler;
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
        /*modEventBus.addListener(RegisterCapabilitiesEvent.class, event -> {
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ForgeBlockEntities.FIRE_CRUCIBLE.get(), (myBlockEntity, side) -> myBlockEntity.getHandler());
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ForgeBlockEntities.FREEZING_STATION.get(), (myBlockEntity, side) -> myBlockEntity.getHandler());
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ForgeBlockEntities.INFUSEMENT_CHAMBER.get(), (myBlockEntity, side) -> myBlockEntity.getHandler());
        });*/ // Probably unneeded
        ClientEvents.ClientModBusEvents.register(modEventBus);

        Lithereal.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }
}
