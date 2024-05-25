package org.lithereal.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.Vec3;
import org.lithereal.entity.ThrownLitherCharge;

import java.util.Random;

public class LitherBerryItem extends Item {
    public LitherBerryItem(Item.Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        ItemStack itemStack2 = super.finishUsingItem(itemStack, level, livingEntity);
        if (!level.isClientSide) {
            Random random = new Random();
            BlockPos targetPos;
            BlockState state;
            Vec3 vec3 = livingEntity.position();
            SoundSource soundSource;
            SoundEvent soundEvent;

            do {
                double d = livingEntity.getX() + (random.nextDouble() - 0.5) * 16.0;
                double e = Mth.clamp(livingEntity.getY() + (double) (random.nextInt(16) - 8), (double) level.getMinBuildHeight(), (double) (level.getMinBuildHeight() + ((ServerLevel) level).getLogicalHeight() - 1));
                double f = livingEntity.getZ() + (random.nextDouble() - 0.5) * 16.0;
                targetPos = new BlockPos((int) d, (int) e, (int) f);
                state = level.getBlockState(targetPos);
            } while (!state.isAir() || !level.getBlockState(targetPos.below()).isSolidRender(level, targetPos.below()));

            if (livingEntity.randomTeleport(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, true)) {
                level.gameEvent(GameEvent.TELEPORT, vec3, Context.of(livingEntity));
                if (livingEntity instanceof Fox) {
                    soundEvent = SoundEvents.FOX_TELEPORT;
                    soundSource = SoundSource.NEUTRAL;
                } else {
                    soundEvent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                    soundSource = SoundSource.PLAYERS;
                }
                level.playSound((Player) null, targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, soundEvent, soundSource);
                livingEntity.resetFallDistance();
            }

            if (livingEntity instanceof Player) {
                Player player = (Player)livingEntity;
                if (!player.getCooldowns().isOnCooldown(this)) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 4, false, false));
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 100, 1, false, false));
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, false, false));
                    player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0, false, false));
                    ThrownLitherCharge charge = new ThrownLitherCharge(level, livingEntity);
                    charge.causeExplosion(new Vec3(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5), 3, Level.ExplosionInteraction.BLOCK, true);
                }
                player.getCooldowns().addCooldown(this, 100);
            }
        }

        return itemStack2;
    }
}