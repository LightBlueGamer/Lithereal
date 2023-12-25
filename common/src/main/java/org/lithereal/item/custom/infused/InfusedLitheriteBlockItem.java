package org.lithereal.item.custom.infused;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import org.lithereal.client.renderer.InfusedLitheriteBlockEntityModel;

public class InfusedLitheriteBlockItem extends BlockItem {
    public InfusedLitheriteBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
    }

    public String getDescriptionId(ItemStack p_43364_) {
        return PotionUtils.getPotion(p_43364_).getName(this.getDescriptionId() + ".effect.");
    }

    public @Nullable String getCreatorModId(ItemStack itemStack) {
        Potion potion = PotionUtils.getPotion(itemStack);
        ResourceLocation resourceLocation = BuiltInRegistries.POTION.getKey(potion);

        return resourceLocation.getNamespace();
    }
}
