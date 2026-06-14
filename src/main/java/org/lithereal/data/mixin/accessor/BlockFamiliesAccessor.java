package org.lithereal.data.mixin.accessor;

import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockFamilies.class)
public interface BlockFamiliesAccessor {
    @Invoker
    static BlockFamily.Builder callFamilyBuilder(Block baseBlock) {
        return null;
    }
}
