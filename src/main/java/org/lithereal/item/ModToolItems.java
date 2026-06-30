package org.lithereal.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.*;
import org.lithereal.item.ability.*;
import org.lithereal.item.burning.*;
import org.lithereal.item.datagen.ItemDataTemplate;
import org.lithereal.item.datagen.ItemDataTemplates;
import org.lithereal.item.infused.*;
import org.lithereal.tags.ModTags;

import static org.lithereal.item.ModItems.*;
import static org.lithereal.item.ModItems.ODYSIUM;
import static org.lithereal.item.ability.Ability.*;

public class ModToolItems {
    public static final RegistrySupplier<Item> LITHERITE_SWORD = ItemDataTemplates.SWORD.addTag(ModTags.LITHERITE_TOOLS).consume("litherite_sword", properties ->
            properties.sword(LITHERITE, 3, -2.4f), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SWORD = ItemDataTemplates.SWORD.addTag(ModTags.LITHERITE_TOOLS).consume("burning_litherite_sword", properties ->
            BURNING.createSwordComponent(LITHERITE, properties), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SWORD = ItemDataTemplates.SWORD.addTag(ModTags.LITHERITE_TOOLS).consume("frozen_litherite_sword", properties ->
            FROZEN.createSwordComponent(LITHERITE, properties), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_SWORD = ItemDataTemplates.SWORD
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTag(ModTags.LITHERITE_TOOLS).consume("smoldering_litherite_sword", properties -> 
                    SMOLDERING.createSwordComponent(LITHERITE, properties), BURNING_LITHERITE_SWORD);

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_SWORD = ItemDataTemplates.SWORD
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTag(ModTags.LITHERITE_TOOLS).consume("frostbitten_litherite_sword", properties -> 
                    FROSTBITTEN.createSwordComponent(LITHERITE, properties), FROZEN_LITHERITE_SWORD);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SWORD = ItemDataTemplates.SWORD
            .copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM)
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("infused_litherite_sword", properties -> 
                    new InfusedSwordItem(LITHERITE, properties), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SWORD = ItemDataTemplates.SWORD.addTag(ModTags.LITHERITE_TOOLS).consume("withering_litherite_sword", properties ->
            WITHERING.createSwordComponent(LITHERITE, properties), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> ODYSIUM_SWORD = ItemDataTemplates.SWORD
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(false))
            .addTag(ModTags.ODYSIUM_TOOLS).consumeWithIngredients("odysium_sword", properties -> 
                    Ability.ODYSIUM.createSwordComponent(ODYSIUM, properties), ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_SWORD, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_SWORD = ItemDataTemplates.SWORD
            .copyWithRecipeOverride(ItemDataTemplate.NONE)
            .addTag(ModTags.ODYSIUM_TOOLS).consume("enhanced_odysium_sword", properties -> 
                    Ability.ENHANCED_ODYSIUM.createSwordComponent(ODYSIUM, properties));

    public static final RegistrySupplier<Item> LITHERITE_PICKAXE = ItemDataTemplates.PICKAXE.addTag(ModTags.LITHERITE_TOOLS).consume("litherite_pickaxe", properties ->
            properties.pickaxe(LITHERITE, 1, -2.8F), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_PICKAXE = ItemDataTemplates.PICKAXE.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("burning_litherite_pickaxe", properties ->
            new BurningPickaxeItem(LITHERITE, properties), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_PICKAXE = ItemDataTemplates.PICKAXE.addTag(ModTags.LITHERITE_TOOLS).consume("frozen_litherite_pickaxe", properties -> 
            FROZEN.createPickaxeComponent(LITHERITE, properties), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_PICKAXE = ItemDataTemplates.PICKAXE
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.TOOLS))
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("smoldering_litherite_pickaxe", properties -> 
                    new BurningPickaxeItem(SMOLDERING, LITHERITE, properties), BURNING_LITHERITE_PICKAXE);

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_PICKAXE = ItemDataTemplates.PICKAXE
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.TOOLS))
            .addTag(ModTags.LITHERITE_TOOLS).consume("frostbitten_litherite_pickaxe", properties -> 
                    FROSTBITTEN.createPickaxeComponent(LITHERITE, properties), FROZEN_LITHERITE_PICKAXE);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_PICKAXE = ItemDataTemplates.PICKAXE
            .copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM)
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("infused_litherite_pickaxe", properties -> 
                    new InfusedPickaxeItem(LITHERITE, properties), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_PICKAXE = ItemDataTemplates.PICKAXE.addTag(ModTags.LITHERITE_TOOLS).consume("withering_litherite_pickaxe", properties ->
            WITHERING.createPickaxeComponent(LITHERITE, properties), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> ODYSIUM_PICKAXE = ItemDataTemplates.PICKAXE
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(true))
            .addTag(ModTags.ODYSIUM_TOOLS).consumeWithIngredients("odysium_pickaxe", properties -> 
                    Ability.ODYSIUM.createPickaxeComponent(ODYSIUM, properties), ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_PICKAXE, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_PICKAXE = ItemDataTemplates.PICKAXE
            .copyWithRecipeOverride(ItemDataTemplate.NONE)
            .addTag(ModTags.ODYSIUM_TOOLS).consume("enhanced_odysium_pickaxe", properties -> 
                    ENHANCED_ODYSIUM.createPickaxeComponent(ODYSIUM, properties));

    public static final RegistrySupplier<Item> LITHERITE_AXE = ItemDataTemplates.AXE.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("litherite_axe", properties ->
            new AxeItem(LITHERITE, 5, -3, properties), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_AXE = ItemDataTemplates.AXE.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("burning_litherite_axe", properties ->
            new BurningAxeItem(LITHERITE, properties), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_AXE = ItemDataTemplates.AXE.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("frozen_litherite_axe", properties ->
            new AxeItem(LITHERITE, 5, -3, FROZEN.createToolComponent(properties)), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_AXE = ItemDataTemplates.AXE
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.TOOLS))
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("smoldering_litherite_axe", properties -> 
                    new BurningAxeItem(SMOLDERING, LITHERITE, properties), BURNING_LITHERITE_AXE);

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_AXE = ItemDataTemplates.AXE
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.TOOLS))
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("frostbitten_litherite_axe", properties -> 
                    new AxeItem(LITHERITE, 5, -3, FROSTBITTEN.createToolComponent(properties)), FROZEN_LITHERITE_AXE);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_AXE = ItemDataTemplates.AXE
            .copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM)
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("infused_litherite_axe", properties -> 
                    new InfusedAxeItem(LITHERITE, properties), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_AXE = ItemDataTemplates.AXE.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("withering_litherite_axe", properties ->
            new AxeItem(LITHERITE, 5, -3, WITHERING.createToolComponent(properties)), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> ODYSIUM_AXE = ItemDataTemplates.AXE
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(true))
            .addTag(ModTags.ODYSIUM_TOOLS).consumeSpecialWithIngredients("odysium_axe", properties -> 
                    new AxeItem(ODYSIUM, 5, -3, Ability.ODYSIUM.createToolComponent(properties)), ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_AXE, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_AXE = ItemDataTemplates.AXE
            .copyWithRecipeOverride(ItemDataTemplate.NONE)
            .addTag(ModTags.ODYSIUM_TOOLS).consumeSpecial("enhanced_odysium_axe", properties -> 
                    new AxeItem(ODYSIUM, 5, -3, Ability.ENHANCED_ODYSIUM.createToolComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_SHOVEL = ItemDataTemplates.SHOVEL.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("litherite_shovel", properties ->
            new ShovelItem(LITHERITE, 1.5F, -3F, properties), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SHOVEL = ItemDataTemplates.SHOVEL.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("burning_litherite_shovel", properties ->
            new BurningShovelItem(LITHERITE, properties), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SHOVEL = ItemDataTemplates.SHOVEL.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("frozen_litherite_shovel", properties ->
            new ShovelItem(LITHERITE, 1.5F, -3F, FROZEN.createToolComponent(properties)), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_SHOVEL = ItemDataTemplates.SHOVEL
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.TOOLS))
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("smoldering_litherite_shovel", properties ->
                    new BurningShovelItem(SMOLDERING, LITHERITE, properties), BURNING_LITHERITE_SHOVEL);

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_SHOVEL = ItemDataTemplates.SHOVEL
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.TOOLS))
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("frostbitten_litherite_shovel", properties ->
                    new ShovelItem(LITHERITE, 1.5F, -3F, FROSTBITTEN.createToolComponent(properties)), FROZEN_LITHERITE_SHOVEL);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SHOVEL = ItemDataTemplates.SHOVEL
            .copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM)
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("infused_litherite_shovel", properties ->
                    new InfusedShovelItem(LITHERITE, properties), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SHOVEL = ItemDataTemplates.SHOVEL.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("withering_litherite_shovel", properties ->
            new ShovelItem(LITHERITE, 1.5F, -3F, WITHERING.createToolComponent(properties)), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> ODYSIUM_SHOVEL = ItemDataTemplates.SHOVEL
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(true))
            .addTag(ModTags.ODYSIUM_TOOLS).consumeSpecialWithIngredients("odysium_shovel", properties ->
                    new ShovelItem(ODYSIUM, 1.5F, -3F, Ability.ODYSIUM.createToolComponent(properties)), ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_SHOVEL, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_SHOVEL = ItemDataTemplates.SHOVEL
            .copyWithRecipeOverride(ItemDataTemplate.NONE)
            .addTag(ModTags.ODYSIUM_TOOLS).consumeSpecial("enhanced_odysium_shovel", properties ->
                    new ShovelItem(ODYSIUM, 1.5F, -3F, Ability.ENHANCED_ODYSIUM.createToolComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_HOE = ItemDataTemplates.HOE.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("litherite_hoe", properties ->
            new HoeItem(LITHERITE, -3, 0, properties), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HOE = ItemDataTemplates.HOE.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("burning_litherite_hoe", properties ->
            new BurningHoeItem(LITHERITE, properties), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HOE = ItemDataTemplates.HOE.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("frozen_litherite_hoe", properties ->
            new HoeItem(LITHERITE, -3, 0, FROZEN.createToolComponent(properties)), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HOE = ItemDataTemplates.HOE
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.TOOLS))
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("smoldering_litherite_hoe", properties ->
                    new BurningHoeItem(SMOLDERING, LITHERITE, properties), BURNING_LITHERITE_HOE);

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HOE = ItemDataTemplates.HOE
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.TOOLS))
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("frostbitten_litherite_hoe", properties ->
                    new HoeItem(LITHERITE, -3, 0, FROSTBITTEN.createToolComponent(properties)), FROZEN_LITHERITE_HOE);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HOE = ItemDataTemplates.HOE
            .copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM)
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("infused_litherite_hoe", properties ->
                    new InfusedHoeItem(LITHERITE, properties), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HOE = ItemDataTemplates.HOE.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("withering_litherite_hoe", properties ->
            new HoeItem(LITHERITE, -3, 0, WITHERING.createToolComponent(properties)), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> ODYSIUM_HOE = ItemDataTemplates.HOE
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(true))
            .addTag(ModTags.ODYSIUM_TOOLS).consumeSpecialWithIngredients("odysium_hoe", properties ->
                    new HoeItem(ODYSIUM, -5, 0, Ability.ODYSIUM.createToolComponent(properties)), ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_HOE, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HOE = ItemDataTemplates.HOE
            .copyWithRecipeOverride(ItemDataTemplate.NONE)
            .addTag(ModTags.ODYSIUM_TOOLS).consumeSpecial("enhanced_odysium_hoe", properties ->
                    new HoeItem(ODYSIUM, -5, 0, Ability.ENHANCED_ODYSIUM.createToolComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_HAMMER = ItemDataTemplates.HAMMER.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("litherite_hammer", properties ->
            new HammerItem(LITHERITE, 5, -3F, 3, properties), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_HAMMER = ItemDataTemplates.HAMMER.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("burning_litherite_hammer", properties ->
            new BurningHammerItem(LITHERITE, 5, -3F, 3, properties), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_HAMMER = ItemDataTemplates.HAMMER.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("frozen_litherite_hammer", properties ->
            new HammerItem(LITHERITE, 5, -3F, 3, FROZEN.createToolComponent(properties)), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_HAMMER = ItemDataTemplates.HAMMER
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.TOOLS))
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("smoldering_litherite_hammer", properties ->
                    new BurningHammerItem(SMOLDERING, LITHERITE, 5, -3F, 3, properties), BURNING_LITHERITE_HAMMER);

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_HAMMER = ItemDataTemplates.HAMMER
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.TOOLS))
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("frostbitten_litherite_hammer", properties ->
                    new HammerItem(LITHERITE, 5, -3F, 3, FROSTBITTEN.createToolComponent(properties)), FROZEN_LITHERITE_HAMMER);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_HAMMER = ItemDataTemplates.HAMMER
            .copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM)
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("infused_litherite_hammer", properties ->
                    new InfusedHammerItem(LITHERITE, 5, -3F, 3, properties), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_HAMMER = ItemDataTemplates.HAMMER.addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("withering_litherite_hammer", properties ->
            new HammerItem(LITHERITE, 5, -3F, 3, WITHERING.createToolComponent(properties)), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> ODYSIUM_HAMMER = ItemDataTemplates.HAMMER.addTag(ModTags.ODYSIUM_TOOLS).consumeSpecial("odysium_hammer", properties ->
            new HammerItem(ODYSIUM, 5, -3F, 5, Ability.ODYSIUM.createToolComponent(properties)), ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_HAMMER = ItemDataTemplates.HAMMER
            .copyWithRecipeOverride(ItemDataTemplate.NONE)
            .addTag(ModTags.ODYSIUM_TOOLS).consumeSpecial("enhanced_odysium_hammer", properties ->
                    new HammerItem(ODYSIUM, 5, -3F, 5, Ability.ENHANCED_ODYSIUM.createToolComponent(properties)));

    public static final RegistrySupplier<Item> LITHERITE_SPEAR = ItemDataTemplates.SPEAR.addTag(ModTags.LITHERITE_TOOLS).consume("litherite_spear", properties ->
            properties.spear(LITHERITE,
                    1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> BURNING_LITHERITE_SPEAR = ItemDataTemplates.SPEAR.addTag(ModTags.LITHERITE_TOOLS).consume("burning_litherite_spear", properties ->
            BURNING.createSpearComponent(properties, LITHERITE,
                    1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F), ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> FROZEN_LITHERITE_SPEAR = ItemDataTemplates.SPEAR.addTag(ModTags.LITHERITE_TOOLS).consume("frozen_litherite_spear", properties ->
            FROZEN.createSpearComponent(properties, LITHERITE,
                    1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F), ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> SMOLDERING_LITHERITE_SPEAR = ItemDataTemplates.SPEAR
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTag(ModTags.LITHERITE_TOOLS).consume("smoldering_litherite_spear", properties ->
                    SMOLDERING.createSpearComponent(properties, LITHERITE,
                            1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F), BURNING_LITHERITE_SPEAR);

    public static final RegistrySupplier<Item> FROSTBITTEN_LITHERITE_SPEAR = ItemDataTemplates.SPEAR
            .copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT))
            .addTag(ModTags.LITHERITE_TOOLS).consume("frostbitten_litherite_spear", properties ->
                    FROSTBITTEN.createSpearComponent(properties, LITHERITE,
                            1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F), FROZEN_LITHERITE_SPEAR);

    public static final RegistrySupplier<Item> INFUSED_LITHERITE_SPEAR = ItemDataTemplates.SPEAR
            .copyWithModelOverride(ItemDataTemplate.INFUSED_SPEAR_ITEM)
            .addTag(ModTags.LITHERITE_TOOLS).consumeSpecial("infused_litherite_spear", properties ->
                    new InfusedSpearItem(properties, LITHERITE,
                            1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F), ModRawMaterialItems.INFUSED_LITHERITE_INGOT);

    public static final RegistrySupplier<Item> WITHERING_LITHERITE_SPEAR = ItemDataTemplates.SPEAR.addTag(ModTags.LITHERITE_TOOLS).consume("withering_litherite_spear", properties ->
            WITHERING.createSpearComponent(properties, LITHERITE,
                    1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F), ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> ODYSIUM_SPEAR = ItemDataTemplates.SPEAR
            .copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(false))
            .addTag(ModTags.ODYSIUM_TOOLS).consumeWithIngredients("odysium_spear", properties ->
                    Ability.ODYSIUM.createSpearComponent(properties, ODYSIUM,
                            1.25F, 1.45F, 0.35F, 2.0F, 8.0F, 5F, 4.6F, 7.5F, 4.1F), ODYSIUM_UPGRADE_SMITHING_TEMPLATE, () -> Items.NETHERITE_SPEAR, ModRawMaterialItems.ODYSIUM_INGOT);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_SPEAR = ItemDataTemplates.SPEAR
            .copyWithRecipeOverride(ItemDataTemplate.NONE)
            .addTag(ModTags.ODYSIUM_TOOLS).consume("enhanced_odysium_spear", properties ->
                    ENHANCED_ODYSIUM.createSpearComponent(properties, ODYSIUM,
                            1.25F, 1.45F, 0.35F, 2.0F, 8.0F, 5F, 4.6F, 7.5F, 4.1F));

    public static final RegistrySupplier<Item> ODYSIUM_BOW = ItemDataTemplates.BOW.consumeSpecialWithIngredients("odysium_bow", properties ->
            new ModBowItem(ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON), 2, 0.2F, 1), ModRawMaterialItems.ODYSIUM_INGOT, () -> Items.IRON_NUGGET, () -> Items.STRING);

    public static final RegistrySupplier<Item> ENHANCED_ODYSIUM_BOW = ItemDataTemplates.BOW
            .copyWithRecipeOverride(ItemDataTemplate.NONE).consumeSpecial("enhanced_odysium_bow", properties -> 
                    new ModBowItem(ODYSIUM, properties.fireResistant().rarity(Rarity.UNCOMMON), 3, 0.1F, 1.5F));

    public static final RegistrySupplier<Item> WAR_HAMMER = ItemDataTemplates.WAR_HAMMER.consumeSpecialWithIngredients("war_hammer", properties ->
            new WarHammerItem(ODYSIUM, 1, -3.4F, properties.durability(500).fireResistant().rarity(Rarity.EPIC)), () -> Items.HEAVY_CORE, MYSTERIOUS_ROD);

    public static final RegistrySupplier<Item> LITHERITE_BRUSH = ItemDataTemplates.BRUSH.consumeSpecial("litherite_brush", properties ->
            new LitheriteBrushItem(properties.durability(256)), ModRawMaterialItems.LITHERITE_CRYSTAL);

    public static final RegistrySupplier<Item> LITHERITE_WRENCH = ItemDataTemplates.WRENCH.consumeSpecialWithIngredients("litherite_wrench", properties ->
            new WrenchItem(properties.stacksTo(1).durability(128)), ModRawMaterialItems.LITHERITE_CRYSTAL, () -> Items.IRON_INGOT);

    public static void register() {

    }
}
