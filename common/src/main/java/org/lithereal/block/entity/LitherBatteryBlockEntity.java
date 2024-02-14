package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.screen.LitherBatteryMenu;
import org.lithereal.screen.LitherCollectorMenu;
import org.lithereal.util.LitherEnergyContainer;

public class LitherBatteryBlockEntity extends BlockEntity implements MenuProvider, IEnergyContainerProvider {
    protected final ContainerData data;

    private final LitherEnergyContainer ENERGY_CONTAINER = new LitherEnergyContainer(0, 100000, 100);

    @Override
    public LitherEnergyContainer getEnergyContainer() {
        return ENERGY_CONTAINER;
    }

    public LitherBatteryBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(LitherealExpectPlatform.getLitherBatteryBlockEntity(), blockPos, blockState);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 1 -> LitherBatteryBlockEntity.this.ENERGY_CONTAINER.energy;
                    case 2 -> LitherBatteryBlockEntity.this.ENERGY_CONTAINER.maxEnergy;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 1 -> LitherBatteryBlockEntity.this.ENERGY_CONTAINER.energy = value;
                    case 2 -> LitherBatteryBlockEntity.this.ENERGY_CONTAINER.maxEnergy = value;
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
        return Component.literal("Lither Battery");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new LitherBatteryMenu(i, inventory, this, this.data);
    }
}
