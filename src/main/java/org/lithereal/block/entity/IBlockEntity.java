package org.lithereal.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Unique;

public interface IBlockEntity {
    @Unique
    void setType(BlockEntityType<?> type);
}
