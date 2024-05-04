package org.lithereal.neoforge.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.FireCrucibleBlockEntity;
import org.lithereal.neoforge.screen.ForgeFireCrucibleMenu;

import java.util.concurrent.atomic.AtomicInteger;

public class ForgeFireCrucibleBlockEntity extends FireCrucibleBlockEntity implements NeoForgeInventory {
    private final ImplementedItemHandler itemHandler = new ImplementedItemHandler(4, this);
    public ForgeFireCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ForgeFireCrucibleMenu(i, inventory, this);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return itemHandler.getStacks();
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        AtomicInteger index = new AtomicInteger();
        items.forEach(itemStack -> {
            if (index.get() > getHandler().getSlots())
                // Die
                throw new IllegalStateException();
            itemHandler.setStackInSlot(index.getAndIncrement(), itemStack);
        });
    }

    @Override
    public ImplementedItemHandler getHandler() {
        return itemHandler;
    }

    @Override
    public void saveItems(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        nbt.put("Items", getHandler().serializeNBT(provider));
    }

    @Override
    public void loadItems(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        itemHandler.deserializeNBT(provider, nbt.getCompound("Items"));
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return itemHandler.setStackInSlotNoUpdate(slot, ItemStack.EMPTY);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int count) {
        return itemHandler.extractItem(slot, count, false);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        itemHandler.setStackInSlot(slot, stack);
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public void clearContent() {
        for (int index = 0; index < itemHandler.getSlots(); index++) {
            removeItemNoUpdate(index);
        }
    }

    @Override
    public int getContainerSize() {
        return itemHandler.getSlots();
    }
}
