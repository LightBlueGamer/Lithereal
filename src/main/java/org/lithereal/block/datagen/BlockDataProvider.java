package org.lithereal.block.datagen;

import com.mojang.datafixers.util.Either;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.lithereal.data.datagen.CustomRecipeProvider;
import org.lithereal.data.datagen.ItemLikeDataProvider;

import java.util.*;
import java.util.function.Consumer;

public record BlockDataProvider(RegistrySupplier<? extends Block> target, Optional<Consumer<CustomRecipeProvider<? extends RecipeProvider>>> recipeCreator, Optional<Consumer<BlockModelGenerators>> modelCreator, TagData tagData) implements ItemLikeDataProvider<Block> {
    public static final Map<String, TagKey<Block>> CONVENTIONAL_TAG_CACHE = new HashMap<>();
    public BlockDataProvider(RegistrySupplier<? extends Block> target, Optional<Consumer<CustomRecipeProvider<? extends RecipeProvider>>> recipeCreator, Optional<Consumer<BlockModelGenerators>> modelCreator, TagData tagData) {
        this.target = target;
        this.recipeCreator = recipeCreator;
        this.modelCreator = modelCreator;
        this.tagData = tagData;
        ALL_DATA_PROVIDERS.add(this);
    }
    public static TagKey<Block> cBlockTag(String name) {
        if (!CONVENTIONAL_TAG_CACHE.containsKey(name)) CONVENTIONAL_TAG_CACHE.put(name, TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", name)));
        return CONVENTIONAL_TAG_CACHE.get(name);
    }

    public enum TagType {
        FIRE,
        NYLIUM,
        DIRTLIKE,
        FARMLAND,
        FENCE_GATES,
        LEAVES,
        PLANKS,
        SAPLINGS,
        SLABS,
        STAIRS,
        STONE_BUTTONS,
        STONES,
        WALLS,
        WOODEN_BUTTONS,
        WOODEN_DOORS,
        WOODEN_FENCES,
        WOODEN_PRESSURE_PLATES,
        WOODEN_SHELVES,
        WOODEN_SLABS,
        WOODEN_STAIRS,
        WOODEN_TRAPDOORS,
        NONE;
        public List<Either<TagKey<Item>, TagKey<Block>>> tags() {
            return switch (this) {
                case FIRE -> List.of(Either.right(BlockTags.DRAGON_TRANSPARENT), Either.right(BlockTags.FIRE));
                case NYLIUM -> List.of(Either.right(BlockTags.ENDERMAN_HOLDABLE), Either.right(BlockTags.NYLIUM), Either.right(BlockTags.OVERRIDES_MUSHROOM_LIGHT_REQUIREMENT));
                case DIRTLIKE -> List.of(Either.right(BlockTags.SUPPORTS_BIG_DRIPLEAF), Either.right(BlockTags.MINEABLE_WITH_SHOVEL));
                case FARMLAND -> List.of(Either.right(BlockTags.GROWS_CROPS), Either.right(BlockTags.SUPPORT_OVERRIDE_CACTUS_FLOWER), Either.right(BlockTags.SUPPORTS_CROPS), Either.right(BlockTags.SUPPORTS_VEGETATION));
                case FENCE_GATES -> List.of(Either.right(BlockTags.FENCE_GATES), Either.left(ItemTags.FENCE_GATES));
                case LEAVES -> List.of(Either.right(BlockTags.LEAVES), Either.left(ItemTags.LEAVES), Either.right(BlockTags.MINEABLE_WITH_HOE));
                case PLANKS -> List.of(Either.right(BlockTags.PLANKS), Either.left(ItemTags.PLANKS));
                case SAPLINGS -> List.of(Either.right(BlockTags.SAPLINGS), Either.left(ItemTags.SAPLINGS));
                case SLABS -> List.of(Either.right(BlockTags.SLABS), Either.left(ItemTags.SLABS));
                case STAIRS -> List.of(Either.right(BlockTags.STAIRS), Either.left(ItemTags.STAIRS));
                case STONE_BUTTONS -> List.of(Either.right(BlockTags.STONE_BUTTONS), Either.left(ItemTags.STONE_BUTTONS));
                case STONES -> List.of(Either.left(ItemTags.STONE_CRAFTING_MATERIALS), Either.left(ItemTags.STONE_TOOL_MATERIALS));
                case WALLS -> List.of(Either.right(BlockTags.WALLS), Either.left(ItemTags.WALLS));
                case WOODEN_BUTTONS -> List.of(Either.right(BlockTags.WOODEN_BUTTONS), Either.left(ItemTags.WOODEN_BUTTONS));
                case WOODEN_DOORS -> List.of(Either.right(BlockTags.WOODEN_DOORS), Either.left(ItemTags.WOODEN_DOORS));
                case WOODEN_FENCES -> List.of(Either.right(BlockTags.WOODEN_FENCES), Either.left(ItemTags.WOODEN_FENCES));
                case WOODEN_PRESSURE_PLATES -> List.of(Either.right(BlockTags.WOODEN_PRESSURE_PLATES), Either.left(ItemTags.WOODEN_PRESSURE_PLATES));
                case WOODEN_SHELVES -> List.of(Either.right(BlockTags.WOODEN_SHELVES), Either.left(ItemTags.WOODEN_SHELVES));
                case WOODEN_SLABS -> List.of(Either.right(BlockTags.WOODEN_SLABS), Either.left(ItemTags.WOODEN_SLABS));
                case WOODEN_STAIRS -> List.of(Either.right(BlockTags.WOODEN_STAIRS), Either.left(ItemTags.WOODEN_STAIRS));
                case WOODEN_TRAPDOORS -> List.of(Either.right(BlockTags.WOODEN_TRAPDOORS), Either.left(ItemTags.WOODEN_TRAPDOORS));
                case NONE -> Collections.emptyList();
            };
        }
    }
}
