package org.lithereal.client.renderer.zombie;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Drowned;
import org.jetbrains.annotations.NotNull;
import org.lithereal.Lithereal;
import org.lithereal.entity.phantom.PhantomMob;

public class PhantomDrownedRenderer<T extends Drowned & PhantomMob<T>> extends AbstractPhantomZombieRenderer<T, BetterDrownedModel<T>, PhantomDrownedModel<T>> {
    private static final ResourceLocation DROWNED_LOCATION = Lithereal.id("textures/entity/phantom_zombie/phantom_drowned.png");
    private static final ResourceLocation PHANTOM_DROWNED_LOCATION = Lithereal.id("textures/entity/phantom_zombie/phantom_drowned_phasing.png");
    public PhantomDrownedRenderer(EntityRendererProvider.Context context) {
        this(context, BetterZombieModel.ZOMBIE, BetterZombieModel.ZOMBIE_INNER_ARMOR, BetterZombieModel.ZOMBIE_OUTER_ARMOR);
    }

    public PhantomDrownedRenderer(
            EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation, ModelLayerLocation modelLayerLocation2, ModelLayerLocation modelLayerLocation3
    ) {
        super(
                context,
                new BetterDrownedModel<>(context.bakeLayer(modelLayerLocation)),
                new PhantomDrownedModel<>(context.bakeLayer(modelLayerLocation2)),
                new PhantomDrownedModel<>(context.bakeLayer(modelLayerLocation3))
        );
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(T zombie) {
        return zombie.isPhasing() ? PHANTOM_DROWNED_LOCATION : DROWNED_LOCATION;
    }

    protected void setupRotations(T drowned, PoseStack poseStack, float f, float g, float h, float i) {
        super.setupRotations(drowned, poseStack, f, g, h, i);
        float j = drowned.getSwimAmount(h);
        if (j > 0.0F) {
            float k = -10.0F - drowned.getXRot();
            float l = Mth.lerp(j, 0.0F, k);
            poseStack.rotateAround(Axis.XP.rotationDegrees(l), 0.0F, drowned.getBbHeight() / 2.0F / i, 0.0F);
        }
    }
}
