package org.lithereal.core.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.lithereal.item.ability.Abilities;
import org.lithereal.item.ability.IAbility;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public record SpecialAbility(IAbility ability,
                             Type type,
                             Map<UUID, Map<Holder<MobEffect>, Integer>> degradationTickerMap,
                             Map<UUID, Map<Holder<MobEffect>, Integer>> healTickerMap,
                             Map<UUID, Map<Holder<MobEffect>, Integer>> untilReadyMap,
                             Map<UUID, Integer> lastUpdatedMap) {
    public static final Codec<SpecialAbility> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Abilities.CODEC.fieldOf("ability").forGetter(SpecialAbility::ability),
                            Type.CODEC.fieldOf("type").forGetter(SpecialAbility::type))
                    .apply(instance, SpecialAbility::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpecialAbility> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistriesTrusted(CODEC);
    public SpecialAbility(IAbility ability,
                          Type type) {
        this(ability, type, new WeakHashMap<>(),  new WeakHashMap<>(), new WeakHashMap<>(), new WeakHashMap<>());
    }

    public void onAttack(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        if (type == Type.ARMOR) return;
        ability.onAttack(this, itemStack, attacked, attacker);
    }
    public void postAttack(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        if (type == Type.ARMOR) return;
        ability.postAttack(this, itemStack, attacked, attacker);
    }
    public void onItemTick(ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot) {
        if (type == Type.ARMOR) return;
        ability.onItemTick(this, itemStack, level, entity, slot);
    }
    public void onArmourTick(ItemStack itemStack, Level level, Entity entity, @Nullable EquipmentSlot slot) {
        if (type == Type.TOOL || !(slot != null && slot.isArmor())) return;
        ability.onArmourTick(this, itemStack, level, entity, slot);
    }
    public float getLavaMovementEfficiency(ItemStack itemStack, LivingEntity instance, float efficiency) {
        if (type == Type.TOOL) return efficiency;
        return ability.getLavaMovementEfficiency(this, itemStack, instance, efficiency);
    }

    public enum Type implements StringRepresentable {
        TOOL("tool"),
        ARMOR("armor"),;

        private final String name;
        public static final EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        Type(String name) {
            this.name = name;
        }

        @Override
        public @NonNull String getSerializedName() {
            return name;
        }
    }
}
