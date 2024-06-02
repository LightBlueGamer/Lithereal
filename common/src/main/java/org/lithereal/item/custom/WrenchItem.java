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

    protected static <T extends Comparable<T>> BlockState rotateProperty(BlockState state, Property<T> property, Predicate<T> filter) {
        if (!state.hasProperty(property)) {
            return null;
        }

        T currentValue = state.getValue(property);
        List<T> array = new ArrayList<>(property.getPossibleValues());

        for(int i = array.size() - 1; i >= 0; i--) {
            T value = array.get(i);

            if (value == currentValue) {
                continue;
            }

            if (filter != null && filter.test(value)) {
                array.remove(value);
            }
        }

        if (array.size() <= 1) {
            return null;
        }

        int index = array.indexOf(currentValue);
        index = (index + 1) % array.size();

        T newValue = array.get(index);
        BlockState newState = state.setValue(property, newValue);

        return newState;
    }

    protected static BlockState rotateDirection(Level world, BlockPos pos, BlockState state) {
        DirectionProperty directionProperty = getDirectionProperty(state);

        if (directionProperty == null) {
            return null;
        }

        Block block = state.getBlock();
        Direction direction = state.getValue(directionProperty);

        return rotateProperty(state, directionProperty, (dir) -> {
            if (dir == direction) {
                return false;
            }

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
    }

    protected static BlockState rotateAxis(Level world, BlockPos pos, BlockState state) {
        EnumProperty<Direction.Axis> axisProperty = getAxisProperty(state);

        if (axisProperty == null) {
            return null;
        }

        return rotateProperty(state, axisProperty, null);
    }

    protected static BlockState rotateSlabType(Level world, BlockPos pos, BlockState state) {
        EnumProperty<SlabType> slabTypeProperty = getSlabTypeProperty(state);

        if (slabTypeProperty == null) {
            return null;
        }

        return rotateProperty(state, slabTypeProperty, (slabType) -> slabType == SlabType.DOUBLE);
    }

    protected static BlockState rotateRotation(Level world, BlockPos pos, BlockState state) {
        return rotateProperty(state, BlockStateProperties.ROTATION_16, null);
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

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        BlockState newState;

        if (isRotationAllowed(state)) {
            newState = rotateSlabType(world, pos, state);

            if (newState == null) {
                newState = rotateDirection(world, pos, state);
            }

            if (newState == null) {
                newState = rotateAxis(world, pos, state);
            }

            if (newState == null) {
                newState = rotateRotation(world, pos, state);
            }

            if (newState != null) {
                newState = updatePostPlacement(world, pos, newState);

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

    protected static boolean isRotationAllowed(BlockState blockState) {
        Block block = blockState.getBlock();

        return !(block instanceof BedBlock)
                && !(block instanceof PistonHeadBlock)
                && !(block instanceof EtherBatteryBlock)
                && !(block instanceof EtherCollectorBlock)
                && !(block instanceof FireCrucibleBlock)
                && !(block instanceof FreezingStationBlock)
                && !(block instanceof InfusementChamberBlock)
                && !(block instanceof LitherealVaultBlock)
                && (!blockState.hasProperty(BlockStateProperties.EXTENDED) || !blockState.getValue(BlockStateProperties.EXTENDED))
                && (!blockState.hasProperty(BlockStateProperties.CHEST_TYPE) || blockState.getValue(BlockStateProperties.CHEST_TYPE) == ChestType.SINGLE);
    }
    }