package org.lithereal.client.renderer.zombie;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.lithereal.Lithereal;
import org.lithereal.client.model.BetterDrownedModel;
import org.lithereal.client.model.BetterZombieModel;
import org.lithereal.client.model.PhantomDrownedModel;
import org.lithereal.client.renderer.state.PhantomZombieRenderState;
import org.lithereal.entity.phantom.PhantomMob;

public class PhantomDrownedRenderer<T extends Drowned & PhantomMob<T>> extends AbstractPhantomZombieRenderer<T, PhantomZombieRenderState, BetterDrownedModel<PhantomZombieRenderState>, PhantomDrownedModel<PhantomZombieRenderState>> {
    private static final Identifier DROWNED_LOCATION = Lithereal.id("textures/entity/phantom_zombie/phantom_drowned");
    private static final Identifier BABY_DROWNED_LOCATION = Lithereal.id("textures/entity/phantom_zombie/phantom_drowned_baby");

    public PhantomDrownedRenderer(final EntityRendererProvider.Context context) {
        super(
                context,
                new BetterDrownedModel<>(context.bakeLayer(BetterZombieModel.ZOMBIE)),
                new BetterDrownedModel<>(context.bakeLayer(BetterZombieModel.BABY_ZOMBIE)),
                ArmorModelSet.bake(ModelLayers.DROWNED_ARMOR, context.getModelSet(), PhantomDrownedModel::new),
                ArmorModelSet.bake(ModelLayers.DROWNED_BABY_ARMOR, context.getModelSet(), PhantomDrownedModel::new)
        );
    }

    @Override
    public PhantomZombieRenderState createRenderState() {
        return new PhantomZombieRenderState();
    }

    @Override
    public Identifier getBaseTexture(PhantomZombieRenderState state) {
        return state.isBaby ? BABY_DROWNED_LOCATION : DROWNED_LOCATION;
    }

    protected void setupRotations(final PhantomZombieRenderState state, final PoseStack poseStack, final float bodyRot, final float entityScale) {
        super.setupRotations(state, poseStack, bodyRot, entityScale);
        float swimAmount = state.swimAmount;
        if (swimAmount > 0.0F) {
            float targetRotationX = -10.0F - state.xRot;
            float rotationX = Mth.lerp(swimAmount, 0.0F, targetRotationX);
            poseStack.rotateAround(Axis.XP.rotationDegrees(rotationX), 0.0F, state.boundingBoxHeight / 2.0F / entityScale, 0.0F);
        }
    }

    protected HumanoidModel.ArmPose getArmPose(final T mob, final HumanoidArm arm) {
        ItemStack item = mob.getItemHeldByArm(arm);
        return mob.getMainArm() == arm && mob.isAggressive() && item.is(Items.TRIDENT) ? HumanoidModel.ArmPose.THROW_TRIDENT : super.getArmPose(mob, arm);
    }
}
