package org.lithereal.client.renderer.zombie;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.component.SwingAnimation;
import org.jspecify.annotations.NonNull;
import org.lithereal.Lithereal;
import org.lithereal.client.renderer.state.PhantomRenderState;
import org.lithereal.entity.phantom.PhantomMob;

public abstract class AbstractPhantomZombieRenderer<T extends Zombie & PhantomMob<T>, S extends ZombieRenderState & PhantomRenderState, M extends ZombieModel<S>, AM  extends ZombieModel<S>> extends HumanoidMobRenderer<T, S, M> {
    private static final Identifier ZOMBIE_LOCATION = Lithereal.id("textures/entity/phantom_zombie/phantom_zombie.png");
    private static final Identifier BABY_ZOMBIE_LOCATION = Lithereal.id("textures/entity/phantom_zombie/phantom_zombie_baby.png");

    protected AbstractPhantomZombieRenderer(
            final EntityRendererProvider.Context context, final M model, final M babyModel, final ArmorModelSet<AM> armorSet, final ArmorModelSet<AM> babyArmorSet
    ) {
        super(context, model, babyModel, 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, armorSet, babyArmorSet, context.getEquipmentRenderer()));
    }

    public @NonNull Identifier getTextureLocation(final S state) {
        return getBaseTexture(state).withSuffix(state.isPhasing() ? "_phasing" : "");
    }

    public Identifier getBaseTexture(final S state) {
        return state.isBaby ? BABY_ZOMBIE_LOCATION : ZOMBIE_LOCATION;
    }

    public void extractRenderState(final T entity, final S state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isAggressive = entity.isAggressive();
        state.isConverting = entity.isUnderWaterConverting();
        state.extract(entity);
    }

    @Override
    public void submit(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        float partialTicks = state.ageInTicks - Mth.floor(state.ageInTicks);
        if (state.isPhasing()) {
            float progress = Mth.clamp((state.getInitialPhasingTimer() - state.getPhasingTimer() - 1 + partialTicks) / 6, 0, 1);
            poseStack.translate(0, progress * (Mth.sin(state.ageInTicks * 0.067F) * 0.125F + 0.125F), 0);
        } else if (state.isTransitioningOut()) {
            float progress = 1 - ((state.getPhasingTimer() + 1 - partialTicks) / -6);
            poseStack.translate(0, progress * (Mth.sin(state.ageInTicks * 0.067F) * 0.125F + 0.125F), 0);
        }
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    protected boolean isShaking(final S state) {
        return super.isShaking(state) || state.isConverting;
    }

    protected HumanoidModel.ArmPose getArmPose(final T mob, final HumanoidArm arm) {
        SwingAnimation otherAnim = mob.getItemHeldByArm(arm.getOpposite()).get(DataComponents.SWING_ANIMATION);
        return otherAnim != null && otherAnim.type() == SwingAnimationType.STAB ? HumanoidModel.ArmPose.SPEAR : super.getArmPose(mob, arm);
    }
}
