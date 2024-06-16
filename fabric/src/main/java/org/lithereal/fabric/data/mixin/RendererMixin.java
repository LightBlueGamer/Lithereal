package org.lithereal.fabric.data.mixin;

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
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityModel;
import org.lithereal.fabric.world.block.FabricBlocks;
import org.lithereal.world.block.entity.InfusedLitheriteBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public class RendererMixin {

    @Unique
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
            InfusedLitheriteBlockEntity blockentity;
            if (blockstate.is(FabricBlocks.INFUSED_LITHERITE_BLOCK)) {
                blockentity = this.chest;
                blockentity.setPotion(stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY));
                this.blockEntityRenderDispatcher.renderItem(blockentity, poseStack, multiBufferSource, i, j);
            }
        }
    }
}
