package org.lithereal.forge.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.forge.block.ForgeBlocks;

public class LitherealBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    private final InfusedLitheriteBlockEntity chest = new InfusedLitheriteBlockEntity(BlockPos.ZERO, ForgeBlocks.INFUSED_LITHERITE_BLOCK.get().defaultBlockState());
    private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
    private final EntityModelSet entityModelSet;
    public LitherealBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
        this.blockEntityRenderDispatcher = p_172550_;
        this.entityModelSet = p_172551_;
    }

    @Override
    public void renderByItem(ItemStack p_108830_, ItemDisplayContext p_270899_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_) {
        Item item = p_108830_.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            BlockState blockstate = block.defaultBlockState();
            BlockEntity blockentity;
            if (blockstate.is(ForgeBlocks.INFUSED_LITHERITE_BLOCK.get())) {
                blockentity = this.chest;
                ((InfusedLitheriteBlockEntity) blockentity).setPotion(PotionUtils.getPotion(p_108830_));
                this.blockEntityRenderDispatcher.renderItem(blockentity, p_108832_, p_108833_, p_108834_, p_108835_);
            }
        }
        super.renderByItem(p_108830_, p_270899_, p_108832_, p_108833_, p_108834_, p_108835_);
    }
}