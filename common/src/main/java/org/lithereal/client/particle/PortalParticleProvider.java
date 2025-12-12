package org.lithereal.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class PortalParticleProvider extends PortalParticle.Provider {
    public PortalParticleProvider(SpriteSet arg) {
        super(arg);
    }

    public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
        Particle output = super.createParticle(simpleParticleType, clientLevel, d, e, f, g, h, i);
        float color = clientLevel.random.nextFloat() * 0.4F + 0.6F;
        output.setColor(color, color, color);
        return output;
    }
}