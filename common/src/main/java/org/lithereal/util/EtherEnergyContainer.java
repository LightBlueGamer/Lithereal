package org.lithereal.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lithereal.block.entity.IEnergyContainerProvider;

import java.util.Arrays;

public class EtherEnergyContainer {
    public int energy;
    public int maxEnergy;
    public int transferRate;

    public EtherEnergyContainer(int energy, int maxEnergy, int transferRate) {
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.transferRate = transferRate;
    }

    public void transferEnergy(EtherEnergyContainer energyContainer, int toTransfer) {
        if(energyContainer.maxEnergy < energyContainer.energy + toTransfer) toTransfer = maxEnergy - energy;
        energyContainer.energy += toTransfer;
        this.energy -= toTransfer;
    }

    public void transferEnergy(BlockEntity pEntity) {
        int validConnections = getConnections(pEntity);

        int dividedTransferRate = validConnections > 0 ? this.transferRate / validConnections : 0;

        if (dividedTransferRate > 0) {
            Arrays.stream(Direction.values())
                    .map(direction -> pEntity.getLevel().getBlockEntity(pEntity.getBlockPos().relative(direction)))
                    .filter(blockEntity -> blockEntity instanceof IEnergyContainerProvider)
                    .forEach(blockEntity -> {
                        IEnergyContainerProvider provider = (IEnergyContainerProvider) blockEntity;
                        this.transferEnergy(provider.getEnergyContainer(), dividedTransferRate);
                    });
        }
    }

    public int getConnections(BlockEntity pEntity) {
        return (int) Arrays.stream(Direction.values())
                .map(direction -> pEntity.getLevel().getBlockEntity(pEntity.getBlockPos().relative(direction)))
                .filter(blockEntity -> blockEntity instanceof IEnergyContainerProvider)
                .count();
    }
}
