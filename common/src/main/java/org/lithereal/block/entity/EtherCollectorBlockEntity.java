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
import org.lithereal.client.gui.screens.inventory.EtherCollectorMenu;
import org.lithereal.util.ether.EtherEnergyContainer;
import org.lithereal.util.ether.IEnergyUserProvider;

public class EtherCollectorBlockEntity extends BlockEntity implements MenuProvider, IEnergyUserProvider {
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 200;

    private final EtherEnergyContainer energyContainer = new EtherEnergyContainer(0, 5000, 20);

    @Override
    public EtherEnergyContainer getEnergyUser() {
        return energyContainer;
    }

    @Override
    public TransferMode getTransferModeForDirection(Direction direction) {
        return TransferMode.BOTH;
    }

    public EtherCollectorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(LitherealExpectPlatform.getEtherCollectorBlockEntity(), blockPos, blockState);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> EtherCollectorBlockEntity.this.progress;
                    case 1 -> EtherCollectorBlockEntity.this.maxProgress;
                    case 2 -> EtherCollectorBlockEntity.this.energyContainer.energy;
                    case 3 -> EtherCollectorBlockEntity.this.energyContainer.maxEnergy;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> EtherCollectorBlockEntity.this.progress = value;
                    case 1 -> EtherCollectorBlockEntity.this.maxProgress = value;
                    case 2 -> EtherCollectorBlockEntity.this.energyContainer.energy = value;
                    case 3 -> EtherCollectorBlockEntity.this.energyContainer.maxEnergy = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public <B extends BlockEntity & IEnergyUserProvider> B asBlockEntity() {
        return (B) this;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Ether Collector" + this.energyContainer.energy + "/" + this.energyContainer.maxEnergy);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new EtherCollectorMenu(i, inventory, this, this.data);
    }
}
