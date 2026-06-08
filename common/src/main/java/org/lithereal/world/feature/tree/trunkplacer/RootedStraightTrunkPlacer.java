package org.lithereal.world.feature.tree.trunkplacer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

public class RootedStraightTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<RootedStraightTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> trunkPlacerParts(instance).apply(instance, RootedStraightTrunkPlacer::new));

    public RootedStraightTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    protected @NotNull TrunkPlacerType<?> type() {
        return ModTrunkPlacers.ROOTED_STRAIGHT.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader levelSimulatedReader,
            BiConsumer<BlockPos, BlockState> biConsumer,
            RandomSource randomSource,
            int height,
            BlockPos blockPos,
            TreeConfiguration treeConfiguration
    ) {
        setDirtAt(levelSimulatedReader, biConsumer, randomSource, blockPos.below(), treeConfiguration);

        for (int offset = 0; offset < height; offset++) {
            this.placeLog(levelSimulatedReader, biConsumer, randomSource, blockPos.above(offset), treeConfiguration);
        }

        int originalX = blockPos.getX();
        int originalY = blockPos.getY();
        int originalZ = blockPos.getZ();

        for(int xOff = -1; xOff <= 1; ++xOff) {
            for(int zOff = -1; zOff <= 1; ++zOff) {
                if ((xOff != 0 || zOff != 0) && randomSource.nextInt(2) <= 0) {
                    int yHeight = randomSource.nextInt(height / 3) + 1;

                    for(int yOffset = 0; yOffset < yHeight; ++yOffset) {
                        this.placeLog(levelSimulatedReader, biConsumer, randomSource, new BlockPos(originalX + xOff, originalY + yOffset - 1, originalZ + zOff), treeConfiguration);
                    }
                }
            }
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(blockPos.above(height), 0, false));
    }
}
