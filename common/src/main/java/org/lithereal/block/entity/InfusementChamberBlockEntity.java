package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.ModBlocks;
import org.lithereal.screen.FreezingStationMenu;
import org.lithereal.screen.InfusementChamberMenu;

import java.util.List;

public class InfusementChamberBlockEntity extends BlockEntity implements MenuProvider {
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 6000;
    protected float power = 1.0f;
    protected float successRate = 0.5f;

    public InfusementChamberBlockEntity(BlockPos pos, BlockState state) {
        super(LitherealExpectPlatform.getInfusementChamberBlockEntity(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> InfusementChamberBlockEntity.this.progress;
                    case 1 -> InfusementChamberBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> InfusementChamberBlockEntity.this.progress = value;
                    case 1 -> InfusementChamberBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Infusement Chamber");
    }

    protected void resetProgress() {
        this.progress = 0;
    }

    protected static boolean canInsertItemIntoOutput(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(2).getItem() == itemStack.getItem() || inventory.getItem(2).isEmpty();
    }

    protected static boolean canInsertAmountIntoOutput(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new InfusementChamberMenu(i, inventory, this, this.data);
    }

    public void setEmpowerments() {
        this.power = 1.0f;
        this.successRate = 0.2f;
        for (int i = 0; i < Direction.values().length; i++) {
            BlockPos adjacentPos = this.getBlockPos().offset(Direction.values()[i].getNormal());
            BlockState blockState = this.level.getBlockState(adjacentPos);
            if(blockState.getBlock() == ModBlocks.FROZEN_LITHERITE_BLOCK.get()) {
                this.power -= 0.1f;
                this.successRate += 0.15f;
            } else if(blockState.getBlock() == ModBlocks.BURNING_LITHERITE_BLOCK.get()) {
                this.power += 0.15f;
                this.successRate -= 0.1f;
            }
        }
    }

}
