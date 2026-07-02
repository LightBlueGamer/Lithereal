package org.lithereal.data.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentHolder;
//? fabric {
/*import net.minecraft.core.component.DataComponentType;
*///?}
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
//? fabric {
/*import net.minecraft.world.item.component.TooltipProvider;
*///?}
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import org.lithereal.core.component.ModComponents;
import org.lithereal.core.component.MultiMiner;
import org.lithereal.core.component.SpecialAbility;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
//? fabric {
/*import org.spongepowered.asm.mixin.Shadow;
*///?}
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder {
    //? fabric {
    /*@Shadow
    public abstract <T extends TooltipProvider> void addToTooltip(DataComponentType<T> type, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag);
    *///?}
    @Inject(method = "addDetailsToTooltip", slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/core/component/DataComponents;FIREWORK_EXPLOSION:Lnet/minecraft/core/component/DataComponentType;", opcode = Opcodes.GETSTATIC), to = @At(value = "FIELD", target = "Lnet/minecraft/core/component/DataComponents;JUKEBOX_PLAYABLE:Lnet/minecraft/core/component/DataComponentType;", opcode = Opcodes.GETSTATIC)), at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V", ordinal = 1))
    private void addTooltipsForCustomComponents(Item.TooltipContext context, TooltipDisplay display, @Nullable Player player, TooltipFlag tooltipFlag, Consumer<Component> builder, CallbackInfo ci) {
        this.addToTooltip(ModComponents.MULTI_MINER.get(), context, display, builder, tooltipFlag);
        this.addToTooltip(ModComponents.SMELTER.get(), context, display, builder, tooltipFlag);
        this.addToTooltip(ModComponents.SPECIAL_ABILITY.get(), context, display, builder, tooltipFlag);
    }
    @Inject(method = "mineBlock", at = @At("HEAD"))
    private void onMineBlock(Level level, BlockState state, BlockPos pos, Player owner, CallbackInfo ci) {
        MultiMiner multiMiner = get(ModComponents.MULTI_MINER.get());
        if (multiMiner != null)
            multiMiner.triggerOnMine(ItemStack.class.cast(this), level, state, pos, owner);
    }
    @Inject(method = "hurtEnemy", at = @At("HEAD"))
    private void onHurtEnemy(LivingEntity mob, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
        SpecialAbility specialAbility = get(ModComponents.SPECIAL_ABILITY.get());
        if (specialAbility != null)
            specialAbility.onAttack(ItemStack.class.cast(this), mob, attacker);
    }
    @Inject(method = "postHurtEnemy", at = @At("HEAD"))
    private void afterHurtEnemy(LivingEntity mob, LivingEntity attacker, CallbackInfo ci) {
        SpecialAbility specialAbility = get(ModComponents.SPECIAL_ABILITY.get());
        if (specialAbility != null)
            specialAbility.postAttack(ItemStack.class.cast(this), mob, attacker);
    }
    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void onInventoryTick(Level level, Entity owner, EquipmentSlot slot, CallbackInfo ci) {
        SpecialAbility specialAbility = get(ModComponents.SPECIAL_ABILITY.get());
        if (specialAbility != null) {
            specialAbility.onItemTick(ItemStack.class.cast(this), level, owner, slot);
            specialAbility.onArmourTick(ItemStack.class.cast(this), level, owner, slot);
        }
    }
}
