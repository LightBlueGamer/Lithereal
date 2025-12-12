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
public class StandardBiomeProvider implements ParticleProvider<SimpleParticleType> {
	private final SpriteSet sprite;

	public StandardBiomeProvider(SpriteSet spriteSet) {
		this.sprite = spriteSet;
	}

	public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double posX, double posY, double posZ, double deltaX, double deltaY, double deltaZ) {
		RandomSource randomSource = clientLevel.random;
		double dirX = randomSource.nextGaussian() * 1.0E-6F;
		double dirY = randomSource.nextGaussian() * 1.0E-4F;
		double dirZ = randomSource.nextGaussian() * 1.0E-6F;

		SuspendedParticle suspendedParticle = new SuspendedParticle(clientLevel, this.sprite, posX, posY, posZ, dirX, dirY, dirZ);
		suspendedParticle.setColor(1, 1, 1);
		return suspendedParticle;
	}
}