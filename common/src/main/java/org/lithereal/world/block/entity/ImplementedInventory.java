package org.lithereal.world.block.entity;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

@FunctionalInterface
public interface ImplementedInventory extends WorldlyContainer {
    default NonNullList<ItemStack> getItems() {
        return getOrSet().get();
    }
    default void setItems(NonNullList<ItemStack> items) {
        getOrSet().set(items);
    }
    GetterAndSetter getOrSet();

    static ImplementedInventory of(NonNullList<ItemStack> items) {
        return new ImplementedInventory() {
            public NonNullList<ItemStack> inventory = items;
            @Override
            public GetterAndSetter getOrSet() {
                return new GetterAndSetter(() -> inventory, itemStacks -> inventory = itemStacks);
            }
        };
    }

    static ImplementedInventory ofSize(int size) {
        return of(NonNullList.withSize(size, ItemStack.EMPTY));
    }

    @Override
    default int @NotNull [] getSlotsForFace(Direction side) {
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override
    default boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
        return true;
    }

    @Override
    default boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return true;
    }

    @Override
    default int getContainerSize() {
        return getItems().size();
    }

    @Override
    default boolean isEmpty() {
        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    default @NotNull ItemStack getItem(int slot) {
        return getItems().get(slot);
    }

    @Override
    default @NotNull ItemStack removeItem(int slot, int count) {
        ItemStack result = ContainerHelper.removeItem(getItems(), slot, count);
        if (!result.isEmpty()) {
            setChanged();
        }

        return result;
    }

    @Override
    default @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(getItems(), slot);
    }

    @Override
    default void setItem(int slot, ItemStack stack) {
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        getItems().set(slot, stack);
        setChanged();
    }

    @Override
    default void clearContent() {
        getItems().clear();
    }

    @Override
    default void setChanged() {
        // Override if you want behavior.
    }

    @Override
    default boolean stillValid(Player player) {
        return true;
    }

    default void saveItems(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        ContainerHelper.saveAllItems(nbt, getItems(), provider);
    }

    default void loadItems(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        NonNullList<ItemStack> inventory = getItems();
        ContainerHelper.loadAllItems(nbt, inventory, provider);
        setItems(inventory);
    }
    record GetterAndSetter(Supplier<NonNullList<ItemStack>> getter, Consumer<NonNullList<ItemStack>> setter) {
        public NonNullList<ItemStack> get() {
            return getter.get();
        }
        public void set(NonNullList<ItemStack> stacks) {
            setter.accept(stacks);
        }
    }
}
