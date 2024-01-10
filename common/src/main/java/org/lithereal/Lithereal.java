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

        ClientLifecycleEvent.CLIENT_SETUP.register(client -> {
            EntityModelLayerRegistry.register(InfusedLitheriteBlockEntityModel.LAYER_LOCATION, InfusedLitheriteBlockEntityModel::createBodyLayer);
            EntityModelLayerRegistry.register(InfusementChamberBlockEntityModel.LAYER_LOCATION, InfusementChamberBlockEntityModel::createBodyLayer);
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

        ColorHandlerRegistry.registerItemColors(itemColor, LitherealExpectPlatform.getInfusedLitheriteBlock().asItem(), ModItems.INFUSED_LITHERITE_INGOT.get(), ModItems.INFUSED_LITHERITE_SWORD.get(), ModItems.INFUSED_LITHERITE_SHOVEL.get(), ModItems.INFUSED_LITHERITE_PICKAXE.get(), ModItems.INFUSED_LITHERITE_AXE.get(), ModItems.INFUSED_LITHERITE_HOE.get(), ModItems.INFUSED_LITHERITE_HAMMER.get(), ModItems.INFUSED_LITHERITE_HELMET.get(), ModItems.INFUSED_LITHERITE_CHESTPLATE.get(), ModItems.INFUSED_LITHERITE_LEGGINGS.get(), ModItems.INFUSED_LITHERITE_BOOTS.get());
        ColorHandlerRegistry.registerBlockColors(blockColor, LitherealExpectPlatform.getInfusedLitheriteBlock());
    }

    private static void registerItemsToTab(Minecraft client) {
        List<ItemStack> itemsToAdd = new ArrayList<>(Arrays.asList(
                LitherealExpectPlatform.getLitheriteItem().getDefaultInstance(),
                LitherealExpectPlatform.getFireCrucibleBlock().asItem().getDefaultInstance(),
                LitherealExpectPlatform.getFreezingStationBlock().asItem().getDefaultInstance(),
                LitherealExpectPlatform.getInfusementChamberBlock().asItem().getDefaultInstance()
        ));

        Field[] fields = Potions.class.getDeclaredFields();

        for (Field field : fields) {
            if (Potion.class.isAssignableFrom(field.getType())) {
                try {
                    Potion potion = (Potion) field.get(null);
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(LitherealExpectPlatform.getInfusedLitheriteBlock()), potion));
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_INGOT.get()), potion));
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_SWORD.get()), potion));
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_SHOVEL.get()), potion));
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_PICKAXE.get()), potion));
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_AXE.get()), potion));
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_HOE.get()), potion));
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_HAMMER.get()), potion));
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_HELMET.get()), potion));
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_CHESTPLATE.get()), potion));
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_LEGGINGS.get()), potion));
                    itemsToAdd.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_BOOTS.get()), potion));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        for (RegistrySupplier<Item> itemRegistrySupplier : ModItems.ITEMS) {
            ItemStack item = new ItemStack(itemRegistrySupplier.get(), 1);
            if (item != null && !itemsToAdd.contains(item)) {
                itemsToAdd.add(item);
            }
        }

        for (RegistrySupplier<Block> blockRegistryObject : ModBlocks.BLOCKS) {
            Block block = blockRegistryObject.get();

            ItemStack blockItem = new ItemStack(block.asItem(), 1);

            if (blockItem != null && !itemsToAdd.contains(blockItem) && blockItem.getCount() == 1) {
                itemsToAdd.add(blockItem);
            }
        }

        itemsToAdd.sort(Comparator.comparing(itemStack -> {
            String descriptionId = LitherealExpectPlatform.getResourceLocation(itemStack).getPath();

            if (descriptionId.startsWith("litherite")) return "1";
            else if (descriptionId.startsWith("burning_litherite")) return "2";
            else if (descriptionId.startsWith("frozen_litherite")) return "3";
            else if (descriptionId.startsWith("withering_litherite")) return "4";
            else if (descriptionId.startsWith("infused_litherite")) return "5";
            else if (descriptionId.startsWith("charged_litherite")) return "6";

            String[] parts = descriptionId.split("[._]");
            return parts.length > 1 ? parts[1] : "0";
        }));

        CreativeTabRegistry.appendBuiltinStack(ModCreativeTabs.LITHEREAL_TAB.get(), itemsToAdd.toArray(new ItemStack[0]));

    }
}
