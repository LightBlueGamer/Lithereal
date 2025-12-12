package org.lithereal.item.ability;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lithereal.item.ModArmorMaterials;

import java.util.List;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public record WitheringAbility<I extends AbilityItem>(List<Holder<ArmorMaterial>> supportedMaterials) implements IAbility<I> {
    @Override
    public void onAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        attacked.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1));
    }

    @Override
    public void onItemTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {

    }

    @Override
    public void onArmourTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        if (entity instanceof Player player) {
            if (!level.isClientSide()) {
                if (player.hurtTime > 0) {
                    DamageSource source = player.getLastDamageSource();
                    if (source == null) return;
                    Entity attacker = source.getEntity();
                    if (attacker instanceof LivingEntity)
                        ((LivingEntity) attacker).addEffect(new MobEffectInstance(MobEffects.WITHER, 200));
                }
                if (hasFullSuitOfArmorOn(player)) {
                    if (hasCorrectArmorOn(supportedMaterials, player)) {
                        if (player.hasEffect(MobEffects.WITHER))
                            player.removeEffect(MobEffects.WITHER);
                    }
                }
            }
        }
    }
}
