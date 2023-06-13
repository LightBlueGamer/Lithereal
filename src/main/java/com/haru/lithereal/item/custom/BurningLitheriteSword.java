package com.haru.lithereal.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BurningLitheriteSword extends SwordItem {
    public BurningLitheriteSword(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity livingEntity) {
            if(livingEntity.isFreezing()) livingEntity.setTicksFrozen(0);
            livingEntity.setSecondsOnFire(1000);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        SmeltingRecipe furnaceRecipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new SimpleContainer(blockState.getBlock().asItem().getDefaultInstance()), level).orElse(null);

        if (furnaceRecipe != null) {
            NonNullList<ItemStack> drops = NonNullList.create();
            drops.add(furnaceRecipe.getResultItem());

            for (ItemStack drop : drops) {
                Block.popResource(level, blockPos, drop);
            }
            itemStack.hurtAndBreak(1, livingEntity, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            level.destroyBlock(blockPos, false);
        }

        return super.mineBlock(itemStack, level, blockState, blockPos, livingEntity);
    }
}
