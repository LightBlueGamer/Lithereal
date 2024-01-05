package org.lithereal.fabric.mixin;

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
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityModel;
import org.lithereal.fabric.block.FabricBlocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public class RendererMixin {

    private final InfusedLitheriteBlockEntity chest = new InfusedLitheriteBlockEntity(BlockPos.ZERO, FabricBlocks.INFUSED_LITHERITE_BLOCK.defaultBlockState());
    @Final
    @Shadow
    private BlockEntityRenderDispatcher blockEntityRenderDispatcher;
    @Final
    @Shadow
    private EntityModelSet entityModelSet;

    @Inject(method = "onResourceManagerReload", at = @At("HEAD"))
    private void setEntityModelSet(CallbackInfo ci){
        this.entityModelSet.bakeLayer(InfusedLitheriteBlockEntityModel.LAYER_LOCATION);
    }

    @Inject(method = "renderByItem", at = @At("HEAD"))
    private void mainRender(ItemStack stack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, CallbackInfo ci) {
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            BlockState blockstate = block.defaultBlockState();
            BlockEntity blockentity;
            if (blockstate.is(FabricBlocks.INFUSED_LITHERITE_BLOCK)) {
                blockentity = this.chest;
                ((InfusedLitheriteBlockEntity) blockentity).setPotion(PotionUtils.getPotion(stack));
                this.blockEntityRenderDispatcher.renderItem(blockentity, poseStack, multiBufferSource, i, j);
            }
        }
    }
}
