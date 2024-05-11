package org.lithereal.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

import static org.lithereal.LitherealExpectPlatform.applyKnockbackToNearbyEntities;

public class WarHammer extends TieredItem {
    private static final int knockbackStrength = 1;
    private boolean isCharged = true;
    private int cooldownTicks = 0;
    private static final int COOLDOWN_DURATION = 25;

    public WarHammer(Tier tier, int damage, float speed, Properties properties) {
        super(tier, properties.attributes(createAttributes(tier, damage, speed)));
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, float damage, float speed) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean canAttackBlock(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        return !player.isCreative();
    }

    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        Level world = attacker.getCommandSenderWorld();

        if (attacker instanceof Player player && isCharged && cooldownTicks == 0 && !player.isSprinting() && !player.isCrouching() && !player.onGround()) {
            List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(3));
            int affectedEntities = 0;

            if (target.isAlive()) {
                for (LivingEntity entity : entities) {
                    if (entity != target && affectedEntities < 3) {
                        applyKnockbackToNearbyEntities(player, entity, knockbackStrength);
                        affectedEntities++;
                    }
                }
            }

            world.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.MACE_SMASH_GROUND, SoundSource.PLAYERS, 1.0f, 1.0f);

            isCharged = false;
            cooldownTicks = COOLDOWN_DURATION;
        }
        return true;
    }


    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        super.inventoryTick(stack, world, entity, slot, isSelected);

        if (cooldownTicks > 0) {
            cooldownTicks--;
        }

        if (cooldownTicks == 0 && !isCharged) {
            isCharged = true;
        }
    }
}