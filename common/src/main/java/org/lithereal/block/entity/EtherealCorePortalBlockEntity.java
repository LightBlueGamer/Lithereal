package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EtherealCorePortalBlockEntity extends TheEndPortalBlockEntity {
    public EtherealCorePortalBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public EtherealCorePortalBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntities.ETHEREAL_CORE_PORTAL.get(), blockPos, blockState);
    }

    public boolean shouldRenderFace(Direction direction) {
        return Block.shouldRenderFace(this.getBlockState(), this.level, this.getBlockPos(), direction, this.getBlockPos().relative(direction));
    }
}
