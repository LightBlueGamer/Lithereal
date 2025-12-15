package org.lithereal.client.renderer.zombie;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.Zombie;
import org.lithereal.entity.phantom.PhantomMob;

public class PhantomZombieRenderer<T extends Zombie & PhantomMob<T>> extends AbstractPhantomZombieRenderer<T, BetterZombieModel<T>, PhantomZombieModel<T>> {
    public PhantomZombieRenderer(EntityRendererProvider.Context context) {
        this(context, BetterZombieModel.ZOMBIE, BetterZombieModel.ZOMBIE_INNER_ARMOR, BetterZombieModel.ZOMBIE_OUTER_ARMOR);
    }

    public PhantomZombieRenderer(
            EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation, ModelLayerLocation modelLayerLocation2, ModelLayerLocation modelLayerLocation3
    ) {
        super(
                context,
                new BetterZombieModel<>(context.bakeLayer(modelLayerLocation)),
                new PhantomZombieModel<>(context.bakeLayer(modelLayerLocation2)),
                new PhantomZombieModel<>(context.bakeLayer(modelLayerLocation3))
        );
    }
}
