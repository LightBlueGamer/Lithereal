package org.lithereal.item.datagen;

import net.minecraft.world.item.Items;

import java.util.Optional;

import static org.lithereal.item.datagen.ItemDataTemplate.*;

public class ItemDataTemplates {
    public static final ItemDataTemplate EMPTY =
            new ItemDataTemplate(Optional.empty(),
                    Optional.empty(),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.NONE));
    public static final ItemDataTemplate STANDARD =
            new ItemDataTemplate(Optional.empty(),
                    Optional.of(BASIC_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.NONE));
    public static final ItemDataTemplate SWORD =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.swordItem(Items.STICK, args[1].get(), args[0].get())),
                    Optional.of(HANDHELD_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.SWORD));
    public static final ItemDataTemplate SPEAR =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.spearItem(Items.STICK, args[1].get(), args[0].get())),
                    Optional.of(SPEAR_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.SPEAR));
    public static final ItemDataTemplate AXE =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.axeItem(Items.STICK, args[1].get(), args[0].get())),
                    Optional.of(HANDHELD_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.AXE));
    public static final ItemDataTemplate PICKAXE =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.pickaxeItem(Items.STICK, args[1].get(), args[0].get())),
                    Optional.of(HANDHELD_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.PICKAXE));
    public static final ItemDataTemplate SHOVEL =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.shovelItem(Items.STICK, args[1].get(), args[0].get())),
                    Optional.of(HANDHELD_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.SHOVEL));
    public static final ItemDataTemplate HOE =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.hoeItem(Items.STICK, args[1].get(), args[0].get())),
                    Optional.of(HANDHELD_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.HOE));
    public static final ItemDataTemplate HAMMER =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.hammerItem(Items.STICK, args[1].get(), args[0].get())),
                    Optional.of(HANDHELD_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.HAMMER));
    public static final ItemDataTemplate WAR_HAMMER =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.warHammerItem(args[2].get(), args[1].get(), args[0].get())),
                    Optional.of(HANDHELD_WAR_HAMMER_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.WAR_HAMMER));
    public static final ItemDataTemplate HELMET =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.helmetItem(args[1].get(), args[0].get())),
                    Optional.of(BASIC_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.HELMET));
    public static final ItemDataTemplate CHESTPLATE =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.chestplateItem(args[1].get(), args[0].get())),
                    Optional.of(BASIC_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.CHESTPLATE));
    public static final ItemDataTemplate LEGGINGS =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.leggingsItem(args[1].get(), args[0].get())),
                    Optional.of(BASIC_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.LEGGINGS));
    public static final ItemDataTemplate BOOTS =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.bootsItem(args[1].get(), args[0].get())),
                    Optional.of(BASIC_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.BOOTS));
    public static final ItemDataTemplate HORSE_ARMOR =
            new ItemDataTemplate(Optional.empty(),
                    Optional.of(BASIC_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.HORSE_ARMOR));
    public static final ItemDataTemplate NAUTILUS_ARMOR =
            new ItemDataTemplate(Optional.empty(),
                    Optional.of(BASIC_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.NAUTILUS_ARMOR));
    public static final ItemDataTemplate BOW =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.bowItem(args[1].get(), args[2].get(), args[3].get(), args[0].get())),
                    Optional.of(BOW_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.BOW));
    public static final ItemDataTemplate BRUSH =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.brushItem(args[1].get(), args[0].get())),
                    Optional.of(BRUSH_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.BRUSH));
    public static final ItemDataTemplate WRENCH =
            new ItemDataTemplate(Optional.of(args -> recipeProvider ->
                    recipeProvider.wrenchItem(args[1].get(), args[2].get(), args[0].get())),
                    Optional.of(HANDHELD_ITEM),
                    new ItemDataProvider.TagDataBuilder(ItemDataProvider.TagType.WRENCH));
}
