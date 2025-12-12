package org.lithereal.item.ability;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lithereal.util.CommonUtils;

import java.util.List;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public record EnhancedAbility<I extends AbilityItem>(List<Holder<ArmorMaterial>> armorMaterials) implements IAbility<I> {
    @Override
    public void onAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        if (CommonUtils.isEnhanced(itemStack)) attacked.addEffect(new MobEffectInstance(MobEffects.WITHER, 150));
    }

    @Override
    public void onItemTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {

    }

    @Override
    public void onArmourTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        if (entity instanceof Player player && CommonUtils.isEnhanced(itemStack) && !level.isClientSide())
            if (hasFullSuitOfArmorOn(player) && hasCorrectArmorOn(armorMaterials, player) && !player.hasEffect(MobEffects.DAMAGE_RESISTANCE))
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1));
    }
}
