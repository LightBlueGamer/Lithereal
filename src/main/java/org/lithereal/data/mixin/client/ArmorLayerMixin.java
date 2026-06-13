package org.lithereal.data.mixin.client;

//? neoforge {
import com.mojang.blaze3d.vertex.PoseStack;
//?}
import net.minecraft.client.model.HumanoidModel;
//? neoforge {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?}
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
//? neoforge {
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import org.lithereal.item.ModArmorMaterials;
import org.lithereal.item.infused.InfusedLitheriteArmorItem;
//?}
import org.spongepowered.asm.mixin.Mixin;
//? neoforge {
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lithereal.util.ClientUtils.hasCorrectArmorOn;
import static org.lithereal.util.ClientUtils.hasFullSuitOfArmorOn;
//?}

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorLayerMixin<S extends HumanoidRenderState, M extends HumanoidModel<S>, A extends HumanoidModel<S>> extends RenderLayer<S, M> {

    public ArmorLayerMixin(RenderLayerParent<S, M> p_117346_) {
        super(p_117346_);
    }
//? neoforge {

    @Inject(method = "renderArmorPiece", at = @At(value = "HEAD"), cancellable = true)
    public void renderArmorPiece(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack itemStack, EquipmentSlot slot, int lightCoords, S state, CallbackInfo ci) {
        if(hasFullSuitOfArmorOn(state) && hasCorrectArmorOn(List.of(ModArmorMaterials.SMOLDERING_LITHERITE, ModArmorMaterials.FROSTBITTEN_LITHERITE, ModArmorMaterials.WITHERING_LITHERITE, ModArmorMaterials.INFUSED_LITHERITE), state) && lithereal$armorHasCorrectEffect(state))
            ci.cancel();
    }

    @Unique
    private boolean lithereal$armorHasCorrectEffect(S state) {
        AtomicBoolean bl = new AtomicBoolean(false);
        for (ItemStack armorStack : List.of(state.headEquipment, state.chestEquipment, state.legsEquipment, state.feetEquipment)) {
            if(!(armorStack.getItem() instanceof InfusedLitheriteArmorItem)) return false;
            armorStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).forEachEffect(mobEffectInstance -> bl.set(bl.get() || mobEffectInstance.getEffect().is(MobEffects.INVISIBILITY)), 1F);
        }
        return bl.get();
    }
//?}
}