package org.lithereal.forge.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.block.ModBlocks;
import org.lithereal.item.ModItems;
import org.lithereal.item.custom.LitheriteItem;

import java.util.Random;

public class ForgeLitheriteItem extends LitheriteItem {
    public ForgeLitheriteItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level level = entity.level();
        if (!level.isClientSide()) {
            BlockState blockState = level.getBlockState(entity.blockPosition());

            if (blockState.is(Blocks.WITHER_ROSE)) {
                ItemStack itemStack = new ItemStack(ModItems.WITHERING_LITHERITE_CRYSTAL.get(), stack.getCount());
                ItemEntity item = new ItemEntity(level, entity.getX() + 0.5, entity.getY() + 1.0, entity.getZ() + 0.5, itemStack);
                level.addFreshEntity(item);
                entity.kill();

                Random random = new Random();
                int witherSkeletonCount = 0;

                for (int i = 0; i < 3; i++) {
                    if (random.nextInt(100) < 20) {
                        spawnWitherSkeleton(level, entity.getX(), entity.getY(), entity.getZ());
                        witherSkeletonCount++;
                    }
                }

                if(witherSkeletonCount > 0) {
                    level.removeBlock(entity.blockPosition(), false);
                }
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }


    private void spawnWitherSkeleton(Level level, double x, double y, double z) {
        WitherSkeleton skeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
        skeleton.setPos(x, y, z);
        level.addFreshEntity(skeleton);
    }
}


