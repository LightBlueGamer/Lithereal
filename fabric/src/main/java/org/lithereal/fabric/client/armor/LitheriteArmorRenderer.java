package org.lithereal.fabric.client.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.component.DyedItemColor;
import org.lithereal.client.renderer.LitherealArmorModel;
import org.lithereal.item.ModArmorMaterials;
import org.lithereal.item.infused.InfusedLitheriteArmorItem;
import org.lithereal.tags.ModTags;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lithereal.util.CommonUtils.hasCorrectArmorOn;
import static org.lithereal.util.CommonUtils.hasFullSuitOfArmorOn;

public class LitheriteArmorRenderer implements ArmorRenderer {
    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, ItemStack stack, LivingEntity entity, EquipmentSlot equipmentSlot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (entity instanceof Player player && hasFullSuitOfArmorOn(player) && hasCorrectArmorOn(List.of(ModArmorMaterials.SMOLDERING_LITHERITE, ModArmorMaterials.FROSTBITTEN_LITHERITE, ModArmorMaterials.WITHERING_LITHERITE, ModArmorMaterials.INFUSED_LITHERITE), player) && armorHasCorrectEffect(player))
            return;
        ItemStack itemStack = entity.getItemBySlot(equipmentSlot);
        Item item = itemStack.getItem();
        boolean innerModel = usesInnerModel(equipmentSlot);
        HumanoidModel<LivingEntity> usedModel;
        if (itemStack.is(ModTags.LITHERITE_ARMORS) || itemStack.is(ModTags.ODYSIUM_ARMORS)) {
            usedModel = innerModel ? LitherealArmorModel.INNER : LitherealArmorModel.OUTER;
        } else return;
        if(item instanceof ArmorItem armorItem) {
            if (armorItem.getEquipmentSlot() == equipmentSlot) {
                contextModel.copyPropertiesTo(usedModel);
                setPartVisibility(usedModel, equipmentSlot);
                ArmorMaterial armorMaterial = armorItem.getMaterial().value();
                int c = armorItem instanceof InfusedLitheriteArmorItem ?
                        itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor() :
                        stack.is(ItemTags.DYEABLE) ?
                                FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(itemStack, -6265536)) : -1;

                for (ArmorMaterial.Layer armorMaterialLayer : armorMaterial.layers()) {
                    renderArmorPart(poseStack, multiBufferSource, light, itemStack, usedModel, armorMaterialLayer.texture(innerModel), c);
                }

                ArmorTrim armortrim = itemStack.get(DataComponents.TRIM);
                if (armortrim != null)
                    renderTrim(armorItem.getMaterial(), poseStack, multiBufferSource, light, armortrim, usedModel, innerModel);
            }
        }
    }

    private static boolean usesInnerModel(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.LEGS;
    }

    public static void renderArmorPart(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, ItemStack stack, Model model, ResourceLocation texture, int color) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
        model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, color);
    }

    public static void setPartVisibility(HumanoidModel<?> armorModel, EquipmentSlot slot) {
        armorModel.setAllVisible(false);
        switch(slot) {
            case HEAD:
                armorModel.head.visible = true;
                armorModel.hat.visible = true;
                break;
            case CHEST:
                armorModel.body.visible = true;
                armorModel.rightArm.visible = true;
                armorModel.leftArm.visible = true;
                break;
            case LEGS:
                armorModel.body.visible = true;
                armorModel.rightLeg.visible = true;
                armorModel.leftLeg.visible = true;
                break;
            case FEET:
                armorModel.rightLeg.visible = true;
                armorModel.leftLeg.visible = true;
        }
    }

    private static void renderTrim(
            Holder<ArmorMaterial> holder, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, ArmorTrim armorTrim, HumanoidModel<LivingEntity> humanoidModel, boolean bl
    ) {
        TextureAtlasSprite textureAtlasSprite = Minecraft.getInstance().getModelManager().getAtlas(Sheets.ARMOR_TRIMS_SHEET).getSprite(bl ? armorTrim.innerTexture(holder) : armorTrim.outerTexture(holder));
        VertexConsumer vertexConsumer = textureAtlasSprite.wrap(multiBufferSource.getBuffer(Sheets.armorTrimsSheet(armorTrim.pattern().value().decal())));
        humanoidModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
    }

    private static boolean armorHasCorrectEffect(Player player) {
        AtomicBoolean bl = new AtomicBoolean(false);
        for (ItemStack armorStack : player.getInventory().armor) {
            armorStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).forEachEffect(mobEffectInstance -> bl.set(bl.get() || mobEffectInstance.getEffect().is(MobEffects.INVISIBILITY)));
            if (bl.get()) return true;
        }
        return false;
    }
}
