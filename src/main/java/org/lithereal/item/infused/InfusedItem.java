package org.lithereal.item.infused;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public interface InfusedItem {
    String getBaseName(ItemStack stack);
    default Component getModifiedName(ItemStack itemStack) {
        ItemStack potion = new ItemStack(Items.POTION);
        potion.set(DataComponents.POTION_CONTENTS, itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY));
        String hoverStr = potion.getHoverName().getString().replaceAll("^(?i)(potion of the |potion of |potion )", "") + " " + getBaseName(itemStack);
        return Component.translatableWithFallback(getName(itemStack), hoverStr);
    }
    default List<MobEffectInstance> transformEffects(ItemStack stack) {
        PotionContents potionContents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        List<MobEffectInstance> newList = Lists.newArrayList();
        float potionDurationScale = stack.getOrDefault(DataComponents.POTION_DURATION_SCALE, 0.1F);
        potionContents.forEachEffect((mobEffectInstance) -> newList.add(transformInstance(mobEffectInstance, 1.0F)), potionDurationScale);
        return newList;
    }
    static MobEffectInstance transformInstance(MobEffectInstance mobEffectInstance, float durationFactor) {
        return new MobEffectInstance(mobEffectInstance.getEffect(), mobEffectInstance.getEffect().value().isInstantenous() ? 1 : (int) Math.floor(Math.max(mobEffectInstance.getDuration() * durationFactor, 100)), mobEffectInstance.getAmplifier(), mobEffectInstance.isAmbient(), mobEffectInstance.isVisible(), mobEffectInstance.showIcon());
    }
    static String getName(ItemStack stack) {
        PotionContents potionContents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        String suffix = potionContents.customName().or(() -> potionContents.potion().map(p -> p.value().name())).orElse("empty");
        return stack.getItem().getDescriptionId() + ".effect." + suffix;
    }
}