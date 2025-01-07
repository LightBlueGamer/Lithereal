package org.lithereal.tags;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.lithereal.Lithereal;

public class ModTags {
    public static final TagKey<Block> NEEDS_ODYSIUM_TOOL = createBlockTag("needs_odysium_tool");
    public static final TagKey<Block> NEEDS_LITHERITE_TOOL = createBlockTag("needs_litherite_tool");
    public static final TagKey<Block> INCORRECT_FOR_ODYSIUM_TOOL = createBlockTag("incorrect_for_odysium_tool");
    public static final TagKey<Block> INCORRECT_FOR_LITHERITE_TOOL = createBlockTag("incorrect_for_litherite_tool");
    public static TagKey<Block> createBlockTag(String name) {
        return create(name, Registries.BLOCK);
    }
    public static <T> TagKey<T> create(String name, ResourceKey<? extends Registry<T>> registry) {
        return TagKey.create(registry, ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, name));
    }
    public static void init() {

    }
}
