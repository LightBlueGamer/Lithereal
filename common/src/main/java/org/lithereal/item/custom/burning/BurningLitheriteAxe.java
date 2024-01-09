package org.lithereal.item.custom.burning;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BurningLitheriteAxe extends AxeItem implements BurningItem {
    public BurningLitheriteAxe(Tier tier, int i, float f, Properties properties) {
        super(tier, i, f, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        if(attacked.isFreezing()) attacked.setTicksFrozen(0);
        attacked.setSecondsOnFire(1000);
        return super.hurtEnemy(itemStack, attacked, attacker);
    }

    @Override
    public void getDrops(Level level, BlockState blockState, BlockPos blockPos, ItemStack itemStack, LivingEntity livingEntity, BlockEntity blockEntity) {
        if(level instanceof ServerLevel) {
            List<ItemStack> origDrops = Block.getDrops(blockState, (ServerLevel) level, blockPos, blockEntity, livingEntity, itemStack);
            SmeltingRecipe[] furnaceRecipes = new SmeltingRecipe[origDrops.size()];

            for (int i = 0; i < origDrops.size(); i++) {
                furnaceRecipes[i] = level.getRecipeManager()
                        .getRecipeFor(RecipeType.SMELTING, new SimpleContainer(origDrops.get(i)), level).orElse(null);
            }


            NonNullList<ItemStack> drops = NonNullList.create();
            for (int i = 0; i < furnaceRecipes.length; i++) {
                if(furnaceRecipes[i] == null) {
                    drops.add(i, origDrops.get(i));
                    continue;
                }
                ItemStack dropStack = new ItemStack(furnaceRecipes[i].getResultItem(level.registryAccess()).getItem(), origDrops.get(i).getCount());
                drops.add(i, dropStack);
            }

            for (ItemStack drop : drops) {
                Block.popResource(level, blockPos, drop);
            }
            itemStack.hurtAndBreak(0, livingEntity, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            level.destroyBlock(blockPos, false);
        }
    }
}
