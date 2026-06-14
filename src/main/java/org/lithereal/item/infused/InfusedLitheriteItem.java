package org.lithereal.item.infused;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.ability.Ability;

import java.util.*;

public class InfusedLitheriteItem extends Item implements InfusedItem {
    public InfusedLitheriteItem(Properties properties) {
        super(properties);
    }

    public @NotNull ItemStack getDefaultInstance() {
        ItemStack itemStack = super.getDefaultInstance();
        itemStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
        return itemStack;
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        return getModifiedName(itemStack);
    }

    @Override
    public String getBaseName(ItemStack stack) {
        return "Litherite Ingot";
    }

    @Override
    public Ability getAbility() {
        return Ability.INFUSED;
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