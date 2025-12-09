package org.lithereal.world.feature.tree.trunkplacer;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
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

public class MegaOakTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<MegaOakTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> trunkPlacerParts(instance).apply(instance, MegaOakTrunkPlacer::new));

    public MegaOakTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.MEGA_OAK.get();
    }

    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int height, BlockPos blockPos, TreeConfiguration treeConfiguration) {
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

        int maxSteps = Math.min(3, Mth.floor(1.382 + Math.pow((double) maxHeight / (double)5.0F, 2.0F)));
        int branchBase = blockPos.getY() + branchHeight;
        int baseYOffset = maxHeight - 5;
        List<FoliageCoords> retCoords = Lists.newArrayList();
        retCoords.add(new FoliageCoords(blockPos.above(baseYOffset), branchBase, true));

        for(; baseYOffset >= 0; --baseYOffset) {
            float distToHeight = treeShape(maxHeight, baseYOffset);
            if (!(distToHeight < 0.0F)) {
                for(int cnt = 0; cnt < maxSteps; ++cnt) {
                    double distOffset = (double)distToHeight * ((double)randomSource.nextFloat() + 1);
                    double randAngle = (double)(randomSource.nextFloat() * 2.0F) * Math.PI;
                    double xOffset = distOffset * Math.sin(randAngle) + (double)0.5F;
                    double zOffset = distOffset * Math.cos(randAngle) + (double)0.5F;
                    int flooredXOffset = Mth.floor(xOffset);
                    int flooredZOffset = Mth.floor(zOffset);
                    BlockPos basePos = blockPos;
                    if (flooredXOffset >= 1 || randomSource.nextFloat() > 0.6) basePos = blockPos.east();
                    if (flooredZOffset >= 1 || randomSource.nextFloat() > 0.6) basePos = blockPos.south();
                    BlockPos newPos = basePos.offset(flooredXOffset, baseYOffset - 1, flooredZOffset).mutable();
                    BlockPos upperPos = newPos.mutable().above(5);
                    if (this.makeLimb(levelSimulatedReader, biConsumer, randomSource, newPos, upperPos, false, treeConfiguration)) {
                        int xDiff = basePos.getX() - newPos.getX();
                        int zDiff = basePos.getZ() - newPos.getZ();
                        double maxY = (double) newPos.getY() - Math.sqrt(xDiff * xDiff + zDiff * zDiff) * 0.381;
                        int raisedY = maxY > (double) branchBase ? branchBase : (int) maxY;
                        BlockPos finalPos = new BlockPos(basePos.getX(), raisedY, basePos.getZ());
                        if (this.makeLimb(levelSimulatedReader, biConsumer, randomSource, finalPos, newPos, false, treeConfiguration)) {
                            retCoords.add(new FoliageCoords(newPos, basePos, finalPos.getY(), false));
                        }
                    }
                }
            }
        }

        for (BlockPos basePos : positions) this.makeLimb(levelSimulatedReader, biConsumer, randomSource, basePos, basePos.above(branchHeight), true, treeConfiguration);
        this.makeBranches(levelSimulatedReader, biConsumer, randomSource, maxHeight, blockPos, retCoords, treeConfiguration);

        for(int xOff = -1; xOff <= 2; ++xOff) {
            for(int zOff = -1; zOff <= 2; ++zOff) {
                if ((xOff < 0 || xOff > 1 || zOff < 0 || zOff > 1) && randomSource.nextInt(2) <= 0) {
                    int yHeight = randomSource.nextInt(3) + 1;

                    for(int yOffset = 0; yOffset < yHeight; ++yOffset) {
                        this.placeLog(levelSimulatedReader, biConsumer, randomSource, new BlockPos(originalX + xOff, originalY + yOffset - 1, originalZ + zOff), treeConfiguration);
                    }
                }
            }
        }
        List<FoliagePlacer.FoliageAttachment> list2 = Lists.newArrayList();

        for(FoliageCoords foliageCoords : retCoords) {
            if (this.trimBranches(maxHeight, foliageCoords.getBranchBase() - blockPos.getY())) {
                list2.add(foliageCoords.attachment);
            }
        }

        return list2;
    }

    private boolean makeLimb(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, BlockPos blockPos, BlockPos blockPos2, boolean canPlace, TreeConfiguration treeConfiguration) {
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
                    return false;
                }
            }

        }
        return true;
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

    private boolean trimBranches(int maxHeight, int height) {
        return (double)height >= (double)maxHeight * 0.2;
    }

    private void makeBranches(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int maxHeight, BlockPos blockPos, List<FoliageCoords> list, TreeConfiguration treeConfiguration) {
        for(FoliageCoords foliageCoords : list) {
            int branchBase = foliageCoords.getBranchBase();
            BlockPos basePos = foliageCoords.getBasePos();
            BlockPos pos = new BlockPos(basePos.getX(), branchBase, basePos.getZ());
            if (!pos.equals(foliageCoords.attachment.pos()) && this.trimBranches(maxHeight, branchBase - blockPos.getY())) {
                this.makeLimb(levelSimulatedReader, biConsumer, randomSource, pos, foliageCoords.attachment.pos(), true, treeConfiguration);
            }
        }

    }

    private static float treeShape(int maxHeight, int baseYOffset) {
        if ((float)baseYOffset < (float)maxHeight * 0.3F) {
            return -1.0F;
        } else {
            float halfHeight = (float)maxHeight / 2.0F;
            float diff = halfHeight - (float)baseYOffset;
            float distToHeight = Mth.sqrt(halfHeight * halfHeight - diff * diff);
            if (diff == 0.0F) {
                distToHeight = halfHeight;
            } else if (Math.abs(diff) >= halfHeight) {
                return 0.0F;
            }

            return distToHeight * 0.5F;
        }
    }

    static class FoliageCoords {
        final FoliagePlacer.FoliageAttachment attachment;
        private final BlockPos basePos;
        private final int branchBase;

        public FoliageCoords(BlockPos blockPos, int branchBase, boolean doubleTrunk) {
            this(blockPos, blockPos, branchBase, doubleTrunk);
        }

        public FoliageCoords(BlockPos blockPos, BlockPos base, int branchBase, boolean doubleTrunk) {
            this.attachment = new FoliagePlacer.FoliageAttachment(blockPos, 0, doubleTrunk);
            this.branchBase = branchBase;
            this.basePos = base;
        }

        public int getBranchBase() {
            return this.branchBase;
        }

        public BlockPos getBasePos() {
            return basePos;
        }
    }
}
