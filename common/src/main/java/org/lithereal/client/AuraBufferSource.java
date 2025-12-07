package org.lithereal.client;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Optionull;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.util.RenderingUtils;

import java.util.Optional;

public class AuraBufferSource implements MultiBufferSource {
    private final MultiBufferSource.BufferSource baseBufferSource;
    private final MultiBufferSource.BufferSource auraBufferSource = MultiBufferSource.immediate(new ByteBufferBuilder(1536));
    private MultiBufferSource altBufferSource;
    private float offset;

    public AuraBufferSource(MultiBufferSource.BufferSource baseBufferSource) {
        this.baseBufferSource = baseBufferSource;
    }

    @Override
    public @NotNull VertexConsumer getBuffer(RenderType renderType) {
        if (renderType.isOutline()) {
            VertexConsumer returned = this.auraBufferSource.getBuffer(renderType);
            return new IntactDelegatingConsumer(returned);
        } else {
            VertexConsumer baseConsumer = this.baseBufferSource.getBuffer(renderType);
            Optional<RenderType> optional = renderType.outline();
            VertexConsumer returned = Optionull.mapOrElse(this.altBufferSource, bufferSource -> bufferSource.getBuffer(renderType), () -> baseConsumer);
            if (optional.isPresent()) {
                VertexConsumer consumer = this.auraBufferSource.getBuffer(RenderingUtils.etherealAura(offset));
                IntactDelegatingConsumer intactDelegatingConsumer = new IntactDelegatingConsumer(consumer);
                return VertexMultiConsumer.create(intactDelegatingConsumer, returned);
            } else return returned;
        }
    }

    public AuraBufferSource setAltBufferSource(@Nullable MultiBufferSource altBufferSource) {
        if (altBufferSource == this.baseBufferSource) return this;
        this.altBufferSource = altBufferSource;
        return this;
    }

    public AuraBufferSource setOffset(float offset) {
        this.offset = offset;
        return this;
    }

    public void endAuraBatch() {
        this.auraBufferSource.endBatch();
    }

    @Environment(EnvType.CLIENT)
    record IntactDelegatingConsumer(VertexConsumer delegate) implements VertexConsumer {
        @Override
        public @NotNull VertexConsumer addVertex(float f, float g, float h) {
            this.delegate.addVertex(f, g, h).setColor(1f, 1, 1, 1);
            return this;
        }

        @Override
        public @NotNull VertexConsumer setColor(int i, int j, int k, int l) {
            return this;
        }

        @Override
        public @NotNull VertexConsumer setUv(float f, float g) {
            this.delegate.setUv(f, g);
            return this;
        }

        @Override
        public @NotNull VertexConsumer setUv1(int i, int j) {
            return this;
        }

        @Override
        public @NotNull VertexConsumer setUv2(int i, int j) {
            return this;
        }

        @Override
        public @NotNull VertexConsumer setNormal(float f, float g, float h) {
            return this;
        }
    }
}
