package org.lithereal.fabric.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.lithereal.item.custom.ModArmorMaterials;
import org.lithereal.item.custom.infused.InfusedLitheriteArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    @Shadow protected abstract boolean usesInnerModel(EquipmentSlot p_117129_);

    @Shadow protected abstract void renderGlint(PoseStack arg, MultiBufferSource arg2, int i, A arg3);

    @Shadow protected abstract void renderModel(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, A humanoidModel, float f, float g, float h, ResourceLocation resourceLocation);

    @Shadow protected abstract void renderTrim(Holder<ArmorMaterial> holder, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, ArmorTrim armorTrim, A humanoidModel, boolean bl);

    @Shadow protected abstract void setPartVisibility(A humanoidModel, EquipmentSlot equipmentSlot);

    public ArmorLayerMixin(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
    }

    @Inject(method = "renderArmorPiece", at = @At(value = "HEAD"), cancellable = true)
    public void renderArmorPiece(PoseStack poseStack, MultiBufferSource multiBufferSource, T entity, EquipmentSlot equipmentSlot, int p_117123_, A p_117124_, CallbackInfo ci) {
        if(entity instanceof Player player && hasFullSuitOfArmorOn(player) && hasCorrectArmorOn(ModArmorMaterials.INFUSED_LITHERITE, player) && armorHasCorrectEffect(player))
            ci.cancel();
    }
    @Inject(method = "renderArmorPiece", at = @At(value = "HEAD"), cancellable = true)
    public void injectPotionChanges(PoseStack poseStack, MultiBufferSource multiBufferSource, T t, EquipmentSlot equipmentSlot, int i, A humanoidModel, CallbackInfo ci) {
        ItemStack itemStack = t.getItemBySlot(equipmentSlot);
        Item armorItem = itemStack.getItem();
        if(armorItem instanceof InfusedLitheriteArmorItem infusedLitheriteArmorItem) {
            if (infusedLitheriteArmorItem.getEquipmentSlot() == equipmentSlot) {
                this.getParentModel().copyPropertiesTo(humanoidModel);
                this.setPartVisibility(humanoidModel, equipmentSlot);
                boolean bl = this.usesInnerModel(equipmentSlot);
                ArmorMaterial armorMaterial = infusedLitheriteArmorItem.getMaterial().value();
                int c = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor();
                float r = (float) (c >> 16 & 255) / 255.0F;
                float g = (float) (c >> 8 & 255) / 255.0F;
                float b = (float) (c & 255) / 255.0F;

                for (ArmorMaterial.Layer armorMaterialLayer : armorMaterial.layers()) {
                    this.renderModel(poseStack, multiBufferSource, i, humanoidModel, r, g, b, armorMaterialLayer.texture(bl));
                }

                ArmorTrim armortrim = itemStack.get(DataComponents.TRIM);
                if (armortrim != null)
                    this.renderTrim(infusedLitheriteArmorItem.getMaterial(), poseStack, multiBufferSource, i, armortrim, humanoidModel, bl);
                if (itemStack.hasFoil())
                    this.renderGlint(poseStack, multiBufferSource, i, humanoidModel);
                ci.cancel();
            }
        }
    }

    private boolean armorHasCorrectEffect(Player player) {
        AtomicBoolean bl = new AtomicBoolean(false);
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof InfusedLitheriteArmorItem)) return false;
            armorStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).forEachEffect(mobEffectInstance -> bl.set(bl.get() || mobEffectInstance.getEffect() == MobEffects.INVISIBILITY));
        }
        return bl.get();
    }
}