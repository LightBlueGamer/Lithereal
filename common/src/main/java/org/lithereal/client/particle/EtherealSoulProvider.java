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

@Environment(EnvType.CLIENT)
public class EtherealSoulProvider implements ParticleProvider<SimpleParticleType> {
	private final SpriteSet sprite;

	public EtherealSoulProvider(SpriteSet spriteSet) {
		this.sprite = spriteSet;
	}

	public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
		RandomSource randomSource = clientLevel.random;
		double j = randomSource.nextGaussian() * 1.0E-6F;
		double k = randomSource.nextGaussian() * 1.0E-4F;
		double l = randomSource.nextGaussian() * 1.0E-6F;
		double red = randomSource.nextDouble();
		double green = randomSource.nextDouble();
		double blue = 1 - red;

		SuspendedParticle suspendedParticle = new SuspendedParticle(clientLevel, this.sprite, d, e, f, j, k, l);
		suspendedParticle.setColor((float) red, (float) green, (float) blue);
		return suspendedParticle;
	}
}