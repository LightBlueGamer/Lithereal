package org.lithereal;

import com.google.common.base.Suppliers;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityModel;
import org.lithereal.client.renderer.InfusementChamberBlockEntityModel;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModItems;
import org.lithereal.recipe.ModRecipes;
import org.lithereal.util.KeyBinding;
import org.lithereal.util.ModBlockColors;
import org.lithereal.util.ModItemColors;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class Lithereal {
    public static final String MOD_ID = "lithereal";

    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static final Logger LOGGER = Logger.getLogger(MOD_ID);
    public static void init() {
        ModCreativeTabs.register();
        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();
        ModRecipes.register();

        System.out.println(LitherealExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}