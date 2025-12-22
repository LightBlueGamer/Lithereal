package org.lithereal.item.burning;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.lithereal.item.ability.Ability;
import org.lithereal.item.ability.AbilityHammerItem;

import java.util.List;

public class BurningLitheriteHammerItem extends AbilityHammerItem implements BurningItem {
    public BurningLitheriteHammerItem(Ability ability, Tier tier, int i, float f, int weaponLevel, Properties properties) {
        super(ability, tier, i, f, weaponLevel, properties);
    }
    public BurningLitheriteHammerItem(Tier tier, int i, float f, int weaponLevel, Properties properties) {
        this(Ability.BURNING, tier, i, f, weaponLevel, properties);
    }

    @Override
    public void findAndBreakNearBlocks(HitResult pick, BlockPos blockPos, ItemStack hammerStack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player)) return;

        final int[] damage = {0};

        forEachMineableBlock(level, (BlockHitResult) pick, blockPos, hammerStack, (pos, targetState) -> {
            if (damage[0] >= (hammerStack.getMaxDamage() - hammerStack.getDamageValue() - 1)) {
                return true;
            }
            level.destroyBlock(pos, false, livingEntity);

            if (!player.isCreative()) {
                boolean correctToolForDrops = hammerStack.isCorrectToolForDrops(targetState);
                if (correctToolForDrops) {
                    targetState.spawnAfterBreak((ServerLevel) level, pos, hammerStack, true);
                    List<ItemStack> drops = getSmeltedDrops(Block.getDrops(targetState, (ServerLevel) level, pos, level.getBlockEntity(pos), livingEntity, hammerStack), level);
                    drops.forEach(e -> Block.popResourceFromFace(level, pos, ((BlockHitResult) pick).getDirection(), e));
                }
            }
            damage[0]++;
            return false;
        });

        if (damage[0] != 0 && !player.isCreative()) {
            Tool tool = hammerStack.get(DataComponents.TOOL);
            if (tool != null) hammerStack.hurtAndBreak(damage[0] * tool.damagePerBlock(), livingEntity, EquipmentSlot.MAINHAND);
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
}