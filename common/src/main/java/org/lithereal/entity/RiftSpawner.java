package org.lithereal.entity;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import org.apache.commons.lang3.mutable.MutableInt;
import org.lithereal.block.EtherealRiftBlock;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.EtherealRiftBlockEntity;
import org.lithereal.world.ModDimensions;

import java.util.ArrayList;
import java.util.List;

public class RiftSpawner extends Entity {
    public static final ResourceKey<Level>[] DESTINATIONS = new ResourceKey[] {BuiltinDimensionTypes.OVERWORLD, BuiltinDimensionTypes.NETHER, BuiltinDimensionTypes.END, ModDimensions.ETHEREAL_CORE};
    public RiftSpawner(EntityType<? extends RiftSpawner> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (level.isClientSide) return;
        Direction.Axis axis = Direction.Axis.getRandom(random);
        RiftType type = RiftType.getRandom(random);
        BlockPos.MutableBlockPos basePos = blockPosition().mutable();
        while (!level.getBlockState(basePos).isAir()) basePos.move(0, 1, 0);
        type.build(level, axis, basePos.immutable(), Util.getRandom(level.getServer().levelKeys().toArray(ResourceKey[]::new), random), random.nextFloat() > 0.65F);
        remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean shouldRender(double d, double e, double f) {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    enum RiftType {
        SMALL,
        NORMAL,
        LARGE;
        public static final SimpleWeightedRandomList<RiftType> WEIGHTED_RANDOM_VALUES = new SimpleWeightedRandomList.Builder<RiftType>().add(SMALL, 4).add(NORMAL, 10).add(LARGE, 2).build();
        public static RiftType getRandom(RandomSource randomSource) {
            return WEIGHTED_RANDOM_VALUES.getRandom(randomSource).map(WeightedEntry.Wrapper::data).orElse(RiftType.NORMAL);
        }
        public void build(Level level, Direction.Axis axis, BlockPos pos, ResourceKey<Level> destination, boolean hasUniqueDestination) {
            Direction.Axis movementAxis = switch (axis) {
                case X, Z -> Direction.Axis.Y;
                case Y -> Direction.Axis.Z;
            };
            Direction.Axis otherMovementAxis = switch (axis) {
                case X, Y -> Direction.Axis.X;
                case Z -> Direction.Axis.Z;
            };
            Direction dirUp = Direction.get(Direction.AxisDirection.POSITIVE, movementAxis);
            Direction dirDown = dirUp.getOpposite();
            Direction dirRight = Direction.get(Direction.AxisDirection.POSITIVE, otherMovementAxis);
            Direction dirLeft = dirRight.getOpposite();
            BlockState attachedState = ModBlocks.ETHEREAL_RIFT.get().defaultBlockState().setValue(EtherealRiftBlock.AXIS, axis);
            MutableInt yOffset = new MutableInt(0);
            BlockPos.MutableBlockPos mutable = pos.mutable();
            List<BlockPos> toSet = new ArrayList<>();
            toSet.add(pos);
            switch (this) {
                case SMALL -> {
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable(), toSet, yOffset);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                }
                case NORMAL -> {
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable(), toSet, yOffset);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirDown);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                }
                case LARGE -> {
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable(), toSet, yOffset);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirDown);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirLeft);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirLeft, 2);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirUp);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                    mutable.move(dirRight);
                    setBlockSafe(level, mutable, mutable.immutable().above(yOffset.getValue()), toSet, yOffset);
                }
            }
            List<BlockPos> newPosSet = toSet.stream().map(upPos -> upPos.above(yOffset.getValue())).toList();
            newPosSet.forEach(blockPos -> {
                level.setBlockAndUpdate(blockPos, attachedState);
                setBlockInformation(level, blockPos, newPosSet, destination, hasUniqueDestination);
            });
        }
        public void setBlockInformation(Level level, BlockPos pos, List<BlockPos> attached, ResourceKey<Level> destination, boolean hasUniqueDestination) {
            if (level.getBlockEntity(pos) instanceof EtherealRiftBlockEntity etherealRiftBlockEntity) {
                if (hasUniqueDestination) etherealRiftBlockEntity.setDestination(destination);
                etherealRiftBlockEntity.setAttached(new ArrayList<>(attached));
                etherealRiftBlockEntity.setMaxLifespan(1200L);
            }
        }
        public void setBlockSafe(Level level, BlockPos.MutableBlockPos pos, BlockPos copyPos, List<BlockPos> toSet, MutableInt yOffset) {
            while (!(level.getBlockState(copyPos).isAir() || level.getBlockState(copyPos).canBeReplaced())) {
                yOffset.add(1);
                copyPos = copyPos.above();
            }
            toSet.add(pos.immutable());
        }
    }
}
