package org.litecraft.lithereal.item.custom.infused;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.litecraft.lithereal.item.custom.ModArmorMaterials;
import org.litecraft.lithereal.util.CommonUtils;

import javax.annotation.Nullable;
import java.util.List;

import static org.litecraft.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.litecraft.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public class InfusedLitheriteArmorItem extends ArmorItem {
    public int regenTicker = 0;
    public int healTicker = 0;

    public InfusedLitheriteArmorItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if(!world.isClientSide()) {
            if(hasFullSuitOfArmorOn(player)) {
                if(hasCorrectArmorOn(ModArmorMaterials.INFUSED_LITHERITE, player)) {
                    PotionUtils.getPotion(stack).getEffects().forEach((mobEffectInstance) -> {
                        boolean bl = mobEffectInstance.getEffect().isBeneficial();
                        if (bl) {
                            if(mobEffectInstance.getEffect() != MobEffects.HEAL || healTicker >= 200) {
                                player.addEffect(CommonUtils.clone(mobEffectInstance));
                                healTicker = 0;
                            }
                        } else {
                            if (player.hasEffect(mobEffectInstance.getEffect())) player.removeEffect(mobEffectInstance.getEffect());
                        }
                    });
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        if (entity instanceof Player player) {
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
        }
        if (itemStack.isDamaged() && regenTicker >= 10) {
            PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
                MobEffect effect = mobEffectInstance.getEffect();
                if(effect == MobEffects.REGENERATION) {
                    itemStack.setDamageValue(itemStack.getDamageValue() - mobEffectInstance.getAmplifier());
                    regenTicker = 0;
                }
            });
        }
        regenTicker++;
        healTicker++;
        super.inventoryTick(itemStack, level, entity, slot, isSelected);
    }

    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
    }

    public void appendHoverText(ItemStack p_43359_, @Nullable Level p_43360_, List<Component> p_43361_, TooltipFlag p_43362_) {
        PotionUtils.addPotionTooltip(p_43359_, p_43361_, 1F);
    }

    public String getDescriptionId(ItemStack p_43364_) {
        return PotionUtils.getPotion(p_43364_).getName(this.getDescriptionId() + ".effect.");
    }

    @Override
    public @org.jetbrains.annotations.Nullable String getCreatorModId(ItemStack itemStack) {
        Potion potionType = PotionUtils.getPotion(itemStack);
        ResourceLocation resourceLocation = ForgeRegistries.POTIONS.getKey(potionType);
        if (resourceLocation != null)
        {
            return resourceLocation.getNamespace();
        }
        return super.getCreatorModId(itemStack);
    }
}