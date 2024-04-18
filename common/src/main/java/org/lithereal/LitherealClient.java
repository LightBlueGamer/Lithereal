package org.lithereal;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.Block;
import org.lithereal.block.ModBlocks;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityModel;
import org.lithereal.client.renderer.InfusementChamberBlockEntityModel;
import org.lithereal.item.ModCreativeTabs;
import org.lithereal.item.ModItems;
import org.lithereal.item.compat.CompatInit;
import org.lithereal.util.KeyBinding;
import org.lithereal.util.ModBlockColors;
import org.lithereal.util.ModItemColors;

import java.util.*;

import static dev.architectury.platform.Platform.isModLoaded;

public class LitherealClient {
    public static void init() {
        EntityModelLayerRegistry.register(InfusedLitheriteBlockEntityModel.LAYER_LOCATION, InfusedLitheriteBlockEntityModel::createBodyLayer);
        EntityModelLayerRegistry.register(InfusementChamberBlockEntityModel.LAYER_LOCATION, InfusementChamberBlockEntityModel::createBodyLayer);
        registerKeyBindings();
        registerColorHandlers();
        registerItemsToTab();
    }

    private static void registerKeyBindings() {
        KeyMappingRegistry.register(KeyBinding.FREEZE_KEY);
        KeyMappingRegistry.register(KeyBinding.SCORCH_KEY);
    }

    private static void registerColorHandlers() {
        ItemColor itemColor = ModItemColors.INFUSED_LITHERITE_COLOR_HANDLER::apply;
        BlockColor blockColor = ModBlockColors.INFUSED_LITHERITE_BLOCK_COLOR;

        ColorHandlerRegistry.registerItemColors(itemColor, LitherealExpectPlatform.getInfusedLitheriteBlock().asItem(), ModItems.INFUSED_LITHERITE_INGOT.get(), ModItems.INFUSED_LITHERITE_SWORD.get(), ModItems.INFUSED_LITHERITE_SHOVEL.get(), ModItems.INFUSED_LITHERITE_PICKAXE.get(), ModItems.INFUSED_LITHERITE_AXE.get(), ModItems.INFUSED_LITHERITE_HOE.get(), ModItems.INFUSED_LITHERITE_HAMMER.get(), ModItems.INFUSED_LITHERITE_HELMET.get(), ModItems.INFUSED_LITHERITE_CHESTPLATE.get(), ModItems.INFUSED_LITHERITE_LEGGINGS.get(), ModItems.INFUSED_LITHERITE_BOOTS.get());
        if (isModLoaded("combatify"))
            CompatInit.setColoursForCombatify(itemColor);
        ColorHandlerRegistry.registerBlockColors(blockColor, LitherealExpectPlatform.getInfusedLitheriteBlock());
    }

    private static void registerItemsToTab() {
        List<ItemStack> litherite = new ArrayList<>();
        List<ItemStack> otherI = new ArrayList<>();
        List<ItemStack> otherB = new ArrayList<>(Arrays.asList(
                LitherealExpectPlatform.getFireCrucibleBlock().asItem().getDefaultInstance(),
                LitherealExpectPlatform.getFreezingStationBlock().asItem().getDefaultInstance(),
                LitherealExpectPlatform.getInfusementChamberBlock().asItem().getDefaultInstance()));

        for (Potion potion : LitherealExpectPlatform.getRegisteredPotions()) {
            litherite.add(PotionUtils.setPotion(new ItemStack(LitherealExpectPlatform.getInfusedLitheriteBlock()), potion));
            litherite.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_INGOT.get()), potion));
            litherite.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_SWORD.get()), potion));
            litherite.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_SHOVEL.get()), potion));
            litherite.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_PICKAXE.get()), potion));
            litherite.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_AXE.get()), potion));
            litherite.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_HOE.get()), potion));
            litherite.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_HAMMER.get()), potion));
            litherite.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_HELMET.get()), potion));
            litherite.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_CHESTPLATE.get()), potion));
            litherite.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_LEGGINGS.get()), potion));
            litherite.add(PotionUtils.setPotion(new ItemStack(ModItems.INFUSED_LITHERITE_BOOTS.get()), potion));
            if (isModLoaded("combatify"))
                litherite = CompatInit.populateInfusedForCombatify(litherite, potion);
        }

        for (RegistrySupplier<Block> blockRegistryObject : ModBlocks.BLOCKS) {
            ItemStack blockItem = new ItemStack(blockRegistryObject.get());

            if (!litherite.contains(blockItem) && isLitherite(LitherealExpectPlatform.getResourceLocation(blockItem).getPath())) {
                litherite.add(blockItem);
            } else otherB.add(blockItem);
        }

        litherite.add(LitherealExpectPlatform.getLitheriteItem().getDefaultInstance());

        for (RegistrySupplier<Item> itemRegistrySupplier : ModItems.ITEMS) {
            ItemStack item = new ItemStack(itemRegistrySupplier.get());

            if (!litherite.contains(item) && isLitherite(LitherealExpectPlatform.getResourceLocation(item).getPath())) {
                litherite.add(item);
            } else otherI.add(item);
        }

        litherite.sort(Comparator.comparing(itemStack -> {
            String descriptionId = LitherealExpectPlatform.getResourceLocation(itemStack).getPath();

            if (descriptionId.startsWith("litherite") || descriptionId.startsWith("deepslate_litherite")) return "1";
            else if (descriptionId.startsWith("burning_litherite")) return "2";
            else if (descriptionId.startsWith("frozen_litherite")) return "3";
            else if (descriptionId.startsWith("withering_litherite")) return "4";
            else if (descriptionId.startsWith("infused_litherite")) return "5";
            else if (descriptionId.startsWith("charged_litherite")) return "6";

            return descriptionId;
        }));
        litherite.addAll(otherI);
        litherite.addAll(otherB);
        litherite = litherite.stream().filter(stack -> !stack.isEmpty()).toList();

        CreativeTabRegistry.appendBuiltinStack(ModCreativeTabs.LITHEREAL_TAB.get(), litherite.toArray(new ItemStack[0]));

    }
    public static boolean isLitherite(String id) {
        return id.startsWith("litherite") || id.startsWith("deepslate_litherite") || id.startsWith("burning_litherite") || id.startsWith("frozen_litherite") || id.startsWith("withering_litherite") || id.startsWith("infused_litherite") || id.startsWith("charged_litherite");
    }
}