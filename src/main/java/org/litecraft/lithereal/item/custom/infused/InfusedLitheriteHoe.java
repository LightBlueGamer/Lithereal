package org.litecraft.lithereal.item.custom.infused;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.litecraft.lithereal.util.CommonUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfusedLitheriteHoe extends HoeItem {
    public int regenTicker = 0;
    public Map<MobEffect, Integer> untilReady = new HashMap<>();
    public InfusedLitheriteHoe(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity livingEntity) {
            PotionUtils.getPotion(stack).getEffects().forEach((mobEffectInstance) -> {
                MobEffect effect = mobEffectInstance.getEffect();
                boolean bl = effect.isBeneficial();
                boolean bl2 = livingEntity.isInvertedHealAndHarm() && effect == MobEffects.HEAL;
                if(!bl || bl2) {
                    if(!(livingEntity.isInvertedHealAndHarm() && effect == MobEffects.HARM)) {
                        if(player.hasEffect(effect))
                            player.removeEffect(effect);
                        livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                    }
                } else {
                    if (livingEntity.hasEffect(effect)) {
                        livingEntity.removeEffect(effect);
                        player.addEffect(CommonUtils.clone(mobEffectInstance));
                    }
                }
            });
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        if(entity instanceof LivingEntity livingEntity && isSelected) {
            PotionUtils.getPotion(itemStack).getEffects().forEach((mobEffectInstance) -> {
                MobEffect effect = mobEffectInstance.getEffect();
                if(!untilReady.containsKey(effect) && effect.isBeneficial() && !effect.isInstantenous()) {
                    livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                    untilReady.put(effect, mobEffectInstance.getDuration() * 2);
                }
                if(effect == MobEffects.MOVEMENT_SPEED) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20, mobEffectInstance.getAmplifier()));
                }
                if(untilReady.containsKey(effect) && untilReady.get(effect) <= 0) {
                    livingEntity.addEffect(CommonUtils.clone(mobEffectInstance));
                    untilReady.put(effect, mobEffectInstance.getDuration() * 2);
                }
            });
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
        untilReady.forEach(((mobEffect, integer) -> untilReady.put(mobEffect, integer - 1)));
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

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (blockState.getBlock().asItem() == Items.POTATO && PotionUtils.getPotion(itemStack) == Potions.POISON) {
            NonNullList<ItemStack> drops = NonNullList.create();
            ItemStack dropStack = Items.POISONOUS_POTATO.getDefaultInstance();
            drops.add(dropStack);

            for (ItemStack drop : drops) {
                Block.popResource(level, blockPos, drop);
            }
            itemStack.hurtAndBreak(1, livingEntity, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            level.destroyBlock(blockPos, false);
        }

        return super.mineBlock(itemStack, level, blockState, blockPos, livingEntity);
    }
}
