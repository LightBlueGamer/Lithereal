package org.lithereal.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LitherealVaultBlockEntity extends VaultBlockEntity {

    public LitherealVaultBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
        ((IBlockEntity) this).setType(ModBlockEntities.LITHEREAL_VAULT.get());
    }
}