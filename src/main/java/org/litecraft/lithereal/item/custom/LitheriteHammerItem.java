package org.litecraft.lithereal.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LitheriteHammerItem extends PickaxeItem {
    public LitheriteHammerItem(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    private int destroySurroundingBlocks(Level level, BlockPos pos) {
        int blocksBroken = 0;
        for (BlockPos p : new BlockPos[]{pos.north(), pos.south(), pos.east(), pos.west(),
                pos.offset(1, 0, 1), pos.offset(1, 0, -1), pos.offset(-1, 0, 1), pos.offset(-1, 0, -1)}) {
            blocksBroken += destroyIfNotBedrock(level, p);
        }

        return blocksBroken;
    }

    private int destroyIfNotBedrock(Level level, BlockPos pos) {
        if (level.getBlockState(pos).getBlock() != Blocks.BEDROCK && level.getBlockState(pos).getBlock() != Blocks.AIR) {
            level.destroyBlock(pos, true);
            return 1;
        }
        return 0;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity entity) {
        Direction facing = Direction.getNearest(blockPos.getX() - entity.getX(), blockPos.getY() - entity.getY(), blockPos.getZ() - entity.getZ());
        int blocksBroken = 0;
        if(facing == Direction.UP || facing == Direction.DOWN) {
            blocksBroken += destroySurroundingBlocks(level, blockPos);
        } else {
            if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                BlockPos[] positions = {
                        blockPos.above(), blockPos.below(),
                        blockPos.east(), blockPos.west(),
                        blockPos.offset(1, 1, 0), blockPos.offset(1, -1, 0),
                        blockPos.offset(-1, 1, 0), blockPos.offset(-1, -1, 0)
                };
                for (BlockPos pos : positions) {
                    if (level.getBlockState(pos).getBlock() != Blocks.BEDROCK && level.getBlockState(pos).getBlock() != Blocks.AIR) {
                        level.destroyBlock(pos, true);
                        blocksBroken++;
                    }
                }
            } else if (facing == Direction.EAST || facing == Direction.WEST) {
                BlockPos[] positions = {
                        blockPos.above(), blockPos.below(),
                        blockPos.north(), blockPos.south(),
                        blockPos.offset(0, 1, 1), blockPos.offset(0, -1, 1),
                        blockPos.offset(0, 1, -1), blockPos.offset(0, -1, -1)
                };
                for (BlockPos pos : positions) {
                    if (level.getBlockState(pos).getBlock() != Blocks.BEDROCK && level.getBlockState(pos).getBlock() != Blocks.AIR) {
                        level.destroyBlock(pos, true);
                        blocksBroken++;
                    }
                }
            }
        }

        itemStack.hurtAndBreak(blocksBroken, entity, (ent) -> ent.broadcastBreakEvent(EquipmentSlot.MAINHAND));

        return super.mineBlock(itemStack, level, blockState, blockPos, entity);
    }
}
