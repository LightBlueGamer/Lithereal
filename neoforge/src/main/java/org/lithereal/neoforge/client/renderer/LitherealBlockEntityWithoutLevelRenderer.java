package org.lithereal.neoforge.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.neoforge.world.block.ForgeBlocks;

public class LitherealBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    private final InfusedLitheriteBlockEntity chest = new InfusedLitheriteBlockEntity(BlockPos.ZERO, ForgeBlocks.INFUSED_LITHERITE_BLOCK.get().defaultBlockState());
    private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
    public LitherealBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
        this.blockEntityRenderDispatcher = p_172550_;
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        Item item = itemStack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            BlockState blockstate = block.defaultBlockState();
            InfusedLitheriteBlockEntity blockentity;
            if (blockstate.is(ForgeBlocks.INFUSED_LITHERITE_BLOCK.get())) {
                blockentity = this.chest;
                blockentity.setPotion(itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY));
                this.blockEntityRenderDispatcher.renderItem(blockentity, poseStack, multiBufferSource, i, j);
            }
        }
        super.renderByItem(itemStack, context, poseStack, multiBufferSource, i, j);
    }
}