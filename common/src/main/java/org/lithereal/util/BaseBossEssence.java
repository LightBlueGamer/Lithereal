package org.lithereal.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionContents;
import org.lithereal.mob_effect.ModMobEffects;

import java.util.List;
import java.util.Optional;

public final class BaseBossEssence implements BossEssenceTier {

    @Override
    public PotionContents generatePotionContents() {
        return new PotionContents(Optional.empty(), Optional.of(14100520), List.of(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModMobEffects.BOSS_POWER.get()), 6000)));
    }
}