package org.litecraft.lithereal.item.custom;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;

public class LitheriteItem extends Item {
    public LitheriteItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level level = entity.level();
        if (!level.isClientSide()) {
            BlockState blockState = level.getBlockState(entity.blockPosition());
            BlockState blockStateBelow = level.getBlockState(entity.blockPosition().below());

            if (blockState.is(Blocks.WITHER_ROSE) || blockStateBelow.is(ModBlocks.WITHERING_LITHERITE_BLOCK.get())) {
                ItemStack itemStack = new ItemStack(ModItems.WITHERING_LITHERITE_CRYSTAL.get(), stack.getCount());
                ItemEntity item = new ItemEntity(level, entity.getX() + 0.5, entity.getY() + 1.0, entity.getZ() + 0.5, itemStack);
                level.addFreshEntity(item);
                entity.kill();
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
