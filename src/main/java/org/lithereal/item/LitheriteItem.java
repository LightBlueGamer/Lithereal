package org.lithereal.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
//? neoforge {
/*import net.minecraft.world.entity.item.ItemEntity;
*///?}
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.item.Item;
//? neoforge {
/*import net.minecraft.world.item.ItemStack;
*///?}
import net.minecraft.world.level.Level;
//? neoforge {
/*import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
*///?}

public class LitheriteItem extends Item {
    public LitheriteItem(Properties properties) {
        super(properties);
    }

    //? neoforge {
    /*@Override
    public boolean onEntityItemUpdate(ItemStack stack, @NotNull ItemEntity entity) {
        Item item = stack.getItem();

        if (item instanceof LitheriteItem) {
            Level level = entity.level();
            if (!level.isClientSide()) {
                BlockState blockState = level.getBlockState(entity.blockPosition());

                if (blockState.is(Blocks.WITHER_ROSE)) {
                    if (!stack.isEmpty()) {
                        ItemStack newItemStack = new ItemStack(ModRawMaterialItems.WITHERING_LITHERITE_CRYSTAL.get(), 1);
                        ItemEntity newItemEntity = new ItemEntity(level, entity.getX() + 0.5, entity.getY() + 1.0, entity.getZ() + 0.5, newItemStack);
                        level.addFreshEntity(newItemEntity);

                        stack.shrink(1);

                        Random random = new Random();
                        int witherCount = 0;
                        for (int i = 0; i < 3; i++) {
                            if (random.nextInt(6) == 0) {
                                spawnWitherSkeleton(level, entity.getX(), entity.getY(), entity.getZ());
                                witherCount++;
                            }
                        }

                        if (stack.isEmpty()) entity.discard();

                        if (witherCount > 1) level.removeBlock(entity.blockPosition(), false);
                    }
                }
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }
    *///?}
    public static void spawnWitherSkeleton(Level level, double x, double y, double z) {
        WitherSkeleton skeleton = EntityType.WITHER_SKELETON.create(level, EntitySpawnReason.TRIGGERED);
        skeleton.setPos(x, y, z);
        if(level instanceof ServerLevel sLevel) skeleton.finalizeSpawn(sLevel, sLevel.getCurrentDifficultyAt(skeleton.blockPosition()), EntitySpawnReason.TRIGGERED, null);
        level.addFreshEntity(skeleton);
    }
}