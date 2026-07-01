package org.lithereal.item.ability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.networking.NetworkManager;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.StringUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gamerules.GameRules;
import org.jspecify.annotations.Nullable;
import org.lithereal.Lithereal;
import org.lithereal.client.KeyMapping;
import org.lithereal.core.component.SpecialAbility;
import org.lithereal.entity.ModDamageTypes;
import org.lithereal.networking.ServerboundSpecialKeyAbilityPacket;
import org.lithereal.util.ChillData;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public record ThermalAbility(int extraDamage,
                             float attackAbilityScalar,
                             EffectDetails effectDetails,
                             List<ResourceKey<EquipmentAsset>> supportedMaterials,
                             List<MobEffectInstance> passiveEffects) implements IAbility {
    public static final Identifier ID = Lithereal.id("thermal_ability");
    public static final MapCodec<ThermalAbility> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("extra_damage", 0).forGetter(ThermalAbility::extraDamage),
                            ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("attack_ability_scalar", 0F).forGetter(ThermalAbility::attackAbilityScalar),
                            EffectDetails.CODEC.fieldOf("effect_details").forGetter(ThermalAbility::effectDetails),
                            ResourceKey.codec(EquipmentAssets.ROOT_ID).listOf().fieldOf("supported_materials").forGetter(ThermalAbility::supportedMaterials),
                            MobEffectInstance.CODEC.listOf().optionalFieldOf("passive_effects", Collections.emptyList()).forGetter(ThermalAbility::passiveEffects))
                    .apply(instance, ThermalAbility::new));
    @Override
    public void onAttack(SpecialAbility ability, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        switch (effectDetails().effectType) {
            case FROSTBURN, FREEZE -> {
                if (attacked.isOnFire()) attacked.extinguishFire();
                attacked.setTicksFrozen((int) (200 * attackAbilityScalar()));
                ChillData.get(attacked).addChill((int) (100 * attackAbilityScalar()), attacked);
            }
            case BURN -> {
                if (attacked.isFreezing()) {
                    attacked.clearFreeze();
                    ChillData.get(attacked).clearChill(attacked);
                }
                attacked.setRemainingFireTicks((int) (100 * attackAbilityScalar()));
            }
        }
    }

    @Override
    public void postAttack(SpecialAbility ability, ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        float bonusDamage = extraDamage();
        switch (effectDetails().effectType) {
            case FROSTBURN -> {
                if (bonusDamage > 0) attacked.hurt(attacker.damageSources().source(ModDamageTypes.FROSTBURN, attacker), bonusDamage);
            }
            case FREEZE -> {
                if (bonusDamage > 0) attacked.hurt(attacker.damageSources().source(ModDamageTypes.FROST, attacker), bonusDamage);
            }
            case BURN -> {
                if (bonusDamage > 0) attacked.hurt(attacker.damageSources().source(ModDamageTypes.BURN, attacker), bonusDamage);
            }
        }
    }

    @Override
    public void onItemTick(SpecialAbility ability, ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot) {

    }

    @Override
    public void onArmourTick(SpecialAbility ability, ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot) {
        EffectDetails effectDetails = effectDetails();
        if (effectDetails.emitsHeat && entity.isInPowderSnow) {
            for (int i = 0; i < 3; i++) {
                BlockPos blockPos = entity.blockPosition().above(i - 1);
                if (level instanceof ServerLevel serverLevel && (serverLevel.getGameRules().get(GameRules.MOB_GRIEFING) || entity instanceof Player) && entity.mayInteract(serverLevel, blockPos) && level.getBlockState(blockPos).is(Blocks.POWDER_SNOW))
                    level.destroyBlock(blockPos, false);
            }
        }

        if (entity.isOnFire() && !(entity instanceof LivingEntity)) {
            entity.extinguishFire();
            entity.setSharedFlagOnFire(false);
        }

        if (entity.isFreezing() && !(entity instanceof LivingEntity))
            entity.clearFreeze();

        if (entity instanceof LivingEntity user) {
            if (user.hurtTime == user.hurtDuration && !user.level().isClientSide()) {
                DamageSource source = user.getLastDamageSource();
                if (source == null) return;
                Entity attacker = source.getEntity();
                if (attacker instanceof LivingEntity livingAttacker) {
                    if (effectDetails.causesFreeze) {
                        attacker.setTicksFrozen(200);
                        ChillData.get(livingAttacker).addChill(100, livingAttacker);
                    }
                    if (effectDetails.causesIgnition) attacker.igniteForTicks(100);
                }
            }
            Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
            EquipmentSlot.Type type = equippable == null ? EquipmentSlot.Type.HUMANOID_ARMOR : equippable.slot().getType();
            if (!level.isClientSide()) {
                if (hasFullSuitOfArmorOn(user, type) && hasCorrectArmorOn(supportedMaterials(), user, type)) {
                    passiveEffects.forEach(statusEffect -> addStatusEffect(user, statusEffect));
                    if (user.isOnFire()) {
                        user.extinguishFire();
                        user.setSharedFlagOnFire(false);
                    }
                    if (user.isFreezing() || ChillData.get(user).chill() > 0) {
                        user.clearFreeze();
                        if (effectDetails().causesFreeze())
                            ChillData.get(user).clearChill(user);
                    }
                }
            } else {
                if (hasFullSuitOfArmorOn(user, type) && hasCorrectArmorOn(supportedMaterials(), user, type)) {
                    if (effectDetails.providesFreeze && KeyMapping.FREEZE_KEY.isDown())
                        NetworkManager.sendToServer(new ServerboundSpecialKeyAbilityPacket(ServerboundSpecialKeyAbilityPacket.SpecialKeyType.FREEZE));
                    if (effectDetails.providesScorch && KeyMapping.SCORCH_KEY.isDown())
                        NetworkManager.sendToServer(new ServerboundSpecialKeyAbilityPacket(ServerboundSpecialKeyAbilityPacket.SpecialKeyType.SCORCH));
                }
            }
        }
    }

    @Override
    public float getLavaMovementEfficiency(SpecialAbility ability, ItemStack itemStack, LivingEntity user, float efficiency) {
        EffectDetails effectDetails = effectDetails();
        Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
        EquipmentSlot.Type type = equippable == null ? EquipmentSlot.Type.HUMANOID_ARMOR : equippable.slot().getType();
        if (hasFullSuitOfArmorOn(user, type) && hasCorrectArmorOn(supportedMaterials(), user, type)) return efficiency + effectDetails.lavaMovementEfficiency;
        return efficiency;
    }

    @Override
    public void addToTooltip(SpecialAbility ability, Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        if (ability.type() == SpecialAbility.Type.TOOL)
            this.effectDetails.addToolTooltip(this, tooltipContext, consumer);
        if (ability.type() == SpecialAbility.Type.ARMOR) {
            this.effectDetails.addArmorTooltip(tooltipContext, consumer);
            consumer.accept(Component.translatable("tooltip.standard_ability.type.armor.0").withStyle(ChatFormatting.RED));
            consumer.accept(Component.translatable("tooltip.standard_ability.type.armor.1").withStyle(ChatFormatting.RED));
            consumer.accept(Component.translatable("tooltip.special_ability.type.armor.materials", combineSupportedMaterialsTogether()));
            PotionContents.addPotionTooltip(this.passiveEffects, consumer, dataComponentGetter.getOrDefault(DataComponents.POTION_DURATION_SCALE, 0.1F), tooltipContext.tickRate());
        }
    }

    @Override
    public MapCodec<? extends IAbility> type() {
        return CODEC;
    }

    private void addStatusEffect(LivingEntity user, MobEffectInstance mapStatusEffect) {
        boolean hasEffect = user.hasEffect(mapStatusEffect.getEffect());

        if (!hasEffect) {
            user.addEffect(new MobEffectInstance(mapStatusEffect));
        }
    }

    public record EffectDetails(EffectType effectType, float lavaMovementEfficiency, boolean emitsHeat, boolean providesScorch, boolean providesFreeze, boolean causesIgnition, boolean causesFreeze) {
        public static final Codec<EffectDetails> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(EffectType.CODEC.optionalFieldOf("effect_type", EffectType.FREEZE).forGetter(EffectDetails::effectType),
                                ExtraCodecs.floatRange(0, 1).optionalFieldOf("lava_movement_efficiency", 0F).forGetter(EffectDetails::lavaMovementEfficiency),
                                Codec.BOOL.fieldOf("emits_heat").forGetter(EffectDetails::emitsHeat),
                                Codec.BOOL.fieldOf("provides_scorch").forGetter(EffectDetails::providesScorch),
                                Codec.BOOL.fieldOf("provides_freeze").forGetter(EffectDetails::providesFreeze),
                                Codec.BOOL.fieldOf("causes_ignition").forGetter(EffectDetails::causesIgnition),
                                Codec.BOOL.fieldOf("causes_freeze").forGetter(EffectDetails::causesFreeze))
                        .apply(instance, EffectDetails::new));
        public static final EffectDetails FROSTBURN = new EffectDetails(EffectType.FROSTBURN, 0.125F, false, false, true, true, true);
        public static final EffectDetails FREEZING = new EffectDetails(EffectType.FREEZE, 0, false, false, true, false, true);
        public static final EffectDetails BURNING = new EffectDetails(EffectType.BURN, 0.2F, true, true, false, true, false);
        public static final EffectDetails SMOLDERING = new EffectDetails(EffectType.BURN, 0.3F, true, true, false, true, false);

        public void addToolTooltip(ThermalAbility ability, Item.TooltipContext tooltipContext, Consumer<Component> consumer) {
            switch (this.effectType()) {
                case BURN -> {
                    if (ability.extraDamage() > 0) consumer.accept(Component.translatable("tooltip.thermal_ability.type.tool.burn.damage", ability.extraDamage()).withStyle(ChatFormatting.RED));
                    consumer.accept(Component.translatable("tooltip.thermal_ability.type.tool.burn", Component.literal(StringUtil.formatTickDuration((int) (100 * ability.attackAbilityScalar()), tooltipContext.tickRate()))).withStyle(ChatFormatting.RED));
                    consumer.accept(Component.translatable("tooltip.thermal_ability.type.tool.burn_negate").withStyle(ChatFormatting.RED));
                }
                case FREEZE, FROSTBURN -> {
                    if (ability.extraDamage() > 0) consumer.accept(Component.translatable("tooltip.thermal_ability.type.tool.freeze.damage", ability.extraDamage()).withStyle(ChatFormatting.BLUE));
                    consumer.accept(Component.translatable("tooltip.thermal_ability.type.tool.freeze.0", Component.literal(StringUtil.formatTickDuration((int) (200 * ability.attackAbilityScalar()), tooltipContext.tickRate()))).withStyle(ChatFormatting.BLUE));
                    consumer.accept(Component.translatable("tooltip.thermal_ability.type.tool.freeze.1", Component.literal(StringUtil.formatTickDuration((int) (100 * ability.attackAbilityScalar()), tooltipContext.tickRate()))).withStyle(ChatFormatting.BLUE));
                    consumer.accept(Component.translatable("tooltip.thermal_ability.type.tool.freeze_negate").withStyle(ChatFormatting.BLUE));
                }
            }
        }

        public void addArmorTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer) {
            if (this.lavaMovementEfficiency() > 0) consumer.accept(Component.translatable("tooltip.thermal_ability.type.armor.lava_movement_efficiency", Component.literal(this.lavaMovementEfficiency() + "").withStyle(ChatFormatting.BLUE)));
            if (this.emitsHeat()) consumer.accept(Component.translatable("tooltip.thermal_ability.type.armor.emits_heat"));
            if (this.providesScorch()) consumer.accept(Component.translatable("tooltip.thermal_ability.type.armor.provides_scorch"));
            if (this.providesFreeze()) consumer.accept(Component.translatable("tooltip.thermal_ability.type.armor.provides_freeze"));
            if (this.causesIgnition()) consumer.accept(Component.translatable("tooltip.thermal_ability.type.armor.causes_ignition", Component.literal(StringUtil.formatTickDuration(100, tooltipContext.tickRate())).withStyle(ChatFormatting.RED)));
            if (this.causesFreeze()) {
                consumer.accept(Component.translatable("tooltip.thermal_ability.type.armor.causes_freeze.0", Component.literal(StringUtil.formatTickDuration(200, tooltipContext.tickRate())).withStyle(ChatFormatting.BLUE)));
                consumer.accept(Component.translatable("tooltip.thermal_ability.type.armor.causes_freeze.1", Component.literal(StringUtil.formatTickDuration(100, tooltipContext.tickRate())).withStyle(ChatFormatting.BLUE)));
            }
            consumer.accept(Component.translatable("tooltip.thermal_ability.type.armor.negate"));
        }
    }

    public enum EffectType implements StringRepresentable {
        FROSTBURN("frostburn"),
        FREEZE("freeze"),
        BURN("burn");

        private final String name;
        public static final Codec<EffectType> CODEC = StringRepresentable.fromEnum(EffectType::values);

        EffectType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
