package org.lithereal.item.custom.burning;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BurningLitheriteShovel extends ShovelItem {
    public BurningLitheriteShovel(Tier tier, float i, float f, Properties properties) {
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
        SmeltingRecipe furnaceRecipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new SimpleContainer(blockState.getBlock().asItem().getDefaultInstance()), level).orElse(null);

        if (furnaceRecipe != null) {
            NonNullList<ItemStack> drops = NonNullList.create();
            ItemStack dropStack = new ItemStack(furnaceRecipe.getResultItem(level.registryAccess()).getItem(), furnaceRecipe.getResultItem(level.registryAccess()).getCount());
            drops.add(dropStack);

            for (ItemStack drop : drops) {
                Block.popResource(level, blockPos, drop);
            }
            itemStack.hurtAndBreak(0, livingEntity, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            level.destroyBlock(blockPos, false);
        }
        return super.mineBlock(itemStack, level, blockState, blockPos, livingEntity);
    }
}
