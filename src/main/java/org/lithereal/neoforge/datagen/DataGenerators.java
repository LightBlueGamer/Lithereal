package org.lithereal.neoforge.datagen;

//? neoforge {
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.lithereal.Lithereal;
import org.lithereal.neoforge.datagen.loot.ModBlockLootTableProvider;
import org.lithereal.neoforge.datagen.loot.ModEntityLootTableProvider;
import org.lithereal.neoforge.datagen.registries.ModDamageTypesProvider;
import org.lithereal.neoforge.datagen.registries.RegistryEntryProvider;
import org.lithereal.neoforge.datagen.tags.ModBlockTagProvider;
import org.lithereal.neoforge.datagen.tags.ModDamageTypeTagsProvider;
import org.lithereal.neoforge.datagen.tags.ModItemTagProvider;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = Lithereal.MOD_ID, value = Dist.CLIENT)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider((packOutput, lookupProvider) -> new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(ModEntityLootTableProvider::new, LootContextParamSets.ENTITY)), lookupProvider));
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createDatapackRegistryObjects(entries(new ModDamageTypesProvider()));
        event.createProvider(ModDamageTypeTagsProvider::new);
        event.createBlockAndItemTags(ModBlockTagProvider::new, ModItemTagProvider::new);

        event.createProvider((packOutput, _) -> new ModModelProvider(packOutput));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static RegistrySetBuilder entries(RegistryEntryProvider<?>... entries) {
        RegistrySetBuilder builder = new RegistrySetBuilder();
        for (RegistryEntryProvider entry : entries) {
            builder.add(entry.key(), entry);
        }
        return builder;
    }
}
//?}