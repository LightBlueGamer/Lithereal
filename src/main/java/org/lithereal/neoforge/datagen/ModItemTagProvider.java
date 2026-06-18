package org.lithereal.neoforge.datagen;

import com.google.common.collect.Maps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;
import org.lithereal.Lithereal;
import org.lithereal.item.datagen.ItemDataProvider;
import org.lithereal.tags.ModTags;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends BlockTagCopyingItemTagProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, Lithereal.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagKey<Item> litheriteKnives = ModTags.createItemTag("litherite_knives");
        TagKey<Item> litheriteLongswords = ModTags.createItemTag("litherite_longswords");
        TagKey<Item> odysiumKnives = ModTags.createItemTag("odysium_knives");
        TagKey<Item> odysiumLongswords = ModTags.createItemTag("odysium_longswords");
        addLitheriteOptionalsToTag(tagForOptionalUse(litheriteKnives), "_knife");
        addLitheriteOptionalsToTag(tagForOptionalUse(litheriteLongswords), "_longsword");
        addOdysiumOptionalsToTag(tagForOptionalUse(odysiumKnives), "_knife");
        addOdysiumOptionalsToTag(tagForOptionalUse(odysiumLongswords), "_longsword");
        tag(ItemDataProvider.combatifyItemTag("knives"))
                .addTags(litheriteKnives, odysiumKnives);
        tag(ItemDataProvider.combatifyItemTag("longswords"))
                .addTags(litheriteLongswords, odysiumLongswords);
        Map<TagKey<Item>, TagAppender<Item, Item>> itemTags = Maps.newHashMap();
        itemTags.computeIfAbsent(ModTags.CHILLERS, this::tag).add(Items.ICE);
        itemTags.computeIfAbsent(ModTags.ETHEREAL_CORE_PORTAL_ITEMS, this::tag).add(Items.DRAGON_BREATH);
        ItemDataProvider.ALL_ITEM_DATA_PROVIDERS.forEach(itemDataProvider -> itemDataProvider.tagData()
                .toAddTo()
                .forEach(itemTagKey -> itemTags.computeIfAbsent(itemTagKey, this::tag)
                        .add(itemDataProvider.item().get())));
        itemTags.computeIfAbsent(ModTags.LITHERITE_TOOLS, this::tag).addTags(litheriteKnives, litheriteLongswords);
        itemTags.computeIfAbsent(ModTags.ODYSIUM_TOOLS, this::tag).addTags(odysiumKnives, odysiumLongswords);
        itemTags.computeIfAbsent(ItemDataProvider.cItemTag("tools/mining_tool"), this::tag).addTags(ModTags.HAMMERS);
        itemTags.computeIfAbsent(ItemDataProvider.cItemTag("tools/melee_weapon"), this::tag).addTags(ModTags.HAMMERS, ModTags.WAR_HAMMERS);
        itemTags.computeIfAbsent(ItemTags.MINING_ENCHANTABLE, this::tag).addTags(ModTags.HAMMERS);
        itemTags.computeIfAbsent(ItemTags.MINING_LOOT_ENCHANTABLE, this::tag).addTags(ModTags.HAMMERS);
        itemTags.computeIfAbsent(ItemTags.SHARP_WEAPON_ENCHANTABLE, this::tag).addTags(ModTags.HAMMERS);
        itemTags.computeIfAbsent(ItemTags.DURABILITY_ENCHANTABLE, this::tag).addTags(ModTags.HAMMERS, ModTags.WAR_HAMMERS);
        itemTags.computeIfAbsent(ItemTags.FIRE_ASPECT_ENCHANTABLE, this::tag).addTags(ModTags.WAR_HAMMERS);
        itemTags.computeIfAbsent(ItemTags.WEAPON_ENCHANTABLE, this::tag).addTags(ModTags.WAR_HAMMERS);
        itemTags.computeIfAbsent(ItemTags.BREAKS_DECORATED_POTS, this::tag).addTags(ModTags.WAR_HAMMERS);
        copy(ModTags.PHANTOM_OAK_LOGS_B, ModTags.PHANTOM_OAK_LOGS);
        copy(ModTags.FORTSHROOM_STEMS_B, ModTags.FORTSHROOM_STEMS);
        copy(ModTags.MALISHROOM_STEMS_B, ModTags.MALISHROOM_STEMS);
    }

    protected TagAppender<ResourceKey<Item>, Item> tagForOptionalUse(TagKey<Item> tag) {
        TagBuilder builder = this.getOrCreateRawBuilder(tag);
        return TagAppender.forBuilder(builder);
    }

    public void addLitheriteOptionalsToTag(TagAppender<ResourceKey<Item>, Item> builder, String suffix) {
        builder.addOptional(Lithereal.key(Registries.ITEM, "litherite" + suffix))
                .addOptional(Lithereal.key(Registries.ITEM, "burning_litherite" + suffix))
                .addOptional(Lithereal.key(Registries.ITEM, "frozen_litherite" + suffix))
                .addOptional(Lithereal.key(Registries.ITEM, "smoldering_litherite" + suffix))
                .addOptional(Lithereal.key(Registries.ITEM, "frostbitten_litherite" + suffix))
                .addOptional(Lithereal.key(Registries.ITEM, "infused_litherite" + suffix))
                .addOptional(Lithereal.key(Registries.ITEM, "withering_litherite" + suffix));
    }

    public void addOdysiumOptionalsToTag(TagAppender<ResourceKey<Item>, Item> builder, String suffix) {
        builder.addOptional(Lithereal.key(Registries.ITEM, "odysium" + suffix))
                .addOptional(Lithereal.key(Registries.ITEM, "enhanced_odysium" + suffix));
    }
}
