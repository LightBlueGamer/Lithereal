package org.lithereal.mob_effect;

import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.entity.ModDamageTypes;
import org.lithereal.networking.ClientboundRetributionDeathPacket;
import org.lithereal.tags.ModTags;

public class RetributionMobEffect extends CustomMobEffect {
	protected RetributionMobEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color, mobEffectInstance -> ModParticles.RETRIBUTION_HOLY_BEAM.get());
	}

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        float baseDamage = mob.is(ModTags.WEAK_TO_HOLY_MAGIC) ? 3 : 1;
        mob.hurtServer(serverLevel, mob.damageSources().source(ModDamageTypes.HOLY), baseDamage + amplification);
        return true;
    }

    @Override
    public void onMobRemoved(ServerLevel level, LivingEntity mob, int amplifier, Entity.RemovalReason reason) {
        if (reason == Entity.RemovalReason.KILLED) {
            NetworkManager.sendToPlayers(level.players(), new ClientboundRetributionDeathPacket(mob.position()));
        }
    }

    @Override
	public boolean shouldApplyEffectTickThisTick(int tick, int amplifier) {
		int rate = 40 >> amplifier;
		return rate <= 0 || tick % rate == 0;
	}
}
