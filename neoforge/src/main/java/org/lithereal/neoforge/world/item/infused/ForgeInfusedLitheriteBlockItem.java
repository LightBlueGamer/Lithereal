package org.lithereal.neoforge.world.item.infused;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.lithereal.world.item.infused.InfusedLitheriteBlockItem;
import org.lithereal.neoforge.client.renderer.LitherealBlockEntityWithoutLevelRenderer;

import java.util.function.Consumer;

public class ForgeInfusedLitheriteBlockItem extends InfusedLitheriteBlockItem {
    public ForgeInfusedLitheriteBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new LitherealBlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
            }
        });
    }
}
