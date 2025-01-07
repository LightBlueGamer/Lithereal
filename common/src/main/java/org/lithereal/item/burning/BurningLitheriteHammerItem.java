package org.lithereal.item.burning;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.ability.AbilityHammerItem;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BurningLitheriteHammerItem extends AbilityHammerItem implements BurningItem {
    public BurningLitheriteHammerItem(Tier tier, int i, float f, Properties properties) {
        super(Ability.BURNING, tier, i, f, properties);
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (!level.isClientSide && blockState.getDestroySpeed(level, blockPos) != 0.0F) itemStack.hurtAndBreak(1, livingEntity, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public void findAndBreakNearBlocks(HitResult pick, BlockPos blockPos, ItemStack hammerStack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player)) return;

        var boundingBox = getBoundingBox((BlockHitResult) pick, blockPos);

        int damage = 0;
        Iterator<BlockPos> iterator = BlockPos.betweenClosedStream(boundingBox).iterator();
        Set<BlockPos> removedPos = new HashSet<>();
        while (iterator.hasNext()) {
            var pos = iterator.next();

            if (damage >= (hammerStack.getMaxDamage() - hammerStack.getDamageValue() - 1)) {
                break;
            }

            BlockState targetState = level.getBlockState(pos);
            if (pos == blockPos || removedPos.contains(pos) || cannotDestroy(targetState, level, pos) || !hammerStack.isCorrectToolForDrops(targetState)) {
                continue;
            }

            removedPos.add(pos);
            level.destroyBlock(pos, false, livingEntity);

            if (!player.isCreative()) {
                boolean correctToolForDrops = hammerStack.isCorrectToolForDrops(targetState);
                if (correctToolForDrops) {
                    targetState.spawnAfterBreak((ServerLevel) level, pos, hammerStack, true);
                    List<ItemStack> drops = getSmeltedDrops(Block.getDrops(targetState, (ServerLevel) level, pos, level.getBlockEntity(pos), livingEntity, hammerStack), level);
                    drops.forEach(e -> Block.popResourceFromFace(level, pos, ((BlockHitResult) pick).getDirection(), e));
                }
            }
            damage ++;
        }

        if (damage != 0 && !player.isCreative()) {
            hammerStack.hurtAndBreak(damage, livingEntity, EquipmentSlot.MAINHAND);
        }
    }

    public List<ItemStack> getSmeltedDrops(List<ItemStack> drops, Level level) {
        List<ItemStack> smeltedDrops = NonNullList.create();
        drops.forEach(e -> {
            RecipeHolder<SmeltingRecipe> furnaceRecipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(e), level).orElse(null);
            if(furnaceRecipe != null) smeltedDrops.add(new ItemStack(furnaceRecipe.value().getResultItem(level.registryAccess()).getItem(), e.getCount()));
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
                RecipeHolder<SmeltingRecipe> holder = level.getRecipeManager()
                        .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(origDrops.get(i)), level).orElse(null);
                furnaceRecipes[i] = holder == null ? null : holder.value();
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
            itemStack.hurtAndBreak(0, livingEntity, EquipmentSlot.MAINHAND);
            HitResult pick = livingEntity.pick(20D, 1F, false);

            if (!(pick instanceof BlockHitResult)) return;

            this.findAndBreakNearBlocks(pick, blockPos, itemStack, level, livingEntity);

            level.destroyBlock(blockPos, false);
        }
    }
}