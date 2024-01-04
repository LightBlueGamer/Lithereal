package org.lithereal.item.custom.withering;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lithereal.item.custom.ModArmorMaterials;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public class WitheringLitheriteArmor extends ArmorItem {

    public WitheringLitheriteArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        if (entity instanceof Player player) {
            if(!level.isClientSide()) {
                if (player.hurtTime > 0) {
                    DamageSource source = player.getLastDamageSource();
                    if(source == null) return;
                    Entity attacker = source.getEntity();
                    if (attacker instanceof LivingEntity) {
                        ((LivingEntity) attacker).addEffect(new MobEffectInstance(MobEffects.WITHER, 200));
                    }
                }
                if(hasFullSuitOfArmorOn(player)) {
                    if(hasCorrectArmorOn(ModArmorMaterials.WITHERING_LITHERITE, player)) {
                        if(player.hasEffect(MobEffects.WITHER)) {
                            player.removeEffect(MobEffects.WITHER);
                        }
                    }
                }
            }
        }
        super.inventoryTick(itemStack, level, entity, slot, isSelected);
    }
}
