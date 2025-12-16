package org.lithereal.world.feature.generic;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.lithereal.world.feature.generic.configurations.ClusterConfiguration;
import org.lithereal.world.feature.generic.configurations.DripstoneReplacementConfiguration;

import java.util.Optional;
import java.util.OptionalInt;

import static java.lang.Math.pow;

public class ClusterFeature extends Feature<ClusterConfiguration> {
	public ClusterFeature(Codec<ClusterConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<ClusterConfiguration> featurePlaceContext) {
		WorldGenLevel worldGenLevel = featurePlaceContext.level();
		BlockPos blockPos = featurePlaceContext.origin();
		ClusterConfiguration clusterConfiguration = featurePlaceContext.config();
		RandomSource randomSource = featurePlaceContext.random();
		if (!DripstoneReplacementUtils.isEmptyOrWater(worldGenLevel, blockPos)) {
			return false;
		} else {
			int height = clusterConfiguration.height().sample(randomSource);
			float wetness = clusterConfiguration.wetness().sample(randomSource);
			float density = clusterConfiguration.density().sample(randomSource);
			int xRadius = clusterConfiguration.radius().sample(randomSource);
			int zRadius = clusterConfiguration.radius().sample(randomSource);

			for (int x = -xRadius; x <= xRadius; x++) {
				for (int z = -zRadius; z <= zRadius; z++) {
					double chanceOfStalagmiteOrStalactite = this.getChanceOfStalagmiteOrStalactite(xRadius, zRadius, x, z, clusterConfiguration);
					BlockPos offsetPos = blockPos.offset(x, 0, z);
					this.placeColumn(worldGenLevel, randomSource, blockPos, offsetPos, xRadius, zRadius, x, z, wetness, chanceOfStalagmiteOrStalactite, height, density, clusterConfiguration);
				}
			}

			return true;
		}
	}

	private void placeColumn(
		WorldGenLevel worldGenLevel,
		RandomSource randomSource,
        BlockPos centerPos,
		BlockPos offsetPos,
        int xRadius,
        int zRadius,
		int x,
		int z,
		float wetness,
		double chanceOfStalagmiteOrStalactite,
		int height,
		float density,
		ClusterConfiguration clusterConfiguration
	) {
		Optional<Column> optional = Column.scan(
			worldGenLevel, offsetPos, clusterConfiguration.floorToCeilingSearchRange(), DripstoneReplacementUtils::isEmptyOrWater, DripstoneReplacementUtils::isNeitherEmptyNorWater
		);
        DripstoneReplacementConfiguration dripstoneReplacementConfiguration = clusterConfiguration.dripstoneReplacementConfiguration();
		if (optional.isPresent()) {
			OptionalInt optionalCeiling = optional.get().getCeiling();
			OptionalInt optionalFloor = optional.get().getFloor();
			if (!optionalCeiling.isEmpty() || !optionalFloor.isEmpty()) {
				boolean isWet = randomSource.nextFloat() < wetness;
				Column column;
				if (isWet && optionalFloor.isPresent() && this.canPlacePool(dripstoneReplacementConfiguration, worldGenLevel, offsetPos.atY(optionalFloor.getAsInt()))) {
					int floor = optionalFloor.getAsInt();
					column = optional.get().withFloor(OptionalInt.of(floor - 1));
					worldGenLevel.setBlock(offsetPos.atY(floor), Blocks.WATER.defaultBlockState(), 2);
				} else column = optional.get();

				OptionalInt columnFloor = column.getFloor();
				boolean isStalactite = randomSource.nextDouble() < chanceOfStalagmiteOrStalactite;
				int dripstoneHeight;
				if (optionalCeiling.isPresent() && isStalactite && !this.isLava(worldGenLevel, offsetPos.atY(optionalCeiling.getAsInt()))) {
					int blockLayerThickness = clusterConfiguration.blockLayerThickness().sample(randomSource);
                    this.replaceBlocksWithDripstoneBlocks(dripstoneReplacementConfiguration, centerPos, xRadius, zRadius, worldGenLevel, offsetPos.atY(optionalCeiling.getAsInt()), blockLayerThickness, Direction.UP);
					int correctedHeight;
					if (columnFloor.isPresent())
                        correctedHeight = Math.min(height, optionalCeiling.getAsInt() - columnFloor.getAsInt());
					else
                        correctedHeight = height;

					dripstoneHeight = this.getDripstoneHeight(randomSource, x, z, density, correctedHeight, clusterConfiguration);
				} else {
					dripstoneHeight = 0;
				}

				boolean isStalagmite = randomSource.nextDouble() < chanceOfStalagmiteOrStalactite;
				int randomHeightDiff;
				if (columnFloor.isPresent() && isStalagmite && !this.isLava(worldGenLevel, offsetPos.atY(columnFloor.getAsInt()))) {
					int blockLayerThickness = clusterConfiguration.blockLayerThickness().sample(randomSource);
					this.replaceBlocksWithDripstoneBlocks(dripstoneReplacementConfiguration, centerPos, xRadius, zRadius, worldGenLevel, offsetPos.atY(columnFloor.getAsInt()), blockLayerThickness, Direction.DOWN);
					if (optionalCeiling.isPresent()) {
						randomHeightDiff = Math.max(
							0,
							dripstoneHeight
								+ Mth.randomBetweenInclusive(
									randomSource, -clusterConfiguration.maxStalagmiteStalactiteHeightDiff(), clusterConfiguration.maxStalagmiteStalactiteHeightDiff()
								)
						);
					} else
                        randomHeightDiff = this.getDripstoneHeight(randomSource, x, z, density, height, clusterConfiguration);
				} else {
					randomHeightDiff = 0;
				}

				int floorEndPoint;
				int ceilingEndPoint;
				if (optionalCeiling.isPresent() && columnFloor.isPresent() && optionalCeiling.getAsInt() - dripstoneHeight <= columnFloor.getAsInt() + randomHeightDiff) {
					int floor = columnFloor.getAsInt();
					int ceiling = optionalCeiling.getAsInt();
					int functionalFloor = Math.max(ceiling - dripstoneHeight, floor + 1);
					int functionalCeiling = Math.min(floor + randomHeightDiff, ceiling - 1);
					int randomIntersectionCeiling = Mth.randomBetweenInclusive(randomSource, functionalFloor, functionalCeiling + 1);
					int randomIntersectionFloor = randomIntersectionCeiling - 1;
					ceilingEndPoint = ceiling - randomIntersectionCeiling;
					floorEndPoint = randomIntersectionFloor - floor;
				} else {
					ceilingEndPoint = dripstoneHeight;
					floorEndPoint = randomHeightDiff;
				}

				boolean connects = randomSource.nextBoolean() && ceilingEndPoint > 0 && floorEndPoint > 0 && column.getHeight().isPresent() && ceilingEndPoint + floorEndPoint == column.getHeight().getAsInt();
                boolean hasPossibleOther = dripstoneReplacementConfiguration.attached().isPresent();
				if (optionalCeiling.isPresent()) {
                    boolean placed = DripstoneReplacementUtils.growPointedDripstone(dripstoneReplacementConfiguration, worldGenLevel, offsetPos.atY(optionalCeiling.getAsInt() - 1), Direction.DOWN, ceilingEndPoint, connects, hasPossibleOther);
                    if (!placed && hasPossibleOther && ceilingEndPoint >= 1) {
                        BlockPos placePos = offsetPos.atY(optionalCeiling.getAsInt() - 1);
                        BlockState blockState = dripstoneReplacementConfiguration.attached().get().getState(worldGenLevel.getRandom(), offsetPos);
                        if (blockState.hasProperty(BlockStateProperties.FACING)) {
                            blockState = blockState.setValue(BlockStateProperties.FACING, Direction.DOWN);
                        }
                        if (blockState.hasProperty(BlockStateProperties.VERTICAL_DIRECTION)) {
                            blockState = blockState.setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.DOWN);
                        }
                        if (blockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                            blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, worldGenLevel.isWaterAt(offsetPos));
                        }
                        worldGenLevel.setBlock(placePos, blockState, 2);
                    }
				}

				if (columnFloor.isPresent()) {
                    boolean placed = DripstoneReplacementUtils.growPointedDripstone(dripstoneReplacementConfiguration, worldGenLevel, offsetPos.atY(columnFloor.getAsInt() + 1), Direction.UP, floorEndPoint, connects, hasPossibleOther);
                    if (!placed && hasPossibleOther && floorEndPoint >= 1) {
                        BlockPos placePos = offsetPos.atY(columnFloor.getAsInt() + 1);
                        BlockState blockState = dripstoneReplacementConfiguration.attached().get().getState(worldGenLevel.getRandom(), offsetPos);
                        if (blockState.hasProperty(BlockStateProperties.FACING)) {
                            blockState = blockState.setValue(BlockStateProperties.FACING, Direction.UP);
                        }
                        if (blockState.hasProperty(BlockStateProperties.VERTICAL_DIRECTION)) {
                            blockState = blockState.setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.UP);
                        }
                        if (blockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                            blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, worldGenLevel.isWaterAt(offsetPos));
                        }
                        worldGenLevel.setBlock(placePos, blockState, 2);
                    }
				}
			}
		}
	}

	private boolean isLava(LevelReader levelReader, BlockPos blockPos) {
		return levelReader.getBlockState(blockPos).is(Blocks.LAVA);
	}

	private int getDripstoneHeight(RandomSource randomSource, int x, int z, float density, int height, ClusterConfiguration clusterConfiguration) {
		if (randomSource.nextFloat() > density) {
			return 0;
		} else {
			int dist = Math.abs(x) + Math.abs(z);
			float g = (float)Mth.clampedMap(dist, 0.0, clusterConfiguration.maxDistanceFromCenterAffectingHeightBias(), height / 2.0, 0.0);
			return (int)randomBetweenBiased(randomSource, 0.0F, height, g, clusterConfiguration.heightDeviation());
		}
	}

	private boolean canPlacePool(DripstoneReplacementConfiguration dripstoneReplacementConfiguration, WorldGenLevel worldGenLevel, BlockPos blockPos) {
		BlockState blockState = worldGenLevel.getBlockState(blockPos);
		if (!blockState.is(Blocks.WATER) && !blockState.is(dripstoneReplacementConfiguration.placedIgnoreBlocks())) {
			if (worldGenLevel.getBlockState(blockPos.above()).getFluidState().is(FluidTags.WATER)) {
				return false;
			} else {
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					if (!this.canBeAdjacentToWater(dripstoneReplacementConfiguration, worldGenLevel, blockPos.relative(direction))) {
						return false;
					}
				}

				return this.canBeAdjacentToWater(dripstoneReplacementConfiguration, worldGenLevel, blockPos.below());
			}
		} else {
			return false;
		}
	}

	private boolean canBeAdjacentToWater(DripstoneReplacementConfiguration dripstoneReplacementConfiguration, LevelAccessor levelAccessor, BlockPos blockPos) {
		BlockState blockState = levelAccessor.getBlockState(blockPos);
		return blockState.is(dripstoneReplacementConfiguration.replaceable()) || blockState.getFluidState().is(FluidTags.WATER);
	}

	private void replaceBlocksWithDripstoneBlocks(DripstoneReplacementConfiguration dripstoneReplacementConfiguration, BlockPos centerPos, int xRadius, int zRadius, WorldGenLevel worldGenLevel, BlockPos blockPos, int thickness, Direction direction) {
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
        float coreXRadius = xRadius * dripstoneReplacementConfiguration.corePercentage();
        float coreYRadius = thickness * dripstoneReplacementConfiguration.corePercentage();
        float coreZRadius = zRadius * dripstoneReplacementConfiguration.corePercentage();

		for (int offset = 0; offset < thickness; offset++) {
            double dist = (pow((mutableBlockPos.getX() - centerPos.getX()), 2) / pow(coreXRadius, 2))
                    + (pow((mutableBlockPos.getY() - blockPos.getY()), 2) / pow(coreYRadius, 2))
                    + (pow((mutableBlockPos.getZ() - centerPos.getZ()), 2) / pow(coreZRadius, 2));
			if (!DripstoneReplacementUtils.placeBlockIfPossible(dripstoneReplacementConfiguration, dist <= 1, worldGenLevel, mutableBlockPos)) {
				return;
			}

			mutableBlockPos.move(direction);
		}
	}

	private double getChanceOfStalagmiteOrStalactite(int i, int j, int k, int l, ClusterConfiguration clusterConfiguration) {
		int m = i - Math.abs(k);
		int n = j - Math.abs(l);
		int o = Math.min(m, n);
		return Mth.clampedMap(
			(float)o,
			0.0F,
			(float)clusterConfiguration.maxDistanceFromEdgeAffectingChanceOfColumn(),
			clusterConfiguration.chanceOfColumnAtMaxDistanceFromCenter(),
			1.0F
		);
	}

	private static float randomBetweenBiased(RandomSource randomSource, float f, float g, float h, float i) {
		return ClampedNormalFloat.sample(randomSource, h, i, f, g);
	}
}
