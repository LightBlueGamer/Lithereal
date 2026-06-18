package org.lithereal.item.datagen;

import com.google.common.collect.ImmutableList;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.lithereal.data.datagen.CustomRecipeProvider;
import org.lithereal.tags.ModTags;

import java.util.*;
import java.util.function.Consumer;

public record ItemDataProvider(RegistrySupplier<? extends Item> item, Optional<Consumer<CustomRecipeProvider<? extends RecipeProvider>>> recipeCreator, Optional<Consumer<ItemModelGenerators>> modelCreator, TagData tagData) {
    public static final Map<String, TagKey<Item>> CONVENTIONAL_TAG_CACHE = new HashMap<>();
    public static final Map<String, TagKey<Item>> COMBATIFY_TAG_CACHE = new HashMap<>();
    public static final List<ItemDataProvider> ALL_ITEM_DATA_PROVIDERS = new ArrayList<>();
    public ItemDataProvider(RegistrySupplier<? extends Item> item, Optional<Consumer<CustomRecipeProvider<? extends RecipeProvider>>> recipeCreator, Optional<Consumer<ItemModelGenerators>> modelCreator, TagData tagData) {
        this.item = item;
        this.recipeCreator = recipeCreator;
        this.modelCreator = modelCreator;
        this.tagData = tagData;
        ALL_ITEM_DATA_PROVIDERS.add(this);
    }
    public record TagData(List<TagKey<Item>> toAddTo) {
    }
    public static class TagDataBuilder {
        private TagType type;
        private final List<TagKey<Item>> tags;
        private ImmutableList.Builder<TagKey<Item>> toAddTo;
        public TagDataBuilder(TagType type) {
            this.type = type;
            this.tags = this.type.tags();
            this.toAddTo = ImmutableList.builder();
            this.toAddTo.addAll(this.tags);
        }

        public TagDataBuilder setType(TagType type) {
            this.type = type;
            return this;
        }

        public TagDataBuilder addTag(TagKey<Item> tag) {
            this.toAddTo.add(tag);
            return this;
        }

        @SafeVarargs
        public final TagDataBuilder addTags(TagKey<Item>... tag) {
            this.toAddTo.add(tag);
            return this;
        }

        public TagData build() {
            TagData tagData = new TagData(this.toAddTo.build());
            this.toAddTo = ImmutableList.builder();
            this.toAddTo.addAll(this.tags);
            return tagData;
        }
    }
    public static TagKey<Item> cItemTag(String name) {
        if (!CONVENTIONAL_TAG_CACHE.containsKey(name)) CONVENTIONAL_TAG_CACHE.put(name, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", name)));
        return CONVENTIONAL_TAG_CACHE.get(name);
    }
    public static TagKey<Item> combatifyItemTag(String name) {
        if (!COMBATIFY_TAG_CACHE.containsKey(name)) COMBATIFY_TAG_CACHE.put(name, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("combatify", name)));
        return COMBATIFY_TAG_CACHE.get(name);
    }
    public enum TagType {
        SWORD,
        SPEAR,
        AXE,
        PICKAXE,
        HAMMER,
        SHOVEL,
        HOE,
        WAR_HAMMER,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        HORSE_ARMOR,
        NAUTILUS_ARMOR,
        BOW,
        BRUSH,
        WRENCH,
        NONE;
        public List<TagKey<Item>> tags() {
            return switch (this) {
                case SWORD -> List.of(ItemTags.SWORDS, cItemTag("tools/melee_weapon"), combatifyItemTag("weapon_type/sword"));
                case SPEAR -> List.of(ItemTags.SPEARS, cItemTag("tools/melee_weapon"), combatifyItemTag("weapon_type/spear"));
                case AXE -> List.of(ItemTags.AXES, cItemTag("tools/melee_weapon"), combatifyItemTag("weapon_type/axe"));
                case PICKAXE -> List.of(ItemTags.PICKAXES, ItemTags.CLUSTER_MAX_HARVESTABLES, cItemTag("tools/mining_tool"), combatifyItemTag("weapon_type/pickaxe"));
                case HAMMER -> List.of(ModTags.HAMMERS);
                case SHOVEL -> List.of(ItemTags.SHOVELS, combatifyItemTag("weapon_type/shovel"));
                case HOE -> List.of(ItemTags.HOES, combatifyItemTag("weapon_type/hoe"));
                case WAR_HAMMER -> List.of(ModTags.WAR_HAMMERS);
                case HELMET -> List.of(ItemTags.HEAD_ARMOR, ItemTags.TRIMMABLE_ARMOR);
                case CHESTPLATE -> List.of(ItemTags.CHEST_ARMOR, ItemTags.TRIMMABLE_ARMOR);
                case LEGGINGS -> List.of(ItemTags.LEG_ARMOR, ItemTags.TRIMMABLE_ARMOR);
                case BOOTS -> List.of(ItemTags.FOOT_ARMOR, ItemTags.TRIMMABLE_ARMOR);
                case HORSE_ARMOR -> List.of(cItemTag("armors/horse"));
                case NAUTILUS_ARMOR -> List.of(cItemTag("armors/nautilus"));
                case BOW -> List.of(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.BOW_ENCHANTABLE, cItemTag("tools/bow"), cItemTag("tools/ranged_weapon"));
                case BRUSH -> List.of(ItemTags.DURABILITY_ENCHANTABLE, cItemTag("tools/brush"));
                case WRENCH -> List.of(ItemTags.DURABILITY_ENCHANTABLE, cItemTag("tools/wrench"));
                case NONE -> Collections.emptyList();
            };
        }
    }
}
