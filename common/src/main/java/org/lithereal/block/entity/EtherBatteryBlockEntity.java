package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import org.lithereal.client.gui.screens.inventory.EtherBatteryMenu;
import org.lithereal.util.ether.EtherEnergyContainer;
import org.lithereal.util.ether.IEnergyUser;
import org.lithereal.util.ether.IEnergyUserProvider;

public class EtherBatteryBlockEntity extends BlockEntity implements MenuProvider, IEnergyUserProvider {
    protected final ContainerData data;

    private final EtherEnergyContainer energyContainer = new EtherEnergyContainer(0, 100000, 100);

    @Override
    public IEnergyUser getEnergyUser() {
        return energyContainer;
    }

    @Override
    public TransferMode getTransferModeForDirection(Direction direction) {
        return TransferMode.BOTH;
    }

    public EtherBatteryBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(LitherealExpectPlatform.getEtherBatteryBlockEntity(), blockPos, blockState);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 1 -> EtherBatteryBlockEntity.this.energyContainer.energy;
                    case 2 -> EtherBatteryBlockEntity.this.energyContainer.maxEnergy;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 1 -> EtherBatteryBlockEntity.this.energyContainer.energy = value;
                    case 2 -> EtherBatteryBlockEntity.this.energyContainer.maxEnergy = value;
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
        return Component.literal("Ether Battery" + this.energyContainer.energy + "/" + this.energyContainer.maxEnergy);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new EtherBatteryMenu(i, inventory, this, this.data);
    }
}
