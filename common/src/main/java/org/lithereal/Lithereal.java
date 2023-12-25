package org.lithereal;

import com.google.common.base.Suppliers;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.ModBlockEntities;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityModel;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModItems;
import org.lithereal.recipe.ModRecipes;
import org.lithereal.util.KeyBinding;
import org.lithereal.util.ModBlockColors;
import org.lithereal.util.ModItemColors;

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
        ModRecipes.register();

        ClientLifecycleEvent.CLIENT_SETUP.register(client -> {
            ModelLayerLocation INFUSED_LITHERITE_BLOCK_LAYER = new ModelLayerLocation(new ResourceLocation(Lithereal.MOD_ID, "infused_litherite_block"), "main");
            EntityModelLayerRegistry.register(INFUSED_LITHERITE_BLOCK_LAYER, InfusedLitheriteBlockEntityModel::createBodyLayer);
            registerKeyBindings(client);
            registerColorHandlers(client);
            registerItemsToTab(client);
        });
        
        System.out.println(LitherealExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }

    private static void registerKeyBindings(Minecraft client) {
        KeyMappingRegistry.register(KeyBinding.FREEZE_KEY);
        KeyMappingRegistry.register(KeyBinding.SCORCH_KEY);
    }

    private static void registerColorHandlers(Minecraft client) {
        ItemColor itemColor = ModItemColors.INFUSED_LITHERITE_COLOR_HANDLER::apply;
        BlockColor blockColor = ModBlockColors.INFUSED_LITHERITE_BLOCK_COLOR;

        ColorHandlerRegistry.registerItemColors(itemColor, LitherealExpectPlatform.getInfusedLitheriteBlock().asItem(), ModItems.INFUSED_LITHERITE_CRYSTAL.get(), ModItems.INFUSED_LITHERITE_SWORD.get(), ModItems.INFUSED_LITHERITE_SHOVEL.get(), ModItems.INFUSED_LITHERITE_PICKAXE.get(), ModItems.INFUSED_LITHERITE_AXE.get(), ModItems.INFUSED_LITHERITE_HOE.get(), ModItems.INFUSED_LITHERITE_HAMMER.get(), ModItems.INFUSED_LITHERITE_HELMET.get(), ModItems.INFUSED_LITHERITE_CHESTPLATE.get(), ModItems.INFUSED_LITHERITE_LEGGINGS.get(), ModItems.INFUSED_LITHERITE_BOOTS.get());
        ColorHandlerRegistry.registerBlockColors(blockColor, LitherealExpectPlatform.getInfusedLitheriteBlock());
    }

    private static void registerItemsToTab(Minecraft client) {
        List<ItemStack> potionVariants = new ArrayList<>();

        Field[] fields = Potions.class.getDeclaredFields();

        for (Field field : fields) {
            if (Potion.class.isAssignableFrom(field.getType())) {
                try {
                    Potion potion = (Potion) field.get(null);
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(LitherealExpectPlatform.getInfusedLitheriteBlock()), potion));
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_CRYSTAL.get()), potion));
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_SWORD.get()), potion));
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_SHOVEL.get()), potion));
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_PICKAXE.get()), potion));
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_AXE.get()), potion));
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_HOE.get()), potion));
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_HAMMER.get()), potion));
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_HELMET.get()), potion));
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_CHESTPLATE.get()), potion));
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_LEGGINGS.get()), potion));
                    potionVariants.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_BOOTS.get()), potion));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        CreativeTabRegistry.appendBuiltinStack(ModCreativeTabs.LITHEREAL_TAB.get(), potionVariants.toArray(new ItemStack[0]));
        CreativeTabRegistry.appendBuiltinStack(ModCreativeTabs.LITHEREAL_TAB.get(), LitherealExpectPlatform.getFireCrucibleBlock().asItem().getDefaultInstance());

    }
}
