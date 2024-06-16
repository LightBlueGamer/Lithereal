package org.lithereal.world.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultConfig;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.Lithereal;
import org.lithereal.world.block.entity.LitherealVaultBlockEntity;
import org.lithereal.world.block.entity.ModBlockEntities;
import org.lithereal.world.item.ModItems;

import java.util.Optional;

public class LitherealVaultBlock extends VaultBlock {
    public static final MapCodec<VaultBlock> CODEC = simpleCodec(LitherealVaultBlock::new);

    public MapCodec<VaultBlock> codec() {
        return CODEC;
    }

    public LitherealVaultBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        LitherealVaultBlockEntity ret = new LitherealVaultBlockEntity(blockPos, blockState);
        ret.setConfig(new VaultConfig(Lithereal.LITHEREAL_CHAMBERS_REWARD, 4.0, 4.5, new ItemStack(ModItems.LITHEREAL_KEY), Optional.empty(), PlayerDetector.INCLUDING_CREATIVE_PLAYERS, PlayerDetector.EntitySelector.SELECT_FROM_LEVEL));
        return ret;
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
