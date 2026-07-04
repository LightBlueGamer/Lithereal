package org.lithereal.item;

import com.mojang.datafixers.util.Either;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.Optionull;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import org.jspecify.annotations.Nullable;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.base.ModArmorItem;
import org.lithereal.item.datagen.ItemDataTemplate;
import org.lithereal.item.datagen.ItemDataTemplates;
import org.lithereal.item.infused.*;
import org.lithereal.util.CommonUtils;

import java.util.*;
import java.util.function.*;

public record EquipmentSet(Map<Type, Supplier<? extends Item>> items, List<Type> tabFlags, Map<Type, UnaryOperator<Item.Properties>> propertiesAlterations, Either<Supplier<? extends Item>, TagKey<Item>> baseMaterial, String prefix, int weaponLevel, boolean infused) {
    public EquipmentSet(Map<Type, UnaryOperator<Item.Properties>> propertiesAlterations, Supplier<? extends Item> baseMaterial, String prefix, int weaponLevel, boolean infused) {
        this(new HashMap<>(), new ArrayList<>(), propertiesAlterations, Either.left(baseMaterial), prefix, weaponLevel, infused);
    }

    public EquipmentSet(Map<Type, UnaryOperator<Item.Properties>> propertiesAlterations, TagKey<Item> baseMaterial, String prefix, int weaponLevel, boolean infused) {
        this(new HashMap<>(), new ArrayList<>(), propertiesAlterations, Either.right(baseMaterial), prefix, weaponLevel, infused);
    }

    public static EquipmentSet createVanilla(Map<Type, Supplier<? extends Item>> items, Map<Type, UnaryOperator<Item.Properties>> propertiesAlterations, Supplier<? extends Item> baseMaterial, String prefix, int weaponLevel) {
        return new EquipmentSet(items, new ArrayList<>(), propertiesAlterations, Either.left(baseMaterial), prefix, weaponLevel, false);
    }

    public static EquipmentSet createVanilla(Map<Type, Supplier<? extends Item>> items, Map<Type, UnaryOperator<Item.Properties>> propertiesAlterations, TagKey<Item> baseMaterial, String prefix, int weaponLevel) {
        return new EquipmentSet(items, new ArrayList<>(), propertiesAlterations, Either.right(baseMaterial), prefix, weaponLevel, false);
    }

    public Supplier<? extends Item> get(Type type) {
        return this.items.get(type);
    }

    public void populateCreativeTab(RegistrySupplier<CreativeModeTab> tab, boolean combat, boolean tool) {
        this.tabFlags.stream().sorted(Comparator.comparing(type -> type.armor ? 13 + type.ordinal() : type.ordinal(), Comparator.naturalOrder())).forEach(type -> {
            if (items.containsKey(type) && ((combat && type.armor) || (combat && type.weapon) || (tool && type.tool))) CreativeTabRegistry.append(tab, items.get(type).get());
        });
    }

    private void put(Type type, RegistrySupplier<? extends Item> item) {
        this.items.put(type, item);
        this.tabFlags.add(type);
    }

    public void registerArmorModel(Consumer<Holder<Item>> registerer) {
        registerer.accept(items.get(Type.HELMET).get().builtInRegistryHolder());
        registerer.accept(items.get(Type.CHESTPLATE).get().builtInRegistryHolder());
        registerer.accept(items.get(Type.LEGGINGS).get().builtInRegistryHolder());
        registerer.accept(items.get(Type.BOOTS).get().builtInRegistryHolder());
    }

    public Item.Properties applyPropertiesForItem(Type type, @Nullable Ability ability, Function<Ability, Item.Properties> abilityPresent, Supplier<Item.Properties> otherwise) {
        return propertiesAlterations().getOrDefault(type, UnaryOperator.identity()).apply(Optionull.mapOrElse(ability, abilityPresent, otherwise));
    }

    private void populateArmorItem(Type type, @Nullable Ability ability, ArmorMaterial armorMaterial, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>> creationFunction) {
        this.put(type, creationFunction.apply(prefix + "_" + type.suffix(), properties ->
                createArmorItem(armorMaterial, type.armorPropertiesFunction(), applyPropertiesForItem(type, ability, ability1 -> ability1.createArmorComponent(properties), () -> properties))));
    }

    private ModArmorItem createArmorItem(ArmorMaterial armorMaterial, BiFunction<Item.Properties, ArmorMaterial, Item.Properties> toMakeArmor, Item.Properties properties) {
        return !this.infused ? new ModArmorItem(armorMaterial, toMakeArmor, properties) : new InfusedArmorItem(armorMaterial, toMakeArmor, properties);
    }

    private void populateSwordItem(@Nullable Ability ability, ToolMaterial toolMaterial, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>> creationFunction) {
        this.put(Type.SWORD, creationFunction.apply(prefix + "_sword", properties ->
                createSwordItem(applyPropertiesForItem(Type.SWORD, ability, ability1 -> ability1.createSwordComponent(toolMaterial, properties), () -> properties.sword(toolMaterial, 3, -2.4F)))));
    }

    private Item createSwordItem(Item.Properties properties) {
        return !this.infused ? new Item(properties) : new InfusedSwordItem(properties);
    }

    private void populateSpearItem(@Nullable Ability ability, ToolMaterial toolMaterial, SpearData spearData, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>> creationFunction) {
        this.put(Type.SPEAR, creationFunction.apply(prefix + "_spear", properties ->
                createSpearItem(applyPropertiesForItem(Type.SPEAR, ability, ability1 -> ability1.createSpearComponent(properties, toolMaterial, spearData), () -> spearData.consume(properties, toolMaterial)))));
    }

    private Item createSpearItem(Item.Properties properties) {
        return !this.infused ? new Item(properties) : new InfusedSpearItem(properties);
    }

    private void populateAxeItem(@Nullable Ability ability, ToolMaterial toolMaterial, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>> creationFunction) {
        this.put(Type.AXE, creationFunction.apply(prefix + "_axe", properties ->
                createAxeItem(toolMaterial, applyPropertiesForItem(Type.AXE, ability, ability1 -> ability1.createToolComponent(properties), () -> properties))));
    }

    private Item createAxeItem(ToolMaterial toolMaterial, Item.Properties properties) {
        return !this.infused ? new AxeItem(toolMaterial, 5, -3, properties) : new InfusedAxeItem(toolMaterial, properties);
    }

    private void populatePickaxeItem(@Nullable Ability ability, ToolMaterial toolMaterial, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>> creationFunction) {
        this.put(Type.PICKAXE, creationFunction.apply(prefix + "_pickaxe", properties ->
                createPickaxeItem(applyPropertiesForItem(Type.PICKAXE, ability, ability1 -> ability1.createPickaxeComponent(toolMaterial, properties), () -> properties.pickaxe(toolMaterial, 1, -2.8F)))));
    }

    private Item createPickaxeItem(Item.Properties properties) {
        return !this.infused ? new Item(properties) : new InfusedPickaxeItem(properties);
    }

    private void populateShovelItem(@Nullable Ability ability, ToolMaterial toolMaterial, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>> creationFunction) {
        this.put(Type.SHOVEL, creationFunction.apply(prefix + "_shovel", properties ->
                createShovelItem(toolMaterial, applyPropertiesForItem(Type.SHOVEL, ability, ability1 -> ability1.createToolComponent(properties), () -> properties))));
    }

    private Item createShovelItem(ToolMaterial toolMaterial, Item.Properties properties) {
        return !this.infused ? new ShovelItem(toolMaterial, 1.5F, -3, properties) : new InfusedShovelItem(toolMaterial, properties);
    }

    private void populateHoeItem(@Nullable Ability ability, ToolMaterial toolMaterial, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>> creationFunction) {
        this.put(Type.HOE, creationFunction.apply(prefix + "_hoe", properties ->
                createHoeItem(toolMaterial, applyPropertiesForItem(Type.HOE, ability, ability1 -> ability1.createToolComponent(properties), () -> properties))));
    }

    private Item createHoeItem(ToolMaterial toolMaterial, Item.Properties properties) {
        return !this.infused ? new HoeItem(toolMaterial, -toolMaterial.attackDamageBonus(), 0, properties) : new InfusedHoeItem(toolMaterial, properties);
    }

    private void populateHammerItem(@Nullable Ability ability, ToolMaterial toolMaterial, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>> creationFunction) {
        this.put(Type.HAMMER, creationFunction.apply(prefix + "_hammer", properties ->
                createHammerItem(applyPropertiesForItem(Type.AXE, ability, ability1 -> ability1.createHammerComponent(toolMaterial, 5, -3F, weaponLevel, properties), () -> CommonUtils.applyHammerProperties(toolMaterial, 5, -3F, weaponLevel, properties)))));
    }

    private Item createHammerItem(Item.Properties properties) {
        return !this.infused ? new Item(properties) : new InfusedHammerItem(properties);
    }

    @SafeVarargs
    public final EquipmentSet populateArmorWithoutRecipe(@Nullable Ability ability, ArmorMaterial armorMaterial, TagKey<Item>... tags) {
        return populateArmor(ability, armorMaterial, template -> template.copyWithRecipeOverride(ItemDataTemplate.NONE), (template, _) -> template::consumeSpecial, tags);
    }
    
    @SafeVarargs
    public final EquipmentSet populateArmorWithStandardRecipe(@Nullable Ability ability, ArmorMaterial armorMaterial, TagKey<Item>... tags) {
        return populateArmor(ability, armorMaterial, UnaryOperator.identity(), (template, _) -> (name, creator) -> template.consumeSpecial(name, creator, baseMaterial), tags);
    }

    @SafeVarargs
    public final EquipmentSet populateArmorWithThermalUpgradeRecipe(@Nullable Ability ability, ArmorMaterial armorMaterial, EquipmentSet toUpgradeFrom, TagKey<Item>... tags) {
        return populateArmor(ability, armorMaterial, (template) -> template.copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(RecipeCategory.COMBAT)), (template, type) -> (name, creator) -> template.consumeSpecial(name, creator, Either.left(toUpgradeFrom.get(type))), tags);
    }

    @SafeVarargs
    public final EquipmentSet populateArmorWithSmithingUpgradeRecipe(@Nullable Ability ability, ArmorMaterial armorMaterial, Supplier<? extends Item> smithingTemplate, EquipmentSet toUpgradeFrom, TagKey<Item>... tags) {
        return populateArmor(ability, armorMaterial, (template) -> template.copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(false)), (template, type) -> (name, creator) -> template.consumeSpecialWithIngredients(name, creator, Either.left(smithingTemplate), Either.left(toUpgradeFrom.get(type)), baseMaterial), tags);
    }
    
    @SafeVarargs
    public final EquipmentSet populateArmor(@Nullable Ability ability, ArmorMaterial armorMaterial, UnaryOperator<ItemDataTemplate> templateModifier, BiFunction<ItemDataTemplate, Type, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>>> creatorFunc, TagKey<Item>... tags) {
        ItemDataTemplate helmet = templateModifier.apply(ItemDataTemplates.HELMET);
        ItemDataTemplate chestplate = templateModifier.apply(ItemDataTemplates.CHESTPLATE);
        ItemDataTemplate leggings = templateModifier.apply(ItemDataTemplates.LEGGINGS);
        ItemDataTemplate boots = templateModifier.apply(ItemDataTemplates.BOOTS);
        if (this.infused) {
            helmet = helmet.copyWithModelOverride(ItemDataTemplate.INFUSED_BASIC_ITEM_NO_OVERLAY);
            chestplate = chestplate.copyWithModelOverride(ItemDataTemplate.INFUSED_BASIC_ITEM_NO_OVERLAY);
            leggings = leggings.copyWithModelOverride(ItemDataTemplate.INFUSED_BASIC_ITEM_NO_OVERLAY);
            boots = boots.copyWithModelOverride(ItemDataTemplate.INFUSED_BASIC_ITEM_NO_OVERLAY);
        }
        populateArmorItem(Type.HELMET, ability, armorMaterial, creatorFunc.apply(helmet.addTags(tags), Type.HELMET));
        populateArmorItem(Type.CHESTPLATE, ability, armorMaterial, creatorFunc.apply(chestplate.addTags(tags), Type.CHESTPLATE));
        populateArmorItem(Type.LEGGINGS, ability, armorMaterial, creatorFunc.apply(leggings.addTags(tags), Type.LEGGINGS));
        populateArmorItem(Type.BOOTS, ability, armorMaterial, creatorFunc.apply(boots.addTags(tags), Type.BOOTS));
        return this;
    }

    @SafeVarargs
    public final EquipmentSet populateHorseAndNautilusArmor(@Nullable Ability ability, ArmorMaterial armorMaterial, TagKey<Item>... tags) {
        return populateHorseAndNautilusArmor(ability, armorMaterial, UnaryOperator.identity(), (template, _) -> template::consumeSpecial, tags);
    }

    @SafeVarargs
    public final EquipmentSet populateHorseAndNautilusArmorWithSmithingUpgradeRecipe(@Nullable Ability ability, ArmorMaterial armorMaterial, Supplier<? extends Item> smithingTemplate, EquipmentSet toUpgradeFrom, TagKey<Item>... tags) {
        return populateHorseAndNautilusArmor(ability, armorMaterial, (template) -> template.copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(false)), (template, type) -> (name, creator) -> template.consumeSpecialWithIngredients(name, creator, Either.left(smithingTemplate), Either.left(toUpgradeFrom.get(type)), baseMaterial), tags);
    }

    @SafeVarargs
    public final EquipmentSet populateHorseAndNautilusArmor(@Nullable Ability ability, ArmorMaterial armorMaterial, UnaryOperator<ItemDataTemplate> templateModifier, BiFunction<ItemDataTemplate, Type, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>>> creatorFunc, TagKey<Item>... tags) {
        ItemDataTemplate horseArmor = templateModifier.apply(ItemDataTemplates.HORSE_ARMOR);
        ItemDataTemplate nautilusArmor = templateModifier.apply(ItemDataTemplates.NAUTILUS_ARMOR);
        if (this.infused) {
            horseArmor = horseArmor.copyWithModelOverride(ItemDataTemplate.INFUSED_BASIC_ITEM_NO_OVERLAY);
            nautilusArmor = nautilusArmor.copyWithModelOverride(ItemDataTemplate.INFUSED_BASIC_ITEM_NO_OVERLAY);
        }
        populateArmorItem(Type.HORSE_ARMOR, ability, armorMaterial, creatorFunc.apply(horseArmor.addTags(tags), Type.HORSE_ARMOR));
        populateArmorItem(Type.NAUTILUS_ARMOR, ability, armorMaterial, creatorFunc.apply(nautilusArmor.addTags(tags), Type.NAUTILUS_ARMOR));
        return this;
    }
    @SafeVarargs
    public final EquipmentSet populateToolsAndWeaponsWithoutRecipe(@Nullable Ability ability, ToolMaterial toolMaterial, SpearData spearData, TagKey<Item>... tags) {
        return populateToolsAndWeapons(ability, toolMaterial, spearData, (template, type) -> template.copyWithRecipeOverride(ItemDataTemplate.NONE), (template, _) -> template::consumeSpecial, tags);
    }

    @SafeVarargs
    public final EquipmentSet populateToolsAndWeaponsWithStandardRecipe(@Nullable Ability ability, ToolMaterial toolMaterial, SpearData spearData, TagKey<Item>... tags) {
        return populateToolsAndWeapons(ability, toolMaterial, spearData, (template, _) -> template, (template, _) -> (name, creator) -> template.consumeSpecial(name, creator, baseMaterial), tags);
    }

    @SafeVarargs
    public final EquipmentSet populateToolsAndWeaponsWithThermalUpgradeRecipe(@Nullable Ability ability, ToolMaterial toolMaterial, SpearData spearData, EquipmentSet toUpgradeFrom, TagKey<Item>... tags) {
        return populateToolsAndWeapons(ability, toolMaterial, spearData, (template, type) -> template.copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(!type.tool ? RecipeCategory.COMBAT : RecipeCategory.TOOLS)), (template, type) -> (name, creator) -> template.consumeSpecial(name, creator, Either.left(toUpgradeFrom.get(type))), tags);
    }

    @SafeVarargs
    public final EquipmentSet populateToolsAndWeaponsWithSmithingUpgradeRecipe(@Nullable Ability ability, ToolMaterial toolMaterial, SpearData spearData, Supplier<? extends Item> smithingTemplate, EquipmentSet toUpgradeFrom, TagKey<Item>... tags) {
        return populateToolsAndWeapons(ability, toolMaterial, spearData, (template, type) -> template.copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(type.tool)), (template, type) -> (name, creator) -> template.consumeSpecialWithIngredients(name, creator, Either.left(smithingTemplate), Either.left(toUpgradeFrom.get(type)), baseMaterial), tags);
    }

    @SafeVarargs
    public final EquipmentSet populateToolsAndWeapons(@Nullable Ability ability, ToolMaterial toolMaterial, SpearData spearData, BiFunction<ItemDataTemplate, Type, ItemDataTemplate> templateModifier, BiFunction<ItemDataTemplate, Type, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>>> creatorFunc, TagKey<Item>... tags) {
        ItemDataTemplate sword = templateModifier.apply(ItemDataTemplates.SWORD, Type.SWORD);
        ItemDataTemplate spear = templateModifier.apply(ItemDataTemplates.SPEAR, Type.SPEAR);
        ItemDataTemplate axe = templateModifier.apply(ItemDataTemplates.AXE, Type.AXE);
        ItemDataTemplate pickaxe = templateModifier.apply(ItemDataTemplates.PICKAXE, Type.PICKAXE);
        ItemDataTemplate shovel = templateModifier.apply(ItemDataTemplates.SHOVEL, Type.SHOVEL);
        ItemDataTemplate hoe = templateModifier.apply(ItemDataTemplates.HOE, Type.HOE);
        if (this.infused) {
            sword = sword.copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM);
            spear = spear.copyWithModelOverride(ItemDataTemplate.INFUSED_SPEAR_ITEM);
            axe = axe.copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM);
            pickaxe = pickaxe.copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM);
            shovel = shovel.copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM);
            hoe = hoe.copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM);
        }
        populateSwordItem(ability, toolMaterial, creatorFunc.apply(sword.addTags(tags), Type.SWORD));
        populateSpearItem(ability, toolMaterial, spearData, creatorFunc.apply(spear.addTags(tags), Type.SPEAR));
        populateAxeItem(ability, toolMaterial, creatorFunc.apply(axe.addTags(tags), Type.AXE));
        populatePickaxeItem(ability, toolMaterial, creatorFunc.apply(pickaxe.addTags(tags), Type.PICKAXE));
        populateShovelItem(ability, toolMaterial, creatorFunc.apply(shovel.addTags(tags), Type.SHOVEL));
        populateHoeItem(ability, toolMaterial, creatorFunc.apply(hoe.addTags(tags), Type.HOE));
        return this;
    }
    @SafeVarargs
    public final EquipmentSet populateCustomToolsAndWeaponsWithoutRecipe(@Nullable Ability ability, ToolMaterial toolMaterial, TagKey<Item>... tags) {
        return populateCustomToolsAndWeapons(ability, toolMaterial, (template, type) -> template.copyWithRecipeOverride(ItemDataTemplate.NONE), (template, _) -> template::consumeSpecial, tags);
    }

    @SafeVarargs
    public final EquipmentSet populateCustomToolsAndWeaponsWithStandardRecipe(@Nullable Ability ability, ToolMaterial toolMaterial, TagKey<Item>... tags) {
        return populateCustomToolsAndWeapons(ability, toolMaterial, (template, _) -> template, (template, _) -> (name, creator) -> template.consumeSpecial(name, creator, baseMaterial), tags);
    }

    @SafeVarargs
    public final EquipmentSet populateCustomToolsAndWeaponsWithThermalUpgradeRecipe(@Nullable Ability ability, ToolMaterial toolMaterial, EquipmentSet toUpgradeFrom, TagKey<Item>... tags) {
        return populateCustomToolsAndWeapons(ability, toolMaterial, (template, type) -> template.copyWithRecipeOverride(ItemDataTemplate.IMPROVED_THERMAL_RECIPE.apply(!type.tool ? RecipeCategory.COMBAT : RecipeCategory.TOOLS)), (template, type) -> (name, creator) -> template.consumeSpecial(name, creator, Either.left(toUpgradeFrom.get(type))), tags);
    }

    @SafeVarargs
    public final EquipmentSet populateCustomToolsAndWeaponsWithSmithingUpgradeRecipe(@Nullable Ability ability, ToolMaterial toolMaterial, Supplier<? extends Item> smithingTemplate, EquipmentSet toUpgradeFrom, TagKey<Item>... tags) {
        return populateCustomToolsAndWeapons(ability, toolMaterial, (template, type) -> template.copyWithRecipeOverride(ItemDataTemplate.UPGRADE_RECIPE.apply(type.tool)), (template, type) -> (name, creator) -> template.consumeSpecialWithIngredients(name, creator, Either.left(smithingTemplate), Either.left(toUpgradeFrom.get(type)), baseMaterial), tags);
    }

    @SafeVarargs
    public final EquipmentSet populateCustomToolsAndWeapons(@Nullable Ability ability, ToolMaterial toolMaterial, BiFunction<ItemDataTemplate, Type, ItemDataTemplate> templateModifier, BiFunction<ItemDataTemplate, Type, BiFunction<String, Function<Item.Properties, Item>, RegistrySupplier<? extends Item>>> creatorFunc, TagKey<Item>... tags) {
        ItemDataTemplate hammer = templateModifier.apply(ItemDataTemplates.HAMMER, Type.HAMMER);
        if (this.infused) {
            hammer = hammer.copyWithModelOverride(ItemDataTemplate.INFUSED_HANDHELD_ITEM);
        }
        populateHammerItem(ability, toolMaterial, creatorFunc.apply(hammer.addTags(tags), Type.HAMMER));
        return this;
    }
    public enum Type {
        HELMET(true, false, false),
        CHESTPLATE(true, false, false),
        LEGGINGS(true, false, false),
        BOOTS(true, false, false),
        HORSE_ARMOR(true, false, false),
        NAUTILUS_ARMOR(true, false, false),
        SWORD(false, true, false),
        SHOVEL(false, false, true),
        PICKAXE(false, false, true),
        AXE(false, true, true),
        SPEAR(false, true, false),
        HOE(false, false, true),
        HAMMER(false, false, true);
        public final boolean armor;
        public final boolean weapon;
        public final boolean tool;

        Type(boolean armor, boolean weapon, boolean tool) {
            this.armor = armor;
            this.weapon = weapon;
            this.tool = tool;
        }

        public String suffix() {
            return switch (this) {
                case HELMET -> "helmet";
                case CHESTPLATE -> "chestplate";
                case LEGGINGS -> "leggings";
                case BOOTS -> "boots";
                case HORSE_ARMOR -> "horse_armor";
                case NAUTILUS_ARMOR -> "nautilus_armor";
                case SWORD -> "sword";
                case SPEAR -> "spear";
                case AXE -> "axe";
                case PICKAXE -> "pickaxe";
                case SHOVEL -> "shovel";
                case HOE -> "hoe";
                case HAMMER -> "hammer";
            };
        }
        public BiFunction<Item.Properties, ArmorMaterial, Item.Properties> armorPropertiesFunction() {
            return switch (this) {
                case HELMET -> (properties, armorMaterial) -> properties.humanoidArmor(armorMaterial, ArmorType.HELMET);
                case CHESTPLATE -> (properties, armorMaterial) -> properties.humanoidArmor(armorMaterial, ArmorType.CHESTPLATE);
                case LEGGINGS -> (properties, armorMaterial) -> properties.humanoidArmor(armorMaterial, ArmorType.LEGGINGS);
                case BOOTS -> (properties, armorMaterial) -> properties.humanoidArmor(armorMaterial, ArmorType.BOOTS);
                case HORSE_ARMOR -> Item.Properties::horseArmor;
                case NAUTILUS_ARMOR -> Item.Properties::nautilusArmor;
                default -> (properties, _) -> properties;
            };
        }
    }
}
