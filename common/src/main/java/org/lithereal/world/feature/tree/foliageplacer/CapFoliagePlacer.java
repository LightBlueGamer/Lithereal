package org.lithereal.world.feature.tree.foliageplacer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

public class CapFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<CapFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		instance -> foliagePlacerParts(instance).and(IntProvider.POSITIVE_CODEC.fieldOf("max_height").forGetter(CapFoliagePlacer::maxHeight)).apply(instance, CapFoliagePlacer::new)
	);

	public final IntProvider maxHeight;

	public CapFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider maxHeight) {
		super(radius, offset);
        this.maxHeight = maxHeight;
    }

	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return ModFoliagePlacers.CAP.get();
	}

	public IntProvider maxHeight() {
		return maxHeight;
	}

	@Override
	protected void createFoliage(
		LevelSimulatedReader levelSimulatedReader,
		FoliageSetter foliageSetter,
		RandomSource randomSource,
		TreeConfiguration treeConfiguration,
		int maxFreeTreeHeight,
		FoliageAttachment foliageAttachment,
		int foliageHeight,
		int foliageRadius,
		int offset
	) {
		boolean doubleTrunk = foliageAttachment.doubleTrunk();
		BlockPos blockPos = foliageAttachment.pos().above(offset);
		int foliageRadiusOffset = 0;
		int pass = 0;
		for (int yOff = -(foliageHeight - 1); yOff <= 0; yOff++) {
			if (pass == foliageHeight / 6)
				foliageRadiusOffset += 1;
			if (pass == (foliageHeight / 3) * 2)
				foliageRadiusOffset += 1;
			if (yOff == 0)
				foliageRadiusOffset += 1;
			this.placeLeavesRow(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, blockPos, foliageRadius - foliageRadiusOffset, yOff, doubleTrunk);
			this.placeLeavesRow(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, blockPos, foliageRadius + foliageAttachment.radiusOffset() - foliageRadiusOffset, yOff, doubleTrunk);
			pass++;
		}
	}

	@Override
	public int foliageHeight(RandomSource randomSource, int treeHeight, TreeConfiguration treeConfiguration) {
		return Math.min(this.maxHeight.sample(randomSource), (treeHeight / 3) * 2);
	}

	@Override
	protected boolean shouldSkipLocation(RandomSource randomSource, int xOff, int yOff, int zOff, int radius, boolean bl) {
		double dist = Mth.sqrt(xOff * xOff + zOff * zOff);
		if (yOff == 0)
			return dist > radius && radius > 0;
		int innerRadius = radius - 2;

		return dist <= innerRadius || (dist > radius && radius > 0);
	}
}
