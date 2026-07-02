package org.lithereal.item.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.lithereal.LitherealPlatform;

public record Chill(LevelBasedValue duration) implements EnchantmentEntityEffect {
    public static final MapCodec<Chill> CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(LevelBasedValue.CODEC.fieldOf("duration").forGetter(Chill::duration)).apply(i, Chill::new));

    public void apply(final ServerLevel serverLevel, final int enchantmentLevel, final EnchantedItemInUse item, final Entity entity, final Vec3 position) {
        if (entity instanceof LivingEntity livingEntity) LitherealPlatform.INSTANCE.getChillData(livingEntity).addChill((int) (this.duration().calculate(enchantmentLevel) * 20), livingEntity);
    }

    public MapCodec<Chill> codec() {
        return CODEC;
    }
}
