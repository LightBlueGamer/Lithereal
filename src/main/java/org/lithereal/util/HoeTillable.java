package org.lithereal.util;

//? fabric {
/*import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
*///?}
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
//? neoforge {
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent;
//?}

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record HoeTillable(Supplier<Block> block, Predicate<UseOnContext> predicate, Consumer<UseOnContext> action, Function<UseOnContext, BlockState> finalState) {
    public HoeTillable(Supplier<Block> block, Supplier<BlockState> toBecome, Predicate<UseOnContext> predicate, Consumer<UseOnContext> action) {
        this(block, predicate, action, _ -> toBecome.get());
    }
    public void registerSelf() {
        //? fabric {
        /*TillableBlockRegistry.register(block.get(), predicate, action);
        *///?}
        //? neoforge {
        NeoForge.EVENT_BUS.<BlockEvent.BlockToolModificationEvent>addListener(event -> {
            UseOnContext context = event.getContext();
            if (ItemAbilities.HOE_TILL == event.getItemAbility() && context.getItemInHand().canPerformAction(ItemAbilities.HOE_TILL)
                    && event.getState().is(block.get()) && predicate.test(context)) {
                if (!event.isSimulated()) {
                    action.accept(context);
                }

                event.setFinalState(finalState.apply(context));
            }
        });
        //?}
    }

    public static Consumer<UseOnContext> changeIntoState(Supplier<BlockState> resultState) {
        return (context) -> {
            BlockState state = resultState.get();
            context.getLevel().setBlock(context.getClickedPos(), state, 11);
            context.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, context.getClickedPos(), GameEvent.Context.of(context.getPlayer(), state));
        };
    }

    public static Consumer<UseOnContext> changeIntoStateAndDropItem(Supplier<BlockState> resultState, ItemLike item) {
        return (context) -> {
            BlockState state = resultState.get();
            context.getLevel().setBlock(context.getClickedPos(), state, 11);
            context.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, context.getClickedPos(), GameEvent.Context.of(context.getPlayer(), state));
            Block.popResourceFromFace(context.getLevel(), context.getClickedPos(), context.getClickedFace(), new ItemStack(item));
        };
    }

    public static boolean onlyIfAirAbove(UseOnContext context) {
        return context.getClickedFace() != Direction.DOWN && context.getLevel().getBlockState(context.getClickedPos().above()).isAir();
    }
}
