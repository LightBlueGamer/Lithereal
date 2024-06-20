package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.client.gui.screens.inventory.FireCrucibleMenu;

public class ElectricCrucibleBlockEntity extends BlockEntity implements MenuProvider {
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 200;
    protected int heatLevel = 0;
    protected int fuelLevel = 0;
    protected int maxFuel = 0;


    public ElectricCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(LitherealExpectPlatform.getFireCrucibleBlockEntity(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ElectricCrucibleBlockEntity.this.progress;
                    case 1 -> ElectricCrucibleBlockEntity.this.maxProgress;
                    case 2 -> ElectricCrucibleBlockEntity.this.heatLevel;
                    case 3 -> ElectricCrucibleBlockEntity.this.fuelLevel;
                    case 4 -> ElectricCrucibleBlockEntity.this.maxFuel;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ElectricCrucibleBlockEntity.this.progress = value;
                    case 1 -> ElectricCrucibleBlockEntity.this.maxProgress = value;
                    case 2 -> ElectricCrucibleBlockEntity.this.heatLevel = value;
                    case 3 -> ElectricCrucibleBlockEntity.this.fuelLevel = value;
                    case 4 -> ElectricCrucibleBlockEntity.this.maxFuel = value;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fire Crucible");
    }

    protected void resetProgress() {
        this.progress = 0;
    }

    private void setFuelLevel(int amount) {
        this.fuelLevel = amount;
    }

    private void setHeatLevel(int amount) {
        this.heatLevel = amount;
    }

    protected static boolean canInsertItemIntoOutput(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(2).getItem() == itemStack.getItem() || inventory.getItem(2).isEmpty();
    }

    protected static boolean canInsertAmountIntoOutput(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    protected static int getFuelLevel(ElectricCrucibleBlockEntity entity) {
        return entity.fuelLevel;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FireCrucibleMenu(i, inventory, this);
    }
}
