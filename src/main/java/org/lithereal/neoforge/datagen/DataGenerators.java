package org.lithereal.neoforge.datagen;

//? neoforge {
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.lithereal.Lithereal;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = Lithereal.MOD_ID, value = Dist.CLIENT)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider((packOutput, lookupProvider) -> new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(ModEntityLootTableProvider::new, LootContextParamSets.ENTITY)), lookupProvider));
        event.createProvider(ModRecipeProvider.Runner::new);

        event.createProvider((packOutput, _) -> new ModModelProvider(packOutput));
    }
}
//?}