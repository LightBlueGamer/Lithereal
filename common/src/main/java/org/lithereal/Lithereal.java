package org.lithereal;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.lithereal.world.block.ModBlocks;
import org.lithereal.world.block.entity.ModBlockEntities;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.core.component.ModComponents;
import org.lithereal.world.entity.ModEntities;
import org.lithereal.world.item.ModCreativeTabs;
import org.lithereal.world.item.ModItems;
import org.lithereal.world.item.ModArmorMaterials;
import org.lithereal.world.item.enchantment.ModEnchantments;
import org.lithereal.tags.ModTags;
import org.lithereal.recipe.ModRecipes;

import java.util.function.Supplier;
import java.util.logging.Logger;

public class Lithereal {
    public static final String MOD_ID = "lithereal";
    public static final ResourceKey<LootTable> LITHEREAL_CHAMBERS_REWARD = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(Lithereal.MOD_ID, "chests/lithereal_chambers/reward"));

    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static final Logger LOGGER = Logger.getLogger(MOD_ID);
    public static void init() {
        ModCreativeTabs.register();
        ModBlocks.register();
        ModArmorMaterials.register();
        ModComponents.register();
        ModTags.init();
        ModItems.register();
        ModBlockEntities.register();
        ModRecipes.register();
        ModEntities.register();
        ModEnchantments.register();
        ModParticles.register();

        System.out.println(LitherealExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}