package org.lithereal.neoforge.world.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class ForgeStrippableLogBlock extends RotatedPillarBlock {
    public static final MapCodec<ForgeStrippableLogBlock> CODEC = simpleCodec(ForgeStrippableLogBlock::new);
    private final Block stripped;

    public ForgeStrippableLogBlock(Block stripped, Properties arg) {
        super(arg);
        this.stripped = stripped;
    }

    public ForgeStrippableLogBlock(Properties arg) {
        super(arg);
        this.stripped = this;
    }

    @Override
    public MapCodec<? extends ForgeStrippableLogBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility == ItemAbilities.AXE_STRIP) return this.stripped.withPropertiesOf(state);
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
