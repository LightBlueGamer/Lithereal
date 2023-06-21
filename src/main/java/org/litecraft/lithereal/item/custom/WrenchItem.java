package org.litecraft.lithereal.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.litecraft.lithereal.block.ModBlocks;

public class WrenchItem extends Item {
    public WrenchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        Direction side = context.getClickedFace();

        stack.hurtAndBreak(1, player, (ent) -> ent.broadcastBreakEvent(EquipmentSlot.MAINHAND));

        if(getBlock(level, pos) == ModBlocks.INFUSEMENT_CHAMBER_CONTROLLER.get()) {
            BlockPos corePos = null;
            Block coreBlock = ModBlocks.INFUSEMENT_CHAMBER_CORE.get();

            boolean validStructure = true;
            boolean hasController = false;

            Direction[] directions = {
              Direction.EAST,
              Direction.WEST,
              Direction.SOUTH,
              Direction.NORTH
            };

            for (Direction direction : directions) {
                BlockPos adjacentPos = pos.relative(direction, 1);
                if (getBlock(level, adjacentPos) == coreBlock) {
                    corePos = adjacentPos;
                    break;
                }
            }

            if(corePos == null) {
                player.displayClientMessage(Component.translatable("message.lithereal.missing_infusement_core"), true);
            } else {
                BlockPos[] positions = {
                        corePos.relative(Direction.NORTH, 1),
                        corePos.relative(Direction.NORTH, 1).relative(Direction.UP, 1),
                        corePos.relative(Direction.NORTH, 1).relative(Direction.UP, 2),
                        corePos.relative(Direction.NORTH, 1).relative(Direction.EAST, 1),
                        corePos.relative(Direction.NORTH, 1).relative(Direction.EAST, 1).relative(Direction.UP, 1),
                        corePos.relative(Direction.NORTH, 1).relative(Direction.EAST, 1).relative(Direction.UP, 2),
                        corePos.relative(Direction.NORTH, 1).relative(Direction.WEST, 1),
                        corePos.relative(Direction.NORTH, 1).relative(Direction.WEST, 1).relative(Direction.UP, 1),
                        corePos.relative(Direction.NORTH, 1).relative(Direction.WEST, 1).relative(Direction.UP, 2),

                        corePos.relative(Direction.SOUTH, 1),
                        corePos.relative(Direction.SOUTH, 1).relative(Direction.UP, 1),
                        corePos.relative(Direction.SOUTH, 1).relative(Direction.UP, 2),
                        corePos.relative(Direction.SOUTH, 1).relative(Direction.EAST, 1),
                        corePos.relative(Direction.SOUTH, 1).relative(Direction.EAST, 1).relative(Direction.UP, 1),
                        corePos.relative(Direction.SOUTH, 1).relative(Direction.EAST, 1).relative(Direction.UP, 2),
                        corePos.relative(Direction.SOUTH, 1).relative(Direction.WEST, 1),
                        corePos.relative(Direction.SOUTH, 1).relative(Direction.WEST, 1).relative(Direction.UP, 1),
                        corePos.relative(Direction.SOUTH, 1).relative(Direction.WEST, 1).relative(Direction.UP, 2),

                        corePos.relative(Direction.UP, 1),
                        corePos.relative(Direction.UP, 2),
                        corePos.relative(Direction.EAST, 1),
                        corePos.relative(Direction.EAST, 1).relative(Direction.UP, 1),
                        corePos.relative(Direction.EAST, 1).relative(Direction.UP, 2),
                        corePos.relative(Direction.WEST, 1),
                        corePos.relative(Direction.WEST, 1).relative(Direction.UP, 1),
                        corePos.relative(Direction.WEST, 1).relative(Direction.UP, 2),
                };

                for(BlockPos blockPos : positions) {
                    if(!hasController) {
                        if(validateBlock(level, blockPos, ModBlocks.INFUSEMENT_CHAMBER_CONTROLLER.get())) {
                            hasController = true;
                        } else if(!validateBlock(level, blockPos, ModBlocks.INFUSEMENT_CHAMBER_CASING.get())) {
                            validStructure = false;
                            break;
                        }
                    } else if(!validateBlock(level, blockPos, ModBlocks.INFUSEMENT_CHAMBER_CASING.get())) {
                        validStructure = false;
                        break;
                    }
                }

                if(!validStructure) player.displayClientMessage(Component.translatable("message.lithereal.missing_infusement_casing"), true);
                else {
                    level.setBlockAndUpdate(pos, ModBlocks.INFUSEMENT_CHAMBER.get().defaultBlockState());
                }
            }


        }

        return super.onItemUseFirst(stack, context);
    }

    public Block getBlock(Level level, BlockPos blockPos) {
        return level.getBlockState(blockPos).getBlock();
    }

    public boolean validateBlock(Level level, BlockPos blockPos, Block block) {
        return getBlock(level, blockPos) == block;
    }
}
