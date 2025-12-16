package org.lithereal.world.feature.tree.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttachedToTrunkDecorator extends TreeDecorator {
	public static final MapCodec<AttachedToTrunkDecorator> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(arg -> arg.probability),
				Codec.intRange(0, 16).fieldOf("exclusion_radius_xz").forGetter(arg -> arg.exclusionRadiusXZ),
				Codec.intRange(0, 16).fieldOf("exclusion_radius_y").forGetter(arg -> arg.exclusionRadiusY),
				BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(arg -> arg.blockProvider),
				Codec.intRange(1, 16).fieldOf("required_empty_blocks").forGetter(arg -> arg.requiredEmptyBlocks),
				ExtraCodecs.nonEmptyList(Direction.CODEC.listOf()).fieldOf("directions").forGetter(arg -> arg.directions)
			)
			.apply(instance, AttachedToTrunkDecorator::new)
	);
	protected final float probability;
	protected final int exclusionRadiusXZ;
	protected final int exclusionRadiusY;
	protected final BlockStateProvider blockProvider;
	protected final int requiredEmptyBlocks;
	protected final List<Direction> directions;

	public AttachedToTrunkDecorator(float probability, int exclusionRadiusXZ, int exclusionRadiusY, BlockStateProvider blockStateProvider, int requiredEmptyBlocks, List<Direction> directions) {
		this.probability = probability;
		this.exclusionRadiusXZ = exclusionRadiusXZ;
		this.exclusionRadiusY = exclusionRadiusY;
		this.blockProvider = blockStateProvider;
		this.requiredEmptyBlocks = requiredEmptyBlocks;
		this.directions = directions;
	}

	@Override
	public void place(Context arg) {
		Set<BlockPos> set = new HashSet();
		RandomSource randomSource = arg.random();

		for (BlockPos blockPos : Util.shuffledCopy(arg.logs(), randomSource)) {
			Direction direction = Util.getRandom(this.directions, randomSource);
			BlockPos offset = blockPos.relative(direction);
			if (!set.contains(offset) && randomSource.nextFloat() < this.probability && this.hasRequiredEmptyBlocks(arg, blockPos, direction)) {
				BlockPos blockPos3 = offset.offset(-this.exclusionRadiusXZ, -this.exclusionRadiusY, -this.exclusionRadiusXZ);
				BlockPos blockPos4 = offset.offset(this.exclusionRadiusXZ, this.exclusionRadiusY, this.exclusionRadiusXZ);

				for (BlockPos blockPos5 : BlockPos.betweenClosed(blockPos3, blockPos4)) {
					set.add(blockPos5.immutable());
				}

				arg.setBlock(offset, this.blockProvider.getState(randomSource, offset));
			}
		}
	}

	private boolean hasRequiredEmptyBlocks(Context arg, BlockPos arg2, Direction arg3) {
		for (int i = 1; i <= this.requiredEmptyBlocks; i++) {
			BlockPos blockPos = arg2.relative(arg3, i);
			if (!arg.isAir(blockPos)) {
				return false;
			}
		}

		return true;
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return TreeDecoratorType.ATTACHED_TO_LEAVES;
	}
}
