package org.lithereal.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.InfiniteEtherSourceBlockEntity;
import org.lithereal.block.entity.ModBlockEntities;

import java.util.Optional;

public class InfiniteEtherSourceBlock extends EtherBatteryBlock {
    public static final MapCodec<InfiniteEtherSourceBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(Codec.INT.fieldOf("transfer_rate").forGetter(block -> block.transferRate),
                            TagKey.codec(Registries.BIOME).optionalFieldOf("biomes").forGetter(block -> Optional.ofNullable(block.biomes)),
                            propertiesCodec())
                    .apply(instance, InfiniteEtherSourceBlock::new));
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private final int transferRate;
    private final @Nullable TagKey<Biome> biomes;

    public InfiniteEtherSourceBlock(int transferRate, @Nullable TagKey<Biome> biomes, Properties properties) {
        super(properties);
        this.transferRate = transferRate;
        this.biomes = biomes;
    }

    public InfiniteEtherSourceBlock(int transferRate, Optional<TagKey<Biome>> biomes, Properties properties) {
        this(transferRate, biomes.orElse(null), properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InfiniteEtherSourceBlockEntity(pos, state, biomes, transferRate);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.INFINITE_ETHER_GENERATOR.get(),
                InfiniteEtherSourceBlockEntity::tick);
    }
}