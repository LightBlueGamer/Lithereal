package org.lithereal.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.item.custom.infused.InfusedItem;

import java.util.List;

public class InfusedLitheriteBlock extends BaseEntityBlock {
    public InfusedLitheriteBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack stack) {
        BlockEntity tileEntity = level.getBlockEntity(pos);

        if (tileEntity instanceof InfusedLitheriteBlockEntity blockEntity) {
            blockEntity.setPotion(PotionUtils.getPotion(stack));
            blockEntity.setChanged();
        }
        super.setPlacedBy(level, pos, state, livingEntity, stack);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_287732_, LootParams.Builder params) {
        List<ItemStack> drops = super.getDrops(p_287732_, params);
        BlockEntity blockentity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if(blockentity instanceof InfusedLitheriteBlockEntity infusedLitheriteBlockEntity) {
            drops.forEach((stack) -> PotionUtils.setPotion(stack, infusedLitheriteBlockEntity.potion));
        }
        return drops;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        ItemStack stack = super.getCloneItemStack(blockGetter, blockPos, blockState);
        BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);
        if (blockEntity instanceof InfusedLitheriteBlockEntity infusedLitheriteBlockEntity)
            PotionUtils.setPotion(stack, infusedLitheriteBlockEntity.potion);
        return stack;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return LitherealExpectPlatform.getInfusedLitheriteBlockEntity().create(p_153215_, p_153216_);
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        super.stepOn(level, blockPos, blockState, entity);
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if(blockEntity instanceof InfusedLitheriteBlockEntity infusedLitheriteBlockEntity) {
            if(entity instanceof LivingEntity livingEntity) {
                infusedLitheriteBlockEntity.potion.getEffects().forEach((mobEffectInstance) -> {
                    if (mobEffectInstance.getEffect().isInstantenous()) mobEffectInstance.getEffect().applyInstantenousEffect(null, null, livingEntity, mobEffectInstance.getAmplifier(), 1.0);
                    else if (!livingEntity.hasEffect(mobEffectInstance.getEffect())) livingEntity.addEffect(InfusedItem.transformInstance(mobEffectInstance, 40));
                });
            }
        }
    }
}
