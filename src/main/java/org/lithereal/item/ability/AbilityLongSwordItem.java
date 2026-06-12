package org.lithereal.item.ability;

import net.atlas.combatify.item.LongSwordItem;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class AbilityLongSwordItem extends LongSwordItem implements AbilityItem {
    final Ability ability;
    public AbilityLongSwordItem(Ability ability, ToolMaterial toolMaterial, int weaponLevel, Properties properties) {
        super(toolMaterial, weaponLevel, properties);
        this.ability = ability;
    }

    @Override
    public void hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        getAbility().onAttack(this, itemStack, attacked, attacker);
        super.hurtEnemy(itemStack, attacked, attacker);
    }

    @Override
    public void postHurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        getAbility().postAttack(this, itemStack, attacked, attacker);
        super.postHurtEnemy(itemStack, attacked, attacker);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        getAbility().onItemTick(this, itemStack, level, owner, slot);
        super.inventoryTick(itemStack, level, owner, slot);
    }

    @Override
    public Ability getAbility() {
        return ability;
    }

    public final Map<UUID, Map<Holder<MobEffect>, Integer>> degradationTickerMap = new WeakHashMap<>();
    public final Map<UUID, Map<Holder<MobEffect>, Integer>> healTickerMap = new WeakHashMap<>();
    public final Map<UUID, Map<Holder<MobEffect>, Integer>> untilReadyMap = new WeakHashMap<>();
    public final Map<UUID, Integer> lastUpdatedMap = new WeakHashMap<>();

    @Override
    public Map<UUID, Map<Holder<MobEffect>, Integer>> getDegradationTicker() {
        return degradationTickerMap;
    }

    @Override
    public Map<UUID, Map<Holder<MobEffect>, Integer>> getHealTicker() {
        return healTickerMap;
    }

    @Override
    public Map<UUID, Map<Holder<MobEffect>, Integer>> getUntilReady() {
        return untilReadyMap;
    }

    @Override
    public Map<UUID, Integer> getLastUpdatedMap() {
        return lastUpdatedMap;
    }
}
