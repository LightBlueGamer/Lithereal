package org.lithereal.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.lithereal.Lithereal;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class RenderingUtils {
    private static final ResourceLocation AURA_LOCATION = ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/entity/ethereal_aura.png");
    public static RenderType etherealAura(float offset) {
        return RenderType.create(
                "ethereal_aura",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                1536,
                false,
                true,
                RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(AURA_LOCATION, false, false))
                        .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(offset, offset))
                        .setTransparencyState(ADDITIVE_TRANSPARENCY)
                        .setCullState(NO_CULL)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        .createCompositeState(false)
        );
    }
}
