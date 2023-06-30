package org.litecraft.lithereal.item.custom.freezing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.litecraft.lithereal.item.custom.ModArmorMaterials;
import org.litecraft.lithereal.util.KeyBinding;

import static org.litecraft.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.litecraft.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public class CooledLitheriteArmorItem extends ArmorItem {

    public CooledLitheriteArmorItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if(!world.isClientSide()) {
            if(hasFullSuitOfArmorOn(player)) {
                if(hasCorrectArmorOn(ModArmorMaterials.COOLED_LITHERITE, player)) {
                    if(player.isFreezing()) {
                        player.setTicksFrozen(0);
                    }
                }
                if(KeyBinding.FREEZE_KEY.isDown()) {
                    for (int x = -4; x <= 4; x++) {
                        for (int z = -4; z <= 4; z++) {
                            BlockPos checkPos = player.blockPosition().offset(x, -1, z);
                            if (world.getBlockState(checkPos).getBlock() == Blocks.WATER) {
                                world.setBlockAndUpdate(checkPos, Blocks.FROSTED_ICE.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean isSelected) {
        if (entity instanceof Player player) {
            if (player.hurtTime > 0 && !player.level().isClientSide) {
                DamageSource source = player.getLastDamageSource();
                if(source == null) return;
                Entity attacker = source.getEntity();
                if (attacker instanceof LivingEntity) {
                    attacker.setTicksFrozen(1000);
                }
            }
        }
        super.inventoryTick(itemStack, level, entity, slot, isSelected);
    }
}
