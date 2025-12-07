package org.lithereal.tags;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.lithereal.Lithereal;

public class ModTags {
    public static <T> TagKey<T> create(String name, ResourceKey<? extends Registry<T>> registry) {
        return TagKey.create(registry, Lithereal.id(name));
    }
    public static TagKey<Biome> createBiomeTag(String name) {
        return create(name, Registries.BIOME);
    }
    public static final TagKey<Block> BLUE_FIRE_BASE_BLOCKS = createBlockTag("blue_fire_base_blocks");
    public static final TagKey<Block> ETHERSTONE_ORE_REPLACEABLES = createBlockTag("etherstone_ore_replaceables");
    public static final TagKey<Block> NEEDS_ODYSIUM_TOOL = createBlockTag("needs_odysium_tool");
    public static final TagKey<Block> NEEDS_LITHERITE_TOOL = createBlockTag("needs_litherite_tool");
    public static final TagKey<Block> INCORRECT_FOR_ODYSIUM_TOOL = createBlockTag("incorrect_for_odysium_tool");
    public static final TagKey<Block> INCORRECT_FOR_LITHERITE_TOOL = createBlockTag("incorrect_for_litherite_tool");
    public static TagKey<Block> createBlockTag(String name) {
        return create(name, Registries.BLOCK);
    }
    public static final TagKey<Item> ETHEREAL_CORE_PORTAL_ITEMS = createItemTag("ethereal_realm_portal_items");
    public static TagKey<Item> createItemTag(String name) {
        return create(name, Registries.ITEM);
    }
    public static final TagKey<MobEffect> APPLY_HASTE_ON_INFUSED = createMobEffectTag("applies_haste_on_litherite_gear");
    public static final TagKey<MobEffect> DEGRADES_LITHERITE_GEAR = createMobEffectTag("degrades_litherite_gear");
    public static final TagKey<MobEffect> PSEUDO_BENEFICIAl = createMobEffectTag("pseudo_beneficial");
    public static TagKey<MobEffect> createMobEffectTag(String name) {
        return create(name, Registries.MOB_EFFECT);
    }
    public static final TagKey<EntityType<?>> IS_ETHEREAL = createEntityTypeTag("is_from_ethereal_realm");
    public static TagKey<EntityType<?>> createEntityTypeTag(String name) {
        return create(name, Registries.ENTITY_TYPE);
    }
    public static void init() {

    }
}
