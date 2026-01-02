package org.lithereal.item.ability;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.Map;
import java.util.UUID;

public interface AbilityItem {
    Ability getAbility();
    Map<UUID, Integer> getDegradationTicker();
    Map<UUID, Integer> getHealTicker();
    Map<UUID, Map<Holder<MobEffect>, Integer>> getUntilReady();
}