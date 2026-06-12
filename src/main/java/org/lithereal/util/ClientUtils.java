package org.lithereal.util;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.item.equipment.ArmorMaterial;
import org.lithereal.item.base.ModArmorItem;

import java.util.List;

public class ClientUtils {
    public static <S extends HumanoidRenderState> boolean hasFullSuitOfArmorOn(S renderState) {
        return !renderState.headEquipment.isEmpty() && !renderState.chestEquipment.isEmpty() && !renderState.legsEquipment.isEmpty() && !renderState.feetEquipment.isEmpty();
    }

    public static <S extends HumanoidRenderState> boolean hasCorrectArmorOn(List<ArmorMaterial> materials, S renderState) {
        return (renderState.headEquipment.getItem() instanceof ModArmorItem headItem && materials.contains(headItem.getArmorMaterial()))
                && (renderState.chestEquipment.getItem() instanceof ModArmorItem chestItem && materials.contains(chestItem.getArmorMaterial()))
                && (renderState.legsEquipment.getItem() instanceof ModArmorItem legsItem && materials.contains(legsItem.getArmorMaterial()))
                && (renderState.feetEquipment.getItem() instanceof ModArmorItem feetItem && materials.contains(feetItem.getArmorMaterial()));
    }
}
