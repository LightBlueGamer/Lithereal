package org.lithereal.item.infused;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import org.apache.commons.compress.utils.Lists;
import org.lithereal.item.ability.AbilityItem;
import org.lithereal.item.ability.Ability;

import java.util.List;

public interface InfusedItem extends AbilityItem {
    String getBaseName(ItemStack stack);
    default Component getModifiedName(ItemStack itemStack) {
        ItemStack potion = new ItemStack(Items.POTION);
        potion.set(DataComponents.POTION_CONTENTS, itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY));
        String hoverStr = potion.getHoverName().getString().replaceAll("^(?i)(potion of the |potion of |potion )", "") + " " + getBaseName(itemStack);
        return Component.translatableWithFallback(itemStack.getDescriptionId(), hoverStr);
    }
    default List<MobEffectInstance> transformEffects(ItemStack stack) {
        PotionContents potionContents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        List<MobEffectInstance> newList = Lists.newArrayList();
        potionContents.forEachEffect((mobEffectInstance) -> newList.add(transformInstance(mobEffectInstance)));
        return newList;
    }
    static MobEffectInstance transformInstance(MobEffectInstance mobEffectInstance) {
        return new MobEffectInstance(mobEffectInstance.getEffect(), mobEffectInstance.getEffect().value().isInstantenous() ? 1 : Math.max(mobEffectInstance.getDuration() / 10, 100), mobEffectInstance.getAmplifier(), mobEffectInstance.isAmbient(), mobEffectInstance.isVisible(), mobEffectInstance.showIcon());
    }

    @Override
    default Ability getAbility() {
        return Ability.INFUSED;
    }
}