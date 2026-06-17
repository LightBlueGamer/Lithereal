package org.lithereal.util;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.Equippable;

import java.util.List;
import java.util.Optional;

public class ClientUtils {
    public static <S extends HumanoidRenderState> boolean hasFullSuitOfArmorOn(S renderState) {
        return !renderState.headEquipment.isEmpty() && !renderState.chestEquipment.isEmpty() && !renderState.legsEquipment.isEmpty() && !renderState.feetEquipment.isEmpty();
    }

    public static <S extends HumanoidRenderState> boolean hasCorrectArmorOn(List<ResourceKey<EquipmentAsset>> materials, S renderState) {
        if (materials.isEmpty()) return true;
        Equippable headEquippable;
        Equippable chestEquippable;
        Equippable legsEquippable;
        Equippable feetEquippable;
        Optional<ResourceKey<EquipmentAsset>> headAssetId;
        Optional<ResourceKey<EquipmentAsset>> chestAssetId;
        Optional<ResourceKey<EquipmentAsset>> legsAssetId;
        Optional<ResourceKey<EquipmentAsset>> feetAssetId;
        return ((headEquippable = renderState.headEquipment.get(DataComponents.EQUIPPABLE)) != null && (headAssetId = headEquippable.assetId()).isPresent() && materials.contains(headAssetId.get()))
                && ((chestEquippable = renderState.chestEquipment.get(DataComponents.EQUIPPABLE)) != null && (chestAssetId = chestEquippable.assetId()).isPresent() && materials.contains(chestAssetId.get()))
                && ((legsEquippable = renderState.legsEquipment.get(DataComponents.EQUIPPABLE)) != null && (legsAssetId = legsEquippable.assetId()).isPresent() && materials.contains(legsAssetId.get()))
                && ((feetEquippable = renderState.feetEquipment.get(DataComponents.EQUIPPABLE)) != null && (feetAssetId = feetEquippable.assetId()).isPresent() && materials.contains(feetAssetId.get()));
    }
}
