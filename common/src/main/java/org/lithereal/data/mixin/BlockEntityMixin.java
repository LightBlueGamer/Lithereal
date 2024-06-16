package org.lithereal.data.mixin;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.lithereal.world.block.entity.IBlockEntity;
import org.spongepowered.asm.mixin.*;

@Mixin(BlockEntity.class)
public class BlockEntityMixin implements IBlockEntity {
    @Shadow @Mutable @Final private BlockEntityType<?> type;

    @Unique
    @Override
    public void setType(BlockEntityType<?> type) {
        this.type = type;
    }
}
