package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.LitherealVaultBlockEntity;
import org.lithereal.block.entity.ModBlockEntities;

public class LitherealVaultBlock extends VaultBlock {
    public static final MapCodec<VaultBlock> CODEC = simpleCodec(LitherealVaultBlock::new);

    public @NotNull MapCodec<VaultBlock> codec() {
        return CODEC;
    }

    public LitherealVaultBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LitherealVaultBlockEntity(blockPos, blockState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        BlockEntityTicker<T> var10000;
        if (level instanceof ServerLevel serverLevel) {
            var10000 = createTickerHelper(blockEntityType, ModBlockEntities.LITHEREAL_VAULT.get(), (levelx, blockPos, blockStatex, vaultBlockEntity) -> VaultBlockEntity.Server.tick(serverLevel, blockPos, blockStatex, vaultBlockEntity.getConfig(), vaultBlockEntity.getServerData(), vaultBlockEntity.getSharedData()));
        } else {
            var10000 = createTickerHelper(blockEntityType, ModBlockEntities.LITHEREAL_VAULT.get(), (levelx, blockPos, blockStatex, vaultBlockEntity) -> VaultBlockEntity.Client.tick(levelx, blockPos, blockStatex, vaultBlockEntity.getClientData(), vaultBlockEntity.getSharedData()));
        }

        return var10000;
    }
}
