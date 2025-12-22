package org.lithereal.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.ModOreBlocks;
import org.lithereal.block.ModStoneBlocks;
import org.lithereal.block.ModTreeBlocks;
import org.lithereal.item.ModArmorItems;
import org.lithereal.item.ModItems;
import org.lithereal.item.ModRawMaterialItems;
import org.lithereal.item.ModToolItems;
import org.lithereal.neoforge.world.block.ForgeBlocks;
import org.lithereal.neoforge.world.item.ForgeItems;
import org.lithereal.tags.ModTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public static final BlockFamily PHANTOM_OAK_PLANKS = new BlockFamily.Builder(ModTreeBlocks.PHANTOM_OAK_PLANKS.get())
            .button(ModTreeBlocks.PHANTOM_OAK_BUTTON.get())
            .fence(ModTreeBlocks.PHANTOM_OAK_FENCE.get())
            .fenceGate(ModTreeBlocks.PHANTOM_OAK_FENCE_GATE.get())
            .pressurePlate(ModTreeBlocks.PHANTOM_OAK_PRESSURE_PLATE.get())
            .sign(ModTreeBlocks.PHANTOM_OAK_SIGN.get(), ModTreeBlocks.PHANTOM_OAK_WALL_SIGN.get())
            .slab(ModTreeBlocks.PHANTOM_OAK_SLAB.get())
            .stairs(ModTreeBlocks.PHANTOM_OAK_STAIRS.get())
            .door(ModTreeBlocks.PHANTOM_OAK_DOOR.get())
            .trapdoor(ModTreeBlocks.PHANTOM_OAK_TRAPDOOR.get())
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();
    public static final BlockFamily PAILITE = new BlockFamily.Builder(ModStoneBlocks.PAILITE.get())
            .wall(ModStoneBlocks.PAILITE_WALL.get())
            .stairs(ModStoneBlocks.PAILITE_STAIRS.get())
            .slab(ModStoneBlocks.PAILITE_SLAB.get())
            .polished(ModStoneBlocks.POLISHED_PAILITE.get())
            .getFamily();
    public static final BlockFamily POLISHED_PAILITE = new BlockFamily.Builder(ModStoneBlocks.POLISHED_PAILITE.get())
            .wall(ModStoneBlocks.POLISHED_PAILITE_WALL.get())
            .stairs(ModStoneBlocks.POLISHED_PAILITE_STAIRS.get())
            .slab(ModStoneBlocks.POLISHED_PAILITE_SLAB.get())
            .getFamily();
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PASSIVE_ETHER_ABSORBER.get(), 1)
                .define('C', ModRawMaterialItems.CHRYON_CRYSTAL.get())
                .define('A', ModRawMaterialItems.ALLIAN_INGOT.get())
                .define('N', ModRawMaterialItems.NERITH_INGOT.get())
                .pattern("ACA")
                .pattern("N N")
                .pattern("ACA")
                .unlockedBy("has_allian_ingot", has(ModRawMaterialItems.ALLIAN_INGOT.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ForgeBlocks.ELECTRIC_CRUCIBLE.get(), 1)
                .define('S', ModRawMaterialItems.AURELITE_DUST.get())
                .define('B', ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get())
                .define('C', ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .define('#', Tags.Items.STORAGE_BLOCKS_IRON)
                .pattern("ISI")
                .pattern("IBI")
                .pattern("#C#")
                .unlockedBy("has_charged_litherite_crystal", has(ModRawMaterialItems.CHARGED_LITHERITE_CRYSTAL.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ETHEREAL_CRYSTAL_BLOCK.get(), 1)
                .define('C', ModRawMaterialItems.IMPURE_ETHEREAL_CRYSTAL_SHARD.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_impure_ethereal_crystal", has(ModRawMaterialItems.IMPURE_ETHEREAL_CRYSTAL_SHARD.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModToolItems.ODYSIUM_BOW.get(), 1)
                .define('O', ModRawMaterialItems.ODYSIUM_INGOT.get())
                .define('I', Items.IRON_NUGGET)
                .define('S', Items.STRING)
                .pattern(" OI")
                .pattern("O S")
                .pattern(" OI")
                .unlockedBy("has_odysium", has(ModRawMaterialItems.ODYSIUM_INGOT.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModRawMaterialItems.UNIFIER.get(), 1)
                .define('A', ModRawMaterialItems.ALLIAN_INGOT.get())
                .define('N', Items.NETHER_STAR)
                .define('B', ModItems.BOSS_ESSENCE.get())
                .pattern("ABA")
                .pattern("ANA")
                .pattern(" A ")
                .unlockedBy("has_boss_essence", has(ModItems.BOSS_ESSENCE.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MYSTERIOUS_ROD.get(), 8)
                .define('O', ModRawMaterialItems.ODYSIUM_INGOT.get())
                .pattern("O")
                .pattern("O")
                .unlockedBy("has_odysium", has(ModRawMaterialItems.ODYSIUM_INGOT.get()))
                .save(recipeOutput);
        improvedThermalItem(recipeOutput, RecipeCategory.COMBAT, ModToolItems.BURNING_LITHERITE_SWORD.get(), ModToolItems.SMOLDERING_LITHERITE_SWORD.get());
        improvedThermalItem(recipeOutput, RecipeCategory.TOOLS, ModToolItems.BURNING_LITHERITE_PICKAXE.get(), ModToolItems.SMOLDERING_LITHERITE_PICKAXE.get());
        improvedThermalItem(recipeOutput, RecipeCategory.TOOLS, ModToolItems.BURNING_LITHERITE_AXE.get(), ModToolItems.SMOLDERING_LITHERITE_AXE.get());
        improvedThermalItem(recipeOutput, RecipeCategory.TOOLS, ModToolItems.BURNING_LITHERITE_SHOVEL.get(), ModToolItems.SMOLDERING_LITHERITE_SHOVEL.get());
        improvedThermalItem(recipeOutput, RecipeCategory.TOOLS, ModToolItems.BURNING_LITHERITE_HOE.get(), ModToolItems.SMOLDERING_LITHERITE_HOE.get());
        improvedThermalItem(recipeOutput, RecipeCategory.TOOLS, ModToolItems.BURNING_LITHERITE_HAMMER.get(), ModToolItems.SMOLDERING_LITHERITE_HAMMER.get());
        improvedThermalItem(recipeOutput, RecipeCategory.COMBAT, ModArmorItems.BURNING_LITHERITE_HELMET.get(), ModArmorItems.SMOLDERING_LITHERITE_HELMET.get());
        improvedThermalItem(recipeOutput, RecipeCategory.COMBAT, ModArmorItems.BURNING_LITHERITE_CHESTPLATE.get(), ModArmorItems.SMOLDERING_LITHERITE_CHESTPLATE.get());
        improvedThermalItem(recipeOutput, RecipeCategory.COMBAT, ModArmorItems.BURNING_LITHERITE_LEGGINGS.get(), ModArmorItems.SMOLDERING_LITHERITE_LEGGINGS.get());
        improvedThermalItem(recipeOutput, RecipeCategory.COMBAT, ModArmorItems.BURNING_LITHERITE_BOOTS.get(), ModArmorItems.SMOLDERING_LITHERITE_BOOTS.get());
        improvedThermalItem(recipeOutput, RecipeCategory.COMBAT, ModToolItems.FROZEN_LITHERITE_SWORD.get(), ModToolItems.FROSTBITTEN_LITHERITE_SWORD.get());
        improvedThermalItem(recipeOutput, RecipeCategory.TOOLS, ModToolItems.FROZEN_LITHERITE_PICKAXE.get(), ModToolItems.FROSTBITTEN_LITHERITE_PICKAXE.get());
        improvedThermalItem(recipeOutput, RecipeCategory.TOOLS, ModToolItems.FROZEN_LITHERITE_AXE.get(), ModToolItems.FROSTBITTEN_LITHERITE_AXE.get());
        improvedThermalItem(recipeOutput, RecipeCategory.TOOLS, ModToolItems.FROZEN_LITHERITE_SHOVEL.get(), ModToolItems.FROSTBITTEN_LITHERITE_SHOVEL.get());
        improvedThermalItem(recipeOutput, RecipeCategory.TOOLS, ModToolItems.FROZEN_LITHERITE_HOE.get(), ModToolItems.FROSTBITTEN_LITHERITE_HOE.get());
        improvedThermalItem(recipeOutput, RecipeCategory.TOOLS, ModToolItems.FROZEN_LITHERITE_HAMMER.get(), ModToolItems.FROSTBITTEN_LITHERITE_HAMMER.get());
        improvedThermalItem(recipeOutput, RecipeCategory.COMBAT, ModArmorItems.FROZEN_LITHERITE_HELMET.get(), ModArmorItems.FROSTBITTEN_LITHERITE_HELMET.get());
        improvedThermalItem(recipeOutput, RecipeCategory.COMBAT, ModArmorItems.FROZEN_LITHERITE_CHESTPLATE.get(), ModArmorItems.FROSTBITTEN_LITHERITE_CHESTPLATE.get());
        improvedThermalItem(recipeOutput, RecipeCategory.COMBAT, ModArmorItems.FROZEN_LITHERITE_LEGGINGS.get(), ModArmorItems.FROSTBITTEN_LITHERITE_LEGGINGS.get());
        improvedThermalItem(recipeOutput, RecipeCategory.COMBAT, ModArmorItems.FROZEN_LITHERITE_BOOTS.get(), ModArmorItems.FROSTBITTEN_LITHERITE_BOOTS.get());
        swordItem(recipeOutput, Items.STICK, ForgeItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_SWORD.get());
        swordItem(recipeOutput, Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_SWORD.get());
        swordItem(recipeOutput, Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_SWORD.get());
        swordItem(recipeOutput, Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_SWORD.get());
        swordItem(recipeOutput, Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_SWORD.get());
        upgradeRecipe(recipeOutput, false, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_SWORD, ModToolItems.ODYSIUM_SWORD.get());
        pickaxeItem(recipeOutput, Items.STICK, ForgeItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_PICKAXE.get());
        pickaxeItem(recipeOutput, Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_PICKAXE.get());
        pickaxeItem(recipeOutput, Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_PICKAXE.get());
        pickaxeItem(recipeOutput, Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_PICKAXE.get());
        pickaxeItem(recipeOutput, Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_PICKAXE.get());
        upgradeRecipe(recipeOutput, true, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_PICKAXE, ModToolItems.ODYSIUM_PICKAXE.get());
        axeItem(recipeOutput, Items.STICK, ForgeItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_AXE.get());
        axeItem(recipeOutput, Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_AXE.get());
        axeItem(recipeOutput, Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_AXE.get());
        axeItem(recipeOutput, Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_AXE.get());
        axeItem(recipeOutput, Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_AXE.get());
        upgradeRecipe(recipeOutput, true, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_AXE, ModToolItems.ODYSIUM_AXE.get());
        shovelItem(recipeOutput, Items.STICK, ForgeItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_SHOVEL.get());
        shovelItem(recipeOutput, Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_SHOVEL.get());
        shovelItem(recipeOutput, Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_SHOVEL.get());
        shovelItem(recipeOutput, Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_SHOVEL.get());
        shovelItem(recipeOutput, Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_SHOVEL.get());
        upgradeRecipe(recipeOutput, true, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_SHOVEL, ModToolItems.ODYSIUM_SHOVEL.get());
        hoeItem(recipeOutput, Items.STICK, ForgeItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_HOE.get());
        hoeItem(recipeOutput, Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_HOE.get());
        hoeItem(recipeOutput, Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_HOE.get());
        hoeItem(recipeOutput, Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_HOE.get());
        hoeItem(recipeOutput, Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_HOE.get());
        upgradeRecipe(recipeOutput, true, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_HOE, ModToolItems.ODYSIUM_HOE.get());
        hammerItem(recipeOutput, Items.STICK, ForgeItems.LITHERITE_CRYSTAL.get(), ModToolItems.LITHERITE_HAMMER.get());
        hammerItem(recipeOutput, Items.STICK, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModToolItems.BURNING_LITHERITE_HAMMER.get());
        hammerItem(recipeOutput, Items.STICK, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModToolItems.FROZEN_LITHERITE_HAMMER.get());
        hammerItem(recipeOutput, Items.STICK, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModToolItems.INFUSED_LITHERITE_HAMMER.get());
        hammerItem(recipeOutput, Items.STICK, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModToolItems.WITHERING_LITHERITE_HAMMER.get());
        hammerItem(recipeOutput, Items.STICK, ModRawMaterialItems.ODYSIUM_INGOT.get(), ModToolItems.ODYSIUM_HAMMER.get());
        helmetItem(recipeOutput, ForgeItems.LITHERITE_CRYSTAL.get(), ModArmorItems.LITHERITE_HELMET.get());
        helmetItem(recipeOutput, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModArmorItems.BURNING_LITHERITE_HELMET.get());
        helmetItem(recipeOutput, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModArmorItems.FROZEN_LITHERITE_HELMET.get());
        helmetItem(recipeOutput, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModArmorItems.INFUSED_LITHERITE_HELMET.get());
        helmetItem(recipeOutput, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModArmorItems.WITHERING_LITHERITE_HELMET.get());
        upgradeRecipe(recipeOutput, false, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_HELMET, ModArmorItems.ODYSIUM_HELMET.get());
        chestplateItem(recipeOutput, ForgeItems.LITHERITE_CRYSTAL.get(), ModArmorItems.LITHERITE_CHESTPLATE.get());
        chestplateItem(recipeOutput, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModArmorItems.BURNING_LITHERITE_CHESTPLATE.get());
        chestplateItem(recipeOutput, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModArmorItems.FROZEN_LITHERITE_CHESTPLATE.get());
        chestplateItem(recipeOutput, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModArmorItems.INFUSED_LITHERITE_CHESTPLATE.get());
        chestplateItem(recipeOutput, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModArmorItems.WITHERING_LITHERITE_CHESTPLATE.get());
        upgradeRecipe(recipeOutput, false, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_CHESTPLATE, ModArmorItems.ODYSIUM_CHESTPLATE.get());
        leggingsItem(recipeOutput, ForgeItems.LITHERITE_CRYSTAL.get(), ModArmorItems.LITHERITE_LEGGINGS.get());
        leggingsItem(recipeOutput, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModArmorItems.BURNING_LITHERITE_LEGGINGS.get());
        leggingsItem(recipeOutput, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModArmorItems.FROZEN_LITHERITE_LEGGINGS.get());
        leggingsItem(recipeOutput, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModArmorItems.INFUSED_LITHERITE_LEGGINGS.get());
        leggingsItem(recipeOutput, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModArmorItems.WITHERING_LITHERITE_LEGGINGS.get());
        upgradeRecipe(recipeOutput, false, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_LEGGINGS, ModArmorItems.ODYSIUM_LEGGINGS.get());
        bootsItem(recipeOutput, ForgeItems.LITHERITE_CRYSTAL.get(), ModArmorItems.LITHERITE_BOOTS.get());
        bootsItem(recipeOutput, ModRawMaterialItems.BURNING_LITHERITE_CRYSTAL.get(), ModArmorItems.BURNING_LITHERITE_BOOTS.get());
        bootsItem(recipeOutput, ModRawMaterialItems.FROZEN_LITHERITE_CRYSTAL.get(), ModArmorItems.FROZEN_LITHERITE_BOOTS.get());
        bootsItem(recipeOutput, ModRawMaterialItems.INFUSED_LITHERITE_INGOT.get(), ModArmorItems.INFUSED_LITHERITE_BOOTS.get());
        bootsItem(recipeOutput, ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), ModArmorItems.WITHERING_LITHERITE_BOOTS.get());
        upgradeRecipe(recipeOutput, false, ModItems.ODYSIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModRawMaterialItems.ODYSIUM_INGOT.get(), Items.NETHERITE_BOOTS, ModArmorItems.ODYSIUM_BOOTS.get());

        oreDual(recipeOutput, List.of(ModOreBlocks.LITHERITE_ORE.get(), ModOreBlocks.DEEPSLATE_LITHERITE_ORE.get(), ModOreBlocks.ETHERSTONE_LITHERITE_ORE.get()), RecipeCategory.MISC, ForgeItems.LITHERITE_CRYSTAL.get(), 1, 200, "litherite_crystal");
        oreDual(recipeOutput, List.of(ModOreBlocks.NERITH_ORE.get(), ModOreBlocks.DEEPSLATE_NERITH_ORE.get(), ModOreBlocks.ETHERSTONE_NERITH_ORE.get(), ModRawMaterialItems.RAW_NERITH.get()), RecipeCategory.MISC, ModRawMaterialItems.NERITH_INGOT.get(), 2, 300, "nerith_ingot");
        oreDual(recipeOutput, List.of(ModOreBlocks.LUMINIUM_ORE.get(), ModOreBlocks.DEEPSLATE_LUMINIUM_ORE.get(), ModOreBlocks.ETHERSTONE_LUMINIUM_ORE.get()), RecipeCategory.MISC, ModRawMaterialItems.LUMINIUM_CRYSTAL.get(), 0.6F, 200, "luminium_crystal");
        oreDual(recipeOutput, List.of(ModOreBlocks.CYRUM_ORE.get(), ModOreBlocks.DEEPSLATE_CYRUM_ORE.get(), ModOreBlocks.ETHERSTONE_CYRUM_ORE.get()), RecipeCategory.MISC, ModRawMaterialItems.CYRUM_CRYSTAL.get(), 0.1F, 200, "cyrum_crystal");
        oreDual(recipeOutput, List.of(ModOreBlocks.COPALITE_ORE.get(), ModOreBlocks.DEEPSLATE_COPALITE_ORE.get(), ModOreBlocks.ETHERSTONE_COPALITE_ORE.get(), ModRawMaterialItems.COPALITE_DUST.get()), RecipeCategory.MISC, ModRawMaterialItems.COPALITE_INGOT.get(), 0.5F, 200, "copalite_ingot");
        oreDual(recipeOutput, List.of(ModOreBlocks.AURELITE_ORE.get(), ModOreBlocks.DEEPSLATE_AURELITE_ORE.get(), ModOreBlocks.ETHERSTONE_AURELITE_ORE.get(), ModRawMaterialItems.AURELITE_DUST.get()), RecipeCategory.MISC, ModRawMaterialItems.AURELITE_INGOT.get(), 0.5F, 200, "aurelite_ingot");
        oreDual(recipeOutput, List.of(ModOreBlocks.SATURNITE_ORE.get()), RecipeCategory.MISC, ModRawMaterialItems.SATURNITE_CRYSTAL.get(), 0.7F, 200, "saturnite_crystal");
        oreDual(recipeOutput, List.of(ModOreBlocks.HELLIONITE_ORE.get()), RecipeCategory.MISC, ModRawMaterialItems.HELLIONITE_CRYSTAL.get(), 0.8F, 200, "hellionite_crystal");
        oreDual(recipeOutput, List.of(ModOreBlocks.ELUNITE_ORE.get()), RecipeCategory.MISC, ModRawMaterialItems.ELUNITE_CRYSTAL.get(), 1, 200, "elunite_crystal");
        oreDual(recipeOutput, List.of(ModOreBlocks.CHRYON_ORE.get()), RecipeCategory.MISC, ModRawMaterialItems.CHRYON_CRYSTAL.get(), 1.4F, 200, "chryon_crystal");
        oreDual(recipeOutput, List.of(ModOreBlocks.ALLIAN_ORE.get(), ModRawMaterialItems.RAW_ALLIUM.get()), RecipeCategory.MISC, ModRawMaterialItems.ALLIAN_INGOT.get(), 2, 300, "allian_ingot");

        generateRecipes(recipeOutput, PAILITE, FeatureFlagSet.of(FeatureFlags.VANILLA));
        generateRecipes(recipeOutput, POLISHED_PAILITE, FeatureFlagSet.of(FeatureFlags.VANILLA));
        generateRecipes(recipeOutput, PHANTOM_OAK_PLANKS, FeatureFlagSet.of(FeatureFlags.VANILLA));
        planksFromLog(recipeOutput, ModTreeBlocks.PHANTOM_OAK_PLANKS.get(), ModTags.PHANTOM_OAK_LOGS, 4);
        woodFromLogs(recipeOutput, ModTreeBlocks.PHANTOM_OAK_WOOD.get(), ModTreeBlocks.PHANTOM_OAK_LOG.get());
        woodFromLogs(recipeOutput, ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
        woodenBoat(recipeOutput, ModItems.PHANTOM_OAK_BOAT.get(), ModTreeBlocks.PHANTOM_OAK_PLANKS.get());
        chestBoat(recipeOutput, ModItems.PHANTOM_OAK_CHEST_BOAT.get(), ModItems.PHANTOM_OAK_BOAT.get());
        hangingSign(recipeOutput, ModItems.PHANTOM_OAK_HANGING_SIGN.get(), ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.PAILITE.get(), 2)
                .define('C', ModRawMaterialItems.CYRUM_CRYSTAL.get())
                .define('E', ModStoneBlocks.ETHERSTONE.get())
                .pattern("EC")
                .pattern("CE")
                .unlockedBy("has_etherstone", has(ModStoneBlocks.ETHERSTONE.get()))
                .save(recipeOutput);
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.PAILITE_SLAB.get(), ModStoneBlocks.PAILITE.get(), 2);
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.PAILITE_STAIRS.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.DECORATIONS, ModStoneBlocks.PAILITE_WALL.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), ModStoneBlocks.PAILITE.get(), 2);
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_STAIRS.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_PAILITE_WALL.get(), ModStoneBlocks.PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), ModStoneBlocks.POLISHED_PAILITE.get(), 2);
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModStoneBlocks.POLISHED_PAILITE_STAIRS.get(), ModStoneBlocks.POLISHED_PAILITE.get());
        stonecutterResultFromBaseModNamespace(recipeOutput, RecipeCategory.DECORATIONS, ModStoneBlocks.POLISHED_PAILITE_WALL.get(), ModStoneBlocks.POLISHED_PAILITE.get());
    }

    protected static void oreSmeltingFromBaseModNamespace(RecipeOutput arg, List<ItemLike> list, RecipeCategory arg2, ItemLike arg3, float f, int i, String string) {
        oreCookingFromBaseModNamespace(arg, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, list, arg2, arg3, f, i, string, "_from_smelting");
    }

    protected static void oreBlastingFromBaseModNamespace(RecipeOutput arg, List<ItemLike> list, RecipeCategory arg2, ItemLike arg3, float f, int i, String string) {
        oreCookingFromBaseModNamespace(arg, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, list, arg2, arg3, f, i, string, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCookingFromBaseModNamespace(
            RecipeOutput arg,
            RecipeSerializer<T> arg2,
            AbstractCookingRecipe.Factory<T> arg3,
            List<ItemLike> list,
            RecipeCategory arg4,
            ItemLike arg5,
            float f,
            int i,
            String string,
            String string2
    ) {
        for (ItemLike itemlike : list) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), arg4, arg5, f, i, arg2, arg3)
                    .group(string)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(arg, "lithereal:" + getItemName(arg5) + string2 + "_" + getItemName(itemlike));
        }
    }

    public static void oreDual(RecipeOutput recipeOutput, List<ItemLike> list, RecipeCategory recipeCategory, ItemLike itemLike, float f, int i, String string) {
        oreSmeltingFromBaseModNamespace(recipeOutput, list, recipeCategory, itemLike, f, i, string);
        oreBlastingFromBaseModNamespace(recipeOutput, list, recipeCategory, itemLike, f, i, string);
    }

    protected static void stonecutterResultFromBaseModNamespace(RecipeOutput recipeOutput, RecipeCategory category, ItemLike to, ItemLike from) {
        stonecutterResultFromBaseModNamespace(recipeOutput, category, to, from, 1);
    }

    protected static void stonecutterResultFromBaseModNamespace(RecipeOutput recipeOutput, RecipeCategory category, ItemLike to, ItemLike from, int count) {
        SingleItemRecipeBuilder recipeBuilder = SingleItemRecipeBuilder.stonecutting(Ingredient.of(from), category, to, count).unlockedBy(getHasName(from), has(from));
        String convertName = getConversionRecipeName(to, from);
        recipeBuilder.save(recipeOutput, "lithereal:" + convertName + "_stonecutting");
    }

    protected static void improvedThermalItem(RecipeOutput recipeOutput, RecipeCategory recipeCategory, Item base, Item improved) {
        ShapedRecipeBuilder.shaped(recipeCategory, improved, 1)
                .define('A', ModRawMaterialItems.AURELITE_DUST.get())
                .define('B', base)
                .define('C', ModRawMaterialItems.CHRYON_CRYSTAL.get())
                .pattern(" A ")
                .pattern("CBC")
                .pattern(" A ")
                .unlockedBy("has_aurelite_dust", has(ModRawMaterialItems.AURELITE_DUST.get()))
                .save(recipeOutput);
    }

    protected static void swordItem(RecipeOutput recipeOutput, Item rod, Item baseMaterial, Item sword) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("B")
                .pattern("B")
                .pattern("R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(recipeOutput);
    }

    protected static void pickaxeItem(RecipeOutput recipeOutput, Item rod, Item baseMaterial, Item pickaxe) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BBB")
                .pattern(" R ")
                .pattern(" R ")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(recipeOutput);
    }

    protected static void axeItem(RecipeOutput recipeOutput, Item rod, Item baseMaterial, Item axe) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BB")
                .pattern("BR")
                .pattern(" R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(recipeOutput);
    }

    protected static void shovelItem(RecipeOutput recipeOutput, Item rod, Item baseMaterial, Item shovel) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("B")
                .pattern("R")
                .pattern("R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(recipeOutput);
    }

    protected static void hoeItem(RecipeOutput recipeOutput, Item rod, Item baseMaterial, Item hoe) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BB")
                .pattern(" R")
                .pattern(" R")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(recipeOutput);
    }

    protected static void hammerItem(RecipeOutput recipeOutput, Item rod, Item baseMaterial, Item hammer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hammer, 1)
                .define('B', baseMaterial)
                .define('R', rod)
                .pattern("BBB")
                .pattern("BBB")
                .pattern(" R ")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(recipeOutput);
    }

    protected static void helmetItem(RecipeOutput recipeOutput, Item baseMaterial, Item helmet) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet, 1)
                .define('B', baseMaterial)
                .pattern("BBB")
                .pattern("B B")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(recipeOutput);
    }

    protected static void chestplateItem(RecipeOutput recipeOutput, Item baseMaterial, Item chestplate) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate, 1)
                .define('B', baseMaterial)
                .pattern("B B")
                .pattern("BBB")
                .pattern("BBB")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(recipeOutput);
    }

    protected static void leggingsItem(RecipeOutput recipeOutput, Item baseMaterial, Item leggings) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings, 1)
                .define('B', baseMaterial)
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(recipeOutput);
    }

    protected static void bootsItem(RecipeOutput recipeOutput, Item baseMaterial, Item boots) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots, 1)
                .define('B', baseMaterial)
                .pattern("B B")
                .pattern("B B")
                .unlockedBy("has_" + getSimpleRecipeName(baseMaterial), has(baseMaterial))
                .save(recipeOutput);
    }

    protected static void upgradeRecipe(RecipeOutput recipeOutput, boolean tool, Item template, Item base, Item addition, Item result) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(template), Ingredient.of(base), Ingredient.of(addition), tool ? RecipeCategory.TOOLS : RecipeCategory.COMBAT, result)
                .unlocks("has_" + getSimpleRecipeName(base), has(base))
                .save(recipeOutput, "lithereal:" + getItemName(result) + "_smithing");
    }
}