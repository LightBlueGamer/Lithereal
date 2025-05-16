package org.lithereal.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.lithereal.Lithereal;
import org.lithereal.neoforge.world.biome.ForgeTerrablender;
import org.lithereal.neoforge.world.block.ForgeBlocks;
import org.lithereal.neoforge.world.block.entity.ForgeBlockEntities;
import org.lithereal.neoforge.client.event.ClientEvents;
import org.lithereal.neoforge.world.item.ForgeItems;
import org.lithereal.neoforge.client.gui.screens.inventory.ForgeMenuTypes;
import org.lithereal.world.biome.surface.ModSurfaceRules;
import terrablender.api.SurfaceRuleManager;

@Mod(Lithereal.MOD_ID)
public class LitherealForge {
    public LitherealForge(IEventBus modEventBus) {
        ForgeBlocks.register(modEventBus);
        ForgeBlockEntities.register(modEventBus);
        ForgeItems.register(modEventBus);
        ForgeMenuTypes.register(modEventBus);
        ForgeTerrablender.registerBiomes();

        ClientEvents.ClientModBusEvents.register(modEventBus);

        Lithereal.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Lithereal.MOD_ID, ModSurfaceRules.makeRules());
        });
    }
}
