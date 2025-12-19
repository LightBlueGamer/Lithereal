package org.lithereal.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;

public class BeamParticle extends TextureSheetParticle {
	private final SpriteSet sprites;
    private final double xStart;
    private final double yStart;
    private final double zStart;
    private float yaw;
    private float yawO;
    private float pitch;
    private float pitchO;

	BeamParticle(ClientLevel clientLevel, float friction, float gravity, double posX, double posY, double posZ, double deltaX, double deltaY, double deltaZ, double scale, SpriteSet spriteSet) {
		super(clientLevel, posX, posY, posZ);
        this.friction = friction;
        this.gravity = gravity;
		this.speedUpWhenYMotionIsBlocked = true;
		this.sprites = spriteSet;
        this.xd = deltaX * scale;
        this.yd = deltaY * scale;
        this.zd = deltaZ * scale;
        this.xStart = this.xo = this.x = posX;
        this.yStart = this.yo = this.y = posY;
        this.zStart = this.zo = this.z = posZ;

		this.quadSize *= 0.75F;
		this.lifetime = (int)(8.0 / (Math.random() * 0.8 + 0.2));
		this.hasPhysics = false;
		this.setSpriteFromAge(spriteSet);
        this.yawO = this.yaw = (float) Mth.atan2(deltaX, deltaZ);
        this.pitchO = this.pitch = (float) Mth.atan2(deltaY, Math.sqrt(deltaX * deltaX + deltaZ * z));
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_LIT;
	}

    @Override
    public void move(double d, double e, double f) {
    }

    @Override
    public int getLightColor(float f) {
        return 240;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        float yaw = Mth.lerp(partialTicks, this.yawO, this.yaw);
        float pitch = Mth.lerp(partialTicks, this.pitchO, this.pitch);
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotationY(yaw).rotateZ(-pitch);
        this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
        quaternionf.rotationY((float) -Math.PI + yaw).rotateZ(pitch);
        this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
    }

	@Override
	public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd = this.yd - 0.04 * this.gravity;
            float progress = (float)this.age / this.lifetime;
            this.x = this.xStart + this.xd * progress;
            this.y = this.yStart + this.yd * progress;
            this.z = this.zStart + this.zd * progress;
            this.yawO = this.yaw;
            this.yaw = (float) Mth.atan2(xd, zd);
            this.pitchO = this.pitch;
            this.pitch = (float) Mth.atan2(yd, Math.sqrt(xd * xd + zd * zd));
            this.xd = this.xd * this.friction;
            this.yd = this.yd * this.friction;
            this.zd = this.zd * this.friction;
        }
		this.setSpriteFromAge(this.sprites);
	}

    @Environment(EnvType.CLIENT)
    public static class RetributionBeamProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public RetributionBeamProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double posX, double posY, double posZ, double g, double h, double i) {
            RandomSource randomSource = clientLevel.random;
            double deltaX = randomSource.nextGaussian();
            double deltaY = randomSource.nextGaussian();
            double deltaZ = randomSource.nextGaussian();
            double scale = randomSource.nextDouble() + 1;

            return new BeamParticle(clientLevel, 1, 0, posX, posY, posZ, deltaX, deltaY, deltaZ, scale, this.sprite);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class RetributionBurstProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public RetributionBurstProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double posX, double posY, double posZ, double g, double h, double i) {
            RandomSource randomSource = clientLevel.random;
            double deltaX = randomSource.nextGaussian() * 0.3;
            double deltaY = randomSource.nextGaussian() * 0.004;
            double deltaZ = randomSource.nextGaussian() * 0.3;
            double scale = randomSource.nextDouble() + 1;

            return new BeamParticle(clientLevel, 1, -3F, posX, posY, posZ, deltaX, deltaY, deltaZ, scale, this.sprite);
        }
    }
}
