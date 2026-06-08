package org.lithereal.world.feature.tree.trunkplacer;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class MegaStraightTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<MegaStraightTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> trunkPlacerParts(instance).apply(instance, MegaStraightTrunkPlacer::new));

    public MegaStraightTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    protected @NotNull TrunkPlacerType<?> type() {
        return ModTrunkPlacers.MEGA_STRAIGHT.get();
    }

    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int height, BlockPos blockPos, TreeConfiguration treeConfiguration) {
        BlockPos[] positions = new BlockPos[] {blockPos, blockPos.east(), blockPos.south(), blockPos.east().south()};
        BlockPos belowPos = blockPos.below();
        setDirtAt(levelSimulatedReader, biConsumer, randomSource, belowPos, treeConfiguration);
        setDirtAt(levelSimulatedReader, biConsumer, randomSource, belowPos.east(), treeConfiguration);
        setDirtAt(levelSimulatedReader, biConsumer, randomSource, belowPos.south(), treeConfiguration);
        setDirtAt(levelSimulatedReader, biConsumer, randomSource, belowPos.south().east(), treeConfiguration);
        int originalX = blockPos.getX();
        int originalY = blockPos.getY();
        int originalZ = blockPos.getZ();
        int maxHeight = height + 2;
        int branchHeight = Mth.floor((double)maxHeight * 0.618);

        List<FoliagePlacer.FoliageAttachment> retAttachments = Lists.newArrayList();
        retAttachments.add(new FoliagePlacer.FoliageAttachment(blockPos.above(branchHeight + 1), 0, true));

        for (BlockPos basePos : positions) this.makeLimb(levelSimulatedReader, biConsumer, randomSource, basePos, basePos.above(branchHeight), true, treeConfiguration);
        for(int xOff = -1; xOff <= 2; ++xOff) {
            for(int zOff = -1; zOff <= 2; ++zOff) {
                if ((xOff < 0 || xOff > 1 || zOff < 0 || zOff > 1)) {
                    if (randomSource.nextInt(2) <= 0) {
                        int yHeight = randomSource.nextInt(3) + 1;

                        for (int yOffset = 0; yOffset < yHeight; ++yOffset) {
                            this.placeLog(levelSimulatedReader, biConsumer, randomSource, new BlockPos(originalX + xOff, originalY + yOffset - 1, originalZ + zOff), treeConfiguration);
                        }
                    }
                    if (randomSource.nextInt(2) <= 0) {
                        int yHeight = randomSource.nextInt(3) + 2;

                        for (int yOffset = 0; yOffset < yHeight; yOffset++) {
                            this.placeLog(levelSimulatedReader, biConsumer, randomSource, new BlockPos(originalX + xOff, originalY + branchHeight - yOffset, originalZ + zOff), treeConfiguration);
                        }
                    }
                }
            }
        }

        return retAttachments;
    }

    private void makeLimb(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, BlockPos blockPos, BlockPos blockPos2, boolean canPlace, TreeConfiguration treeConfiguration) {
        if (canPlace || !Objects.equals(blockPos, blockPos2)) {
            BlockPos distPos = blockPos2.offset(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
            int steps = this.getSteps(distPos);
            float adjX = (float) distPos.getX() / (float) steps;
            float adjY = (float) distPos.getY() / (float) steps;
            float adjZ = (float) distPos.getZ() / (float) steps;

            for (int cnt = 0; cnt <= steps; ++cnt) {
                BlockPos resPos = blockPos.offset(Mth.floor(0.5F + (float) cnt * adjX), Mth.floor(0.5F + (float) cnt * adjY), Mth.floor(0.5F + (float) cnt * adjZ));
                if (canPlace) {
                    Axis logAxis = this.getLogAxis(blockPos, resPos);
                    this.placeLog(levelSimulatedReader, biConsumer, randomSource, resPos, treeConfiguration, (blockState) -> blockState.trySetValue(RotatedPillarBlock.AXIS, logAxis));
                } else if (!this.isFree(levelSimulatedReader, resPos)) {
                    return;
                }
            }

        }
    }

    private int getSteps(BlockPos blockPos) {
        int x = Mth.abs(blockPos.getX());
        int y = Mth.abs(blockPos.getY());
        int z = Mth.abs(blockPos.getZ());
        return Math.max(x, Math.max(y, z));
    }

    private Direction.Axis getLogAxis(BlockPos blockPos, BlockPos blockPos2) {
        Direction.Axis axis = Axis.Y;
        int xDist = Math.abs(blockPos2.getX() - blockPos.getX());
        int zDist = Math.abs(blockPos2.getZ() - blockPos.getZ());
        int greaterDist = Math.max(xDist, zDist);
        if (greaterDist > 0) {
            if (xDist == greaterDist) axis = Axis.X;
            else axis = Axis.Z;
        }

        return axis;
    }
}
