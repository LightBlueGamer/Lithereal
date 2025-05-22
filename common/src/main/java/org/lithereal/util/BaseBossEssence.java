package org.lithereal.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionContents;
import org.lithereal.mob_effect.ModMobEffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record BaseBossEssence(int color, int amplifier, List<MobEffectInstance> excessEffects) {
    public PotionContents generatePotionContents() {
        List<MobEffectInstance> full = new ArrayList<>(excessEffects);
        full.add(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModMobEffects.BOSS_POWER.get()), 6000, amplifier));
        return new PotionContents(Optional.empty(), Optional.of(color), full);
    }
}