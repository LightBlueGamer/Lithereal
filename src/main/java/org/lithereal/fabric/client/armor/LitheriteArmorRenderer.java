package org.lithereal.fabric.client.armor;

//? fabric {
/*import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.EquipmentAssetManager;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import org.lithereal.client.model.LitherealArmorModel;
import org.lithereal.item.ModArmorMaterials;
import org.lithereal.item.base.ModArmorItem;
import org.lithereal.item.infused.InfusedLitheriteArmorItem;
import org.lithereal.tags.ModTags;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer.shouldRender;
import static org.lithereal.util.ClientUtils.hasCorrectArmorOn;
import static org.lithereal.util.ClientUtils.hasFullSuitOfArmorOn;

public class LitheriteArmorRenderer implements ArmorRenderer {
    private final EquipmentAssetManager equipmentAssets;
    private final Function<LayerTextureKey, Identifier> layerTextureLookup;
    private final Function<TrimSpriteKey, TextureAtlasSprite> trimSpriteLookup;
    public LitheriteArmorRenderer(EntityRendererProvider.Context context) {
        this.equipmentAssets = context.getEquipmentAssets();
        TextureAtlas armorTrimAtlas = context.getAtlas(AtlasIds.ARMOR_TRIMS);
        this.layerTextureLookup = Util.memoize(key -> key.layer.getTextureLocation(key.layerType));
        this.trimSpriteLookup = Util.memoize(key -> armorTrimAtlas.getSprite(key.spriteId()));
    }
    private static boolean usesInnerModel(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.LEGS;
    }

    private boolean armorHasCorrectEffect(HumanoidRenderState state) {
        AtomicBoolean bl = new AtomicBoolean(false);
        for (ItemStack armorStack : List.of(state.headEquipment, state.chestEquipment, state.legsEquipment, state.feetEquipment)) {
            if(!(armorStack.getItem() instanceof InfusedLitheriteArmorItem)) return false;
            armorStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).forEachEffect(mobEffectInstance -> bl.set(bl.get() || mobEffectInstance.getEffect().is(MobEffects.INVISIBILITY)), 1F);
        }
        return bl.get();
    }

    @Override
    public void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack stack, HumanoidRenderState state, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
        if (hasFullSuitOfArmorOn(state) && hasCorrectArmorOn(List.of(ModArmorMaterials.SMOLDERING_LITHERITE, ModArmorMaterials.FROSTBITTEN_LITHERITE, ModArmorMaterials.WITHERING_LITHERITE, ModArmorMaterials.INFUSED_LITHERITE), state) && armorHasCorrectEffect(state))
            return;
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && shouldRender(stack, slot)) {
            int nextOrder = 1;
            boolean innerModel = usesInnerModel(slot);
            HumanoidModel<HumanoidRenderState> usedModel;
            EquipmentClientInfo.LayerType layerType;
            if (stack.is(ModTags.LITHERITE_ARMORS) || stack.is(ModTags.ODYSIUM_ARMORS)) {
                usedModel = (state.isBaby ? LitherealArmorModel.BABY_ARMOR_MODEL_SET : LitherealArmorModel.ARMOR_MODEL_SET).get(equippable.slot());
                layerType = state.isBaby ? EquipmentClientInfo.LayerType.HUMANOID_BABY : innerModel ? EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS : EquipmentClientInfo.LayerType.HUMANOID;
            } else return;
            if (!(stack.getItem() instanceof ModArmorItem armorItem)) return;
            ArmorMaterial armorMaterial = armorItem.getArmorMaterial();
            int c = DyedItemColor.getOrDefault(stack, 0);
            List<EquipmentClientInfo.Layer> layers = this.equipmentAssets.get(armorMaterial.assetId()).getLayers(layerType);
            boolean renderFoil = stack.hasFoil();
            if (layers.isEmpty()) return;
            for (EquipmentClientInfo.Layer layer : layers) {
                int color = armorItem instanceof InfusedLitheriteArmorItem ?
                        stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor() : getColorForLayer(layer, c);
                if (color != 0) {
                    Identifier layerTexture = this.layerTextureLookup.apply(new LayerTextureKey(layerType, layer));
                    ArmorRenderer.submitTransformCopyingModel(contextModel, state, usedModel, state, true, submitNodeCollector.order(nextOrder++), poseStack, RenderTypes.armorCutoutNoCull(layerTexture), light, OverlayTexture.NO_OVERLAY, color, null, state.outlineColor, null);
                    if (renderFoil) {
                        ArmorRenderer
                                .submitTransformCopyingModel(contextModel, state, usedModel, state, true, submitNodeCollector.order(nextOrder++), poseStack, RenderTypes.armorEntityGlint(), light, OverlayTexture.NO_OVERLAY, color, null, state.outlineColor, null);
                    }

                    renderFoil = false;
                }
            }

            ArmorTrim trim = stack.get(DataComponents.TRIM);
            if (trim != null && layerType != EquipmentClientInfo.LayerType.HUMANOID_BABY) {
                TextureAtlasSprite sprite = this.trimSpriteLookup.apply(new TrimSpriteKey(trim, layerType, armorMaterial.assetId()));
                RenderType renderType = Sheets.armorTrimsSheet(trim.pattern().value().decal());
                ArmorRenderer.submitTransformCopyingModel(contextModel, state, usedModel, state, true, submitNodeCollector.order(nextOrder), poseStack, renderType, light, OverlayTexture.NO_OVERLAY, -1, sprite, state.outlineColor, null);
            }
        }
    }

    private static int getColorForLayer(final EquipmentClientInfo.Layer layer, final int dyeColor) {
        Optional<EquipmentClientInfo.Dyeable> dyeable = layer.dyeable();
        if (dyeable.isPresent()) {
            int colorWhenUndyed = dyeable.get().colorWhenUndyed().map(ARGB::opaque).orElse(0);
            return dyeColor != 0 ? dyeColor : colorWhenUndyed;
        } else {
            return -1;
        }
    }

    @Environment(EnvType.CLIENT)
    private record LayerTextureKey(EquipmentClientInfo.LayerType layerType, EquipmentClientInfo.Layer layer) {
    }

    @Environment(EnvType.CLIENT)
    private record TrimSpriteKey(ArmorTrim trim, EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> equipmentAssetId) {
        public Identifier spriteId() {
            return this.trim.layerAssetId(this.layerType.trimAssetPrefix(), this.equipmentAssetId);
        }
    }
}
*///?}