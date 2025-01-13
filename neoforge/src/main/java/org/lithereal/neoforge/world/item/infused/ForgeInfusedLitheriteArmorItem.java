package org.lithereal.neoforge.world.item.infused;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.lithereal.item.infused.InfusedLitheriteArmorItem;

import java.util.function.Consumer;

public class ForgeInfusedLitheriteArmorItem extends InfusedLitheriteArmorItem {

    public ForgeInfusedLitheriteArmorItem(Holder<ArmorMaterial> armorMaterial, Type type, int durability, Properties properties) {
        super(armorMaterial, type, durability, properties);
    }


    @SuppressWarnings("removal")
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public int getArmorLayerTintColor(@NotNull ItemStack stack, @NotNull LivingEntity entity, ArmorMaterial.@NotNull Layer layer, int layerIdx, int fallbackColor) {
                return stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor();
            }
        });
    }
}