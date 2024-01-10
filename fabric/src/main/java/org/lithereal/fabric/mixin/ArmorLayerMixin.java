package org.lithereal.fabric.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.lithereal.item.custom.ModArmorMaterials;
import org.lithereal.item.custom.infused.InfusedLitheriteArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    @Shadow protected abstract boolean usesInnerModel(EquipmentSlot p_117129_);

    @Shadow protected abstract void renderModel(PoseStack arg, MultiBufferSource arg2, int i, ArmorItem arg3, A arg4, boolean bl, float f, float g, float h, String string);

    @Shadow protected abstract void renderTrim(ArmorMaterial arg, PoseStack arg2, MultiBufferSource arg3, int i, ArmorTrim arg4, A arg5, boolean bl);

    @Shadow protected abstract void renderGlint(PoseStack arg, MultiBufferSource arg2, int i, A arg3);

    public ArmorLayerMixin(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
    }

    @Inject(method = "renderArmorPiece", at = @At(value = "HEAD"), cancellable = true)
    public void renderArmorPiece(PoseStack poseStack, MultiBufferSource multiBufferSource, T entity, EquipmentSlot equipmentSlot, int p_117123_, A p_117124_, CallbackInfo ci) {
        if(entity instanceof Player player && hasFullSuitOfArmorOn(player)) {
            if(hasCorrectArmorOn(ModArmorMaterials.INFUSED_LITHERITE, player)) {
                if(armorHasCorrectEffect(player))
                    ci.cancel();
            }
        }
    }
    @Inject(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/HumanoidModel;ZFFFLjava/lang/String;)V", ordinal = 2), cancellable = true)
    public void injectPotionChanges(PoseStack poseStack, MultiBufferSource multiBufferSource, T t, EquipmentSlot equipmentSlot, int i, A humanoidModel, CallbackInfo ci) {
        ItemStack itemStack = t.getItemBySlot(equipmentSlot);
        Item armorItem = itemStack.getItem();
        if(armorItem instanceof InfusedLitheriteArmor infusedLitheriteArmorItem) {
            boolean flag = this.usesInnerModel(equipmentSlot);
            int j = PotionUtils.getColor(itemStack);
            float f = (float)(j >> 16 & 255) / 255.0F;
            float g = (float)(j >> 8 & 255) / 255.0F;
            float h = (float)(j & 255) / 255.0F;
            renderModel(poseStack, multiBufferSource, i, infusedLitheriteArmorItem, humanoidModel, flag, f, g, h, null);
            ArmorTrim.getTrim(t.level().registryAccess(), itemStack).ifPresent((p_289638_) -> this.renderTrim(infusedLitheriteArmorItem.getMaterial(), poseStack, multiBufferSource, i, p_289638_, humanoidModel, flag));
            if (itemStack.hasFoil()) {
                this.renderGlint(poseStack, multiBufferSource, i, humanoidModel);
            }
            ci.cancel();
        }
    }

    private boolean armorHasCorrectEffect(Player player) {
        AtomicBoolean bl = new AtomicBoolean(false);
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof InfusedLitheriteArmor)) return false;
            List<MobEffectInstance> mobEffectInstanceList = PotionUtils.getPotion(armorStack).getEffects();
            mobEffectInstanceList.forEach(mobEffectInstance -> bl.set(bl.get() || mobEffectInstance.getEffect() == MobEffects.INVISIBILITY));
        }
        return bl.get();
    }
}