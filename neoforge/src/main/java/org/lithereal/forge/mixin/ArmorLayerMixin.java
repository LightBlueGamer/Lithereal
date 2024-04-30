package org.lithereal.forge.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.jetbrains.annotations.Nullable;
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

    @Shadow protected abstract void renderModel(PoseStack p_289664_, MultiBufferSource p_289689_, int p_289681_, ArmorItem p_289650_, Model p_289658_, boolean p_289668_, float p_289678_, float p_289674_, float p_289693_, ResourceLocation armorResource);

    @Shadow protected abstract Model getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model);

    @Shadow protected abstract boolean usesInnerModel(EquipmentSlot p_117129_);

    @Shadow public abstract ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type);

    @Shadow protected abstract void renderGlint(PoseStack p_289673_, MultiBufferSource p_289654_, int p_289649_, Model p_289659_);

    @Shadow protected abstract void renderTrim(ArmorMaterial p_289690_, PoseStack p_289687_, MultiBufferSource p_289643_, int p_289683_, ArmorTrim p_289692_, Model p_289663_, boolean p_289651_);

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
    @Inject(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/Model;ZFFFLnet/minecraft/resources/ResourceLocation;)V", ordinal = 2), cancellable = true)
    public void injectPotionChanges(PoseStack poseStack, MultiBufferSource multiBufferSource, T t, EquipmentSlot equipmentSlot, int i, A a, CallbackInfo ci) {
        ItemStack itemStack = t.getItemBySlot(equipmentSlot);
        Item armorItem = itemStack.getItem();
        if(armorItem instanceof InfusedLitheriteArmor infusedLitheriteArmorItem) {
            Model model = getArmorModelHook(t, itemStack, equipmentSlot, a);
            boolean flag = this.usesInnerModel(equipmentSlot);
            int j = PotionUtils.getColor(itemStack);
            float f = (float)(j >> 16 & 255) / 255.0F;
            float g = (float)(j >> 8 & 255) / 255.0F;
            float h = (float)(j & 255) / 255.0F;
            this.renderModel(poseStack, multiBufferSource, i, infusedLitheriteArmorItem, model, flag, f, g, h, this.getArmorResource(t, itemStack, equipmentSlot, null));
            ArmorTrim.getTrim(t.level().registryAccess(), itemStack).ifPresent((p_289638_) -> this.renderTrim(infusedLitheriteArmorItem.getMaterial(), poseStack, multiBufferSource, i, p_289638_, model, flag));
            if (itemStack.hasFoil()) {
                this.renderGlint(poseStack, multiBufferSource, i, model);
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