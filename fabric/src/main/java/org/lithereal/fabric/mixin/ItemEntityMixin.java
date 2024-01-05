package org.lithereal.fabric.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.item.ModItems;
import org.lithereal.item.custom.LitheriteItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow public abstract ItemStack getItem();

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void onTick(CallbackInfo ci) {
        Item item = this.getItem().getItem();
        ItemEntity entity = (ItemEntity)(Object)this;
        if(item instanceof LitheriteItem) {
            Level level = ((ItemEntity)(Object)this).level();
            if(!level.isClientSide()) {
                BlockState blockState = level.getBlockState(entity.blockPosition());

                if (blockState.is(Blocks.WITHER_ROSE)) {
                    ItemStack itemStack = new ItemStack(ModItems.WITHERING_LITHERITE_CRYSTAL.get(), item.getDefaultInstance().getCount());
                    ItemEntity newItem = new ItemEntity(level, entity.getX() + 0.5, entity.getY() + 1.0, entity.getZ() + 0.5, itemStack);
                    level.addFreshEntity(newItem);
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
        }
    }

    @Unique
    private void spawnWitherSkeleton(Level level, double x, double y, double z) {
        WitherSkeleton skeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
        skeleton.setPos(x, y, z);
        level.addFreshEntity(skeleton);
    }
}
