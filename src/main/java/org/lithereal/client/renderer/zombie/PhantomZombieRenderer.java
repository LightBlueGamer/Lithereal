package org.lithereal.client.renderer.zombie;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.zombie.Zombie;
import org.lithereal.client.model.BetterZombieModel;
import org.lithereal.client.model.PhantomZombieModel;
import org.lithereal.client.renderer.state.PhantomZombieRenderState;
import org.lithereal.entity.phantom.PhantomMob;

public class PhantomZombieRenderer<T extends Zombie & PhantomMob<T>> extends AbstractPhantomZombieRenderer<T, PhantomZombieRenderState, BetterZombieModel<PhantomZombieRenderState>, PhantomZombieModel<PhantomZombieRenderState>> {
    public PhantomZombieRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new BetterZombieModel<>(context.bakeLayer(BetterZombieModel.ZOMBIE)),
                new BetterZombieModel<>(context.bakeLayer(BetterZombieModel.BABY_ZOMBIE)),
                ArmorModelSet.bake(ModelLayers.ZOMBIE_ARMOR, context.getModelSet(), PhantomZombieModel::new),
                ArmorModelSet.bake(ModelLayers.ZOMBIE_BABY_ARMOR, context.getModelSet(), PhantomZombieModel::new)
        );
    }

    @Override
    public PhantomZombieRenderState createRenderState() {
        return new PhantomZombieRenderState();
    }
}
