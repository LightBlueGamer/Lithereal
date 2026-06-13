package org.lithereal.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

public class PortalParticleProvider extends PortalParticle.Provider {
    public PortalParticleProvider(SpriteSet arg) {
        super(arg);
    }

    @Override
    public @NonNull Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
        PortalParticle output = (PortalParticle) super.createParticle(options, level, x, y, z, xAux, yAux, zAux, random);
        float color = random.nextFloat() * 0.4F + 0.6F;
        output.setColor(color, color, color);
        return output;
    }
}