package org.lithereal.item.custom.infused;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.lithereal.item.custom.ModArmorMaterials;
import org.lithereal.util.CommonUtils;

import java.util.List;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public class InfusedLitheriteArmor extends ArmorItem implements InfusedItem {
    public int regenTicker = 0;
    public int healTicker = 0;

    public InfusedLitheriteArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        if (entity instanceof Player player && player.getInventory().armor.contains(itemStack)) {
            if (player.hurtTime > 0 && !player.level().isClientSide) {
                DamageSource source = player.getLastDamageSource();
                if(source == null) return;
                Entity attacker = source.getEntity();
                if(attacker instanceof LivingEntity livingEntity) {
                    PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
                        MobEffect effect = mobEffectInstance.getEffect();
                        boolean bl = effect.isBeneficial();
                        boolean bl2 = livingEntity.isInvertedHealAndHarm() && effect == MobEffects.HEAL;
                        if(!bl || bl2) {
                            if(!(livingEntity.isInvertedHealAndHarm() && effect == MobEffects.HARM))
                                livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                        } else {
                            if (livingEntity.hasEffect(effect))
                                livingEntity.removeEffect(effect);
                        }
                    });
                }
            }
            if(!level.isClientSide()) {
                if(hasFullSuitOfArmorOn(player)) {
                    if(hasCorrectArmorOn(ModArmorMaterials.INFUSED_LITHERITE, player)) {
                        boolean bl2 = PotionUtils.getPotion(itemStack).getEffects().size() > 1;
                        PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
                            boolean bl = mobEffectInstance.getEffect().isBeneficial();
                            if (bl || bl2) {
                                if(mobEffectInstance.getEffect() != MobEffects.HEAL || healTicker >= 200) {
                                    MobEffectInstance mobEff = InfusedItem.transformInstance(mobEffectInstance, 100);
                                    player.addEffect(mobEff);
                                    if(mobEffectInstance.getEffect() == MobEffects.HEAL) healTicker = 0;
                                }
                            } else {
                                if (player.hasEffect(mobEffectInstance.getEffect())) player.removeEffect(mobEffectInstance.getEffect());
                            }
                        });
                    }
                }
            }
            if (itemStack.isDamaged() && regenTicker >= 10) {
                PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
                    MobEffect effect = mobEffectInstance.getEffect();
                    if(effect == MobEffects.REGENERATION) {
                        itemStack.hurtAndBreak(2 * mobEffectInstance.getAmplifier(), player, player1 -> player1.broadcastBreakEvent(getEquipmentSlot()));
                        regenTicker = 0;
                    }
                });
            }
        }
        regenTicker++;
        healTicker++;
        super.inventoryTick(itemStack, level, entity, slot, isSelected);
    }

    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.EMPTY);
    }

    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        PotionUtils.addPotionTooltip(transformEffects(itemStack, 100), components, 1F);
    }

    public String getDescriptionId(ItemStack p_43364_) {
        return PotionUtils.getPotion(p_43364_).getName(this.getDescriptionId() + ".effect.");
    }

    @Override
    public Component getName(ItemStack itemStack) {
        return getModifiedName(itemStack);
    }

    @Override
    public String getBaseName(ItemStack stack) {
        String armorType = "";
        if (stack.getItem() instanceof ArmorItem armorItem) {
            EquipmentSlot slot = armorItem.getEquipmentSlot();
            armorType = switch (slot) {
                case HEAD -> "Helmet";
                case CHEST -> "Chestplate";
                case LEGS -> "Leggings";
                case FEET -> "Boots";
                default -> armorType;
            };
        }
        return "Litherite " + armorType;
    }
}
