package org.lithereal.item.custom.infused;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.apache.commons.compress.utils.Lists;
import org.lithereal.item.custom.Ability;
import org.lithereal.item.custom.ability.AbilityItem;

import java.util.List;

public interface InfusedItem extends AbilityItem {
    String getBaseName(ItemStack stack);
    default Component getModifiedName(ItemStack itemStack) {
        String hoverStr;
        ItemStack potion = PotionUtils.setPotion(new ItemStack(Items.POTION), PotionUtils.getPotion(itemStack));
        hoverStr = potion.getHoverName().getString().replaceAll("^(?i)(potion of the |potion of |potion )", "") + " " + getBaseName(itemStack);
        return Component.translatableWithFallback(itemStack.getDescriptionId(), hoverStr);
    }
    default List<MobEffectInstance> transformEffects(ItemStack stack, int timeNonInstant) {
        List<MobEffectInstance> original = PotionUtils.getMobEffects(stack);
        List<MobEffectInstance> newList = Lists.newArrayList();
        for (MobEffectInstance mobEffectInstance : original) {
            newList.add(transformInstance(mobEffectInstance, timeNonInstant));
        }
        return newList;
    }
    static MobEffectInstance transformInstance(MobEffectInstance mobEffectInstance, int timeNonInstant) {
        return new MobEffectInstance(mobEffectInstance.getEffect(), mobEffectInstance.getEffect().isInstantenous() ? 1 : timeNonInstant, mobEffectInstance.getAmplifier(), mobEffectInstance.isAmbient(), mobEffectInstance.isVisible(), mobEffectInstance.showIcon());
    }

    @Override
    default Ability getAbility() {
        return Ability.INFUSED;
    }
}
