package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LitherealVaultBlockEntity extends VaultBlockEntity {

    public LitherealVaultBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
        ((IBlockEntity) this).setType(ModBlockEntities.LITHEREAL_VAULT.get());
    }

    @Override
    public boolean isValidBlockState(BlockState blockState) {
        return super.isValidBlockState(blockState) || ModBlockEntities.LITHEREAL_VAULT.get().isValid(blockState);
    }
}