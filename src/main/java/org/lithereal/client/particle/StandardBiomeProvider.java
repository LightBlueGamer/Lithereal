package org.lithereal.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.Nullable;

public class StandardBiomeProvider implements ParticleProvider<SimpleParticleType> {
	private final SpriteSet sprite;

	public StandardBiomeProvider(SpriteSet spriteSet) {
		this.sprite = spriteSet;
	}

	@Override
	public @Nullable Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
		double dirX = random.nextGaussian() * 1.0E-6F;
		double dirY = random.nextGaussian() * 1.0E-4F;
		double dirZ = random.nextGaussian() * 1.0E-6F;

		SuspendedParticle suspendedParticle = new SuspendedParticle(level, x, y, z, dirX, dirY, dirZ, this.sprite.get(random));
		suspendedParticle.setColor(1, 1, 1);
		return suspendedParticle;
	}
}