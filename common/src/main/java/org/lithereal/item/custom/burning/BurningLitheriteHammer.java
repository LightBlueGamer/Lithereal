package org.lithereal.item.custom.burning;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.lithereal.item.custom.Hammer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BurningLitheriteHammer extends Hammer implements BurningItem {
    public BurningLitheriteHammer(Tier tier, int i, float f, Properties properties) {
        super(tier, i, f, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        if(attacked.isFreezing()) attacked.setTicksFrozen(0);
        attacked.setSecondsOnFire(1000);
        return super.hurtEnemy(itemStack, attacked, attacker);
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (!level.isClientSide && blockState.getDestroySpeed(level, blockPos) != 0.0F) {
            itemStack.hurtAndBreak(1, livingEntity, (livingEntityx) -> livingEntityx.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
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

            if (!player.isCreative()) {
                boolean correctToolForDrops = hammerStack.isCorrectToolForDrops(targetState);
                if (correctToolForDrops) {
                    targetState.spawnAfterBreak((ServerLevel) level, pos, hammerStack, true);
                    List<ItemStack> drops = getSmeltedDrops(Block.getDrops(targetState, (ServerLevel) level, pos, level.getBlockEntity(pos), livingEntity, hammerStack), level);
                    drops.forEach(e -> {
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
    }

    public List<ItemStack> getSmeltedDrops(List<ItemStack> drops, Level level) {
        List<ItemStack> smeltedDrops = NonNullList.create();
        drops.forEach(e -> {
            SmeltingRecipe furnaceRecipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, new SimpleContainer(e), level).orElse(null);
            if(furnaceRecipe != null) smeltedDrops.add(new ItemStack(furnaceRecipe.getResultItem(level.registryAccess()).getItem(), e.getCount()));
            else smeltedDrops.add(e);
        });
        return smeltedDrops;
    }

    @Override
    public void getDrops(Level level, BlockState blockState, BlockPos blockPos, ItemStack itemStack, LivingEntity livingEntity, BlockEntity blockEntity) {
        if(level instanceof ServerLevel) {
            List<ItemStack> origDrops = Block.getDrops(blockState, (ServerLevel) level, blockPos, blockEntity, livingEntity, itemStack);
            SmeltingRecipe[] furnaceRecipes = new SmeltingRecipe[origDrops.size()];

            for (int i = 0; i < origDrops.size(); i++) {
                furnaceRecipes[i] = level.getRecipeManager()
                        .getRecipeFor(RecipeType.SMELTING, new SimpleContainer(origDrops.get(i)), level).orElse(null);
            }


            NonNullList<ItemStack> drops = NonNullList.create();
            for (int i = 0; i < furnaceRecipes.length; i++) {
                if(furnaceRecipes[i] == null) {
                    drops.add(i, origDrops.get(i));
                    continue;
                }
                ItemStack dropStack = new ItemStack(furnaceRecipes[i].getResultItem(level.registryAccess()).getItem(), origDrops.get(i).getCount());
                drops.add(i, dropStack);
            }

            for (ItemStack drop : drops) {
                Block.popResource(level, blockPos, drop);
            }
            itemStack.hurtAndBreak(0, livingEntity, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            HitResult pick = livingEntity.pick(20D, 1F, false);

            if (!(pick instanceof BlockHitResult)) return;

            this.findAndBreakNearBlocks(pick, blockPos, itemStack, level, livingEntity);

            level.destroyBlock(blockPos, false);
        }
    }
}
