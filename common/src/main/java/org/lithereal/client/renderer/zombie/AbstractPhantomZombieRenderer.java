package org.lithereal.client.renderer.zombie;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Zombie;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;
import org.lithereal.entity.phantom.PhantomMob;

public class AbstractPhantomZombieRenderer<T extends Zombie & PhantomMob<T>, M extends ZombieModel<T>, AM extends ZombieModel<T>> extends HumanoidMobRenderer<T, M> {
    private static final ResourceLocation ZOMBIE_LOCATION = Lithereal.id("textures/entity/phantom_zombie/phantom_zombie.png");
    private static final ResourceLocation PHANTOM_LOCATION = Lithereal.id("textures/entity/phantom_zombie/phantom_zombie_phasing.png");

    protected AbstractPhantomZombieRenderer(EntityRendererProvider.Context context, M zombieModel, AM zombieModel2, AM zombieModel3) {
        super(context, zombieModel, 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, zombieModel2, zombieModel3, context.getModelManager()));
    }

    @Override
    public void render(T livingEntity, float f, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLightIn) {
        poseStack.pushPose();
        if (livingEntity.isPhasing()) {
            float progress = Mth.clamp((livingEntity.getInitialPhasingTimer() - livingEntity.getPhasingTimer() - 1 + partialTicks) / 6, 0, 1);
            float bob = livingEntity.tickCount + partialTicks;
            poseStack.translate(0, progress * (Mth.sin(bob * 0.067F) * 0.125F + 0.125F), 0);
        } else if (livingEntity.isTransitioningOut()) {
            float progress = 1 - ((livingEntity.getPhasingTimer() + 1 - partialTicks) / -6);
            float bob = livingEntity.tickCount + partialTicks;
            poseStack.translate(0, progress * (Mth.sin(bob * 0.067F) * 0.125F + 0.125F), 0);
        }
        super.render(livingEntity, f, partialTicks, poseStack, multiBufferSource, packedLightIn);
        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(T zombie) {
        return zombie.isPhasing() ? PHANTOM_LOCATION : ZOMBIE_LOCATION;
    }

    protected boolean isShaking(T zombie) {
        return super.isShaking(zombie) || zombie.isUnderWaterConverting();
    }
}
