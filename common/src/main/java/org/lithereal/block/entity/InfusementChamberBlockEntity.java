package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.ModBlocks;
import org.lithereal.screen.InfusementChamberMenu;

public abstract class InfusementChamberBlockEntity extends BlockEntity implements MenuProvider {
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 6000;
    protected int powerLevel = 0;
    protected float power = 1.0f;
    protected float successRate = 0.2f;
    protected int usedPotions = 0;

    public abstract PotionContents getStoredPotion();
    public abstract ItemStack getStoredItem();

    public InfusementChamberBlockEntity(BlockPos pos, BlockState state) {
        super(LitherealExpectPlatform.getInfusementChamberBlockEntity(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> InfusementChamberBlockEntity.this.progress;
                    case 1 -> InfusementChamberBlockEntity.this.maxProgress;
                    case 2 -> InfusementChamberBlockEntity.this.powerLevel;
                    case 3 -> InfusementChamberBlockEntity.this.usedPotions;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> InfusementChamberBlockEntity.this.progress = value;
                    case 1 -> InfusementChamberBlockEntity.this.maxProgress = value;
                    case 2 -> InfusementChamberBlockEntity.this.powerLevel = value;
                    case 3 -> InfusementChamberBlockEntity.this.usedPotions = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
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

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new InfusementChamberMenu(i, inventory, this, this.data);
    }

    public void setEmpowerments() {
        this.power = 1.0f;
        this.successRate = 0.2f;
        int frozenBlocks = 0;
        int burningBlocks = 0;
        int chargedBlocks = 0;
        for (int i = 0; i < Direction.values().length; i++) {
            BlockPos adjacentPos = this.getBlockPos().offset(Direction.values()[i].getNormal());
            BlockState blockState = this.level.getBlockState(adjacentPos);
            if(blockState.getBlock() == ModBlocks.FROZEN_LITHERITE_BLOCK.get()) {
                this.power -= 0.1f;
                this.successRate += 0.15f;
                frozenBlocks++;
            } else if(blockState.getBlock() == ModBlocks.BURNING_LITHERITE_BLOCK.get()) {
                this.power += 0.15f;
                this.successRate -= 0.1f;
                burningBlocks++;
            } else if(blockState.getBlock() == ModBlocks.CHARGED_LITHERITE_BLOCK.get()) {
                this.power += 0.2f;
                this.successRate += 0.2f;
                chargedBlocks++;
            }

            if(frozenBlocks > 0 || burningBlocks > 0 || chargedBlocks > 0) {
                if (frozenBlocks > burningBlocks && frozenBlocks > chargedBlocks) {
                    this.powerLevel = 1;
                } else if (burningBlocks > frozenBlocks && burningBlocks > chargedBlocks) {
                    this.powerLevel = 2;
                } else if (chargedBlocks > frozenBlocks && chargedBlocks > burningBlocks) {
                    this.powerLevel = 3;
                }
            } else this.powerLevel = 0;
        }

    }

}
