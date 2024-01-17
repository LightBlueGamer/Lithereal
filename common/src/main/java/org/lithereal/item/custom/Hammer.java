package org.lithereal.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

// Credits to ErrorMikey for hammer code https://github.com/ErrorMikey/JustHammers
public class Hammer extends DiggerItem {

    protected final int depth = 1;
    protected final int radius = 3;
    public Hammer(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(damage, attackSpeed, tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    protected boolean actualIsCorrectToolForDrops(BlockState state) {
        int i = this.getTier().getLevel();
        if (i < 3 && state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        } else if (i < 2 && state.is(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        } else {
            return (i >= 1 || !state.is(BlockTags.NEEDS_STONE_TOOL)) && state.is(BlockTags.MINEABLE_WITH_PICKAXE);
        }
    }

    @Override
    public boolean mineBlock(ItemStack hammerStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (level.isClientSide || blockState.getDestroySpeed(level, blockPos) == 0.0F) return true;

        HitResult pick = livingEntity.pick(20D, 1F, false);

        // Not possible?
        if (!(pick instanceof BlockHitResult)) return super.mineBlock(hammerStack, level, blockState, blockPos, livingEntity);


        this.findAndBreakNearBlocks(pick, blockPos, hammerStack, level, livingEntity);
        return super.mineBlock(hammerStack, level, blockState, blockPos, livingEntity);
    }

    public void findAndBreakNearBlocks(HitResult pick, BlockPos blockPos, ItemStack hammerStack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player)) return;

        var size = (radius / 2);
        var offset = size - 1;

        Direction direction = ((BlockHitResult) pick).getDirection();
        var boundingBox = switch (direction) {
            case DOWN, UP -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - (direction == Direction.UP ? depth - 1 : 0), blockPos.getZ() - size, blockPos.getX() + size, blockPos.getY() + (direction == Direction.DOWN ? depth - 1 : 0), blockPos.getZ() + size);
            case NORTH, SOUTH -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - size + offset, blockPos.getZ() - (direction == Direction.SOUTH ? depth - 1 : 0), blockPos.getX() + size, blockPos.getY() + size + offset, blockPos.getZ() + (direction == Direction.NORTH ? depth - 1 : 0));
            case WEST, EAST -> new BoundingBox(blockPos.getX() - (direction == Direction.EAST ? depth - 1 : 0), blockPos.getY() - size + offset, blockPos.getZ() - size, blockPos.getX() + (direction == Direction.WEST ? depth - 1 : 0), blockPos.getY() + size + offset, blockPos.getZ() + size);
        };

        int damage = 0;
        Iterator<BlockPos> iterator = BlockPos.betweenClosedStream(boundingBox).iterator();
        Set<BlockPos> removedPos = new HashSet<>();
        while (iterator.hasNext()) {
            var pos = iterator.next();

            if (damage >= (hammerStack.getMaxDamage() - hammerStack.getDamageValue() - 1)) {
                break;
            }

            BlockState targetState = level.getBlockState(pos);
            if (pos == blockPos || removedPos.contains(pos) || !canDestroy(targetState, level, pos)) {
                continue;
            }
            // Skips any blocks that require a higher tier hammer
            if (!actualIsCorrectToolForDrops(targetState)) {
                continue;
            }

            removedPos.add(pos);
            level.destroyBlock(pos, false, livingEntity);

            if (!player.isCreative()) {
                boolean correctToolForDrops = hammerStack.isCorrectToolForDrops(targetState);
                if (correctToolForDrops) {
                    targetState.spawnAfterBreak((ServerLevel) level, pos, hammerStack, true);
                    List<ItemStack> drops = Block.getDrops(targetState, (ServerLevel) level, pos, level.getBlockEntity(pos), livingEntity, hammerStack);
                    drops.forEach(e ->
                            Block.popResourceFromFace(level, pos, ((BlockHitResult) pick).getDirection(), e));
                }
            }
            damage ++;
        }

        if (damage != 0 && !player.isCreative()) {
            hammerStack.hurtAndBreak(damage, livingEntity, (livingEntityx) -> {
                livingEntityx.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
    }

    protected boolean canDestroy(BlockState targetState, Level level, BlockPos pos) {
        if (targetState.getDestroySpeed(level, pos) <= 0) {
            return false;
        }

        return level.getBlockEntity(pos) == null;
    }
}
