package org.lithereal.data.recipes;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public record ContainerRecipeInput(SimpleContainer simpleContainer) implements RecipeInput {

    @Override
    public @NotNull ItemStack getItem(int i) {
        return simpleContainer.getItem(i);
    }

    @Override
    public int size() {
        return simpleContainer.getContainerSize();
    }
}
