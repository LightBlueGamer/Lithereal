package org.lithereal.core.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.HolderSet;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.List;
import java.util.function.Consumer;

public record Smelter(HolderSet<Item> neverProcess) implements TooltipProvider {
    public static final Smelter DEFAULT = new Smelter(HolderSet.direct());
    public static final Codec<Smelter> CODEC = RegistryCodecs.homogeneousList(Registries.ITEM).xmap(Smelter::new, Smelter::neverProcess);
    public List<ItemStack> processDrops(List<ItemStack> drops, ServerLevel level) {
        List<ItemStack> smeltedDrops = NonNullList.create();
        drops.forEach(e -> {
            if (e.is(this.neverProcess())) {
                smeltedDrops.add(e);
                return;
            }
            SingleRecipeInput furnaceInput = new SingleRecipeInput(e);
            RecipeHolder<SmeltingRecipe> furnaceRecipe = level.recipeAccess()
                    .getRecipeFor(RecipeType.SMELTING, furnaceInput, level).orElse(null);
            if(furnaceRecipe != null) smeltedDrops.add(new ItemStack(furnaceRecipe.value().assemble(furnaceInput).getItem(), e.getCount()));
            else smeltedDrops.add(e);
        });
        return smeltedDrops;
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        consumer.accept(Component.translatable("tooltip.smelter"));
    }
}
