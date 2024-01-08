package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.screen.FireCrucibleMenu;

public class FireCrucibleBlockEntity extends BlockEntity implements MenuProvider {
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 200;
    protected int heatLevel = 0;
    protected int fuelLevel = 0;
    protected int maxFuel = 0;
    protected int hasBucket = 0;

    public FireCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(LitherealExpectPlatform.getFireCrucibleBlockEntity(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FireCrucibleBlockEntity.this.progress;
                    case 1 -> FireCrucibleBlockEntity.this.maxProgress;
                    case 2 -> FireCrucibleBlockEntity.this.heatLevel;
                    case 3 -> FireCrucibleBlockEntity.this.fuelLevel;
                    case 4 -> FireCrucibleBlockEntity.this.maxFuel;
                    case 5 -> FireCrucibleBlockEntity.this.hasBucket;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FireCrucibleBlockEntity.this.progress = value;
                    case 1 -> FireCrucibleBlockEntity.this.maxProgress = value;
                    case 2 -> FireCrucibleBlockEntity.this.heatLevel = value;
                    case 3 -> FireCrucibleBlockEntity.this.fuelLevel = value;
                    case 4 -> FireCrucibleBlockEntity.this.maxFuel = value;
                    case 5 -> FireCrucibleBlockEntity.this.hasBucket = value;
                }
            }

            @Override
            public int getCount() {
                return 6;
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

    private void setHasBucket(int amount) {
        this.hasBucket = amount;
    }

    protected void setBucket(Container inventory) {
        if(hasBucket(inventory)) {
            setHasBucket(1);
        } else setHasBucket(0);
    }

    public boolean hasBucket(Container inventory) {
        return inventory.getItem(3).is(Items.BUCKET);
    }

    protected static boolean canInsertItemIntoOutput(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(2).getItem() == itemStack.getItem() || inventory.getItem(2).isEmpty();
    }

    protected static boolean canInsertAmountIntoOutput(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    protected static int getFuelLevel(FireCrucibleBlockEntity entity) {
        return entity.fuelLevel;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FireCrucibleMenu(i, inventory, this, this.data);
    }
}
