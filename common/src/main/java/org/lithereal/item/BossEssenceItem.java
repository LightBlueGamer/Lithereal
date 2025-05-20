package org.lithereal.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.lithereal.util.BaseBossEssence;
import org.lithereal.util.BossEssenceTier;

public class BossEssenceItem extends Item {
    public static final BossEssenceTier BASE = new BaseBossEssence();
    public BossEssenceItem(Properties properties, BossEssenceTier tier) {
        super(properties.component(DataComponents.POTION_CONTENTS, tier.generatePotionContents()));
    }

    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player)livingEntity : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, itemStack);
        }

        if (!level.isClientSide) {
            PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            potionContents.forEachEffect((mobEffectInstance) -> {
                if (mobEffectInstance.getEffect().value().isInstantenous()) {
                    mobEffectInstance.getEffect().value().applyInstantenousEffect(player, player, livingEntity, mobEffectInstance.getAmplifier(), (double)1.0F);
                } else {
                    livingEntity.addEffect(mobEffectInstance);
                }

            });
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            itemStack.consume(1, player);
        }

        if (player == null || !player.hasInfiniteMaterials()) {
            if (itemStack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        livingEntity.gameEvent(GameEvent.DRINK);
        return itemStack;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }

    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return 40;
    }

    public @NotNull UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.DRINK;
    }
}
