package org.lithereal.data.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.lithereal.item.datagen.ItemDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface ItemLikeDataProvider<I extends ItemLike> {
    List<ItemLikeDataProvider<?>> ALL_DATA_PROVIDERS = new ArrayList<>();
    RegistrySupplier<? extends I> target();
    default Item asItem() {
        return target().get().asItem();
    }
    Optional<Consumer<CustomRecipeProvider<? extends RecipeProvider>>> recipeCreator();
    TagData tagData();
    record TagData(List<Either<TagKey<Item>, TagKey<Block>>> toAddTo) {
        public List<TagKey<Item>> itemTags() {
            return toAddTo.stream().map(tagKeyTagKeyEither -> tagKeyTagKeyEither.left()).filter(Optional::isPresent).map(Optional::get).toList();
        }
        public List<TagKey<Block>> blockTags() {
            return toAddTo.stream().map(tagKeyTagKeyEither -> tagKeyTagKeyEither.right()).filter(Optional::isPresent).map(Optional::get).toList();
        }
    }
    class TagDataBuilder {
        private final List<Either<TagKey<Item>, TagKey<Block>>> tags;
        private ImmutableList.Builder<Either<TagKey<Item>, TagKey<Block>>> toAddTo;
        public TagDataBuilder(List<Either<TagKey<Item>, TagKey<Block>>> tags) {
            this.tags = tags;
            this.toAddTo = ImmutableList.builder();
            this.toAddTo.addAll(this.tags);
        }

        public static TagDataBuilder builder(ItemDataProvider.TagType tagType) {
            return new TagDataBuilder(tagType.tags().stream().<Either<TagKey<Item>, TagKey<Block>>>map(Either::left).toList());
        }

        public TagDataBuilder addAllTags(List<Either<TagKey<Item>, TagKey<Block>>> tags) {
            this.toAddTo.addAll(tags);
            return this;
        }

        public TagDataBuilder addBlockTag(TagKey<Block> tag) {
            this.toAddTo.add(Either.right(tag));
            return this;
        }

        public TagDataBuilder addTag(TagKey<Item> tag) {
            this.toAddTo.add(Either.left(tag));
            return this;
        }

        @SafeVarargs
        public final TagDataBuilder addBlockTags(TagKey<Block>... tag) {
            for (TagKey<Block> tagKey : tag) {
                this.toAddTo.add(Either.right(tagKey));
            }
            return this;
        }

        @SafeVarargs
        public final TagDataBuilder addTags(TagKey<Item>... tag) {
            for (TagKey<Item> tagKey : tag) {
                this.toAddTo.add(Either.left(tagKey));
            }
            return this;
        }

        public TagData build() {
            TagData tagData = new TagData(this.toAddTo.build());
            this.toAddTo = ImmutableList.builder();
            this.toAddTo.addAll(this.tags);
            return tagData;
        }
    }
}
