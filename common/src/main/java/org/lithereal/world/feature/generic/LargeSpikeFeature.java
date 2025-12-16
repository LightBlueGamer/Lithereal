package org.lithereal.world.feature.generic;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.lithereal.world.feature.generic.configurations.DripstoneReplacementConfiguration;
import org.lithereal.world.feature.generic.configurations.LargeSpikeConfiguration;

import java.util.Optional;

public class LargeSpikeFeature extends Feature<LargeSpikeConfiguration> {
	public LargeSpikeFeature(Codec<LargeSpikeConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<LargeSpikeConfiguration> featurePlaceContext) {
		WorldGenLevel worldGenLevel = featurePlaceContext.level();
		BlockPos blockPos = featurePlaceContext.origin();
		LargeSpikeConfiguration largeSpikeConfiguration = featurePlaceContext.config();
		RandomSource randomSource = featurePlaceContext.random();
        DripstoneReplacementConfiguration dripstoneReplacementConfiguration = largeSpikeConfiguration.dripstoneReplacementConfiguration();
		if (!DripstoneReplacementUtils.isEmptyOrWater(worldGenLevel, blockPos)) {
			return false;
		} else {
			Optional<Column> optional = Column.scan(
				worldGenLevel, blockPos, largeSpikeConfiguration.floorToCeilingSearchRange(), DripstoneReplacementUtils::isEmptyOrWater, blockState -> DripstoneReplacementUtils.isBaseOrLava(dripstoneReplacementConfiguration, blockState)
			);
			if (optional.isPresent() && optional.get() instanceof Column.Range range) {
                if (range.height() < 4) {
					return false;
				} else {
					int i = (int)(range.height() * largeSpikeConfiguration.maxColumnRadiusToCaveHeightRatio());
					int j = Mth.clamp(i, largeSpikeConfiguration.columnRadius().getMinValue(), largeSpikeConfiguration.columnRadius().getMaxValue());
					int k = Mth.randomBetweenInclusive(randomSource, largeSpikeConfiguration.columnRadius().getMinValue(), j);
					LargeSpike largeSpike = makeSpike(
						blockPos.atY(range.ceiling() - 1), false, randomSource, k, largeSpikeConfiguration.stalactiteBluntness(), largeSpikeConfiguration.heightScale()
					);
					LargeSpike largeSpike2 = makeSpike(
						blockPos.atY(range.floor() + 1), true, randomSource, k, largeSpikeConfiguration.stalagmiteBluntness(), largeSpikeConfiguration.heightScale()
					);
					LargeSpikeFeature.WindOffsetter windOffsetter;
					if (largeSpike.isSuitableForWind(largeSpikeConfiguration) && largeSpike2.isSuitableForWind(largeSpikeConfiguration)) {
						windOffsetter = new LargeSpikeFeature.WindOffsetter(blockPos.getY(), randomSource, largeSpikeConfiguration.windSpeed());
					} else {
						windOffsetter = LargeSpikeFeature.WindOffsetter.noWind();
					}

					boolean success = largeSpike.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldGenLevel, windOffsetter);
					boolean success2 = largeSpike2.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldGenLevel, windOffsetter);
					if (success) {
						largeSpike.placeBlocks(dripstoneReplacementConfiguration, worldGenLevel, randomSource, windOffsetter);
					}

					if (success2)
                        largeSpike2.placeBlocks(dripstoneReplacementConfiguration, worldGenLevel, randomSource, windOffsetter);

					return true;
				}
			} else {
				return false;
			}
		}
	}

	private static LargeSpike makeSpike(
            BlockPos blockPos, boolean bl, RandomSource randomSource, int i, FloatProvider floatProvider, FloatProvider floatProvider2
	) {
		return new LargeSpike(blockPos, bl, i, floatProvider.sample(randomSource), floatProvider2.sample(randomSource));
	}

	static final class LargeSpike {
		private BlockPos root;
		private final boolean pointingUp;
		private int radius;
		private final double bluntness;
		private final double scale;

		LargeSpike(BlockPos blockPos, boolean bl, int i, double d, double e) {
			this.root = blockPos;
			this.pointingUp = bl;
			this.radius = i;
			this.bluntness = d;
			this.scale = e;
		}

		private int getHeight() {
			return this.getHeightAtRadius(0.0F);
		}

		private int getMinY() {
			return this.pointingUp ? this.root.getY() : this.root.getY() - this.getHeight();
		}

		private int getMaxY() {
			return !this.pointingUp ? this.root.getY() : this.root.getY() + this.getHeight();
		}

		boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel worldGenLevel, LargeSpikeFeature.WindOffsetter windOffsetter) {
			while (this.radius > 1) {
				BlockPos.MutableBlockPos mutableBlockPos = this.root.mutable();
				int i = Math.min(10, this.getHeight());

				for (int j = 0; j < i; j++) {
					if (worldGenLevel.getBlockState(mutableBlockPos).is(Blocks.LAVA)) {
						return false;
					}

					if (DripstoneReplacementUtils.isCircleMostlyEmbeddedInStone(worldGenLevel, windOffsetter.offset(mutableBlockPos), this.radius)) {
						this.root = mutableBlockPos;
						return true;
					}

					mutableBlockPos.move(this.pointingUp ? Direction.DOWN : Direction.UP);
				}

				this.radius /= 2;
			}

			return false;
		}

		private int getHeightAtRadius(float dist) {
			return (int)DripstoneReplacementUtils.getDripstoneHeight(dist, this.radius, this.scale, this.bluntness);
		}

		void placeBlocks(DripstoneReplacementConfiguration dripstoneReplacementConfiguration, WorldGenLevel worldGenLevel, RandomSource randomSource, LargeSpikeFeature.WindOffsetter windOffsetter) {
			for (int xOff = -this.radius; xOff <= this.radius; xOff++) {
				for (int zOff = -this.radius; zOff <= this.radius; zOff++) {
					float dist = Mth.sqrt(xOff * xOff + zOff * zOff);
                    float coreRadius = this.radius * dripstoneReplacementConfiguration.corePercentage();
					if (!(dist > this.radius)) {
						int height = this.getHeightAtRadius(dist);
                        int coreHeight = (int) (height * dripstoneReplacementConfiguration.corePercentage());
						if (height > 0) {
							if (randomSource.nextFloat() < 0.2) {
								height = (int)(height * Mth.randomBetween(randomSource, 0.8F, 1.0F));
							}

							BlockPos.MutableBlockPos mutableBlockPos = this.root.offset(xOff, 0, zOff).mutable();
							boolean placed = false;
							int maxHeight = this.pointingUp ? worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE_WG, mutableBlockPos.getX(), mutableBlockPos.getZ()) : Integer.MAX_VALUE;

							for (int yOff = 0; yOff < height && mutableBlockPos.getY() < maxHeight; yOff++) {
								BlockPos blockPos = windOffsetter.offset(mutableBlockPos);
								if (DripstoneReplacementUtils.isEmptyOrWaterOrLava(worldGenLevel, blockPos)) {
									placed = true;
									worldGenLevel.setBlock(blockPos, dist <= coreRadius && yOff <= coreHeight ? dripstoneReplacementConfiguration.core().getState(worldGenLevel, randomSource, blockPos) : dripstoneReplacementConfiguration.outer().getState(worldGenLevel, randomSource, blockPos), 2);
								} else if (placed && worldGenLevel.getBlockState(blockPos).is(dripstoneReplacementConfiguration.replaceable())) {
									break;
								}

								mutableBlockPos.move(this.pointingUp ? Direction.UP : Direction.DOWN);
							}
						}
					}
				}
			}
		}

		boolean isSuitableForWind(LargeSpikeConfiguration largeSpikeConfiguration) {
			return this.radius >= largeSpikeConfiguration.minRadiusForWind() && this.bluntness >= largeSpikeConfiguration.minBluntnessForWind();
		}
	}

	static final class WindOffsetter {
		private final int originY;
		@Nullable
		private final Vec3 windSpeed;

		WindOffsetter(int i, RandomSource randomSource, FloatProvider floatProvider) {
			this.originY = i;
			float f = floatProvider.sample(randomSource);
			float g = Mth.randomBetween(randomSource, 0.0F, (float) Math.PI);
			this.windSpeed = new Vec3(Mth.cos(g) * f, 0.0, Mth.sin(g) * f);
		}

		private WindOffsetter() {
			this.originY = 0;
			this.windSpeed = null;
		}

		static LargeSpikeFeature.WindOffsetter noWind() {
			return new LargeSpikeFeature.WindOffsetter();
		}

		BlockPos offset(BlockPos blockPos) {
			if (this.windSpeed == null) {
				return blockPos;
			} else {
				int i = this.originY - blockPos.getY();
				Vec3 vec3 = this.windSpeed.scale(i);
				return blockPos.offset(Mth.floor(vec3.x), 0, Mth.floor(vec3.z));
			}
		}
	}
}
