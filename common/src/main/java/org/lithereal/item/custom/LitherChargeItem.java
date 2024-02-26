package org.lithereal.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lithereal.entity.ThrownLitherCharge;

public class LitherChargeItem extends Item {
    public LitherChargeItem(Item.Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 60);
        if (!level.isClientSide) {
            ThrownLitherCharge thrownLitherCharge = new ThrownLitherCharge(level, player);
            thrownLitherCharge.setItem(itemStack);
            thrownLitherCharge.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(thrownLitherCharge);
        }
        itemStack.hurtAndBreak(1, player, (playerEntity) -> {
            playerEntity.broadcastBreakEvent(interactionHand);
        });

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}