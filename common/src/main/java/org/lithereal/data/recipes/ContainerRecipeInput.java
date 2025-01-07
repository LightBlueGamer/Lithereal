package org.lithereal.data.recipes;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ContainerRecipeInput(SimpleContainer simpleContainer) implements RecipeInput {

    @Override
    public ItemStack getItem(int i) {
        return simpleContainer.getItem(i);
    }

    @Override
    public int size() {
        return simpleContainer.getContainerSize();
    }
}
