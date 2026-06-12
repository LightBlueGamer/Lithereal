package org.lithereal.item;

import net.minecraft.ChatFormatting;
import net.minecraft.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;
import java.util.function.Function;

public class ModSmithingTemplates {
    private static final ChatFormatting DESCRIPTION_FORMAT;
    private static final Component ODYSIUM_UPGRADE_APPLIES_TO;
    private static final Component ODYSIUM_UPGRADE_INGREDIENTS;
    private static final Component ODYSIUM_UPGRADE_BASE_SLOT_DESCRIPTION;
    private static final Component ODYSIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION;
    private static final Identifier EMPTY_SLOT_HELMET;
    private static final Identifier EMPTY_SLOT_CHESTPLATE;
    private static final Identifier EMPTY_SLOT_LEGGINGS;
    private static final Identifier EMPTY_SLOT_BOOTS;
    private static final Identifier EMPTY_SLOT_HOE;
    private static final Identifier EMPTY_SLOT_AXE;
    private static final Identifier EMPTY_SLOT_SWORD;
    private static final Identifier EMPTY_SLOT_SHOVEL;
    private static final Identifier EMPTY_SLOT_PICKAXE;
    private static final Identifier EMPTY_SLOT_INGOT;
    public static Function<Item.Properties, SmithingTemplateItem> createOdysiumUpgradeTemplate() {
        return properties -> new SmithingTemplateItem(ODYSIUM_UPGRADE_APPLIES_TO, ODYSIUM_UPGRADE_INGREDIENTS, ODYSIUM_UPGRADE_BASE_SLOT_DESCRIPTION, ODYSIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION, createOdysiumUpgradeIconList(), createOdysiumUpgradeMaterialList(), properties);
    }

    private static List<Identifier> createOdysiumUpgradeIconList() {
        return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_SWORD, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_PICKAXE, EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_AXE, EMPTY_SLOT_BOOTS, EMPTY_SLOT_HOE, EMPTY_SLOT_SHOVEL);
    }

    private static List<Identifier> createOdysiumUpgradeMaterialList() {
        return List.of(EMPTY_SLOT_INGOT);
    }

    static {
        DESCRIPTION_FORMAT = ChatFormatting.BLUE;
        ODYSIUM_UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId("item", Identifier.fromNamespaceAndPath("lithereal", "smithing_template.odysium_upgrade.applies_to"))).withStyle(DESCRIPTION_FORMAT);
        ODYSIUM_UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId("item", Identifier.fromNamespaceAndPath("lithereal", "smithing_template.odysium_upgrade.ingredients"))).withStyle(DESCRIPTION_FORMAT);
        ODYSIUM_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", Identifier.fromNamespaceAndPath("lithereal", "smithing_template.odysium_upgrade.base_slot_description")));
        ODYSIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", Identifier.fromNamespaceAndPath("lithereal", "smithing_template.odysium_upgrade.additions_slot_description")));
        EMPTY_SLOT_HELMET = Identifier.withDefaultNamespace("item/empty_armor_slot_helmet");
        EMPTY_SLOT_CHESTPLATE = Identifier.withDefaultNamespace("item/empty_armor_slot_chestplate");
        EMPTY_SLOT_LEGGINGS = Identifier.withDefaultNamespace("item/empty_armor_slot_leggings");
        EMPTY_SLOT_BOOTS = Identifier.withDefaultNamespace("item/empty_armor_slot_boots");
        EMPTY_SLOT_HOE = Identifier.withDefaultNamespace("item/empty_slot_hoe");
        EMPTY_SLOT_AXE = Identifier.withDefaultNamespace("item/empty_slot_axe");
        EMPTY_SLOT_SWORD = Identifier.withDefaultNamespace("item/empty_slot_sword");
        EMPTY_SLOT_SHOVEL = Identifier.withDefaultNamespace("item/empty_slot_shovel");
        EMPTY_SLOT_PICKAXE = Identifier.withDefaultNamespace("item/empty_slot_pickaxe");
        EMPTY_SLOT_INGOT = Identifier.withDefaultNamespace("item/empty_slot_ingot");
    }
}
