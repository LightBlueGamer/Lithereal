package org.lithereal.world.feature.generic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import org.lithereal.world.feature.generic.configurations.DripstoneReplacementConfiguration;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class DripstoneReplacementUtils {
    protected static double getDripstoneHeight(double dist, double radius, double scale, double bluntness) {
        if (dist < bluntness) {
            dist = bluntness;
        }

        double factor = dist / radius * 0.384;
        double high = 0.75 * Math.pow(factor, 1.3333333333333333);
        double mid = Math.pow(factor, 0.6666666666666666);
        double low = 0.3333333333333333 * Math.log(factor);
        double height = scale * (high - mid - low);
        height = Math.max(height, 0.0);
        return height / 0.384 * radius;
    }

    protected static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel worldGenLevel, BlockPos blockPos, int radius) {
        if (isEmptyOrWaterOrLava(worldGenLevel, blockPos)) {
            return false;
        } else {
            float angleOff = 6.0F / radius;

            for (float angle = 0.0F; angle < (float) (Math.PI * 2); angle += angleOff) {
                int xOff = (int)(Mth.cos(angle) * radius);
                int zOff = (int)(Mth.sin(angle) * radius);
                if (isEmptyOrWaterOrLava(worldGenLevel, blockPos.offset(xOff, 0, zOff))) {
                    return false;
                }
            }

            return true;
        }
    }

    protected static boolean isEmptyOrWater(LevelAccessor levelAccessor, BlockPos blockPos) {
        return levelAccessor.isStateAtPosition(blockPos, DripstoneReplacementUtils::isEmptyOrWater);
    }

    protected static boolean isEmptyOrWaterOrLava(LevelAccessor levelAccessor, BlockPos blockPos) {
        return levelAccessor.isStateAtPosition(blockPos, DripstoneReplacementUtils::isEmptyOrWaterOrLava);
    }

    protected static void buildBaseToTipColumn(DripstoneReplacementConfiguration dripstoneReplacementConfiguration, Direction direction, int endPoint, boolean merge, Consumer<BiFunction<BlockPos, RandomSource, BlockState>> consumer) {
        if (endPoint >= 3) {
            consumer.accept((blockPos, randomSource) -> createPointedDripstone(dripstoneReplacementConfiguration, randomSource, blockPos, direction, DripstoneThickness.BASE));

            for (int pos = 0; pos < endPoint - 3; pos++) {
                consumer.accept((blockPos, randomSource) -> createPointedDripstone(dripstoneReplacementConfiguration, randomSource, blockPos, direction, DripstoneThickness.MIDDLE));
            }
        }

        if (endPoint >= 2) {
            consumer.accept((blockPos, randomSource) -> createPointedDripstone(dripstoneReplacementConfiguration, randomSource, blockPos, direction, DripstoneThickness.FRUSTUM));
        }

        if (endPoint >= 1) {
            consumer.accept((blockPos, randomSource) -> createPointedDripstone(dripstoneReplacementConfiguration, randomSource, blockPos, direction, merge ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }
    }

    protected static boolean growPointedDripstone(DripstoneReplacementConfiguration dripstoneReplacementConfiguration, LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, int endPoint, boolean connects, boolean hasPossibleOther) {
        if (hasPossibleOther && levelAccessor.getRandom().nextBoolean()) return false;
        if (isBase(dripstoneReplacementConfiguration, levelAccessor.getBlockState(blockPos.relative(direction.getOpposite())))) {
            if (dripstoneReplacementConfiguration.dripstoneSpike().isEmpty())
                return false;
            BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
            buildBaseToTipColumn(dripstoneReplacementConfiguration, direction, endPoint, connects, func -> {
                BlockState blockState = func.apply(mutableBlockPos.immutable(), levelAccessor.getRandom());
                if (blockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                    blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, levelAccessor.isWaterAt(mutableBlockPos));
                }

                levelAccessor.setBlock(mutableBlockPos, blockState, 2);
                mutableBlockPos.move(direction);
            });
        }
        return true;
    }

    protected static boolean placeBlockIfPossible(DripstoneReplacementConfiguration dripstoneReplacementConfiguration, boolean isCore, WorldGenLevel worldGenLevel, BlockPos blockPos) {
        BlockState blockState = worldGenLevel.getBlockState(blockPos);
        if (blockState.is(dripstoneReplacementConfiguration.replaceable())) {
            worldGenLevel.setBlock(blockPos, isCore ? dripstoneReplacementConfiguration.core()
                    .getState(worldGenLevel, worldGenLevel.getRandom(), blockPos) : dripstoneReplacementConfiguration.outer().getState(worldGenLevel, worldGenLevel.getRandom(), blockPos), 2);
            return true;
        } else {
            return false;
        }
    }

    private static BlockState createPointedDripstone(DripstoneReplacementConfiguration dripstoneReplacementConfiguration, RandomSource randomSource, BlockPos basePos, Direction direction, DripstoneThickness dripstoneThickness) {
        return dripstoneReplacementConfiguration.dripstoneSpike().map(blockStateProvider -> {
            BlockState baseState = blockStateProvider.getState(randomSource, basePos);
            if (baseState.hasProperty(BlockStateProperties.VERTICAL_DIRECTION)) baseState = baseState
                    .setValue(BlockStateProperties.VERTICAL_DIRECTION, direction);
            if (baseState.hasProperty(BlockStateProperties.FACING)) baseState = baseState
                    .setValue(BlockStateProperties.FACING, direction);
            if (baseState.hasProperty(BlockStateProperties.DRIPSTONE_THICKNESS)) baseState = baseState
                    .setValue(BlockStateProperties.DRIPSTONE_THICKNESS, dripstoneThickness);
            return baseState;
        }).orElse(Blocks.AIR.defaultBlockState());
    }

    public static boolean isBaseOrLava(DripstoneReplacementConfiguration dripstoneReplacementConfiguration, BlockState blockState) {
        return isBase(dripstoneReplacementConfiguration, blockState) || blockState.is(Blocks.LAVA);
    }

    public static boolean isBase(DripstoneReplacementConfiguration dripstoneReplacementConfiguration, BlockState blockState) {
        return blockState.is(dripstoneReplacementConfiguration.baseBlocks()) || blockState.is(dripstoneReplacementConfiguration.replaceable());
    }

    public static boolean isEmptyOrWater(BlockState blockState) {
        return blockState.isAir() || blockState.is(Blocks.WATER);
    }

    public static boolean isNeitherEmptyNorWater(BlockState blockState) {
        return !blockState.isAir() && !blockState.is(Blocks.WATER);
    }

    public static boolean isEmptyOrWaterOrLava(BlockState blockState) {
        return blockState.isAir() || blockState.is(Blocks.WATER) || blockState.is(Blocks.LAVA);
    }
}
