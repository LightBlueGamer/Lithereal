package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultConfig;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.Lithereal;
import org.lithereal.block.LitherealVaultBlock;
import org.lithereal.item.ModItems;

import java.util.Optional;

public class LitherealVaultBlockEntity extends VaultBlockEntity {
    public static final VaultConfig DEFAULT =
            new VaultConfig(Lithereal.LITHEREAL_CHAMBERS_REWARD,
                    4.0,
                    4.5,
                    new ItemStack(ModItems.LITHEREAL_KEY.get()),
                    Optional.empty());
    public LitherealVaultBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
        ((IBlockEntity) this).setType(ModBlockEntities.LITHEREAL_VAULT.get());
        setConfig(DEFAULT);
    }

    @Override
    public boolean isValidBlockState(BlockState blockState) {
        return super.isValidBlockState(blockState) || ModBlockEntities.LITHEREAL_VAULT.get().isValid(blockState);
    }
}