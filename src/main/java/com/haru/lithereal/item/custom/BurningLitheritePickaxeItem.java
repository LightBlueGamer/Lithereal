package com.haru.lithereal.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class BurningLitheritePickaxeItem extends PickaxeItem {
    public BurningLitheritePickaxeItem(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
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
