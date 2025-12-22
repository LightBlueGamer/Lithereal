package org.lithereal.item.burning;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.item.ability.AbilityItem;

import java.util.List;

public interface BurningItem extends AbilityItem {
    default void getDrops(Level level, BlockState blockState, BlockPos blockPos, ItemStack itemStack, LivingEntity livingEntity, BlockEntity blockEntity) {
        if (level instanceof ServerLevel) {
            List<ItemStack> origDrops = Block.getDrops(blockState, (ServerLevel) level, blockPos, blockEntity, livingEntity, itemStack);
            SmeltingRecipe[] furnaceRecipes = new SmeltingRecipe[origDrops.size()];

            for (int i = 0; i < origDrops.size(); i++) {
                RecipeHolder<SmeltingRecipe> holder = level.getRecipeManager()
                        .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(origDrops.get(i)), level).orElse(null);
                furnaceRecipes[i] = holder == null ? null : holder.value();
            }

            NonNullList<ItemStack> drops = NonNullList.create();
            for (int i = 0; i < furnaceRecipes.length; i++) {
                if (furnaceRecipes[i] == null) {
                    drops.add(i, origDrops.get(i));
                    continue;
                }
                ItemStack dropStack = new ItemStack(furnaceRecipes[i].getResultItem(level.registryAccess()).getItem(), origDrops.get(i).getCount());
                drops.add(i, dropStack);
            }

            drops.forEach(drop -> Block.popResource(level, blockPos, drop));
            blockState.spawnAfterBreak((ServerLevel)level, blockPos, itemStack, true);
        }
    }
}