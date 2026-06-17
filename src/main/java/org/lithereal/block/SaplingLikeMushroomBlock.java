package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class SaplingLikeMushroomBlock extends SaplingBlock {
    public static final MapCodec<SaplingLikeMushroomBlock> CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(TreeGrower.CODEC.fieldOf("tree").forGetter((b) -> b.treeGrower), propertiesCodec()).apply(i, SaplingLikeMushroomBlock::new));

    public SaplingLikeMushroomBlock(TreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
    }

    @Override
    public MapCodec<? extends SaplingLikeMushroomBlock> codec() {
        return CODEC;
    }

    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(25) == 0) {
            int max = 5;

            for(BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-4, -1, -4), pos.offset(4, 1, 4))) {
                if (level.getBlockState(blockPos).is(this)) {
                    --max;
                    if (max <= 0) {
                        return;
                    }
                }
            }

            BlockPos offset = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

            for(int i = 0; i < 4; ++i) {
                if (level.isEmptyBlock(offset) && state.canSurvive(level, offset)) {
                    pos = offset;
                }

                offset = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            }

            if (level.isEmptyBlock(offset) && state.canSurvive(level, offset)) {
                level.setBlock(offset, state, 2);
            }
        }

    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.isSolidRender();
    }
}
