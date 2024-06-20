package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.item.infused.InfusedItem;

import java.util.List;

public class InfusedLitheriteBlock extends BaseEntityBlock {
    public static final MapCodec<InfusedLitheriteBlock> CODEC = simpleCodec(InfusedLitheriteBlock::new);
    public InfusedLitheriteBlock(Properties p_49795_) {
        super(p_49795_.sound(SoundType.METAL));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack stack) {
        BlockEntity tileEntity = level.getBlockEntity(pos);

        if (tileEntity instanceof InfusedLitheriteBlockEntity blockEntity) {
            blockEntity.applyComponentsFromItemStack(stack);
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
            drops.forEach((stack) -> stack.set(DataComponents.POTION_CONTENTS, infusedLitheriteBlockEntity.potion));
        }
        return drops;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        ItemStack stack = super.getCloneItemStack(levelReader, blockPos, blockState);
        levelReader.getBlockEntity(blockPos, LitherealExpectPlatform.getInfusedLitheriteBlockEntity()).ifPresent(infusedLitheriteBlockEntity -> infusedLitheriteBlockEntity.saveToItem(stack, levelReader.registryAccess()));
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
                infusedLitheriteBlockEntity.potion.forEachEffect((mobEffectInstance) -> {
                    Holder<MobEffect> effect = mobEffectInstance.getEffect();
                    if (effect.value().isInstantenous()) effect.value().applyInstantenousEffect(null, null, livingEntity, mobEffectInstance.getAmplifier(), 1.0);
                    else if (!livingEntity.hasEffect(effect)) livingEntity.addEffect(InfusedItem.transformInstance(mobEffectInstance, 100));
                });
            }
        }
    }
}
