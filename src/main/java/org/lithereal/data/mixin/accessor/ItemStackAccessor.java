package org.lithereal.data.mixin.accessor;

//? neoforge {
import net.minecraft.world.InteractionResult;
//?}
import net.minecraft.world.item.ItemStack;
//? neoforge {
import net.minecraft.world.item.context.UseOnContext;
//?}
import org.spongepowered.asm.mixin.Mixin;
//? neoforge {
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Function;
//?}

@Mixin(ItemStack.class)
public interface ItemStackAccessor {
    //? neoforge {
    @Invoker
    InteractionResult callOnItemUse(UseOnContext context, Function<UseOnContext, InteractionResult> callback);
    //?}
}