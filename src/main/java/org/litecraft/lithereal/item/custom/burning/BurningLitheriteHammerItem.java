package org.litecraft.lithereal.item.custom.burning;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.litecraft.lithereal.item.custom.LitheriteHammerItem;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BurningLitheriteHammerItem extends LitheriteHammerItem {
    public BurningLitheriteHammerItem(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
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

            SmeltingRecipe furnaceRecipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, new SimpleContainer(targetState.getBlock().asItem().getDefaultInstance()), level).orElse(null);

            if (!player.isCreative()) {
                boolean correctToolForDrops = hammerStack.isCorrectToolForDrops(targetState);
                if (correctToolForDrops) {
                    targetState.spawnAfterBreak((ServerLevel) level, pos, hammerStack, true);
                    List<ItemStack> drops = Block.getDrops(targetState, (ServerLevel) level, pos, level.getBlockEntity(pos), livingEntity, hammerStack);
                    drops.forEach(e -> {
                        if (furnaceRecipe != null) {
                            e = new ItemStack(furnaceRecipe.getResultItem(level.registryAccess()).getItem(), furnaceRecipe.getResultItem(level.registryAccess()).getCount());
                        }
                        Block.popResourceFromFace(level, pos, ((BlockHitResult) pick).getDirection(), e);
                    });
                }
            }
            damage ++;
        }

        if (damage != 0 && !player.isCreative()) {
            hammerStack.hurtAndBreak(damage, livingEntity, (livingEntityx) -> {
                livingEntityx.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        super.findAndBreakNearBlocks(pick, blockPos, hammerStack, level, livingEntity);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity livingEntity) {
            if(livingEntity.isFreezing()) livingEntity.setTicksFrozen(0);
            livingEntity.setSecondsOnFire(1000);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
