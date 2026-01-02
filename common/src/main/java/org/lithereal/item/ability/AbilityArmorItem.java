package org.lithereal.item.ability;

import com.google.common.collect.Streams;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.lithereal.item.ModArmorMaterials;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class AbilityArmorItem extends ArmorItem implements AbilityItem {
    final Ability ability;
    public AbilityArmorItem(Ability ability, Holder<ArmorMaterial> armorMaterial, Type type, int durability, Properties properties) {
        super(armorMaterial, type, properties.durability(ModArmorMaterials.HEALTH_FUNCTION_FOR_TYPE.apply(type, durability)));
        this.ability = ability;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        if (entity instanceof LivingEntity user && Streams.stream(user.getArmorSlots()).anyMatch(itemStack::equals)) getAbility().onArmourTick(this, itemStack, level, entity, slot, isSelected);
        super.inventoryTick(itemStack, level, entity, slot, isSelected);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }

    @Override
    public Ability getAbility() {
        return ability;
    }

    public final Map<UUID, Integer> degradationTickerMap = new WeakHashMap<>();
    public final Map<UUID, Integer> healTickerMap = new WeakHashMap<>();
    public final Map<UUID, Map<Holder<MobEffect>, Integer>> untilReadyMap = new WeakHashMap<>();
    public final Map<UUID, Integer> lastUpdatedMap = new WeakHashMap<>();

    @Override
    public Map<UUID, Integer> getDegradationTicker() {
        return degradationTickerMap;
    }

    @Override
    public Map<UUID, Integer> getHealTicker() {
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