package org.lithereal.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import org.lithereal.block.custom.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class WrenchItem extends Item {

    public WrenchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        if (isRotationAllowed(state)) {
            BlockState newState = rotateBlock(world, pos, state);

            if (newState != null) {
                Player player = context.getPlayer();
                SoundType soundType = state.getSoundType();

                world.setBlock(pos, newState, 11);
                world.playSound(player, pos, soundType.getPlaceSound(), SoundSource.BLOCKS, 1.0f, world.random.nextFloat() * 0.4f + 0.8f);
                world.playSound(player, pos, SoundType.METAL.getPlaceSound(), SoundSource.BLOCKS, 1.0f, world.random.nextFloat() * 0.4f + 0.8f);

                if (player != null) {
                    context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }

    protected static boolean isRotationAllowed(BlockState state) {
        Block block = state.getBlock();

        return !(block instanceof BedBlock)
                && !(block instanceof PistonHeadBlock)
                && !(block instanceof EtherBatteryBlock)
                && !(block instanceof EtherCollectorBlock)
                && !(block instanceof FireCrucibleBlock)
                && !(block instanceof FreezingStationBlock)
                && !(block instanceof InfusementChamberBlock)
                && !(block instanceof LitherealVaultBlock)
                && (!state.hasProperty(BlockStateProperties.EXTENDED) || !state.getValue(BlockStateProperties.EXTENDED))
                && (!state.hasProperty(BlockStateProperties.CHEST_TYPE) || state.getValue(BlockStateProperties.CHEST_TYPE) == ChestType.SINGLE);
    }

    protected static BlockState rotateBlock(Level world, BlockPos pos, BlockState state) {
        BlockState newState = rotateDirection(world, pos, state);
        if (newState != null) {
            return newState;
        }

        newState = rotateAxis(world, pos, state);
        if (newState != null) {
            return newState;
        }

        newState = rotateSlabType(world, pos, state);
        if (newState != null) {
            return newState;
        }

        return rotateRotation(world, pos, state);
    }

    protected static BlockState updatePostPlacement(Level world, BlockPos pos, BlockState state) {
        DirectionProperty directionProperty = getDirectionProperty(state);

        if (directionProperty != null) {
            Direction facing = state.getValue(directionProperty);

            if (facing != null) {
                BlockPos facingPos = pos.relative(facing);
                BlockState facingState = world.getBlockState(facingPos);

                state = state.updateShape(facing, facingState, world, pos, facingPos);
            }
        }

        return state;
    }

    protected static <T extends Comparable<T>> List<T> filterPossibleValues(Property<T> property, T currentValue, Predicate<T> filter) {
        List<T> filteredValues = new ArrayList<>(property.getPossibleValues());

        for (T value : property.getPossibleValues()) {
            if (filter != null && filter.test(value)) {
                filteredValues.remove(value);
            }
        }

        return filteredValues;
    }

    protected static BlockState rotateDirection(Level world, BlockPos pos, BlockState state) {
        DirectionProperty directionProperty = getDirectionProperty(state);

        if (directionProperty == null) {
            return null;
        }

        Block block = state.getBlock();
        Direction direction = state.getValue(directionProperty);

        List<Direction> filteredDirections = filterPossibleValues(directionProperty, direction, dir -> {
            BlockState tmpState = state.setValue(directionProperty, dir);
            boolean isValidPos = tmpState.canSurvive(world, pos);

            BlockState facingState = world.getBlockState(pos.relative(dir, -1));
            Block facingBlock = facingState.getBlock();

            if (isValidPos && facingBlock instanceof WallSignBlock && block instanceof WallSignBlock) {
                if (facingState.getValue(directionProperty).getOpposite().equals(dir)) {
                    isValidPos = false;
                }
            }

            return !isValidPos;
        });

        if (filteredDirections.size() <= 1) {
            return null;
        }

        int index = filteredDirections.indexOf(direction);
        index = (index + 1) % filteredDirections.size();

        Direction newDirection = filteredDirections.get(index);
        return state.setValue(directionProperty, newDirection);
    }

    protected static BlockState rotateAxis(Level world, BlockPos pos, BlockState state) {
        EnumProperty<Direction.Axis> axisProperty = getAxisProperty(state);

        if (axisProperty == null) {
            return null;
        }

        List<Direction.Axis> filteredAxes = filterPossibleValues(axisProperty, state.getValue(axisProperty), null);

        if (filteredAxes.size() <= 1) {
            return null;
        }

        int index = filteredAxes.indexOf(state.getValue(axisProperty));
        index = (index + 1) % filteredAxes.size();

        Direction.Axis newAxis = filteredAxes.get(index);
        return state.setValue(axisProperty, newAxis);
    }

    protected static BlockState rotateSlabType(Level world, BlockPos pos, BlockState state) {
        EnumProperty<SlabType> slabTypeProperty = getSlabTypeProperty(state);

        if (slabTypeProperty == null) {
            return null;
        }

        List<SlabType> filteredSlabTypes = filterPossibleValues(slabTypeProperty, state.getValue(slabTypeProperty), slabType -> slabType == SlabType.DOUBLE);

        if (filteredSlabTypes.size() <= 1) {
            return null;
        }

        int index = filteredSlabTypes.indexOf(state.getValue(slabTypeProperty));
        index = (index + 1) % filteredSlabTypes.size();

        SlabType newSlabType = filteredSlabTypes.get(index);
        return state.setValue(slabTypeProperty, newSlabType);
    }

    protected static BlockState rotateRotation(Level world, BlockPos pos, BlockState state) {
        BlockState rotatedState = rotateProperty(state, BlockStateProperties.ROTATION_16, null);
        return rotatedState != null ? rotatedState : state;
    }

    protected static DirectionProperty getDirectionProperty(BlockState state) {
        if (state.hasProperty(BlockStateProperties.FACING)) {
            return BlockStateProperties.FACING;
        } else if (state.hasProperty(BlockStateProperties.FACING_HOPPER)) {
            return BlockStateProperties.FACING_HOPPER;
        } else if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            return BlockStateProperties.HORIZONTAL_FACING;
        } else {
            return null;
        }
    }

    protected static EnumProperty<Direction.Axis> getAxisProperty(BlockState state) {
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            return BlockStateProperties.HORIZONTAL_AXIS;
        } else if (state.hasProperty(BlockStateProperties.AXIS)) {
            return BlockStateProperties.AXIS;
        } else {
            return null;
        }
    }

    protected static EnumProperty<SlabType> getSlabTypeProperty(BlockState state) {
        if (state.hasProperty(BlockStateProperties.SLAB_TYPE)) {
            return BlockStateProperties.SLAB_TYPE;
        } else {
            return null;
        }
    }

    protected static <T extends Comparable<T>> BlockState rotateProperty(BlockState state, Property<T> property, Predicate<T> filter) {
        if (!state.hasProperty(property)) {
            return null;
        }

        T currentValue = state.getValue(property);
        List<T> filteredValues = filterPossibleValues(property, currentValue, filter);

        if (filteredValues.size() <= 1) {
            return null;
        }

        int index = filteredValues.indexOf(currentValue);
        index = (index + 1) % filteredValues.size();

        T newValue = filteredValues.get(index);
        return state.setValue(property, newValue);
    }
}