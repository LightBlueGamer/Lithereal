package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.CustomHangingSignBlockEntity;
import org.lithereal.block.entity.ModBlockEntities;

public class CustomCeilingHangingSignBlock extends CeilingHangingSignBlock {
    public static final MapCodec<CeilingHangingSignBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(WoodType.CODEC.fieldOf("wood_type").forGetter(SignBlock::type), propertiesCodec()).apply(instance, CeilingHangingSignBlock::new)
    );

    @Override
    public @NotNull MapCodec<CeilingHangingSignBlock> codec() {
        return CODEC;
    }
    public CustomCeilingHangingSignBlock(WoodType woodType, Properties properties) {
        super(woodType, properties);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CustomHangingSignBlockEntity(blockPos, blockState);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.HANGING_SIGN.get(), SignBlockEntity::tick);
    }
}
