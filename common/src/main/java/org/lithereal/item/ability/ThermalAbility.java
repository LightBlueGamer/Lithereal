package org.lithereal.item.ability;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.block.ModBlocks;
import org.lithereal.client.KeyMapping;
import org.lithereal.entity.ModDamageTypes;

import java.util.List;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public record ThermalAbility<I extends AbilityItem>(int extraDamage,
                                                    float attackAbilityScalar,
                                                    ArmorType armorType,
                                                    List<Holder<ArmorMaterial>> armorMaterials,
                                                    List<MobEffectInstance> effects) implements IAbility<I> {
    @Override
    public void onAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        switch (armorType()) {
            case FROSTBURN, FREEZING -> {
                if (attacked.isOnFire()) attacked.extinguishFire();
                attacked.setTicksFrozen((int) (1000 * attackAbilityScalar()));
            }
            case BURNING -> {
                if (attacked.isFreezing()) attacked.setTicksFrozen(0);
                attacked.setRemainingFireTicks((int) (20000 * attackAbilityScalar()));
            }
        }
    }

    @Override
    public void postAttack(I item, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        float bonusDamage = extraDamage();
        switch (armorType()) {
            case FROSTBURN -> {
                if (bonusDamage > 0) attacked.hurt(attacker.damageSources().source(ModDamageTypes.FROSTBURN, attacker), bonusDamage);
            }
            case FREEZING -> {
                if (bonusDamage > 0) attacked.hurt(attacker.damageSources().source(ModDamageTypes.FROST, attacker), bonusDamage);
            }
            case BURNING -> {
                if (bonusDamage > 0) attacked.hurt(attacker.damageSources().source(ModDamageTypes.BURN, attacker), bonusDamage);
            }
        }
    }

    @Override
    public void onItemTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {

    }

    @Override
    public void onArmourTick(I item, ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        ArmorType armorType = armorType();
        if (armorType.emitsHeat && entity.isInPowderSnow) {
            for (int i = 0; i < 3; i++) {
                BlockPos blockPos = entity.blockPosition().above(i - 1);
                if ((level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || entity instanceof Player) && entity.mayInteract(level, blockPos) && level.getBlockState(blockPos).is(Blocks.POWDER_SNOW))
                    level.destroyBlock(blockPos, false);
            }
        }

        if (entity.isOnFire() && !(entity instanceof Player)) {
            entity.extinguishFire();
            entity.setSharedFlagOnFire(false);
        }

        if (entity.isFreezing() && !(entity instanceof Player))
            entity.setTicksFrozen(0);

        if (entity instanceof LivingEntity user) {
            if (user.hurtTime > 0 && !user.level().isClientSide) {
                DamageSource source = user.getLastDamageSource();
                if (source == null) return;
                Entity attacker = source.getEntity();
                if (attacker instanceof LivingEntity) {
                    if (armorType.causesFreeze) attacker.setTicksFrozen(1000);
                    if (armorType.causesIgnition) attacker.setRemainingFireTicks(100);
                }
            }
            if(!level.isClientSide()) {
                if(hasFullSuitOfArmorOn(user)) {
                    if(hasCorrectArmorOn(armorMaterials(), user)) {
                        effects.forEach(statusEffect -> addStatusEffect(user, statusEffect));
                        if (user.isOnFire()) {
                            user.extinguishFire();
                            user.setSharedFlagOnFire(false);
                        }
                        if (user.isFreezing())
                            user.setTicksFrozen(0);
                        BlockPos belowPos = user.blockPosition().below();
                        BlockState blockBelowState = level.getBlockState(belowPos);
                        Block blockBelow = blockBelowState.getBlock();
                        if (armorType.providesFreeze && KeyMapping.FREEZE_KEY.isDown()) {
                            for (int x = -4; x <= 4; x++) {
                                for (int z = -4; z <= 4; z++) {
                                    BlockPos checkPos = belowPos.offset(x, 0, z);
                                    if (level.getBlockState(checkPos).getBlock() == Blocks.WATER) {
                                        level.setBlockAndUpdate(checkPos, Blocks.FROSTED_ICE.defaultBlockState());
                                    }
                                }
                            }
                        }
                        if (armorType.providesScorch && KeyMapping.SCORCH_KEY.isDown()) {
                            if (blockBelow == Blocks.NETHERRACK) level.setBlockAndUpdate(belowPos, ModBlocks.SCORCHED_NETHERRACK.get().defaultBlockState());
                            else if (blockBelow == Blocks.CRIMSON_NYLIUM) level.setBlockAndUpdate(belowPos, ModBlocks.SCORCHED_CRIMSON_NYLIUM.get().defaultBlockState());
                            else if (blockBelow == Blocks.WARPED_NYLIUM) level.setBlockAndUpdate(belowPos, ModBlocks.SCORCHED_WARPED_NYLIUM.get().defaultBlockState());
                        }
                    }
                }
            }
        }
    }

    @Override
    public float getLavaMovementEfficiency(I castedItem, ItemStack itemStack, LivingEntity user, float efficiency) {
        ArmorType armorType = armorType();
        if (hasFullSuitOfArmorOn(user) && hasCorrectArmorOn(armorMaterials(), user)) return efficiency + armorType.lavaMovementEfficiency * this.attackAbilityScalar;
        return efficiency;
    }

    private void addStatusEffect(LivingEntity user, MobEffectInstance mapStatusEffect) {
        boolean hasEffect = user.hasEffect(mapStatusEffect.getEffect());

        if (!hasEffect) {
            user.addEffect(new MobEffectInstance(mapStatusEffect));
        }
    }

    public enum ArmorType {
        FROSTBURN(0.1F, false, false, true, true, true),
        FREEZING(0, false, false, true, false, true),
        BURNING(0.2F, true, true, false, true, false);
        public final float lavaMovementEfficiency;
        public final boolean emitsHeat;
        public final boolean providesScorch;
        public final boolean providesFreeze;
        public final boolean causesIgnition;
        public final boolean causesFreeze;

        ArmorType(float lavaMovementEfficiency, boolean emitsHeat, boolean providesScorch, boolean providesFreeze, boolean causesIgnition, boolean causesFreeze) {
            this.lavaMovementEfficiency = lavaMovementEfficiency;
            this.emitsHeat = emitsHeat;
            this.providesScorch = providesScorch;
            this.providesFreeze = providesFreeze;
            this.causesIgnition = causesIgnition;
            this.causesFreeze = causesFreeze;
        }
    }
}
