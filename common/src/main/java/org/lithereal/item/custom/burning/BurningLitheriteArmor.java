package org.lithereal.item.custom.burning;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.lithereal.block.ModBlocks;
import org.lithereal.item.custom.ModArmorMaterials;
import org.lithereal.util.KeyBinding;

import java.util.Map;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public class BurningLitheriteArmor extends ArmorItem {
    private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                    .put(ModArmorMaterials.BURNING_LITHERITE,
                            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 1)).build();
    public BurningLitheriteArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        if (entity instanceof Player player) {
            if (player.hurtTime > 0 && !player.level().isClientSide) {
                DamageSource source = player.getLastDamageSource();
                if(source == null) return;
                Entity attacker = source.getEntity();
                if (attacker instanceof LivingEntity) {
                    attacker.setSecondsOnFire(5);
                }
            }
            if(!level.isClientSide()) {
                if(hasFullSuitOfArmorOn(player)) {
                    evaluateArmorEffects(player);
                    Block blockBelow = level.getBlockState(player.blockPosition().below()).getBlock();
                    if(hasCorrectArmorOn(ModArmorMaterials.BURNING_LITHERITE, player)) {
                        if(KeyBinding.SCORCH_KEY.isDown()) {
                            if(blockBelow == Blocks.NETHERRACK) level.setBlockAndUpdate(player.blockPosition().below(), ModBlocks.SCORCHED_NETHERRACK.get().defaultBlockState());
                            else if(blockBelow == Blocks.CRIMSON_NYLIUM) level.setBlockAndUpdate(player.blockPosition().below(), ModBlocks.SCORCHED_CRIMSON_NYLIUM.get().defaultBlockState());
                            else if(blockBelow == Blocks.WARPED_NYLIUM) level.setBlockAndUpdate(player.blockPosition().below(), ModBlocks.SCORCHED_WARPED_NYLIUM.get().defaultBlockState());
                        }
                    }
                }
            }
        }

        super.inventoryTick(itemStack, level, entity, slot, isSelected);
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            MobEffectInstance mapStatusEffect = entry.getValue();

            if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
            }
        }
    }

    private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial,
                                            MobEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());

        if(hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(mapStatusEffect.getEffect(),
                    mapStatusEffect.getDuration(), mapStatusEffect.getAmplifier()));
        }
    }
}