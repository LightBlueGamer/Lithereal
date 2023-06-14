package org.litecraft.lithereal.item.custom;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.litecraft.lithereal.block.ModBlocks;
import org.litecraft.lithereal.item.ModItems;

public class LitheriteItem extends Item {
    public LitheriteItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level level = entity.getLevel();
        if(!level.isClientSide()) {
            if(level.getBlockState(entity.blockPosition()).getBlock() == Blocks.WITHER_ROSE) {
                ItemStack itemStack = new ItemStack(ModItems.WITHERING_LITHERITE_CRYSTAL.get(), stack.getCount());
                ItemEntity item = new ItemEntity(
                        level,
                        entity.blockPosition().getX() + 0.5,
                        entity.blockPosition().getY() + 1.0,
                        entity.blockPosition().getZ() + 0.5,
                        itemStack);
                level.addFreshEntity(item);
                entity.kill();
            } else if (level.getBlockState(entity.blockPosition().below()).getBlock() == ModBlocks.WITHERING_LITHERITE_BLOCK.get()) {
                ItemStack itemStack = new ItemStack(ModItems.WITHERING_LITHERITE_CRYSTAL.get(), stack.getCount());
                ItemEntity item = new ItemEntity(
                        level,
                        entity.blockPosition().getX() + 0.5,
                        entity.blockPosition().getY() + 1.0,
                        entity.blockPosition().getZ() + 0.5,
                        itemStack);
                level.addFreshEntity(item);
                entity.kill();
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
