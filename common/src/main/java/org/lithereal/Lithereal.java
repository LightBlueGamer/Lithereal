package org.lithereal;

import com.google.common.base.Suppliers;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.logging.Log;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModItems;
import org.lithereal.util.KeyBinding;
import org.lithereal.util.ModItemColors;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class Lithereal {
    public static final String MOD_ID = "lithereal";

    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    private static final Logger LOGGER = Logger.getLogger(MOD_ID);
    public static void init() {

        ModCreativeTabs.register();
        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();

        ClientLifecycleEvent.CLIENT_SETUP.register(client -> {
            registerKeyBindings(client);
            registerItemColorHandlers(client);
            registerItemsToTab(client);
        });
        
        System.out.println(LitherealExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }

    private static void registerKeyBindings(Minecraft client) {
        KeyMappingRegistry.register(KeyBinding.FREEZE_KEY);
        KeyMappingRegistry.register(KeyBinding.SCORCH_KEY);
    }

    private static void registerItemColorHandlers(Minecraft client) {
        ItemColor itemColor = ModItemColors.INFUSED_LITHERITE_COLOR_HANDLER::apply;

        ColorHandlerRegistry.registerItemColors(itemColor, ModItems.INFUSED_LITHERITE_CRYSTAL.get());
    }

    private static void registerItemsToTab(Minecraft client) {
        List<ItemStack> potionVariants = new ArrayList<>();

        Field[] fields = Potions.class.getDeclaredFields();

        for (Field field : fields) {
            if (Potion.class.isAssignableFrom(field.getType())) {
                try {
                    Potion potion = (Potion) field.get(null);
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_CRYSTAL.get()), potion));
                    LOGGER.info("Added potion variant: " + potion.getName(""));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        LOGGER.info(potionVariants.toString());

        CreativeTabRegistry.appendBuiltinStack(ModCreativeTabs.LITHEREAL_TAB.get(), potionVariants.toArray(new ItemStack[0]));

    }
}
