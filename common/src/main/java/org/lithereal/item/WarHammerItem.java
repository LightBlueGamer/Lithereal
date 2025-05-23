package org.lithereal.item;

import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.lithereal.data.compat.ModWeaponType;

import java.util.function.Predicate;

public class WarHammerItem extends TieredItem {

    public WarHammerItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, properties.attributes(createAttributes(tier, damage, speed)));
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, float damage, float speed) {
        if (Platform.isModLoaded("combatify")) {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            ModWeaponType.WAR_HAMMER.addCombatAttributes(4, tier, builder);
            return builder.build();
        }
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    public boolean canAttackBlock(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        return !player.isCreative();
    }

    public int getEnchantmentValue() {
        return 15;
    }

    public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        itemStack.hurtAndBreak(1, livingEntity2, EquipmentSlot.MAINHAND);
        if (livingEntity2 instanceof ServerPlayer serverPlayer) {
            if (canSmashAttack(serverPlayer)) {
                ServerLevel serverLevel = (ServerLevel)livingEntity2.level();
                serverPlayer.currentImpulseImpactPos = serverPlayer.position();
                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
                if (livingEntity.onGround()) {
                    serverPlayer.setSpawnExtraParticlesOnFall(true);
                    SoundEvent soundEvent = SoundEvents.MACE_SMASH_GROUND;
                    serverLevel.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), soundEvent, serverPlayer.getSoundSource(), 1.0F, 1.0F);
                }

                knockback(serverLevel, serverPlayer, livingEntity);
                return true;
            }
        }

        return false;
    }

    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack2.is(ModItems.MYSTERIOUS_ROD);
    }

    @Override
    public float getAttackDamageBonus(Entity entity, float f, DamageSource damageSource) {
        return canSmashAttack(entity) ? f : 0.0F;
    }

    private static void knockback(Level level, Player player, Entity entity) {
        level.levelEvent(2013, entity.getOnPos(), 750);
        level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3.5), knockbackPredicate(player, entity)).forEach((livingEntity) -> {
            Vec3 vec3 = livingEntity.position().subtract(entity.position());
            double strength = getKnockbackPower(livingEntity, vec3);
            Vec3 vec32 = vec3.normalize().scale(strength);
            if (strength > 0.0) {
                livingEntity.push(vec32.x, 0.52499999105, vec32.z);
            }

        });
    }

    private static Predicate<LivingEntity> knockbackPredicate(Player player, Entity entity) {
        return (livingEntity) -> {
            boolean var10000;
            boolean bl;
            boolean bl2;
            boolean bl3;
            label44: {
                bl = !livingEntity.isSpectator();
                bl2 = livingEntity != player && livingEntity != entity;
                bl3 = !player.isAlliedTo(livingEntity);
                if (livingEntity instanceof ArmorStand armorStand) {
                    if (armorStand.isMarker()) {
                        var10000 = false;
                        break label44;
                    }
                }

                var10000 = true;
            }

            boolean bl4 = var10000;
            boolean bl5 = entity.distanceToSqr(livingEntity) <= Math.pow(3.5, 2.0);
            return bl && bl2 && bl3 && bl4 && bl5;
        };
    }

    private static double getKnockbackPower(LivingEntity livingEntity, Vec3 vec3) {
        return (3.5 - vec3.length()) * 0.52499999105 * 2 * (1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
    }

    public static boolean canSmashAttack(Entity entity) {
        return entity instanceof LivingEntity livingEntity && entity.fallDistance >= 0.1F && !livingEntity.isFallFlying() && !entity.isSprinting();
    }
}