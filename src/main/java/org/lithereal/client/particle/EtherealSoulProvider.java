package org.lithereal.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class EtherealSoulProvider implements ParticleProvider<SimpleParticleType> {
	private final SpriteSet sprite;

	public EtherealSoulProvider(SpriteSet spriteSet) {
		this.sprite = spriteSet;
	}

	@Override
	public @Nullable Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
		double xDelta = random.nextGaussian() * 1.0E-6F;
		double yDelta = random.nextGaussian() * 1.0E-4F;
		double zDelta = random.nextGaussian() * 1.0E-6F;
		double red = random.nextDouble();
		double green = random.nextDouble();
		double blue = 1 - red;

		SuspendedParticle suspendedParticle = new SuspendedParticle(level, x, y, z, xDelta, yDelta, zDelta, this.sprite.get(random));
		suspendedParticle.setColor((float) red, (float) green, (float) blue);
		return suspendedParticle;
	}
}