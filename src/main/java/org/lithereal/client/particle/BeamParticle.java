package org.lithereal.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;
import org.jspecify.annotations.Nullable;

public class BeamParticle extends SingleQuadParticle {
	private final SpriteSet sprites;
    private final double xStart;
    private final double yStart;
    private final double zStart;
    private float yaw;
    private float yawO;
    private float pitch;
    private float pitchO;

	BeamParticle(ClientLevel clientLevel, float friction, float gravity, double posX, double posY, double posZ, double deltaX, double deltaY, double deltaZ, double scale, SpriteSet spriteSet) {
		super(clientLevel, posX, posY, posZ, spriteSet.first());
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
    public void move(double d, double e, double f) {
    }

    @Override
    public void extract(QuadParticleRenderState particleTypeRenderState, Camera camera, float partialTickTime) {
        float yaw = Mth.lerp(partialTickTime, this.yawO, this.yaw);
        float pitch = Mth.lerp(partialTickTime, this.pitchO, this.pitch);
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotationY(yaw).rotateZ(-pitch);
        this.extractRotatedQuad(particleTypeRenderState, camera, quaternionf, partialTickTime);
        quaternionf.rotationY((float) -Math.PI + yaw).rotateZ(pitch);
        this.extractRotatedQuad(particleTypeRenderState, camera, quaternionf, partialTickTime);
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

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class RetributionBeamProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public RetributionBeamProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
            double deltaX = random.nextGaussian();
            double deltaY = random.nextGaussian();
            double deltaZ = random.nextGaussian();
            double scale = random.nextDouble() + 1;

            return new BeamParticle(level, 1, 0, x, y, z, deltaX, deltaY, deltaZ, scale, this.sprite);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class RetributionBurstProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public RetributionBurstProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource randomSource) {
            double deltaX = randomSource.nextGaussian() * 0.3;
            double deltaY = randomSource.nextGaussian() * 0.004;
            double deltaZ = randomSource.nextGaussian() * 0.3;
            double scale = randomSource.nextDouble() + 1;

            return new BeamParticle(level, 1, -3F, x, y, z, deltaX, deltaY, deltaZ, scale, this.sprite);
        }
    }
}
