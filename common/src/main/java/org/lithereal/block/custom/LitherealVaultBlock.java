package org.lithereal.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultConfig;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.Lithereal;
import org.lithereal.item.ModItems;

import java.util.Optional;

public class LitherealVaultBlock extends VaultBlock {
    public static final MapCodec<VaultBlock> CODEC = simpleCodec(LitherealVaultBlock::new);
    public static final VaultConfig DEFAULT = new VaultConfig(Lithereal.LITHEREAL_CHAMBERS_REWARD, 4.0, 4.5, new ItemStack(ModItems.LITHEREAL_KEY), Optional.empty(), PlayerDetector.INCLUDING_CREATIVE_PLAYERS, PlayerDetector.EntitySelector.SELECT_FROM_LEVEL);

    public MapCodec<VaultBlock> codec() {
        return CODEC;
    }

    public LitherealVaultBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        VaultBlockEntity ret = new VaultBlockEntity(blockPos, blockState);
        ret.setConfig(DEFAULT);
        return ret;
    }
}
