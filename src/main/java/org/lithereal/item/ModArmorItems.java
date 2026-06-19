package org.lithereal.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorType;
import org.lithereal.item.base.ModArmorItem;
import org.lithereal.item.datagen.ItemDataTemplate;
import org.lithereal.item.datagen.ItemDataTemplates;
import org.lithereal.item.infused.InfusedArmorItem;
import org.lithereal.tags.ModTags;

import static org.lithereal.item.ability.Ability.*;

public class ModArmorItems {
    public static final RegistrySupplier<Item> LITHERITE_HELMET = ItemDataTemplates.HELMET.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("litherite_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.HELMET, properties), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HELMET = ItemDataTemplates.HELMET.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("burning_litherite_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.BURNING_LITHERITE, ArmorType.HELMET, BURNING.createArmorComponent(properties)), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HELMET = ItemDataTemplates.HELMET.addTags(ModTags.LITHERITE_ARMORS, ItemTags.FREEZE_IMMUNE_WEARABLES).consumeSpecial("frozen_litherite_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.FROZEN_LITHERITE, ArmorType.HELMET, FROZEN.createArmorComponent(properties)), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HELMET = ItemDataTemplates.HELMET
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("smoldering_litherite_helmet", properties ->
                    new ModArmorItem(ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.HELMET, SMOLDERING.createArmorComponent(properties)), BURNING_LITHERITE_HELMET);

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HELMET = ItemDataTemplates.HELMET
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTags(ModTags.LITHERITE_ARMORS, ItemTags.FREEZE_IMMUNE_WEARABLES).consumeSpecial("frostbitten_litherite_helmet", properties ->
                    new ModArmorItem(ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.HELMET, FROSTBITTEN.createArmorComponent(properties)), FROZEN_LITHERITE_HELMET);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HELMET = ItemDataTemplates.HELMET
            .copyWithModelOverride(ItemDataTemplate.INFUSED_BASIC_ITEM_NO_OVERLAY)
            .addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("infused_litherite_helmet", properties ->
                    new InfusedArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.HELMET, properties), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HELMET = ItemDataTemplates.HELMET.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("withering_litherite_helmet", properties ->
            new ModArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorType.HELMET, WITHERING.createArmorComponent(properties)), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> ODYSIUM_HELMET = ItemDataTemplates.HELMET
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(false))
            .addTag(ModTags.ODYSIUM_ARMORS).consumeSpecialWithIngredients("odysium_helmet", properties ->
                    new ModArmorItem(ModArmorMaterials.ODYSIUM, ArmorType.HELMET, ODYSIUM.createArmorComponent(properties)), ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_HELMET, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HELMET = ItemDataTemplates.HELMET
            .copyWithRecipeOverride(ItemDataTemplate.NONE)
            .addTag(ModTags.ODYSIUM_ARMORS).consumeSpecial("enhanced_odysium_helmet", properties ->
                    new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.HELMET, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_CHESTPLATE = ItemDataTemplates.CHESTPLATE.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("litherite_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.CHESTPLATE, properties), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_CHESTPLATE = ItemDataTemplates.CHESTPLATE.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("burning_litherite_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.BURNING_LITHERITE, ArmorType.CHESTPLATE, BURNING.createArmorComponent(properties)), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_CHESTPLATE = ItemDataTemplates.CHESTPLATE.addTags(ModTags.LITHERITE_ARMORS, ItemTags.FREEZE_IMMUNE_WEARABLES).consumeSpecial("frozen_litherite_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.FROZEN_LITHERITE, ArmorType.CHESTPLATE, FROZEN.createArmorComponent(properties)), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_CHESTPLATE = ItemDataTemplates.CHESTPLATE
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("smoldering_litherite_chestplate", properties ->
                    new ModArmorItem(ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.CHESTPLATE, SMOLDERING.createArmorComponent(properties)), BURNING_LITHERITE_CHESTPLATE);

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_CHESTPLATE = ItemDataTemplates.CHESTPLATE
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTags(ModTags.LITHERITE_ARMORS, ItemTags.FREEZE_IMMUNE_WEARABLES).consumeSpecial("frostbitten_litherite_chestplate", properties ->
                    new ModArmorItem(ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.CHESTPLATE, FROSTBITTEN.createArmorComponent(properties)), FROZEN_LITHERITE_CHESTPLATE);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_CHESTPLATE = ItemDataTemplates.CHESTPLATE
            .copyWithModelOverride(ItemDataTemplate.INFUSED_BASIC_ITEM_NO_OVERLAY)
            .addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("infused_litherite_chestplate", properties ->
                    new InfusedArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.CHESTPLATE, properties), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_CHESTPLATE = ItemDataTemplates.CHESTPLATE.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("withering_litherite_chestplate", properties ->
            new ModArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorType.CHESTPLATE, WITHERING.createArmorComponent(properties)), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> ODYSIUM_CHESTPLATE = ItemDataTemplates.CHESTPLATE
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(false))
            .addTag(ModTags.ODYSIUM_ARMORS).consumeSpecialWithIngredients("odysium_chestplate", properties ->
                    new ModArmorItem(ModArmorMaterials.ODYSIUM, ArmorType.CHESTPLATE, ODYSIUM.createArmorComponent(properties)), ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_CHESTPLATE, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_CHESTPLATE = ItemDataTemplates.CHESTPLATE
            .copyWithRecipeOverride(ItemDataTemplate.NONE)
            .addTag(ModTags.ODYSIUM_ARMORS).consumeSpecial("enhanced_odysium_chestplate", properties ->
                    new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.CHESTPLATE, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_LEGGINGS = ItemDataTemplates.LEGGINGS.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("litherite_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.LEGGINGS, properties), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_LEGGINGS = ItemDataTemplates.LEGGINGS.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("burning_litherite_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.BURNING_LITHERITE, ArmorType.LEGGINGS, BURNING.createArmorComponent(properties)), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_LEGGINGS = ItemDataTemplates.LEGGINGS.addTags(ModTags.LITHERITE_ARMORS, ItemTags.FREEZE_IMMUNE_WEARABLES).consumeSpecial("frozen_litherite_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.FROZEN_LITHERITE, ArmorType.LEGGINGS, FROZEN.createArmorComponent(properties)), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_LEGGINGS = ItemDataTemplates.LEGGINGS
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("smoldering_litherite_leggings", properties ->
                    new ModArmorItem(ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.LEGGINGS, SMOLDERING.createArmorComponent(properties)), BURNING_LITHERITE_LEGGINGS);

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_LEGGINGS = ItemDataTemplates.LEGGINGS
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTags(ModTags.LITHERITE_ARMORS, ItemTags.FREEZE_IMMUNE_WEARABLES).consumeSpecial("frostbitten_litherite_leggings", properties ->
                    new ModArmorItem(ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.LEGGINGS, FROSTBITTEN.createArmorComponent(properties)), FROZEN_LITHERITE_LEGGINGS);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_LEGGINGS = ItemDataTemplates.LEGGINGS
            .copyWithModelOverride(ItemDataTemplate.INFUSED_BASIC_ITEM_NO_OVERLAY)
            .addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("infused_litherite_leggings", properties ->
                    new InfusedArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.LEGGINGS, properties), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_LEGGINGS = ItemDataTemplates.LEGGINGS.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("withering_litherite_leggings", properties ->
            new ModArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorType.LEGGINGS, WITHERING.createArmorComponent(properties)), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> ODYSIUM_LEGGINGS = ItemDataTemplates.LEGGINGS
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(false))
            .addTag(ModTags.ODYSIUM_ARMORS).consumeSpecialWithIngredients("odysium_leggings", properties ->
                    new ModArmorItem(ModArmorMaterials.ODYSIUM, ArmorType.LEGGINGS, ODYSIUM.createArmorComponent(properties)), ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_LEGGINGS, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_LEGGINGS = ItemDataTemplates.LEGGINGS
            .copyWithRecipeOverride(ItemDataTemplate.NONE)
            .addTag(ModTags.ODYSIUM_ARMORS).consumeSpecial("enhanced_odysium_leggings", properties ->
                    new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.LEGGINGS, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_BOOTS = ItemDataTemplates.BOOTS.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("litherite_boots", properties ->
            new ModArmorItem(ModArmorMaterials.LITHERITE, ArmorType.BOOTS, properties), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_BOOTS = ItemDataTemplates.BOOTS.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("burning_litherite_boots", properties ->
            new ModArmorItem(ModArmorMaterials.BURNING_LITHERITE, ArmorType.BOOTS, BURNING.createArmorComponent(properties)), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_BOOTS = ItemDataTemplates.BOOTS.addTags(ModTags.LITHERITE_ARMORS, ItemTags.FREEZE_IMMUNE_WEARABLES).consumeSpecial("frozen_litherite_boots", properties ->
            new ModArmorItem(ModArmorMaterials.FROZEN_LITHERITE, ArmorType.BOOTS, FROZEN.createArmorComponent(properties)), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_BOOTS = ItemDataTemplates.BOOTS
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("smoldering_litherite_boots", properties ->
                    new ModArmorItem(ModArmorMaterials.SMOLDERING_LITHERITE, ArmorType.BOOTS, SMOLDERING.createArmorComponent(properties)), BURNING_LITHERITE_BOOTS);

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_BOOTS = ItemDataTemplates.BOOTS
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTags(ModTags.LITHERITE_ARMORS, ItemTags.FREEZE_IMMUNE_WEARABLES).consumeSpecial("frostbitten_litherite_boots", properties ->
                    new ModArmorItem(ModArmorMaterials.FROSTBITTEN_LITHERITE, ArmorType.BOOTS, FROSTBITTEN.createArmorComponent(properties)), FROZEN_LITHERITE_BOOTS);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_BOOTS = ItemDataTemplates.BOOTS
            .copyWithModelOverride(ItemDataTemplate.INFUSED_BASIC_ITEM_NO_OVERLAY)
            .addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("infused_litherite_boots", properties ->
                    new InfusedArmorItem(ModArmorMaterials.INFUSED_LITHERITE, ArmorType.BOOTS, properties), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_BOOTS = ItemDataTemplates.BOOTS.addTag(ModTags.LITHERITE_ARMORS).consumeSpecial("withering_litherite_boots", properties ->
            new ModArmorItem(ModArmorMaterials.WITHERING_LITHERITE, ArmorType.BOOTS, WITHERING.createArmorComponent(properties)), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> ODYSIUM_BOOTS = ItemDataTemplates.BOOTS
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(false))
            .addTag(ModTags.ODYSIUM_ARMORS).consumeSpecialWithIngredients("odysium_boots", properties ->
                    new ModArmorItem(ModArmorMaterials.ODYSIUM, ArmorType.BOOTS, ODYSIUM.createArmorComponent(properties)), ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_BOOTS, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_BOOTS = ItemDataTemplates.BOOTS
            .copyWithRecipeOverride(ItemDataTemplate.NONE)
            .addTag(ModTags.ODYSIUM_ARMORS).consumeSpecial("enhanced_odysium_boots", properties ->
                    new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, ArmorType.BOOTS, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_HORSE_ARMOR = ItemDataTemplates.HORSE_ARMOR
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(false)).consumeSpecialWithIngredients("odysium_horse_armor", properties ->
                    new ModArmorItem(ModArmorMaterials.ODYSIUM, Item.Properties::horseArmor, ODYSIUM.createArmorComponent(properties)), ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_HORSE_ARMOR, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HORSE_ARMOR = ItemDataTemplates.HORSE_ARMOR.consumeSpecial("enhanced_odysium_horse_armor", properties ->
            new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, Item.Properties::horseArmor, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static final RegistrySupplier<Item> ODYSIUM_NAUTILUS_ARMOR = ItemDataTemplates.NAUTILUS_ARMOR
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(false)).consumeSpecialWithIngredients("odysium_nautilus_armor", properties ->
                    new ModArmorItem(ModArmorMaterials.ODYSIUM, Item.Properties::nautilusArmor, ODYSIUM.createArmorComponent(properties)), ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_NAUTILUS_ARMOR, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_NAUTILUS_ARMOR = ItemDataTemplates.NAUTILUS_ARMOR.consumeSpecial("enhanced_odysium_nautilus_armor", properties ->
            new ModArmorItem(ModArmorMaterials.ENHANCED_ODYSIUM, Item.Properties::nautilusArmor, ENHANCED_ODYSIUM.createArmorComponent(properties)));

    public static void register() {

    }
}
