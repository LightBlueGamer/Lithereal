package org.lithereal.neoforge.data.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import org.lithereal.item.ModArmorMaterials;
import org.lithereal.item.infused.InfusedLitheriteArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public ArmorLayerMixin(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
    }

    @Inject(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V", at = @At(value = "HEAD"), cancellable = true)
    public void renderArmorPiece(PoseStack arg, MultiBufferSource arg2, LivingEntity entity, EquipmentSlot arg4, int i, HumanoidModel<LivingEntity> arg5, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if(entity instanceof Player player && hasFullSuitOfArmorOn(player) && hasCorrectArmorOn(List.of(ModArmorMaterials.SMOLDERING_LITHERITE, ModArmorMaterials.FROSTBITTEN_LITHERITE, ModArmorMaterials.WITHERING_LITHERITE, ModArmorMaterials.INFUSED_LITHERITE), player) && lithereal$armorHasCorrectEffect(player))
            ci.cancel();
    }

    @Unique
    private boolean lithereal$armorHasCorrectEffect(Player player) {
        AtomicBoolean bl = new AtomicBoolean(false);
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof InfusedLitheriteArmorItem)) return false;
            armorStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).forEachEffect(mobEffectInstance -> bl.set(bl.get() || mobEffectInstance.getEffect().is(MobEffects.INVISIBILITY)));
        }
        return bl.get();
    }
}