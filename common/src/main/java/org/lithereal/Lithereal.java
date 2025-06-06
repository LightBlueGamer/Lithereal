package org.lithereal;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.core.component.ModComponents;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.mob_effect.ModMobEffects;
import org.lithereal.mob_effect.potion.ModPotions;
import org.lithereal.tags.ModTags;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.entity.ModEntities;
import org.lithereal.item.ModArmorMaterials;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModItems;
import org.lithereal.world.feature.ModFeatures;
import org.lithereal.world.structure.ModStructureProcessorTypes;

import java.util.function.Supplier;
import java.util.logging.Logger;

public class Lithereal {
    public static final String MOD_ID = "lithereal";
    public static final ResourceKey<LootTable> LITHEREAL_CHAMBERS_REWARD = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "chests/lithereal_chambers/reward"));

    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static final Logger LOGGER = Logger.getLogger(MOD_ID);
    public static void init() {
        ModBlocks.register();
        ModCreativeTabs.register();
        ModArmorMaterials.register();
        ModComponents.register();
        ModMobEffects.register();
        ModPotions.register();
        ModTags.init();
        ModItems.register();
        ModBlockEntities.register();
        ModRecipes.register();
        ModEntities.register();
        ModParticles.register();
        ModStructureProcessorTypes.register();
        ModFeatures.register();

        System.out.println(LitherealExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}