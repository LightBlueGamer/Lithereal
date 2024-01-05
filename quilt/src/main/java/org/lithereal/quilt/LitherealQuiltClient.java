package org.lithereal.quilt;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityRenderer;
import org.lithereal.fabric.block.entity.QuiltBlockEntities;

public class LitherealQuiltClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(QuiltBlockEntities.INFUSED_LITHERITE_BLOCK_ENTITY, InfusedLitheriteBlockEntityRenderer::new);
    }
}
