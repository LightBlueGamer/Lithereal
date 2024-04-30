package org.lithereal;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.entity.ModEntities;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModItems;
import org.lithereal.item.custom.ModArmorMaterials;
import org.lithereal.item.custom.component.ModComponents;
import org.lithereal.item.enchantment.ModEnchantments;
import org.lithereal.item.tag.ModTags;
import org.lithereal.recipe.ModRecipes;

import java.util.function.Supplier;
import java.util.logging.Logger;

public class Lithereal {
    public static final String MOD_ID = "lithereal";

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

        System.out.println(LitherealExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}