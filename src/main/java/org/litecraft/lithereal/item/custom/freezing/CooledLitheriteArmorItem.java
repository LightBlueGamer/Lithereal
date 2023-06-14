package org.litecraft.lithereal.item.custom.freezing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.litecraft.lithereal.item.custom.ModArmorMaterials;
import org.litecraft.lithereal.util.KeyBinding;

public class CooledLitheriteArmorItem extends ArmorItem {

    public CooledLitheriteArmorItem(ArmorMaterial armorMaterial, EquipmentSlot type, Properties properties) {
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

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack breastplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !breastplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        for (ItemStack armorStack: player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
        ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }
}
