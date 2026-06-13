package org.lithereal.fabric.data.mixin;

//? fabric {
/*import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.item.LitheriteItem;
import org.lithereal.item.ModRawMaterialItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static org.lithereal.item.LitheriteItem.spawnWitherSkeleton;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow public abstract ItemStack getItem();

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void onTick(CallbackInfo ci) {
        ItemEntity entity = (ItemEntity)(Object)this;
        ItemStack stack = entity.getItem();
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
    }
}
*///?}